package com.github.xqplus.sample.vmware;

import com.vmware.vim25.*;
import lombok.Data;

import java.util.List;

/**
 * 虚拟机信息
 *
 * @author chenq
 * @since 1.1.0
 */
@Data
public class VirtualMachineInfo {

    private Boolean cbtEnabled;
    private String guestId;
    private Integer memoryMB;
    private String name;
    private Integer numCPU;
    private Integer numCoresPerSocket;
    private String version;
    private String vmPathName;
    private String firmware; // 操作系统引导固件 bios | efi
    private Boolean nestedHVEnabled; // cpu硬件虚拟化 since 2.0.0
    private List<VirtualCdrom> cdroms;
    private List<VirtualController> controllers;
    private List<VirtualDisk> disks;
    private List<VirtualEthernetCard> ethernetCards;
    private List<VirtualFloppy> floppies;
    private List<VirtualKeyboard> keyboards;
    private List<VirtualParallelPort> parallelPorts;
    private List<VirtualPointingDevice> pointings;
    private List<VirtualSerialPort> serialPorts;
    private List<VirtualSoundCard> soundCards;
    private List<VirtualMachineVideoCard> videoCards;
    private List<VirtualMachineVMCIDevice> VMCIs;
}
