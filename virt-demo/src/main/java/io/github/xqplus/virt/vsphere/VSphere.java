package io.github.xqplus.virt.vsphere;

import com.alibaba.fastjson.JSON;
import com.vmware.vim25.*;
import com.vmware.vim25.mo.*;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * VMware vSphere 平台对象信息查询器
 *
 * @author chenq
 * @since 1.0.0
 */
public class VSphere {
    public static final Logger logger = LoggerFactory.getLogger(VSphere.class);

    private final URL url;
    private final String username;
    private final String password;
    private final ServiceInstance serviceInstance;
    // 原始 ServerConnection 对象
    private ServerConnection serverConnection;
    private final InventoryNavigator inventoryNavigator;

    public VSphere(String ip, String username, String password) throws IOException, ReflectiveOperationException {
        url = new URL("https://" + ip + "/sdk");
        this.username = username;
        this.password = password;
        serviceInstance = new ServiceInstance(url, username, password, true, 30 * 1000, 30 * 1000);
        serverConnection = serviceInstance.getServerConnection();
        // 利用反射将 ServiceInstance 中 ServiceConnection 替换为代理子类
        ServerConnection serverConnectionProxy = new ServerConnectionProxyFactory().getProxy();
        Field serverConnectionField = ServiceInstance.class.getSuperclass().getDeclaredField("serverConnection");
        serverConnectionField.setAccessible(true);
        serverConnectionField.set(this.serviceInstance, serverConnectionProxy);
        // 这之后从 ServiceInstance 生成的所有管理对象 ServerConnection 都会被代理
        inventoryNavigator = new InventoryNavigator(serviceInstance.getRootFolder());
    }

    private class ServerConnectionProxyFactory implements MethodInterceptor {

