package com.github.xqplus.sample.vmware;

import com.vmware.vim25.*;
import com.vmware.vim25.mo.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

public class CreateVM {

    public static void main(String[] args) throws MalformedURLException, RemoteException, InterruptedException {
        ServiceInstance serviceInstance = new ServiceInstance(new URL("https://192.168.8.12/sdk"),
                "administrator@vsphere.local", "Dsg_123456!", true);
//        ServiceInstance serviceInstance = new ServiceInstance(new URL("https://192.168.7.2/sdk"),
//                "root", "dsgcd_201231", true);
//        ServiceInstance serviceInstance = new ServiceInstance(new URL("https://192.168.6.155/sdk"),
//                "root", "dsgcd_211208", true);
        InventoryNavigator inventoryNavigator = new InventoryNavigator(serviceInstance.getRootFolder());

        Datacenter datacenter = (Datacenter) inventoryNavigator.searchManagedEntity("Datacenter", "Datacenter1");
        ResourcePool resourcePool = (ResourcePool) inventoryNavigator.searchManagedEntity("ResourcePool", "测试虚拟机");
        HostSystem hostSystem = (HostSystem) inventoryNavigator.searchManagedEntity("HostSystem", "192.168.8.2");
//        createVMNormal(datacenter, resourcePool, hostSystem);
        createVMDefault(datacenter, resourcePool, hostSystem);

//        VirtualMachine vm = (VirtualMachine) inventoryNavigator.searchManagedEntity("VirtualMachine", "IDE-ISCSIcp");
//        addDevice(vm);

        serviceInstance.getServerConnection().logout();
    }

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
        fileInfo.vmPathName = "[NFS_8151] IDE-ISCSIcp/IDE-ISCSIcp.vmx";
        configSpec.files = fileInfo;
        configSpec.name = "IDE-ISCSIcp";
        configSpec.version = "vmx-17";
        configSpec.guestId = "rhel7_64Guest";
        configSpec.numCPUs = 2;
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
        }
    }
}
