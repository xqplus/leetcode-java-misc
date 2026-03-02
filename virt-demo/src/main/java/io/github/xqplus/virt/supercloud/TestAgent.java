package io.github.xqplus.virt.supercloud;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class TestAgent {

    public static void main(String[] args) throws IOException {
        Socket socket = getSocket();
        String uuid = "c1fc0dfe-2885-440f-b71c-a1069cce22fb";
//        String cmd = "rbd feature disable volumes/volume-" + uuid + " object-map fast-diff deep-flatten; rbd map volumes/volume-" + uuid;
        String cmd = "rbd unmap /dev/rbd0";
        Union<byte[], String> stringUnion = executeHyperVCommand(socket, cmd, "UTF-8");
        for (byte b : stringUnion.p1) {
            System.out.println(b);
        }
        System.out.println(stringUnion.p2);
    }

    public static Socket getSocket() throws SocketException {
        // 改造成需要验证连接的
        InetSocketAddress addr = new InetSocketAddress("192.168.8.125", 5033); // 注意端口号
        Socket socket = new Socket();
        socket.setKeepAlive(true);
        try {
            // 设置socket连接的超时时间 5秒 ，超过这个时间还没有连接上socket的话，抛出异常
            socket.connect(addr, 5000);
            // socket连接都是为了发送命令的，所以这里先将第一个头发送过去，后续就只需要发送命令了，executeHyperVCommand()方法中
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            // 这里需要读取返回的随机值，4位byte
            byte[] rdByte = new byte[4];
            inputStream.read(rdByte);
//        byte[] authPass = AuthUtils.getAuthPass(rdByte, "BKP", new byte[]{0,0,0,0});
            byte[] authPass = AuthUtils.getAuthPass(rdByte, "BKP", new byte[]{0, 0, 0, 0});
            outputStream.write(authPass);
            outputStream.flush();
            // 读取认证结果的返回值
            byte[] b4 = new byte[4];
            inputStream.read(b4);
            int respCode = LenUtils.bytesToInt(b4, 0);
            if (0 != respCode) {
                System.err.println("0");
            }
            byte[] head1 = new byte[]{0, 0, 0, 0, 0, 0, 2, 0};
            outputStream.write(head1);
            outputStream.flush();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return socket;
    }

//    public static String getEncoding(Socket socket) throws IOException {
//        String cmd = "chcp";
//        Union<byte[], String> union = executeHyperVCommand(socket, cmd, "GBK");
//        if (union.p1[6] != 6) {
//            return "GBK";
//        }
//        String[] s = union.p2.split(": ");
//        String encodingNum = s[s.length - 1].trim();
//        if (encodingNum.equals("65001")) {
//            return "UTF-8";
//        } else {
//            return "GBK";
//        }
//    }

    public static Union<byte[], String> executeHyperVCommand(Socket socket, String cmd, String encoding) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        InputStream inputStream = socket.getInputStream();
        byte[] head2 = new byte[]{0, 0, 0, 0, 0, 0, 8, 0};
//        byte[] bytes = cmd.getBytes(StandardCharsets.UTF_8);
        byte[] bytes = cmd.getBytes(encoding);
        LenUtils.intToBytes(bytes.length, head2);

        outputStream.write(head2);
        outputStream.flush();
        outputStream.write(bytes);
        outputStream.flush();
        byte[] buf = new byte[8];
        inputStream.read(buf);
        int len = LenUtils.bytesToInt(buf, 0);
        byte[] responseBuf = new byte[len];
        int readCount = 0;
        while (readCount < len) {
            readCount += inputStream.read(responseBuf, readCount, len - readCount);
        }

        String response = new String(responseBuf, encoding);
        return new Union<>(buf, response);
    }
}
