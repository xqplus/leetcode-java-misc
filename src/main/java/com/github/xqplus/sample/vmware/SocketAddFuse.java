package com.github.xqplus.sample.vmware;

import com.alibaba.fastjson2.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SocketAddFuse {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        try {
            socket.setKeepAlive(true);
            socket.connect(new InetSocketAddress("192.168.8.151", 1999), 2000);
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();

            JSONObject reqJSON = new JSONObject();
            reqJSON.put("version_path", "/storage/data/9350b527-6203-40fe-b30c-a9fca51bc75d");
            reqJSON.put("next", 0);
            reqJSON.put("last", 0);
            reqJSON.put("recover_path", "/storage/data/9350b527-6203-40fe-b30c-a9fca51bc75d/recovery_fine_grained/0");
            reqJSON.put("target_name", "image_2000");
            reqJSON.put("compress", 0);
            reqJSON.put("vmType", 2);  // 0: hw 1: h3c 2: vmware

            byte[] head = new byte[]{0, 0, 0, 0, 0, 0, 1, 0};
            try {

                reqJSON.put("id", 2000);
                reqJSON.put("disk_path", "/ffbddce6-9d2f-4209-96ab-fa6894b6af8c/image_2000-flat.vmdk");
                reqJSON.put("cache_path", "/storage/data/9350b527-6203-40fe-b30c-a9fca51bc75d/recovery_fine_grained/0/2000.cache");
                reqJSON.put("dump_path", "/storage/data/9350b527-6203-40fe-b30c-a9fca51bc75d/backup/2000.dump");
                System.out.println(reqJSON);
                byte[] bytes = reqJSON.toString().getBytes(StandardCharsets.UTF_8);
                intToBytes(bytes.length, head);
                outputStream.write(head);
                outputStream.flush();
                outputStream.write(bytes);
                outputStream.flush();
                byte[] buf = new byte[8];
                inputStream.read(buf); // 错误信息长度不为0，报错
                if (0 != bytesToInt(buf, 0)) {
                    byte[] errBuf = new byte[bytesToInt(buf, 0)];
                    inputStream.read(errBuf);
                    throw new RuntimeException("recovery failed");
                }
            } finally {
                byte[] endBuf = new byte[]{0, 0, 0, 0, 0, 0, 4, 0};
                try {
                    outputStream.write(endBuf);
                    outputStream.flush();
                    socket.close();
                } catch (Exception e) {
                    System.err.println("===");
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            if (socket.isConnected()) {
                socket.close();
            }
        }
    }

    public static void intToBytes(int value, byte[] src) {
        src[0] = (byte) ((value >> 24) & 0xFF);
        src[1] = (byte) ((value >> 16) & 0xFF);
        src[2] = (byte) ((value >> 8) & 0xFF);
        src[3] = (byte) (value & 0xFF);
    }

    public static int bytesToInt(byte[] src, int offset) {
        int value;
        value = (int) (((src[offset] & 0xFF) << 24)

                | ((src[offset + 1] & 0xFF) << 16)

                | ((src[offset + 2] & 0xFF) << 8)

                | (src[offset + 3] & 0xFF));
        return value;
    }
}
