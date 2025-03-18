package io.github.xqplus.virt.casuis;

import java.io.IOException;

public class CreateVirtualMachine {

    public static void main(String[] args) throws IOException {
//        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//        credentialsProvider.setCredentials(
//                new AuthScope("192.168.8.171", 8080, "VMC RESTful Web Services"),
//                new UsernamePasswordCredentials("admin", "Dsg_123456!")
//        );
//        CloseableHttpClient client = HttpClients.custom()
//                .setDefaultCredentialsProvider(credentialsProvider).build();
//
//        String config = VirtualMachineConfigConverter.vSphereToCasUis("E:\\centos7.conf", "测试 虚拟机1",
//                2L, 30, 2L, "vswitch0", 1500L, false, "isopool");
//
//        HttpPost httpPost = new HttpPost("http://192.168.8.171:8080/cas/domain/add");
//        httpPost.addHeader("Content-Type", "application/json");
//        httpPost.addHeader("accept", "application/json");
//        httpPost.addHeader("Accept-Encoding", "UTF-8");
//        httpPost.setEntity(new StringEntity(config));
//        CloseableHttpResponse response = client.execute(httpPost);
//        if (response.getStatusLine().getStatusCode() == 409) {
//            Header[] headers = response.getHeaders("Error-Code");
//            System.err.println(Arrays.toString(headers));
//            String reason = new String(Arrays.toString(response.getHeaders("Error-Message")).getBytes(StandardCharsets.ISO_8859_1), "GBK");
//            reason = new String(reason.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
//            reason = new String(reason.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
//            System.err.println(reason);
//        } else {
//            System.out.println(EntityUtils.toString(response.getEntity()));
//        }
    }
}
