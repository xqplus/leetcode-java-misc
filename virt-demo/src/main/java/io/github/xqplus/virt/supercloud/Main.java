package io.github.xqplus.virt.supercloud;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import okhttp3.*;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;
import java.io.IOException;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final String ADDR = "https://192.168.8.125";
    private static final String SESSION_ID = "pma2gnrd1jx2xcjkc2qau4ofq110pv15";
    private static final String NAME_IDX = "05";

    public static void main(String[] args) throws Exception {
//        login();
        switchProject("92e32da62c52460e958545685ee14fd3");

        // 创建虚拟机
//        createVmBfc();
//        createBootableVolume();
//        createVmCombination();

//        httpGet("/portal/api/instances/availability_zone");

        // 云主机
//        createVmBfc();
//        createVmV2();
//        httpGet("/portal/api/instances");
//        httpGet("/portal/api/instances/3b651ab9-8f96-448b-b77a-32877c8a02ba");
//        httpGet("/portal/api/instances/b35f4be8-1812-4fa2-bc11-89d69b9248bb");
//        httpGet("/portal/api/instances/flavors");
//        httpGet("/portal/api/instances/flavors/d274f593-99de-4e50-bd2e-982a1717e008");
//        attachDataVolume();
//        int i = 0;
//        while (i < 10) {
//            Thread.sleep(1000);
//            httpGet("/portal/api/instances/c01df1be-51a3-4303-9932-1b31c613bfa1");
//            httpGet("/portal/api/instances/bfc/c01df1be-51a3-4303-9932-1b31c613bfa1/get_volume");
//            i++;
//        }
//        httpPost("/portal/api/instances/5d0548d6-ad56-4e55-bbe0-cae113ee4116/stop", "{}");

//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("delete_on_terminate", "true");
//        httpDelete("/portal/api/instances/bfc/4e4ed606-9add-4264-88c5-73289a142604", jsonObject.toString());

        // 网络
//        httpGet("/portal/api/networks/available_networks");
//        httpGet("/portal/api/networks/security_groups");

        // 镜像
//        httpGet("/portal/api/images/available_images");
//        httpGet("/portal/api/images");

        // 云硬盘
//        httpGet("/portal/api/volumes/volume_types");
        httpGet("/portal/api/instances/bfc/f016ba98-d688-4176-bf8f-0beb777e59ff/get_volume");
//        httpGet("/portal/api/instances/f11bac6d-892e-4954-8eb0-81e265a26303/volumes");
//        httpGet("/portal/api/tasks/7b33d5f3-3eb8-46a0-a05f-7687687c6614");
//        httpGet("/portal/api/volumes/b7442b45-f4f5-410e-8b4a-578753288eb1");
//        createVolume();
    }

    private static void createBootableVolume() throws IOException {
        JSONObject volume = new JSONObject();
//        volume.put("description", "");
//        volume.put("display_volume_type", "");
        volume.put("image_id", "fa3fd03e-62f5-40d3-96d0-6a1caa91e697"); // boot_image_linux
        volume.put("is_bfc", true);
        volume.put("multiattach", false);
        volume.put("name", "bootable-" + NAME_IDX);
        volume.put("size", 5);
        volume.put("volume_source_type", "bfc_source");
        volume.put("volume_type", "d65e63b5-8c53-4755-a082-e9b8c0d7c951"); // Ceph

        httpPost("/portal/api/volumes", volume.toString());
    }

    private static void createVolume() throws IOException {
        JSONObject volume = new JSONObject();
        volume.put("name", "system-01");
        volume.put("description", "");
        volume.put("multiattach", false);
        volume.put("volume_type", "d65e63b5-8c53-4755-a082-e9b8c0d7c951"); // Ceph
        volume.put("display_volume_type", "");
        volume.put("size", 5); // GB
        volume.put("is_iso", false);
        httpPost("/portal/api/volumes", volume.toString());
    }

    private static void attachDataVolume() throws IOException {
        JSONObject jo = new JSONObject();
        jo.put("server_id", "c01df1be-51a3-4303-9932-1b31c613bfa1");
        httpPost("/portal/api/volumes/79594f0a-8385-4420-bd75-b87b4f7f0391/attach", jo.toString());
    }

    private static void createVmBfc() throws IOException {
        JSONObject vmInfo = new JSONObject();
        vmInfo.put("availability_zone", "迪思杰POC");
        vmInfo.put("count", 1);
        vmInfo.put("create_volume", false);
//        vmInfo.put("data_volumes", new JSONArray());
        vmInfo.put("disk_num", 0);
        vmInfo.put("display_flavor_name", "1C1G");
        vmInfo.put("flavor_id", "d274f593-99de-4e50-bd2e-982a1717e008");
//        vmInfo.put("key_name", "");
        vmInfo.put("memory_num", 1024); // M
        vmInfo.put("name", "bfc-" + NAME_IDX);
//        vmInfo.put("password", "Admin@1234");
        vmInfo.put("processor_num", 1);
//        vmInfo.put("security_groups", new JSONArray());
        vmInfo.put("source_id", "5d164557-c5a1-4c34-906c-3e0837a8d52b");
        vmInfo.put("source_type", "instance_volume");
        JSONArray subnets = new JSONArray();
        subnets.add("5a661ff2-6caf-4d27-8839-07fc8c841add");
        vmInfo.put("subnets", subnets);
        vmInfo.put("username", "root");
        vmInfo.put("volume_size", "5");
//        vmInfo.put("volume_type", "d65e63b5-8c53-4755-a082-e9b8c0d7c951");

        httpPost("/portal/api/instances/bfc", vmInfo.toString());
    }

    private static void createVmCombination() throws IOException {
        JSONObject vmInfo = new JSONObject();
        vmInfo.put("availability_zone", "迪思杰POC");
        vmInfo.put("count", 1);
        vmInfo.put("create_volume", false);

        JSONArray dataVolumes = new JSONArray();
        JSONObject dataVolume1 = new JSONObject();
        dataVolume1.put("size", 1); // GB
        dataVolume1.put("volume_type", "d65e63b5-8c53-4755-a082-e9b8c0d7c951");
        dataVolumes.add(dataVolume1);
        JSONObject dataVolume2 = new JSONObject();
        dataVolume2.put("size", 2); // GB
        dataVolume2.put("volume_type", "d65e63b5-8c53-4755-a082-e9b8c0d7c951");
        dataVolumes.add(dataVolume2);
        vmInfo.put("data_volumes", dataVolumes);

        vmInfo.put("disk_num", 2);
        vmInfo.put("display_flavor_name", "1C1G");
        vmInfo.put("flavor_id", "d274f593-99de-4e50-bd2e-982a1717e008");
//        vmInfo.put("key_name", "");
        vmInfo.put("memory_num", 1024); // M
        vmInfo.put("name", "combination-" + NAME_IDX);

        JSONArray networks = new JSONArray();
        JSONObject network1 = new JSONObject();
//        network1.put("fixed_ip_address", "");
        network1.put("network_id", "45f074b3-78f4-4344-a59c-765dadf8a9f4");
        network1.put("subnet_id", "5a661ff2-6caf-4d27-8839-07fc8c841add");
        networks.add(network1);
        vmInfo.put("networks", networks);

//        vmInfo.put("password", "Admin@1234");
        vmInfo.put("processor_num", 1);
//        vmInfo.put("security_groups", new JSONArray());
        vmInfo.put("source_id", "cb4684e2-6b81-4b43-bea6-809c9310a070"); // 可启动云硬盘uuid
        vmInfo.put("source_type", "instance_volume");
        vmInfo.put("username", "root");
        vmInfo.put("volume_size", "5"); // GB
//        vmInfo.put("volume_type", "d65e63b5-8c53-4755-a082-e9b8c0d7c951"); // Ceph

        httpPost("/portal/api/instances/combination", vmInfo.toString());
    }

    private static void createVmV2() throws IOException {
        JSONObject vmInfo = new JSONObject();
        vmInfo.put("admin_user", "root");
        vmInfo.put("admin_pass", "Admin@1234");
        vmInfo.put("availability_zone", "迪思杰POC");
        vmInfo.put("catalog", "");
        vmInfo.put("count", 1);
        vmInfo.put("description", "Created by VBP");
        vmInfo.put("firmware_type", "bios");
        vmInfo.put("flavor_id", "d274f593-99de-4e50-bd2e-982a1717e008"); // 1C1G
        vmInfo.put("key_name", "");
        vmInfo.put("metadata", new JSONObject());
        vmInfo.put("name", "y0");

        JSONArray networks = new JSONArray();
        JSONObject network = new JSONObject();
        network.put("ip_address", "");
        network.put("mac_address", "");
        network.put("subnet_id", "5a661ff2-6caf-4d27-8839-07fc8c841add");
        network.put("network_id", "45f074b3-78f4-4344-a59c-765dadf8a9f4");
        network.put("security_group_ids", new JSONArray());
        network.put("floating_ip", new JSONObject());
        networks.add(network);
        vmInfo.put("networks", networks);

        JSONArray volumes = new JSONArray();
        JSONObject systemVolume = new JSONObject();
        systemVolume.put("size", "10");
//        systemVolume.put("source_id", "9a269899-28d3-4dff-95ca-2829517b4915");
        systemVolume.put("source_type", "blank");
        systemVolume.put("volume_type", "d65e63b5-8c53-4755-a082-e9b8c0d7c951"); // Ceph
        systemVolume.put("bootable", true);
        volumes.add(systemVolume);
//        JSONObject dataVolume = new JSONObject();
//        dataVolume.put("size", "1");
//        dataVolume.put("source_type", "blank");
//        dataVolume.put("volume_type", "84bffd8a-c32a-4975-bec2-ee777ab15f48");
//        volumes.add(dataVolume);
        vmInfo.put("volumes", volumes);

        httpPost("/portal/api/instances/v2", vmInfo.toString());
    }

    private static void httpGet(String reqPath) throws IOException {
        Request request = new Request.Builder()
                .url(ADDR + reqPath)
                .get()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Accept-Encoding", "gzip, deflate, br")
//                .addHeader("Connection", "keep-alive")
                .addHeader("Cookie", "sessionid=" + SESSION_ID)
                .build();
        Response response = getUnsafeOkHttpClient().newCall(request).execute();
        System.out.println(response.code());
        String res = response.body().string();
        if (res.startsWith("[")) {
            JSONArray ja = JSON.parseArray(res);
            System.out.println(ja.toString(SerializerFeature.PrettyFormat));
        } else {
            JSONObject jo1 = JSON.parseObject(res);
            System.out.println(jo1.toString(SerializerFeature.PrettyFormat));
        }
    }

    private static void httpPost(String reqPath, String reqParam) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, reqParam);
        Request request = new Request.Builder()
                .url(ADDR + reqPath)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Accept-Encoding", "gzip, deflate, br")
