package io.github.xqplus.virt.vsphere;

import io.github.xqplus.virt.common.LenUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class fuse {

    public static void main(String[] args) throws IOException {
        List<String> fuseDiskFileNames = new ArrayList<>();
        fuseDiskFileNames.add("/storage/mnt/fuse/b13fd431-d549-4608-83fd-7d3936603a64/centos7_test_2disk_20250829172913_2000");
        fuseDiskFileNames.add("/storage/mnt/fuse/b13fd431-d549-4608-83fd-7d3936603a64/centos7_test_2disk_20250829172913_2001");
        try (Socket socket = new Socket()) {
            socket.setKeepAlive(true);
            socket.connect(new InetSocketAddress("192.168.8.169", 1999), 2000);
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();

            byte[] head = {0, 0, 0, 0, 0, 0, 2, 0}; // REMOVE_DISK
            for (String fuseDiskFileName : fuseDiskFileNames) {
                String targetFileName = fuseDiskFileName.substring("/storage/mnt/fuse".length());
                byte[] bytes = targetFileName.getBytes(StandardCharsets.UTF_8);
                LenUtils.intToBytes(bytes.length, head);
                outputStream.write(head);
                outputStream.flush();
                outputStream.write(bytes);
                outputStream.flush();

                byte[] buffer = new byte[8];
                inputStream.read(buffer);
                int length = LenUtils.bytesToInt(buffer, 0);
                if (length > 0) {
                    buffer = new byte[length];
                    inputStream.read(buffer);
                    throw new RuntimeException(new String(buffer, StandardCharsets.UTF_8));
                }
            }
            head = new byte[]{0, 0, 0, 0, 0, 0, 5, 0}; // REMOVE_DISK_END
            outputStream.write(head);
            outputStream.flush();
        }
    }
}
