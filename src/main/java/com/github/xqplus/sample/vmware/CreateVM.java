package com.github.xqplus.sample.vmware;

import com.alibaba.fastjson2.JSON;
import com.vmware.vim25.InvalidArgument;
import com.vmware.vim25.LocalizedMethodFault;
import com.vmware.vim25.VirtualMachineConfigSpec;
import com.vmware.vim25.VirtualMachineFileInfo;
import com.vmware.vim25.mo.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

public class CreateVM {

    // TODO 虚拟机参数不完整
    public static void main(String[] args) throws MalformedURLException, RemoteException, InterruptedException {
        ServiceInstance serviceInstance = new ServiceInstance(new URL("https://192.168.8.12/sdk"),
                "administrator@vsphere.local", "Dsg_123456!", true);
//        ServiceInstance serviceInstance = new ServiceInstance(new URL("https://192.168.7.2/sdk"),
//                "root", "dsgcd_201231", true);
//        ServiceInstance serviceInstance = new ServiceInstance(new URL("https://192.168.6.155/sdk"),
//                "root", "dsgcd_211208", true);

        Datacenter datacenter = (Datacenter) new InventoryNavigator(serviceInstance.getRootFolder())
                .searchManagedEntities("Datacenter")[0];
        ResourcePool resourcePool = (ResourcePool) new InventoryNavigator(serviceInstance.getRootFolder())
                .searchManagedEntities("ResourcePool")[0];
        HostSystem hostSystem = (HostSystem) new InventoryNavigator(serviceInstance.getRootFolder())
                .searchManagedEntities("HostSystem")[0];

        VirtualMachineConfigSpec configSpec = new VirtualMachineConfigSpec();
        VirtualMachineFileInfo fileInfo = new VirtualMachineFileInfo();
        fileInfo.vmPathName = "[vsanDatastore] testVM%/testVM%.vmx";
        configSpec.files = fileInfo;
        configSpec.guestId = "centos8_64Guest";
        configSpec.name = "testVM%";

        Task task = datacenter.getVmFolder().createVM_Task(configSpec, resourcePool, hostSystem);
        if (!Task.SUCCESS.equals(task.waitForTask())) {
            System.err.println(JSON.toJSONString(task.getTaskInfo()));
            // 判断 guestId 是否适配兼容性
            LocalizedMethodFault error = task.getTaskInfo().error;
            boolean retryByGuestIdCompatibility = false; // 是否为了guestId兼容性做重试
            if (error.fault instanceof InvalidArgument) {
                InvalidArgument invalidArgument = (InvalidArgument) error.fault;
                if ("configSpec.guestId".equals(invalidArgument.invalidProperty)
                        || "A specified parameter was not correct.".equals(error.localizedMessage.trim())) { // 5.5
                    System.out.println("========");
                    configSpec.guestId = "otherGuest64";
                    task = datacenter.getVmFolder().createVM_Task(configSpec, resourcePool, hostSystem);
                    retryByGuestIdCompatibility = true;
                }
            }
            if (!retryByGuestIdCompatibility || !Task.SUCCESS.equals(task.waitForTask())) {
                System.err.println(task.getTaskInfo().error.localizedMessage);
            }
        }

        serviceInstance.getServerConnection().logout();
    }
}
