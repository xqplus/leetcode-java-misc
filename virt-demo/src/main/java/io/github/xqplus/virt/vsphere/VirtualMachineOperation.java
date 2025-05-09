package io.github.xqplus.virt.vsphere;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.vmware.vim25.*;
import com.vmware.vim25.mo.*;
import org.springframework.lang.Nullable;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.*;

public class VirtualMachineOperation {

    private static InventoryNavigator inventoryNavigator;

    public static void main(String[] args) throws Exception {
        ServiceInstance serviceInstance = new ServiceInstance(new URL("https://192.168.8.12/sdk"),
                "administrator@vsphere.local", "Dsg_123456!", true, 20000, 20000);
//        ServiceInstance serviceInstance = new ServiceInstance(new URL("https://192.168.6.2/sdk"),
//                "root", "dsgcd_211208", true, 20 * 1000, 20 * 1000);
//        ServiceInstance serviceInstance = new ServiceInstance(new URL("https://192.168.6.247/sdk"),
//                "administrator@vsphere.local", "Dsg_123456!", true);
        inventoryNavigator = new InventoryNavigator(serviceInstance.getRootFolder());

        try {
            VirtualMachine vm = findVirtualMachine("redhat9.3");
            vm.createSnapshot_Task("s1", "", false, false);
        } finally {
            serviceInstance.getServerConnection().logout();
        }
    }

    private static void collectVbpSnapshotTree(List<VirtualMachineSnapshotTree> vbpSnapshotTrees,
                                               VirtualMachineSnapshotTree[] snapshotTrees) {
        if (snapshotTrees != null && snapshotTrees.length > 0) {
            for (VirtualMachineSnapshotTree snapshotTree : snapshotTrees) {
                if (snapshotTree.name.startsWith("VBP") || snapshotTree.name.startsWith("BDMP")) { // 以 VBP 开头则认为是我们平台打的快照
                    vbpSnapshotTrees.add(snapshotTree);
                }
                collectVbpSnapshotTree(vbpSnapshotTrees, snapshotTree.childSnapshotList);
            }
        }
    }

    @Nullable
    public static VirtualMachine findVirtualMachine(String vmId, String uuid) throws RemoteException {
        ManagedEntity[] managedEntities = inventoryNavigator.searchManagedEntities("VirtualMachine");
        Optional<VirtualMachine> optional = Arrays.stream(managedEntities)
                .map(managedEntity -> (VirtualMachine) managedEntity)
                .filter(vm -> vm.getMOR().val.equals(vmId) || vm.getConfig().uuid.equals(uuid))
                .findFirst();
        return optional.orElse(null);
    }

    private static String findSameNetSegmentIp(GuestNicInfo[] guestNicInfos, String gateway) {
        if (guestNicInfos != null && gateway != null) {
            for (GuestNicInfo guestNicInfo : guestNicInfos) {
                if (guestNicInfo.ipAddress != null) {
                    for (String ipAddress : guestNicInfo.ipAddress) {
                        if (isSameSegmentIpAddress(ipAddress, gateway)) {
                            return ipAddress;
                        }
                    }
                }
            }
        }
        return null;
    }

    private static boolean isSameSegmentIpAddress(String ipAddressA, String ipAddressB) {
        if (ipAddressA == null || ipAddressB == null) {
            return false;
        }
        int lastIndexOfA = ipAddressA.lastIndexOf(".");
        String segmentA = lastIndexOfA == -1 ? ipAddressA : ipAddressA.substring(0, lastIndexOfA);
        int lastIndexOfB = ipAddressB.lastIndexOf(".");
        String segmentB = lastIndexOfB == -1 ? ipAddressB : ipAddressB.substring(0, lastIndexOfB);
        return segmentA.equals(segmentB);
    }

