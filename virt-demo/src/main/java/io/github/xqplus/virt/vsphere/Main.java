package io.github.xqplus.virt.vsphere;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.vmware.vim25.VirtualDevice;
import com.vmware.vim25.VirtualDisk;
import com.vmware.vim25.mo.*;

import java.net.URL;

public class Main {

    public static void main(String[] args) throws Exception {
//        VSphere vSphere = new VSphere("192.168.8.12", "administrator@vsphere.local", "Dsg_123456!");
        ServiceInstance serviceInstance = new ServiceInstance(new URL("https://192.168.8.12/sdk"),
                "administrator@vsphere.local", "Dsg_123456!", true, 30 * 1000, 30 * 1000);
        try {
            ServerConnection serverConnection = serviceInstance.getServerConnection();
            Thread.sleep(100 * 1000);
            System.out.println(serverConnection.getServiceInstance().currentTime());

//            InventoryNavigator inventoryNavigator = new InventoryNavigator(serviceInstance.getRootFolder());
//            VirtualMachine vm = (VirtualMachine) inventoryNavigator.searchManagedEntity("VirtualMachine", "ceshi-centos7");
//            for (VirtualDevice virtualDevice : vm.getConfig().hardware.device) {
//                if (virtualDevice instanceof VirtualDisk) {
//                    System.out.println(JSON.toJSONString(virtualDevice, SerializerFeature.PrettyFormat));
//                }
//            }
        } finally {
//            vSphere.logout();
            serviceInstance.getServerConnection().logout();
        }
    }
}
