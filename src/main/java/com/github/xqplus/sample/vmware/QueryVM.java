package com.github.xqplus.sample.vmware;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.github.xqplus.sample.common.exception.ManagedEntityNotFoundException;
import com.vmware.vim25.*;
import com.vmware.vim25.mo.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

/**
 * 查询虚拟机相关信息
 *
 * @author chenq
 */
public class QueryVM {

    public static void main(String[] args) throws MalformedURLException, RemoteException, InterruptedException {
        ServiceInstance serviceInstance = new ServiceInstance(new URL("https://192.168.8.12/sdk"),
                "administrator@vsphere.local", "Dsg_123456!", true);
//        ServiceInstance serviceInstance = new ServiceInstance(new URL("https://192.168.6.2/sdk"),
//                "root", "dsgcd_211208", true);
//        ServiceInstance serviceInstance = new ServiceInstance(new URL("https://192.168.6.178/sdk"),
//                "root", "dsgcd_211208", true);

        VirtualMachine vm = queryVirtualMachine(serviceInstance.getRootFolder(), "IDE-ISCSIcp");
//        String changeId = "52 b9 f6 04 5c e3 b9 0c-3a c9 a8 71 42 00 2b 89/105";
//        ManagedObjectReference mor = findSnapshotMOR(vm.getSnapshot().rootSnapshotList, "s3");
//        VirtualMachineSnapshot snapshot = new VirtualMachineSnapshot(vm.getServerConnection(), mor);
//        queryChangedDiskAreas(vm, snapshot, 2000, changeId);

//        VirtualMachineSnapshot s11 = createSnapshot(vm, "s11");
//        System.out.println(s11.getMOR().val);

//        ManagedObjectReference s2MOR = findSnapshotMOR(vm.getSnapshot().rootSnapshotList, "s2");
//        VirtualMachineSnapshot s2 = new VirtualMachineSnapshot(vm.getServerConnection(), s2MOR);
//        Task task = s2.removeSnapshot_Task(false);
//        task.waitForTask();

        System.out.println(JSON.toJSONString(vm.getConfig().hardware, JSONWriter.Feature.PrettyFormat));
        serviceInstance.getServerConnection().logout();
    }

    public static VirtualMachine queryVirtualMachine(Folder rootFolder, String name) throws RemoteException {
        return (VirtualMachine) new InventoryNavigator(rootFolder).searchManagedEntity("VirtualMachine", name);
    }

    public static void queryChangedDiskAreas(VirtualMachine vm, VirtualMachineSnapshot snapshot,
                                             int deviceKey, String changeId) throws RemoteException {
        DiskChangeInfo diskChangeInfo = vm.queryChangedDiskAreas(snapshot, deviceKey, 0, changeId);
        System.out.println(diskChangeInfo.startOffset + ", " + diskChangeInfo.length);
        for (DiskChangeExtent changeExtent : diskChangeInfo.changedArea) {
            System.out.println(changeExtent.start + ", " + changeExtent.length);
        }
    }

    public static VirtualMachineSnapshot createSnapshot(VirtualMachine vm, String name) throws RemoteException, InterruptedException {
        Task task = vm.createSnapshot_Task(name, null, false, false);
        if (!Task.SUCCESS.equals(task.waitForTask())) {
            throw task.getTaskInfo().error.fault;
        }
        ManagedObjectReference snapshotMOR = findSnapshotMOR(vm.getSnapshot().rootSnapshotList, name);
        if (snapshotMOR == null) {
            throw new ManagedEntityNotFoundException("VirtualMachineSnapshot", name);
        }
        return new VirtualMachineSnapshot(vm.getServerConnection(), snapshotMOR);
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

    public static void printVMConfigInfo(VirtualMachine vm) {
        VirtualMachineConfigInfo config = vm.getConfig();
        System.out.println(JSON.toJSONString(config));
    }
}