    private static void testQueryChangedDiskAreas() throws RemoteException {
        VirtualMachine virtualMachine = findVirtualMachine("centos7-226");
//            VirtualMachineSnapshot s1 = findVirtualMachineSnapshot(virtualMachine, "test_1");
//            for (VirtualDevice device : s1.getConfig().hardware.device) {
//                if (device instanceof VirtualDisk) {
//                    System.out.println(device.key);
//                    VirtualDiskFlatVer2BackingInfo backingInfo = (VirtualDiskFlatVer2BackingInfo) device.backing;
//                    System.out.println(backingInfo.changeId);
//                    System.out.println(((VirtualDisk) device).capacityInBytes);
//                }
//            }

        String changeId = "52 ab f5 21 86 ad f2 ff-5b ef c0 f2 3b 53 eb 3b/78";
        long capacityBytes = 10737418240L;
        VirtualMachineSnapshot s2 = findVirtualMachineSnapshot(virtualMachine, "test_2");
        long offset = 0;
        do {
            DiskChangeInfo changeInfo = virtualMachine.queryChangedDiskAreas(s2, 2000, offset, changeId);
            if (offset == 0 && changeInfo.changedArea == null) { // This vmdk is not changed at all
                break;
            }
            System.out.println("= " + changeInfo.startOffset + ", " + changeInfo.length);
            for (DiskChangeExtent changeExtent : changeInfo.changedArea) {
                System.out.println(changeExtent.start + ", " + changeExtent.length);
            }
            System.out.println("===============================");
            offset = changeInfo.startOffset + changeInfo.length;
        } while (offset < capacityBytes);
    }

    private static void addCdrom(VirtualMachine vm) throws RemoteException {
        VirtualMachineConfigSpec vmConfigSpec = new VirtualMachineConfigSpec();

        VirtualCdrom cdrom = new VirtualCdrom();
        cdrom.key = 1;
        VirtualCdromIsoBackingInfo backingInfo = new VirtualCdromIsoBackingInfo();
        backingInfo.fileName = "[vsanDatastore] mkisofs.iso";
        cdrom.backing = backingInfo;
        cdrom.controllerKey = 15000;

        VirtualDeviceConfigSpec deviceConfigSpec = new VirtualDeviceConfigSpec();
        deviceConfigSpec.operation = VirtualDeviceConfigSpecOperation.add;
        deviceConfigSpec.device = cdrom;

        vmConfigSpec.deviceChange = new VirtualDeviceConfigSpec[]{deviceConfigSpec};
        vm.reconfigVM_Task(vmConfigSpec);
    }

    private static void createResourcePool(ResourcePool parent, String name) throws RemoteException {
        ResourceConfigSpec resourceConfigSpec = new ResourceConfigSpec();

        // cpu、内存按照界面创建的默认值填充
        ResourceAllocationInfo cpuResourceAllocation = createDefaultResourceAllocation(ResourceAllocationType.CPU);
        ResourceAllocationInfo memoryResourceAllocation = createDefaultResourceAllocation(ResourceAllocationType.MEMORY);

        resourceConfigSpec.cpuAllocation = cpuResourceAllocation;
        resourceConfigSpec.memoryAllocation = memoryResourceAllocation;
        parent.createResourcePool(name, resourceConfigSpec);
    }

    private static ResourceAllocationInfo createDefaultResourceAllocation(ResourceAllocationType type) {
        ResourceAllocationInfo resourceAllocation = new ResourceAllocationInfo();
        resourceAllocation.reservation = 0L; // 预留
        resourceAllocation.limit = -1L; // 限制，-1 表示不受限制
        resourceAllocation.expandableReservation = true; // 预留类型：可扩展
        SharesInfo sharesInfo = new SharesInfo();
        sharesInfo.level = SharesLevel.normal;
        sharesInfo.shares = type == ResourceAllocationType.CPU ? 4000 : 163840;
        resourceAllocation.shares = sharesInfo;
        return resourceAllocation;
    }

    private enum ResourceAllocationType {
        CPU, MEMORY;
    }

    public static VirtualMachine findVirtualMachine(String name) throws RemoteException {
        ManagedEntity managedEntity = inventoryNavigator.searchManagedEntity("VirtualMachine", name);
        return (VirtualMachine) managedEntity;
    }

    public static VirtualMachineSnapshot findVirtualMachineSnapshot(VirtualMachine virtualMachine, String name) {
        ManagedObjectReference snapshotMOR = findSnapshotMOR(virtualMachine.getSnapshot().rootSnapshotList, name);
        return new VirtualMachineSnapshot(virtualMachine.getServerConnection(), snapshotMOR);
    }

    public static ManagedObjectReference findSnapshotMOR(VirtualMachineSnapshotTree[] snapshotTrees, String name) {
        if (snapshotTrees != null && snapshotTrees.length > 0) {
            for (VirtualMachineSnapshotTree snapshotTree : snapshotTrees) {
                if (snapshotTree.name.equals(name)) {
                    return snapshotTree.snapshot;
                }
                ManagedObjectReference snapshotMOR = findSnapshotMOR(snapshotTree.childSnapshotList, name);
                if (snapshotMOR != null) {
                    return snapshotMOR;
                }
            }
        }
        return null;
    }

