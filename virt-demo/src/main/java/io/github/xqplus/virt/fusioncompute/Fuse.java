package io.github.xqplus.virt.fusioncompute;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Fuse {

    public static void main(String[] args) throws IOException {
//        addDiskLink();
        removeDiskLink();
    }

    private static void addDiskLink() throws IOException {
        Socket socket = new Socket();
        socket.setKeepAlive(true);
        socket.connect(new InetSocketAddress("192.168.8.151", 1999), 2000);
        OutputStream outputStream = socket.getOutputStream();
        InputStream inputStream = socket.getInputStream();

        byte[] head = {0, 0, 0, 0, 0, 0, 1, 0}; // TRANS_FILE_PATH
        JSONObject socketMessage = new JSONObject();

        // 公共消息
        socketMessage.put("version_path", "/storage/data/a5198e46-4e43-4816-a03b-f7db18d733af");
        socketMessage.put("next", 0);
        socketMessage.put("last", 0);
        socketMessage.put("recover_path", "/storage/v2v-test");
        socketMessage.put("compress", 0);
        socketMessage.put("vmType", 0);  // 0: hw 1: h3c 2: vmware

        // 第一块盘
        socketMessage.put("id", 0);
        socketMessage.put("target_name", "");
        socketMessage.put("disk_path", "/c7/c7_0.raw");
        socketMessage.put("cache_path", "/storage/v2v-test/0.cache");
        socketMessage.put("dump_path", "/storage/data/a5198e46-4e43-4816-a03b-f7db18d733af/backup/0.dump");
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
            System.err.println(new String(buffer, StandardCharsets.UTF_8));
            return;
        }

        // 第二块盘
        socketMessage.put("id", 1);
        socketMessage.put("disk_path", "/c7/c7_1.raw");
        socketMessage.put("cache_path", "/storage/v2v-test/1.cache");
        socketMessage.put("dump_path", "/storage/data/a5198e46-4e43-4816-a03b-f7db18d733af/backup/1.dump");
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
            System.err.println(new String(buffer, StandardCharsets.UTF_8));
            return;
        }

        head = new byte[]{0, 0, 0, 0, 0, 0, 4, 0}; // TRANS_FILE_END
        outputStream.write(head);
        outputStream.flush();
    }

    private static void removeDiskLink() throws IOException {
        Socket socket = new Socket();
        socket.setKeepAlive(true);
        socket.connect(new InetSocketAddress("192.168.8.152", 1999), 5000);
        OutputStream outputStream = socket.getOutputStream();
        InputStream inputStream = socket.getInputStream();
        byte[] head = new byte[]{0, 0, 0, 0, 0, 0, 2, 0}; // REMOVE_DISK

        // 第一块盘
        String diskPath = "/arv/fusioncompute/vol/vol_aa7f8e1d-592b-44d2-9fd1-869bb2c8cce1/vol_aa7f8e1d-592b-44d2-9fd1-869bb2c8cce1.img";
        intToBytes(diskPath.length(), head);
        outputStream.write(head);
        outputStream.flush();
        outputStream.write(diskPath.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();

        byte[] buffer = new byte[8];
        inputStream.read(buffer);
        int length;
        if ((length = bytesToInt(buffer, 0)) != 0) { // 错误信息长度不为0，链接失败
            buffer = new byte[length];
            inputStream.read(buffer);
            System.err.println(new String(buffer, StandardCharsets.UTF_8));
            return;
        }

        head = new byte[]{0, 0, 0, 0, 0, 0, 5, 0}; // REMOVE_DISK_END
        outputStream.write(head);
        outputStream.flush();
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
