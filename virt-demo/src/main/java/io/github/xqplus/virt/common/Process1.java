package io.github.xqplus.virt.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Process1 {

    public static void main(String[] args) throws Exception {
//        testEthReadWrite();
        System.out.println("/service/sites/367C0709/folders/223823".replaceAll("folders", "folder"));
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
