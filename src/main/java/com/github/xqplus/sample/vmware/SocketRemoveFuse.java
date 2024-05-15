package com.github.xqplus.sample.vmware;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SocketRemoveFuse {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        try {
            socket.setKeepAlive(true);
            socket.connect(new InetSocketAddress("192.168.8.151", 1999), 5000);
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();

            byte[] head = new byte[]{0, 0, 0, 0, 0, 0, 2, 0};
            try {
                String diskPath = "/ffbddce6-9d2f-4209-96ab-fa6894b6af8c/image_2000-flat.vmdk";
                intToBytes(diskPath.length(), head);
                outputStream.write(head);
                outputStream.flush();
                outputStream.write(diskPath.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
                byte[] buf = new byte[8];
                inputStream.read(buf);
                if (0 != bytesToInt(buf, 0)) {
                    byte[] errBuf = new byte[bytesToInt(buf, 0)];
                    inputStream.read(errBuf);
                    throw new RuntimeException("stop recovery rapid failed");
                }
            } finally {
                // 给fuse发送 REMOVED_DISK_END 标记
                byte[] endBytes = new byte[]{0, 0, 0, 0, 0, 0, 5, 0};
                outputStream.write(endBytes);
                outputStream.flush();
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
