package com.github.xqplus.sample.vmware;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.vmware.vim25.VirtualDevice;
import com.vmware.vim25.VirtualDisk;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VirtualMachine;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

/**
 * 查询虚拟机磁盘信息
 *
 * @author chenq
 */
public class QueryVMDiskInfo {

    public static void main(String[] args) throws MalformedURLException, RemoteException {
        ServiceInstance serviceInstance = new ServiceInstance(new URL("https://192.168.6.178/sdk"),
                "root", "dsgcd_211208", true);

        VirtualMachine vm = (VirtualMachine) new InventoryNavigator(serviceInstance.getRootFolder())
                .searchManagedEntity("VirtualMachine", "test_centos76");
        for (VirtualDevice device : vm.getConfig().hardware.device) {
            if (device instanceof VirtualDisk) {
                VirtualDisk disk = (VirtualDisk) device;
                System.out.println(disk.backing.getClass().getName());
                System.out.println(JSON.toJSONString(disk, JSONWriter.Feature.PrettyFormat));
            }
        }
        System.out.println("============================");
        for (VirtualDevice device : vm.getCurrentSnapShot().getConfig().hardware.device) {
            if (device instanceof VirtualDisk) {
                VirtualDisk disk = (VirtualDisk) device;
                System.out.println(disk.backing.getClass().getName());
                System.out.println(JSON.toJSONString(disk, JSONWriter.Feature.PrettyFormat));
            }
        }

        serviceInstance.getServerConnection().logout();
    }
}
