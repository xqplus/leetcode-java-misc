package com.github.xqplus.sample.vmware;

import com.vmware.vim25.*;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

public class UpdateDisk {

    public static void main(String[] args) throws MalformedURLException, RemoteException, InterruptedException {
        VirtualDiskFlatVer2BackingInfo backingInfo = new VirtualDiskFlatVer2BackingInfo();
        backingInfo.diskMode = "persistent";
        backingInfo.eagerlyScrub = false;
        backingInfo.fileName = "[8.151] fuse3/fuse3_2000.vmdk";
        backingInfo.thinProvisioned = true;

        VirtualDisk disk = new VirtualDisk();
        disk.backing = backingInfo;
        disk.capacityInKB = 15728640;
        disk.controllerKey = 1000;
        disk.key = 2000;
        disk.unitNumber = 0;

        VirtualDeviceConfigSpec deviceSpec = new VirtualDeviceConfigSpec();
        deviceSpec.setDevice(disk);
        deviceSpec.setOperation(VirtualDeviceConfigSpecOperation.add);

        VirtualMachineConfigSpec vmSpec = new VirtualMachineConfigSpec();
        vmSpec.setDeviceChange(new VirtualDeviceConfigSpec[]{deviceSpec});


        ServiceInstance serviceInstance = new ServiceInstance(new URL("https://192.168.8.12/sdk"),
                "administrator@vsphere.local", "Dsg_123456!", true);
//        ServiceInstance serviceInstance = new ServiceInstance(new URL("https://192.168.7.2/sdk"),
//                "root", "dsgcd_201231", true);
//        ServiceInstance serviceInstance = new ServiceInstance(new URL("https://192.168.6.155/sdk"),
//                "root", "dsgcd_211208", true);

        VirtualMachine virtualMachine = (VirtualMachine) new InventoryNavigator(serviceInstance.getRootFolder())
                .searchManagedEntity("VirtualMachine", "fuse3");

        Task task = virtualMachine.reconfigVM_Task(vmSpec);
        if (!Task.SUCCESS.equals(task.waitForTask())) {
            System.err.println(task.getTaskInfo().error.localizedMessage);
        }

        serviceInstance.getServerConnection().logout();
    }
}
