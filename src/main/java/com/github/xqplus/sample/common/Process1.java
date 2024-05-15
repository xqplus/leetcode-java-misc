package com.github.xqplus.sample.common;

public class Process1 {

    private static int count;

    public static void main(String[] args) throws InterruptedException {
        String[] as = {"fc-0x21000024ffaa83e2-0x21000024ff3cbb5a-lun-0",
                "fc-0x21000024ffaa83e2-0x21000024ff3cbb5a-lun-1-part1",
                "ip-192.168.6.3:3260-iscsi-iqn.2005-10.org.freenas.ctl:target1-lun-0",
                "ip-192.168.6.3:3260-iscsi-iqn.2005-10.org.freenas.ctl:target1-lun-0-part1",
                "pci-0000:02:01.0-ata-1",
                "pci-0000:03:00.0-scsi-0:0:0:0-part1",
                "pci-0000:81:00.0-fc-0x21000024ff3cbb5a-lun-0"
        };

        String prefix = "fc";
        String identifierName = "0x21000024ff3cbb5a";
        String lunReg = ".*-lun-\\d+$";
        for (String a : as) {
            if (a.matches(prefix + ".*" + identifierName + lunReg)) {
                System.out.println(a);
            }
        }
    }
}