    public static HostSystem findHostSystem(String name) throws RemoteException {
        ManagedEntity managedEntity = inventoryNavigator.searchManagedEntity("HostSystem", name);
        return (HostSystem) managedEntity;
    }

    public static ResourcePool findResourcePool(String name) throws RemoteException {
        ManagedEntity managedEntity = inventoryNavigator.searchManagedEntity("ResourcePool", name);
        return (ResourcePool) managedEntity;
    }

//    public static void createVMByPublicConfig(Datacenter datacenter, ResourcePool resourcePool,
//                                              HostSystem hostSystem, Datastore datastore,
//                                              PublicVirtualMachineConfig config, String name) throws RemoteException, InterruptedException {
//        VirtualMachineConfigSpec configSpec = new VirtualMachineConfigSpec();
//
//        VirtualMachineFileInfo fileInfo = new VirtualMachineFileInfo();
//        fileInfo.vmPathName = "[" + datastore.getName() + "] " + name + "/" + name + ".vmx";
//        configSpec.files = fileInfo;
//        configSpec.name = name;
//        configSpec.version = "vmx-17"; // 根据恢复的主机生成
//        configSpec.guestId = config.getOsVersion();
//        configSpec.numCPUs = config.getCpuSocket() * config.getCorePerSocket();
//        configSpec.numCoresPerSocket = config.getCorePerSocket();
//        configSpec.memoryMB = Long.valueOf(config.getMemoryMB());
//
//        // Disk
//
//        // SCSI 控制器
//        VirtualDeviceConfigSpec scsiConfigSpec = new VirtualDeviceConfigSpec();
//        ParaVirtualSCSIController scsiController = new ParaVirtualSCSIController();
//        scsiController.key = 1; // 唯一正整数
//        scsiController.sharedBus = VirtualSCSISharing.noSharing;
//        scsiConfigSpec.device = scsiController;
//        scsiConfigSpec.operation = VirtualDeviceConfigSpecOperation.add;
//
//        // 网络适配器
//        VirtualDeviceConfigSpec networkConfigSpec = new VirtualDeviceConfigSpec();
//        VirtualVmxnet3 vmxnet3 = new VirtualVmxnet3();
//        vmxnet3.key = 2;
//        VirtualEthernetCardNetworkBackingInfo networkBackingInfo = new VirtualEthernetCardNetworkBackingInfo();
//        networkBackingInfo.deviceName = "VM Network";
//        vmxnet3.backing = networkBackingInfo;
//        networkConfigSpec.device = vmxnet3;
//        networkConfigSpec.operation = VirtualDeviceConfigSpecOperation.add;
//
//        // CD/DVD 驱动器
//
//        // SATA 控制器
//        VirtualDeviceConfigSpec sataConfigSpec = new VirtualDeviceConfigSpec();
//        VirtualAHCIController sataController = new VirtualAHCIController();
//        sataController.key = 3;
//        sataConfigSpec.device = sataController;
//        sataConfigSpec.operation = VirtualDeviceConfigSpecOperation.add;
//
//        // 自定义设备只需要 硬盘 SCSI控制器 网络适配器 CD/DVD SATA控制器 （按vCenter创建顺序），其他设备服务端会自动添加
//        configSpec.deviceChange = new VirtualDeviceConfigSpec[]{scsiConfigSpec, networkConfigSpec, sataConfigSpec};
//
//        Task task = datacenter.getVmFolder().createVM_Task(configSpec, resourcePool, hostSystem);
//        if (!Task.SUCCESS.equals(task.waitForTask())) {
//            System.err.println(task.getTaskInfo().error.localizedMessage);
//        }
//    }

//    public static void addDiskByPublicConfig(VirtualMachine vm, Datastore datastore,
//                                             PublicVirtualMachineConfig publicConfig, String name) throws RemoteException, InterruptedException {
//        VirtualMachineConfigSpec vmConfigSpec = new VirtualMachineConfigSpec();
//        List<VirtualDeviceConfigSpec> vdConfigSpecs = new ArrayList<>();
//
//        int index = 1;
//        for (PublicVirtualDiskConfig diskConfig : publicConfig.getDiskConfigs()) {
//            VirtualDeviceConfigSpec diskConfigSpec = new VirtualDeviceConfigSpec();
//            VirtualDisk disk = new VirtualDisk();
//            VirtualDiskFlatVer2BackingInfo backingInfo = new VirtualDiskFlatVer2BackingInfo();
//            backingInfo.fileName = "[" + datastore.getName() + "] " + name + "/" + name + ".vmdk";
//            backingInfo.diskMode = VirtualDiskMode.persistent.name();
//            if (diskConfig.getType() == 0) {
//                backingInfo.thinProvisioned = false;
//                backingInfo.eagerlyScrub = false;
//            } else if (diskConfig.getType() == 1) {
//                backingInfo.thinProvisioned = false;
//                backingInfo.eagerlyScrub = true;
//            } else if (diskConfig.getType() == 2) {
//                backingInfo.thinProvisioned = true;
//            }
//            disk.backing = backingInfo;
//            disk.key = index++;
//            disk.capacityInKB = diskConfig.getCapacityMB() * 1024;
//
//            diskConfigSpec.device = disk;
//            diskConfigSpec.operation = VirtualDeviceConfigSpecOperation.add;
//        }
//
//        vmConfigSpec.deviceChange = vdConfigSpecs.toArray(new VirtualDeviceConfigSpec[0]);
//        Task task = vm.reconfigVM_Task(vmConfigSpec);
//        if (!Task.SUCCESS.equals(task.waitForTask())) {
//            System.err.println(task.getTaskInfo().error.localizedMessage);
//        }
//    }

