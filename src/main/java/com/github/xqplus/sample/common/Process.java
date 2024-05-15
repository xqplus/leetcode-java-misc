package com.github.xqplus.sample.common;

import com.vmware.vim25.VirtualDeviceBackingInfo;
import com.vmware.vim25.VirtualDiskFlatVer2BackingInfo;

import java.io.IOException;
import java.lang.reflect.Field;

public class Process {

    public static void main(String[] args) throws IOException, NoSuchFieldException, IllegalAccessException {
//        Path randomPath = Paths.get("E:\\" + UUID.randomUUID());
//        System.out.println(randomPath);
//
//        //Path file = Files.createFile(randomPath);
//
//        List<String> list = new ArrayList<>();
//        list.add("vixDiskLib.transport.san.blacklist = all");
//        list.add("vixDiskLib.transport.san.whitelist = /dev/sdd,/dev/sdf");
//        Path write = Files.write(randomPath, list);
//        System.out.println(write);

//        Integer a = 4;
//        System.out.println(TransportMethod.SAN.equals(a));

        VirtualDiskFlatVer2BackingInfo backingInfo = new VirtualDiskFlatVer2BackingInfo();
        backingInfo.uuid = "asjdkhaskjdh";

        VirtualDeviceBackingInfo refBackingInfo = backingInfo;
        Field uuid = refBackingInfo.getClass().getField("uuid");
        System.out.println(uuid);
        System.out.println(uuid.getName());
        System.out.println(uuid.get(refBackingInfo));
    }
}
