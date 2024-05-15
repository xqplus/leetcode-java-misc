package com.github.xqplus.sample.vmware;

import com.vmware.vim25.DiskChangeExtent;
import com.vmware.vim25.DiskChangeInfo;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VirtualMachine;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

public class QueryVM {

    public static void main(String[] args) throws MalformedURLException, RemoteException, InterruptedException {
        ServiceInstance serviceInstance = new ServiceInstance(new URL("https://192.168.8.12/sdk"),
                "administrator@vsphere.local", "Dsg_123456!", true);
//        ServiceInstance serviceInstance = new ServiceInstance(new URL("https://192.168.7.2/sdk"),
//                "root", "dsgcd_201231", true);
//        ServiceInstance serviceInstance = new ServiceInstance(new URL("https://192.168.6.155/sdk"),
//                "root", "dsgcd_211208", true);
//        ServiceInstance serviceInstance = new ServiceInstance(new URL("https://192.168.6.2/sdk"),
//                "root", "dsgcd_211208", true);

        VirtualMachine virtualMachine = (VirtualMachine) new InventoryNavigator(serviceInstance.getRootFolder())
                .searchManagedEntity("VirtualMachine", "centos7");

//        Datastore datastore = (Datastore) new InventoryNavigator(serviceInstance.getRootFolder())
//                .searchManagedEntity("Datastore", "ssd2");
//
//        VirtualMachineRelocateSpec vmrs = new VirtualMachineRelocateSpec();
//        vmrs.datastore = datastore.getMOR();
//        Task task = virtualMachine.relocateVM_Task(vmrs);
//        System.out.println(task.getTaskInfo().error.localizedMessage);

        DiskChangeInfo diskChangeInfo = virtualMachine.queryChangedDiskAreas(virtualMachine.getCurrentSnapShot(), 2000, 0, "*");
        for (DiskChangeExtent changeExtent : diskChangeInfo.getChangedArea()) {
            System.out.println(changeExtent.start + ", " + changeExtent.length);
        }

        serviceInstance.getServerConnection().logout();
    }
}