    public static void createVMNormal(Datacenter datacenter, ResourcePool resourcePool, HostSystem hostSystem) throws RemoteException, InterruptedException {
        // VMware平台配置创建虚拟机，同构异构平台配置用数字表示 1: vmware 2: h3c
        // 异构平台配置虚拟机，必须的配置用数字表示 1: h3c异构配置
        VirtualMachineConfigSpec configSpec = new VirtualMachineConfigSpec();

        // 名称和操作系统
        configSpec.name = "IDE-ISCSIcp"; // 虚拟机名称 1 2
        configSpec.version = "vmx-17"; // 兼容性 1同构兼容转化 2异构转化，根据主机版本自定义规则
        configSpec.guestId = "rhel7_64Guest"; // 操作系统版本（操作系统系列没找到对应的配置项）1同构可用性检测及转化 2异构转化？

        // 标准存储（没找到配置项，这里通过配置vmx的位置来绑定存储）
        VirtualMachineFileInfo fileInfo = new VirtualMachineFileInfo();
        fileInfo.vmPathName = "[NFS_8151] IDE-ISCSIcp/IDE-ISCSIcp.vmx"; // 1 2
        configSpec.files = fileInfo;

        // CPU
        configSpec.numCPUs = 2; // cpu总内核数 1 2 cpuCores * cpuSockets
        configSpec.numCoresPerSocket = 1; // 每个插槽内核数 h3c: cpuCores
//        configSpec.cpuHotAddEnabled = false;
//        configSpec.cpuHotRemoveEnabled = false; // cpu热插拔，默认值都是false
//        ResourceAllocationInfo cpuAllocation = new ResourceAllocationInfo();
//        cpuAllocation.expandableReservation = false; // 可扩展预留，虚拟机中该字段无效
//        cpuAllocation.overheadLimit = null; // 允许最大内存开销，适用于已通电虚拟机，vcenter server可用
//        cpuAllocation.reservation = 0L; // 预留
//        cpuAllocation.limit = -1L; // 限制，-1表示不受限制
//        SharesInfo sharesInfo = new SharesInfo();
//        sharesInfo.level = SharesLevel.normal; // 共享级别
//        sharesInfo.shares = 1000; // 共享份额
//        cpuAllocation.shares = sharesInfo; // 份额共享
//        configSpec.cpuAllocation = cpuAllocation; // cpu资源限制
//        configSpec.nestedHVEnabled = false; // 硬件虚拟化
//        configSpec.vPMCEnabled = false; // 性能计数器;
//        configSpec.cpuAffinity = null; // 调度相关性

        // 内存
        configSpec.memoryMB = 2048L; // 内存大小 MB
//        ResourceAllocationInfo memoryAllocation = new ResourceAllocationInfo();
//        memoryAllocation.expandableReservation = false; // 可扩展预留，虚拟机中该字段无效
//        memoryAllocation.overheadLimit = null; // 允许最大内存开销，适用于已通电虚拟机，vcenter server可用
//        memoryAllocation.reservation = 0L; // 预留
//        memoryAllocation.limit = -1L; // 限制，-1表示不受限制
//        SharesInfo memorySharesInfo = new SharesInfo();
//        memorySharesInfo.level = SharesLevel.normal; // 共享级别
//        memorySharesInfo.shares = 20480; // 共享份额
//        memoryAllocation.shares = memorySharesInfo; // 份额共享
//        configSpec.memoryAllocation = memoryAllocation; // 内存资源限制
//        configSpec.memoryHotAddEnabled = false; // 内存热插拔

        // 磁盘

        /*
        // SCSI 控制器
        VirtualDeviceConfigSpec scsiConfigSpec = new VirtualDeviceConfigSpec();
        ParaVirtualSCSIController scsiController = new ParaVirtualSCSIController();
        scsiController.key = 1; // 新增时取唯一正整数，其他必须是服务器分配的值
        scsiController.sharedBus = VirtualSCSISharing.noSharing; // SISI 总线共享
        scsiConfigSpec.device = scsiController;
        scsiConfigSpec.operation = VirtualDeviceConfigSpecOperation.add;

        // SATA 控制器
        VirtualDeviceConfigSpec sataConfigSpec = new VirtualDeviceConfigSpec();
        VirtualSATAController sataController = new VirtualSATAController();
        sataController.key = 2;
        sataConfigSpec.device = sataController;
        sataConfigSpec.operation = VirtualDeviceConfigSpecOperation.add;

        // USB 控制器
        VirtualDeviceConfigSpec usbConfigSpec = new VirtualDeviceConfigSpec();
        VirtualUSBController usbController = new VirtualUSBController();
        usbController.key = 3;
        usbConfigSpec.device = usbController;
        usbConfigSpec.operation = VirtualDeviceConfigSpecOperation.add;

        // 网络适配器
        VirtualDeviceConfigSpec networkConfigSpec = new VirtualDeviceConfigSpec();
        VirtualVmxnet3 vmxnet3 = new VirtualVmxnet3();
        vmxnet3.key = 4;
        VirtualEthernetCardNetworkBackingInfo networkBackingInfo = new VirtualEthernetCardNetworkBackingInfo();
        networkBackingInfo.deviceName = "VM Network"; // 网络适配器名称
//        Network network = null;
//        for (Network n : hostSystem.getNetworks()) {
//            if (n.getName().equals("VM Network")) {
//                network = n;
//            }
//        }
//        networkBackingInfo.network = network.getMOR();
        vmxnet3.backing = networkBackingInfo;
//        vmxnet3.wakeOnLanEnabled = true; // 打开电源时连接
//        vmxnet3.addressType = "generated"; // MAC地址自动分配
        networkConfigSpec.device = vmxnet3;
        networkConfigSpec.operation = VirtualDeviceConfigSpecOperation.add;

        // CD/DVD 驱动器

        // 显卡
        VirtualDeviceConfigSpec videoCardConfigSpec = new VirtualDeviceConfigSpec();
        VirtualMachineVideoCard videoCard = new VirtualMachineVideoCard();
        videoCard.key = 5;
//        videoCard.useAutoDetect = true; // 默认设置（自动检测设置）
        videoCardConfigSpec.device = videoCard;
        videoCardConfigSpec.operation = VirtualDeviceConfigSpecOperation.add;

        configSpec.deviceChange = new VirtualDeviceConfigSpec[]{
                scsiConfigSpec, sataConfigSpec, usbConfigSpec, networkConfigSpec, videoCardConfigSpec};
         */

        Task task = datacenter.getVmFolder().createVM_Task(configSpec, resourcePool, hostSystem);
        if (!Task.SUCCESS.equals(task.waitForTask())) {
            System.err.println(task.getTaskInfo().error.localizedMessage);
        }
    }

