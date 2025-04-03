package io.github.xqplus.virt.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.vmware.vim25.VirtualMachineSnapshotTree;
import com.vmware.vim25.mo.VirtualMachine;
import org.apache.commons.lang3.StringUtils;
import sun.java2d.loops.ProcessPath;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Process1 {

    public static void main(String[] args) throws Exception {
        int i = (1 << 4) - 1;
        System.out.println(Integer.toBinaryString(i));
    }

    private void printPID() {
        // 获取 JVM 的运行时名称（格式：pid@hostname）
        String runtimeName = ManagementFactory.getRuntimeMXBean().getName();
        System.out.println(runtimeName);
        // 解析 PID
        long pid = Long.parseLong(runtimeName.split("@")[0]);
        System.out.println("当前进程 PID: " + pid);
    }

    private static void writeToFile() throws IOException {
        Path txt = Paths.get("E:\\tmp.txt");
        List<String> lines = new ArrayList<>();
        lines.add("TYPE=Ethernet");
        lines.add("DEVICE=eth0");
        Files.write(txt, lines);
    }

    private static void testEthReadWrite() throws IOException {
        JSONArray eths = new JSONArray();

        JSONObject eth0 = new JSONObject();
        eth0.put("device", "eth0");
        List<String> setting0 = new ArrayList<>();
        setting0.add("TYPE=Ethernet");
        setting0.add("DEVICE=eth0");
        setting0.add("BOOTPROTO=static");
        setting0.add("ONBOOT=yes");
        setting0.add("IPADDR=192.168.21.10");
        setting0.add("NETMASK=255.255.255.0");
        setting0.add("GATEWAY=192.168.21.1");
//        setting0.add("DNS1=61.139.2.69");
        eth0.put("setting", setting0);
        eths.add(eth0);

        String ethsStr = eths.toString();
        System.out.println(ethsStr);
//        Path tmp = Paths.get("E:\\tmp");
//        Files.write(tmp, ethsStr.getBytes(StandardCharsets.UTF_8));
//
//        byte[] bytes = Files.readAllBytes(tmp);
//        List<JSONObject> list = JSON.parseArray(new String(bytes, StandardCharsets.UTF_8), JSONObject.class);
//        for (JSONObject jsonObject : list) {
//            String deviceName = jsonObject.getString("deviceName");
//            List<String> setting = jsonObject.getObject("setting", TypeReference.LIST_STRING);
//            Files.write(Paths.get("E:\\" + deviceName), setting);
//        }
    }
}