//                .addHeader("Connection", "keep-alive")
                .addHeader("Cookie", "sessionid=" + SESSION_ID)
                .build();
        Response response = getUnsafeOkHttpClient().newCall(request).execute();
        System.out.println(response.code());
        String res = response.body().string();
        if (res.startsWith("[")) {
            JSONArray ja = JSON.parseArray(res);
            System.out.println(ja.toString(SerializerFeature.PrettyFormat));
        } else {
            JSONObject jo1 = JSON.parseObject(res);
            System.out.println(jo1.toString(SerializerFeature.PrettyFormat));
        }
    }

    public static void httpDelete(String reqPath, String reqParam) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, reqParam);
        Request request = new Request.Builder()
                .url(ADDR + reqPath)
                .delete(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Accept-Encoding", "gzip, deflate, br")
//                .addHeader("Connection", "keep-alive")
                .addHeader("Cookie", "sessionid=" + SESSION_ID)
                .build();
        Response response = getUnsafeOkHttpClient().newCall(request).execute();
        System.out.println(response.code());
        String res = response.body().string();
        if (res.startsWith("[")) {
            JSONArray ja = JSON.parseArray(res);
            System.out.println(ja.toString(SerializerFeature.PrettyFormat));
        } else {
            JSONObject jo1 = JSON.parseObject(res);
            System.out.println(jo1.toString(SerializerFeature.PrettyFormat));
        }
    }

    private static void login() throws NoSuchAlgorithmException, KeyManagementException, IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "username=demo@demo.com&password=P@ss_w0rd");
        Request request = new Request.Builder()
                .url("https://192.168.8.125/mental/auth/login/")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Accept-Encoding", "gzip, deflate, br")
                .build();
        Response response = getUnsafeOkHttpClient().newCall(request).execute();
        System.out.println(response.code());
        for (String header : response.headers("Set-Cookie")) {
            System.out.println(header);
        }