    public static void addDevice(VirtualMachine vm) throws RemoteException, InterruptedException {
        VirtualMachineConfigSpec configSpec = new VirtualMachineConfigSpec();

        // SCSI控制器
//        VirtualDeviceConfigSpec scsiConfigSpec = new VirtualDeviceConfigSpec();
//        ParaVirtualSCSIController scsiController = new ParaVirtualSCSIController();
//        scsiController.key = 1;
//        scsiController.sharedBus = VirtualSCSISharing.noSharing;
//        scsiConfigSpec.device = scsiController;
//        scsiConfigSpec.operation = VirtualDeviceConfigSpecOperation.add;

        // SATA控制器
//        VirtualDeviceConfigSpec sataConfigSpec = new VirtualDeviceConfigSpec();
//        VirtualSATAController sataController = new VirtualSATAController();
//        sataController.key = 1;
//        sataController.unitNumber = 99;
//        sataConfigSpec.device = sataController;
//        sataConfigSpec.operation = VirtualDeviceConfigSpecOperation.add;

        // 网络适配器
        VirtualDeviceConfigSpec networkConfigSpec = new VirtualDeviceConfigSpec();
        VirtualVmxnet3 vmxnet3 = new VirtualVmxnet3();
        vmxnet3.key = 1;
        VirtualEthernetCardNetworkBackingInfo backingInfo = new VirtualEthernetCardNetworkBackingInfo();
        backingInfo.deviceName = "VM Network";
        vmxnet3.backing = backingInfo;
        networkConfigSpec.device = vmxnet3;
        networkConfigSpec.operation = VirtualDeviceConfigSpecOperation.add;

        configSpec.deviceChange = new VirtualDeviceConfigSpec[]{networkConfigSpec};
        Task task = vm.reconfigVM_Task(configSpec);
        if (!Task.SUCCESS.equals(task.waitForTask())) {
            System.err.println(task.getTaskInfo().error.localizedMessage);
        }
    }

