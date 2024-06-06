package com.github.xqplus.sample.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Process {

    public static void main(String[] args) throws IOException, NoSuchFieldException, IllegalAccessException {
//        Path path = Paths.get("E:\\a");
//        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path)) {
//            for (Path path1 : directoryStream) {
//                Files.delete(path1);
//            }
//        }

        Socket socket = new Socket();
        socket.setKeepAlive(true);
        socket.connect(new InetSocketAddress("192.168.8.151", 1999), 5000);
        OutputStream outputStream = socket.getOutputStream();
        InputStream inputStream = socket.getInputStream();
        byte[] head = new byte[]{0, 0, 0, 0, 0, 0, 2, 0}; // REMOVE_DISK


        String disk = "/c71c52bd-d7c9-4962-97cd-8f4de459f04d/image_2000-flat.vmdk";
        byte[] bytes = disk.getBytes(StandardCharsets.UTF_8);
        LenUtils.intToBytes(bytes.length, head);
        outputStream.write(head);
        outputStream.flush();
        outputStream.write(bytes);
        outputStream.flush();
        byte[] buf = new byte[8];
        inputStream.read(buf);
        if (0 != LenUtils.bytesToInt(buf, 0)) {
            byte[] errBuf = new byte[LenUtils.bytesToInt(buf, 0)];
            inputStream.read(errBuf);
            System.err.println("xxx");
        }
    }
}