//        String res = response.body().string();
//        if (res.startsWith("[")) {
//            JSONArray ja = JSON.parseArray(res);
//            System.out.println(ja.toString(SerializerFeature.PrettyFormat));
//        } else {
//            JSONObject jo1 = JSON.parseObject(res);
//            System.out.println(jo1.toString(SerializerFeature.PrettyFormat));
//        }
    }

    public static void switchProject(String projectId) throws IOException {
        Request request = new Request.Builder()
                .url(ADDR + "/mental/auth/switch/" + projectId)
                .get()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Accept-Encoding", "gzip, deflate, br")
//                .addHeader("Connection", "keep-alive")
                .addHeader("Cookie", "sessionid=" + SESSION_ID)
                .build();
        Response response = getUnsafeOkHttpClient().newCall(request).execute();
    }

    public static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // 创建一个信任所有证书的TrustManager
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509ExtendedTrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {

                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {

                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType, SSLEngine engine) throws CertificateException {

                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType, SSLEngine engine) throws CertificateException {

                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }
                    }
            };
            // 创建一个不验证证书的 SSLContext，并使用上面的TrustManager初始化
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // 使用上面创建的SSLContext创建一个SSLSocketFactory
            javax.net.ssl.SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509ExtendedTrustManager) trustAllCerts[0]);
            builder.hostnameVerifier((hostname, session) -> true);
            builder.readTimeout(1, TimeUnit.MINUTES);

            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