    public static void createVMDefault(Datacenter datacenter, ResourcePool resourcePool, HostSystem hostSystem) throws RemoteException, InterruptedException {
        VirtualMachineConfigSpec configSpec = new VirtualMachineConfigSpec();

        VirtualMachineFileInfo fileInfo = new VirtualMachineFileInfo();
        fileInfo.vmPathName = "[vsanDatastore] test-c7/test-c7.vmx";
        configSpec.files = fileInfo;
        configSpec.name = "test-c7";
        configSpec.version = "vmx-17";
        configSpec.guestId = "centos7_64Guest";
        configSpec.numCPUs = 1;
        configSpec.numCoresPerSocket = 1;
        configSpec.memoryMB = 2048L;

        // Disk

        // SCSI 控制器
        VirtualDeviceConfigSpec scsiConfigSpec = new VirtualDeviceConfigSpec();
        ParaVirtualSCSIController scsiController = new ParaVirtualSCSIController();
        scsiController.key = 1; // 唯一正整数
        scsiController.sharedBus = VirtualSCSISharing.noSharing;
        scsiConfigSpec.device = scsiController;
        scsiConfigSpec.operation = VirtualDeviceConfigSpecOperation.add;

        // 网络适配器
        VirtualDeviceConfigSpec networkConfigSpec = new VirtualDeviceConfigSpec();
        VirtualVmxnet3 vmxnet3 = new VirtualVmxnet3();
        vmxnet3.key = 2;
        VirtualEthernetCardNetworkBackingInfo networkBackingInfo = new VirtualEthernetCardNetworkBackingInfo();
        networkBackingInfo.deviceName = "VM Network";
        vmxnet3.backing = networkBackingInfo;
        networkConfigSpec.device = vmxnet3;
        networkConfigSpec.operation = VirtualDeviceConfigSpecOperation.add;

        // CD/DVD 驱动器

        // SATA 控制器
        VirtualDeviceConfigSpec sataConfigSpec = new VirtualDeviceConfigSpec();
        VirtualAHCIController sataController = new VirtualAHCIController();
        sataController.key = 3;
        sataConfigSpec.device = sataController;
        sataConfigSpec.operation = VirtualDeviceConfigSpecOperation.add;

        // 自定义设备只需要 硬盘 SCSI控制器 网络适配器 CD/DVD SATA控制器 （按vCenter创建顺序），其他设备服务端会自动添加
        configSpec.deviceChange = new VirtualDeviceConfigSpec[]{scsiConfigSpec, networkConfigSpec, sataConfigSpec};

        Task task = datacenter.getVmFolder().createVM_Task(configSpec, resourcePool, hostSystem);
        if (!Task.SUCCESS.equals(task.waitForTask())) {
            System.err.println(task.getTaskInfo().error.localizedMessage);
        } else {
            System.out.println(JSON.toJSONString(task.getTaskInfo(), SerializerFeature.PrettyFormat));
            System.out.println(task.getTaskInfo().result.getClass().getName());
        }

        new VirtualMachine(task.getServerConnection(), (ManagedObjectReference) task.getTaskInfo().result);
    }
}