        public ServerConnection getProxy() {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(ServerConnection.class);
            enhancer.setCallback(this);
            Class<?>[] argTypes = new Class[]{URL.class, VimPortType.class, ServiceInstance.class};
            Object[] args = new Object[]{serverConnection.getUrl(), serverConnection.getVimService(), serviceInstance};
            return (ServerConnection) enhancer.create(argTypes, args);
        }

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            // 只拦截 getVimService 方法，因为几乎所有请求都需要先获取执行器
            if ("getVimService".equals(method.getName())) {
                logger.debug("Intercepted ServerConnection method: {}", method.getName());
                checkSessionValidAndReLogin();
            }
            return proxy.invokeSuper(obj, args);
        }
    }

    private void checkSessionValidAndReLogin() throws IOException, ReflectiveOperationException {
        try {
            // 利用原始 ServerConnection 进行连接检查（防止 getVimService 递归），这里的调用无意义，只是为了检查会话是否有效
            serverConnection.getVimService().currentTime(serviceInstance.getMOR());
        } catch (NotAuthenticated e) {
            logger.info("The session is not authenticated, retry login");
            // 重新登录，然后用新的 VimPortType 执行器替换 ServerConnection 代理子类中的执行器
            ServiceInstance newServiceInstance = new ServiceInstance(url, username, password, true, 30 * 1000, 30 * 1000);
            VimPortType newVimService = newServiceInstance.getServerConnection().getVimService();
            Field vimServiceField = ServerConnection.class.getDeclaredField("vimService");
            vimServiceField.setAccessible(true);
            vimServiceField.set(this.serviceInstance.getServerConnection(), newVimService);
            // 原始 ServerConnection 也要更新
            serverConnection = newServiceInstance.getServerConnection();
        }
    }

    public void logout() {
        serviceInstance.getServerConnection().logout();
    }

    public String getVersion() {
        return serviceInstance.getAboutInfo().getVersion();
    }

    public Folder getRootFolder() {
        return serviceInstance.getRootFolder();
    }

    public ServerConnection getServerConnection() {
        return serviceInstance.getServerConnection();
    }

    public boolean isHostAgent() {
        return "HostAgent".equals(serviceInstance.getAboutInfo().apiType);
    }

    /**
     * Get {@link HostSystem} list.
     */
    public List<HostSystem> getHostSystems() throws RemoteException {
        ManagedEntity[] mes = inventoryNavigator.searchManagedEntities("HostSystem");
        return Arrays.stream(mes)
                .map(i -> (HostSystem) i)
                .collect(Collectors.toList());
    }

    /**
     * Get {@link ResourcePool} list.
     */
    public List<ResourcePool> getResourcePools() throws RemoteException {
        ManagedEntity[] mes = inventoryNavigator.searchManagedEntities("ResourcePool");
        return Arrays.stream(mes).map(i -> (ResourcePool) i).collect(Collectors.toList());
    }

    /**
     * Get {@link ResourcePool} by name.
     *
     * @deprecated 存在同名问题
     */
    public ResourcePool getResourcePool(String name) throws RemoteException {
        ManagedEntity me = inventoryNavigator.searchManagedEntity("ResourcePool", name);
        return (ResourcePool) me;
    }

    /**
     * 通过实例ID获取资源池对象
     *
     * @param val 资源池MorVal
     * @return {@link ResourcePool}
     */
    public ResourcePool getResourcePoolByVal(String val) throws RemoteException {
        ResourcePool rp = getResourcePools().stream().filter(i -> val.equals(i.getMOR().val)).findFirst().orElse(null);
        if (rp == null) {
            throw new RemoteException("Resource pool by MorVal [" + val + "] not found.");
        }
        return rp;
    }

    /**
     * Get {@link Datastore} list from host.
     */
    public List<Datastore> getDatastores(String host) throws RemoteException {
        HostSystem hs = findHostSystem(host);
        return Arrays.asList(hs.getDatastores());
    }

    @Nullable
    public Datastore findDatastore(HostSystem hostSystem, String name) throws RemoteException {
        return Arrays.stream(hostSystem.getDatastores())
                .filter(datastore -> datastore.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public Datastore getDatastore(String datastoreName) throws RemoteException {
        return (Datastore) inventoryNavigator.searchManagedEntity("Datastore", datastoreName);
    }

    /**
     * Get {@link Network} list from host.
     */
    public List<Network> getNetworks(String host) throws RemoteException {
        HostSystem hs = findHostSystem(host);
        return Arrays.asList(hs.getNetworks());
    }

    /**
     * Get {@link Network} from host by name.
     */
    public Network getNetwork(String name, String host) throws RemoteException {
        HostSystem hs = findHostSystem(host);
        return getNetwork(name, hs);
    }

    public Network getNetwork(String name, HostSystem hs) throws RemoteException {
        if (hs == null) {
            return null;
        }
        return Arrays.stream(hs.getNetworks())
                .filter(i -> i.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public void removeVirtualMachineSnapshot(VirtualMachineSnapshot snapshot) throws RemoteException, InterruptedException {
        processTask(snapshot.removeSnapshot_Task(false));
    }

    public void revertVirtualMachineSnapshot(VirtualMachineSnapshot vmSnapshot) throws RemoteException, InterruptedException {
        processTask(vmSnapshot.revertToSnapshot_Task(null));
    }

    public Datastore createNfsDatastore(HostSystem hostSystem, String datastoreName, String remoteHost, String remotePath) throws RemoteException {
        HostNasVolumeSpec nasVolumeSpec = new HostNasVolumeSpec();
        nasVolumeSpec.remoteHost = remoteHost;
        nasVolumeSpec.remotePath = remotePath;
        nasVolumeSpec.localPath = datastoreName;
        nasVolumeSpec.accessMode = HostMountMode.readWrite.toString();
        return hostSystem.getHostDatastoreSystem().createNasDatastore(nasVolumeSpec);
    }

    /**
     * 由于在某些环境情况下（未重现）vm-id 会发生变化，所以在此基础上用 uuid 辅助查询虚拟机
     *
     * @param vmId vm-id
     * @param uuid 虚拟机 UUID（TODO 初步验证唯一，但需要更多的用例来证明完全唯一性）
     * @return 虚拟机实例 {@link VirtualMachine}
     */
    @Nullable
    public VirtualMachine findVirtualMachine(String vmId, String uuid) throws RemoteException {
        return getAllVm().stream()
                // 这里要跳过config为null的虚拟机
                .filter(vm -> vm.getMOR().val.equals(vmId) || (vm.getConfig() != null && vm.getConfig().uuid.equals(uuid)))
                .findFirst().orElse(null);
    }

    @Nullable
    public VirtualMachine findVirtualMachine(HostSystem hostSystem, String vmId, String uuid) throws RemoteException {
        return Arrays.stream(hostSystem.getVms())
                // 这里要跳过config为null的虚拟机
                .filter(vm -> vm.getMOR().val.equals(vmId) || (vm.getConfig() != null && vm.getConfig().uuid.equals(uuid)))
                .findFirst().orElse(null);
    }

    public List<VirtualMachine> getAllVm() throws RemoteException {
        ManagedEntity[] managedEntities = inventoryNavigator.searchManagedEntities("VirtualMachine");
        return Arrays.stream(managedEntities)
                .map(managedEntity -> (VirtualMachine) managedEntity)
                .collect(Collectors.toList());
    }

    @Nullable
    public VirtualMachineSnapshot createVirtualMachineSnapshot(VirtualMachine vm, String name) throws RemoteException, InterruptedException {
        Task task = vm.createSnapshot_Task(name, null, false, false);
        processTask(task);
        return new VirtualMachineSnapshot(vm.getServerConnection(), (ManagedObjectReference) task.getTaskInfo().result);
    }

    @Nullable
    public VirtualMachineSnapshot findVirtualMachineSnapshot(VirtualMachine vm, String name) {
        if (vm.getSnapshot() != null) {
            ManagedObjectReference snapshotMor = findVirtualMachineSnapshotMor(vm.getSnapshot().rootSnapshotList, name);
            return snapshotMor != null ? new VirtualMachineSnapshot(vm.getServerConnection(), snapshotMor) : null;
        }
        return null;
    }

    private ManagedObjectReference findVirtualMachineSnapshotMor(VirtualMachineSnapshotTree[] snapshotTrees, String name) {
        if (snapshotTrees != null && snapshotTrees.length > 0) {
            for (VirtualMachineSnapshotTree snapshotTree : snapshotTrees) {
                if (snapshotTree.name.equals(name)) {
                    return snapshotTree.snapshot;
                }
                ManagedObjectReference snapshotMor = findVirtualMachineSnapshotMor(snapshotTree.childSnapshotList, name);
                if (snapshotMor != null) {
                    return snapshotMor;
                }
            }
        }
        return null;
    }

    @Nullable
    public HostSystem findHostSystem(String name) throws RemoteException {
        if (isHostAgent()) {
            return getHostSystems().get(0);
        }
        return (HostSystem) inventoryNavigator.searchManagedEntity("HostSystem", name);
    }

    public void powerOnVirtualMachine(VirtualMachine vm, HostSystem hostSystem) throws RemoteException, InterruptedException {
        processTask(vm.powerOnVM_Task(hostSystem));
    }

    private Task processTask(Task task) throws RemoteException, InterruptedException {
        if (!Task.SUCCESS.equals(task.waitForTask())) {
            throw new RemoteException(task.getTaskInfo().error.localizedMessage);
        }
        return task;
    }
}
