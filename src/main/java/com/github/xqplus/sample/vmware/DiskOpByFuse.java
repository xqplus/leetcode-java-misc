package com.github.xqplus.sample.vmware;

import com.alibaba.fastjson2.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class DiskOpByFuse {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        try {
            socket.setKeepAlive(true);
            socket.connect(new InetSocketAddress("192.168.8.151", 1999), 2000);
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();

            byte[] head = {0, 0, 0, 0, 0, 0, 1, 0}; // TRANS_FILE_PATH
            JSONObject socketMessage = new JSONObject();
            // 公共消息
            socketMessage.put("version_path", "/storage/data/6d5a3459-b1ee-4eaf-a8f2-223c4786db2d");
            socketMessage.put("next", 0);
            socketMessage.put("last", 0);
            socketMessage.put("recover_path", "/storage/test-v2v");
            socketMessage.put("target_name", "");
            socketMessage.put("compress", 0);
            socketMessage.put("vmType", 1);  // 0: hw 1: h3c 2: vmware

            // 第一块盘
            socketMessage.put("id", 0);
            socketMessage.put("disk_path", "/test-v2v/0.qcow2");
            socketMessage.put("cache_path", "/storage/test-v2v/0.cache");
            socketMessage.put("dump_path", "/storage/data/6d5a3459-b1ee-4eaf-a8f2-223c4786db2d/backup/0.dump");
            System.out.println(socketMessage);
            byte[] bytes = socketMessage.toString().getBytes(StandardCharsets.UTF_8);
            intToBytes(bytes.length, head);
            outputStream.write(head);
            outputStream.flush();
            outputStream.write(bytes);
            outputStream.flush();

            byte[] buffer = new byte[8];
            inputStream.read(buffer);
            int length;
            if ((length = bytesToInt(buffer, 0)) != 0) { // 错误信息长度不为0，链接失败
                buffer = new byte[length];
                inputStream.read(buffer);
                System.err.println("error: " + new String(buffer, StandardCharsets.UTF_8));
                // fuse端失败了会主动断开socket
                throw new RuntimeException("create virtual disk failed");
            }

            // 第二块盘
            socketMessage.put("id", 1);
            socketMessage.put("disk_path", "/test-v2v/1.qcow2");
            socketMessage.put("cache_path", "/storage/test-v2v/1.cache");
            socketMessage.put("dump_path", "/storage/data/6d5a3459-b1ee-4eaf-a8f2-223c4786db2d/backup/1.dump");
            System.out.println(socketMessage);
            bytes = socketMessage.toString().getBytes(StandardCharsets.UTF_8);
            intToBytes(bytes.length, head);
            outputStream.write(head);
            outputStream.flush();
            outputStream.write(bytes);
            outputStream.flush();

            buffer = new byte[8];
            inputStream.read(buffer);
            if ((length = bytesToInt(buffer, 0)) != 0) { // 错误信息长度不为0，链接失败
                buffer = new byte[length];
                inputStream.read(buffer);
                System.err.println("error: " + new String(buffer, StandardCharsets.UTF_8));
                // fuse端失败了会主动断开socket
                throw new RuntimeException("create virtual disk failed");
            }

            head = new byte[]{0, 0, 0, 0, 0, 0, 4, 0}; // TRANS_FILE_END
            outputStream.write(head);
            outputStream.flush();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
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
