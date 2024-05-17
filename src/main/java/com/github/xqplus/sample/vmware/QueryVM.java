package com.github.xqplus.sample.vmware;

import com.vmware.vim25.DiskChangeExtent;
import com.vmware.vim25.DiskChangeInfo;
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
//        ServiceInstance serviceInstance = new ServiceInstance(new URL("https://192.168.8.12/sdk"),
//                "administrator@vsphere.local", "Dsg_123456!", true);
//        ServiceInstance serviceInstance = new ServiceInstance(new URL("https://192.168.6.2/sdk"),
//                "root", "dsgcd_211208", true);
        ServiceInstance serviceInstance = new ServiceInstance(new URL("https://192.168.6.178/sdk"),
                "root", "dsgcd_211208", true);

        VirtualMachine vm = queryVirtualMachine(serviceInstance.getRootFolder(), "c7");
        String changeId = "*";
        // 52 6f f7 d2 fd 27 ac b1-76 44 e2 ef 5f b6 1a 84/12  vinchin
        // 52 6f f7 d2 fd 27 ac b1-76 44 e2 ef 5f b6 1a 84/16  bdmp
        queryChangedDiskAreas(vm, vm.getCurrentSnapShot(), 2000, changeId);

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
}
