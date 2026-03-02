package io.github.xqplus.virt.common;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Fuse {

    public static void main(String[] args) throws Exception {
        linkFilesToFuse();
    }

    private static void linkFilesToFuse() throws Exception {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("192.168.8.152", 1999), 5000);
            socket.setKeepAlive(true);
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();

            JSONObject socketMessage = new JSONObject();
            socketMessage.put("version_path", "/storage/data/128b52d7-faec-494f-bac2-01f5e25659a6");
            socketMessage.put("next", 0);
            socketMessage.put("last", 0);
            socketMessage.put("version_chain", "0");
            socketMessage.put("recover_path", "/storage/data/128b52d7-faec-494f-bac2-01f5e25659a6/v2v-test");
            socketMessage.put("compress", 0);
            socketMessage.put("vmType", 1);

            byte[] head = {0, 0, 0, 0, 0, 0, 1, 0};

            socketMessage.put("id", 0);
            socketMessage.put("target_name", "0");
            socketMessage.put("disk_path", "/t1/0.fuse");
            socketMessage.put("cache_path", "/storage/data/128b52d7-faec-494f-bac2-01f5e25659a6/v2v-test/0.cache");
            socketMessage.put("dump_path", "/storage/data/128b52d7-faec-494f-bac2-01f5e25659a6/v2v-test/0.ref");

            byte[] socketMessageBytes = socketMessage.toString().getBytes(StandardCharsets.UTF_8);
            LenUtils.intToBytes(socketMessageBytes.length, head);
            outputStream.write(head);
            outputStream.flush();
            outputStream.write(socketMessageBytes);
            outputStream.flush();

            byte[] buffer = new byte[8];
            int read = inputStream.read(buffer);
            int len = LenUtils.bytesToInt(buffer, 0);
            if (len != 0) { // 错误信息长度不为0，链接失败
                buffer = new byte[len];
                read = inputStream.read(buffer);
                // fuse端失败了会主动断开socket
                System.err.println("Link file to fuse failed: " + new String(buffer, StandardCharsets.UTF_8));
            }

            socketMessage.put("id", 1);
            socketMessage.put("target_name", "1");
            socketMessage.put("disk_path", "/t1/1.fuse");
            socketMessage.put("cache_path", "/storage/data/128b52d7-faec-494f-bac2-01f5e25659a6/v2v-test/1.cache");
            socketMessage.put("dump_path", "/storage/data/128b52d7-faec-494f-bac2-01f5e25659a6/v2v-test/1.ref");

            socketMessageBytes = socketMessage.toString().getBytes(StandardCharsets.UTF_8);
            LenUtils.intToBytes(socketMessageBytes.length, head);
            outputStream.write(head);
            outputStream.flush();
            outputStream.write(socketMessageBytes);
            outputStream.flush();

            buffer = new byte[8];
            read = inputStream.read(buffer);
            len = LenUtils.bytesToInt(buffer, 0);
            if (len != 0) { // 错误信息长度不为0，链接失败
                buffer = new byte[len];
                read = inputStream.read(buffer);
                // fuse端失败了会主动断开socket
                System.err.println("Link file to fuse failed: " + new String(buffer, StandardCharsets.UTF_8));
            }

            socketMessage.put("id", 2);
            socketMessage.put("target_name", "2");
            socketMessage.put("disk_path", "/t1/2.fuse");
            socketMessage.put("cache_path", "/storage/data/128b52d7-faec-494f-bac2-01f5e25659a6/v2v-test/2.cache");
            socketMessage.put("dump_path", "/storage/data/128b52d7-faec-494f-bac2-01f5e25659a6/v2v-test/2.ref");

            socketMessageBytes = socketMessage.toString().getBytes(StandardCharsets.UTF_8);
            LenUtils.intToBytes(socketMessageBytes.length, head);
            outputStream.write(head);
            outputStream.flush();
            outputStream.write(socketMessageBytes);
            outputStream.flush();

            buffer = new byte[8];
            read = inputStream.read(buffer);
            len = LenUtils.bytesToInt(buffer, 0);
            if (len != 0) { // 错误信息长度不为0，链接失败
                buffer = new byte[len];
                read = inputStream.read(buffer);
                // fuse端失败了会主动断开socket
                System.err.println("Link file to fuse failed: " + new String(buffer, StandardCharsets.UTF_8));
            }

            byte[] transEndBytes = {0, 0, 0, 0, 0, 0, 4, 0};
            outputStream.write(transEndBytes);
            outputStream.flush();
        }
    }
}
