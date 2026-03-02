package io.github.xqplus.virt.winstack;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.vmware.vim25.*;
import com.vmware.vim25.mo.*;
import io.github.xqplus.virt.ResourceType;
import io.github.xqplus.virt.VirtResource;
import io.github.xqplus.virt.VirtVm;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        ServiceInstance serviceInstance = new ServiceInstance(new URL("https://192.168.7.3/sdk"),
                "admin", "Dsg_123456!", true, 30 * 1000, 30 * 1000);
        InventoryNavigator in = new InventoryNavigator(serviceInstance.getRootFolder());
        try {
            VirtualMachine vm = (VirtualMachine) in.searchManagedEntity("VirtualMachine", "redhat8-raw_2");

//            for (VirtualMachineSnapshotTree tree : vm.getSnapshot().rootSnapshotList) {
//                System.out.println(tree.name);
//                System.out.println(tree.createTime.getTime());
//            }


            OptionValue optionValue = vm.getConfig().extraConfig[0];
            System.out.println(JSON.toJSONString(optionValue.value, SerializerFeature.PrettyFormat));

//            System.out.println(vm.getConfig().changeTrackingEnabled);
//            VirtualMachineConfigSpec configSpec = new VirtualMachineConfigSpec();
//            configSpec.changeTrackingEnabled = true;
//            vm.reconfigVM_Task(configSpec).waitForTask();
//            System.out.println(vm.getConfig().changeTrackingEnabled);

//            VirtualMachineSnapshot snapshot = getVmSnapshot(vm, "s0_backup");
//            VirtualMachineSnapshot snapshot = createVmSnapshot(vm, "e0_backup");
//            for (VirtualDevice device : vm.getConfig().hardware.device) {
//                if (device instanceof VirtualDisk) {
//                    System.out.println(JSON.toJSONString(device, SerializerFeature.PrettyFormat));
//                }
//            }

//            String changeId = "*";
//            DiskChangeInfo diskChangeInfo = vm.queryChangedDiskAreas(snapshot, 2000, 0, changeId);
//            for (DiskChangeExtent diskChangeExtent : diskChangeInfo.changedArea) {
//                System.out.println(diskChangeExtent.start + ", " + diskChangeExtent.length);
//            }
        } finally {
            serviceInstance.getServerConnection().logout();
        }
    }

    public static VirtualMachineSnapshot createVmSnapshot(VirtualMachine vm, String snapshotName) throws RemoteException, InterruptedException {
        processTask(vm.createSnapshot_Task(snapshotName, "Created by VBP", false, false));
        return getVmSnapshot(vm, snapshotName);
    }

    private static Task processTask(Task task) throws RemoteException, InterruptedException {
        if (!Task.SUCCESS.equals(task.waitForTask())) {
            throw new RemoteException(task.getTaskInfo().error.localizedMessage);
        }
        return task;
    }

    public static VirtualMachineSnapshot getVmSnapshot(VirtualMachine vm, String snapshotName) {
        if (vm.getSnapshot() != null) {
            ManagedObjectReference snapshotMor = getVmSnapshotMor(vm.getSnapshot().rootSnapshotList, snapshotName);
            return snapshotMor != null ? new VirtualMachineSnapshot(vm.getServerConnection(), snapshotMor) : null;
        }
        return null;
    }

    private static ManagedObjectReference getVmSnapshotMor(VirtualMachineSnapshotTree[] snapshotTreeArr, String snapshotName) {
        if (snapshotTreeArr != null) {
            for (VirtualMachineSnapshotTree snapshotTree : snapshotTreeArr) {
                if (snapshotTree.name.equals(snapshotName)) return snapshotTree.snapshot;
                ManagedObjectReference snapshotMor = getVmSnapshotMor(snapshotTree.childSnapshotList, snapshotName);
                if (snapshotMor != null) return snapshotMor;
            }
        }
        return null;
    }

    private static void recursionMeAndCollectVrVm(ManagedEntity managedEntity, List<VirtResource> resources, List<VirtVm> vms,
                                                  String parentId, Integer centerId, String dcId, String cluId) throws RemoteException {
        if (managedEntity instanceof Folder) { // 这里一般是顶层 Folder
            Folder folder = (Folder) managedEntity;
            for (ManagedEntity childEntity : folder.getChildEntity()) {
                recursionMeAndCollectVrVm(childEntity, resources, vms, parentId, centerId, dcId, cluId);
            }
        } else if (managedEntity instanceof Datacenter) {
            Datacenter datacenter = (Datacenter) managedEntity;
            VirtResource vrdc = newVirtResource(parentId, datacenter.getName(), ResourceType.DATACENTER, centerId);
            resources.add(vrdc);
            recursionMeAndCollectVrVm(datacenter.getHostFolder(), resources, vms, vrdc.getId(), centerId, vrdc.getId(), dcId);
        } else if (managedEntity instanceof ClusterComputeResource) {
            ClusterComputeResource cluster = (ClusterComputeResource) managedEntity;
            VirtResource vrc = newVirtResource(parentId, cluster.getName(), ResourceType.CLUSTER, centerId);
            resources.add(vrc);
            for (HostSystem hostSystem : cluster.getHosts()) {
                recursionMeAndCollectVrVm(hostSystem, resources, vms, vrc.getId(), centerId, dcId, vrc.getId());
            }
        } else if (managedEntity instanceof HostSystem) {
            HostSystem hostSystem = (HostSystem) managedEntity;
            VirtResource vrhs = newVirtResource(parentId, hostSystem.getName(), ResourceType.HOST, centerId);
            resources.add(vrhs);
            for (VirtualMachine vm : hostSystem.getVms()) {
                vms.add(newVirtVm(vm, centerId, dcId, cluId, vrhs.getId()));
            }
        }
    }

    private static VirtVm newVirtVm(VirtualMachine vm, Integer centerId, String datacenterId, String clusterId, String hostId) {
        VirtVm vvm = new VirtVm();
        vvm.setCenterId(centerId);
        vvm.setVmId(vm.getMOR().val);
        vvm.setVmName(vm.getName());
        VirtualMachineConfigInfo vmConfig = vm.getConfig();
        long totalSizeInKB = Arrays.stream(vmConfig.hardware.device)
                .filter(d -> d instanceof VirtualDisk)
                .mapToLong(d -> ((VirtualDisk) d).capacityInKB)
                .sum();
        vvm.setVmSizeTotal(totalSizeInKB / 1024);
//        vvm.setVmSizeActual(vm.getSummary().storage.committed / 1024 / 1024);
        vvm.setVmSizeActual(0L); // TODO 使用 restapi 查询？
        vvm.setOsType(vmConfig.guestId.contains("Windows") ? 0 : 1);
        vvm.setOsName(vmConfig.guestFullName);
        VirtualMachinePowerState ps = vm.getRuntime().powerState;
        vvm.setPowerState(ps == VirtualMachinePowerState.poweredOn ? 1 : ps == VirtualMachinePowerState.suspended ? 2 : 0);
        vvm.setDatacenterId(datacenterId);
        vvm.setClusterId(clusterId);
        vvm.setHostId(hostId);
        return vvm;
    }

    protected static VirtResource newVirtResource(String pId, String name, ResourceType resourceType, Integer centerId) {
        VirtResource resource = new VirtResource();
        resource.setId(UUID.randomUUID().toString());
        resource.setParentId(pId);
        resource.setName(name);
        resource.setType(resourceType.getCode());
        resource.setCenterId(centerId);
        switch (resourceType) {
            case DATACENTER:
                resource.setIcon("dc");
                break;
            case CLUSTER:
                resource.setIcon("clu");
                break;
            case HOST:
                resource.setIcon("hs");
                break;
        }
        return resource;
    }
}
