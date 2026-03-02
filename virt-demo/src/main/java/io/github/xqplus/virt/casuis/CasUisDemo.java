package io.github.xqplus.virt.casuis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CasUisDemo {

    private static void get(String uri) throws IOException, URISyntaxException {
//        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//        credentialsProvider.setCredentials(
//                new AuthScope("192.168.8.171", 8080, "VMC RESTful Web Services"),
//                new UsernamePasswordCredentials("admin", "Dsg_123456!")
//        );
//        CloseableHttpClient client = HttpClients.custom()
//                .setDefaultCredentialsProvider(credentialsProvider).build();
        CloseableHttpClient client = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet("http://192.168.10.2:8080" + uri);
        httpGet.addHeader("accept", "application/json");
        CloseableHttpResponse response = client.execute(httpGet, login());

        if (response.getStatusLine().getStatusCode() == 409) {
            System.err.println(Arrays.toString(response.getHeaders("Error-Code")));
            String errorMessage = Arrays.toString(response.getHeaders("Error-Message"));
            // 原始为 GBK，这里使用 ISO_8859_1 解析了，反向还原
            errorMessage = new String(errorMessage.getBytes(StandardCharsets.ISO_8859_1), "GBK");
            System.err.println(errorMessage);
        } else {
            String result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8).replaceFirst("@", "");
            System.out.println(result);
            System.out.println(JSON.parseObject(result).toString(SerializerFeature.PrettyFormat));
        }
    }

    private static void delete(String uri) throws IOException {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                new AuthScope("192.168.8.171", 8080, "VMC RESTful Web Services"),
                new UsernamePasswordCredentials("admin", "Dsg_123456!")
        );
        CloseableHttpClient client = HttpClients.custom()
                .setDefaultCredentialsProvider(credentialsProvider).build();

        HttpDelete httpDelete = new HttpDelete("http://192.168.8.171:8080" + uri);
        httpDelete.addHeader("accept", "application/json");
        CloseableHttpResponse response = client.execute(httpDelete);

        if (response.getStatusLine().getStatusCode() == 409) {
            System.err.println(Arrays.toString(response.getHeaders("Error-Code")));
            String errorMessage = Arrays.toString(response.getHeaders("Error-Message"));
            // 原始为 GBK，这里使用 ISO_8859_1 解析了，反向还原
            errorMessage = new String(errorMessage.getBytes(StandardCharsets.ISO_8859_1), "GBK");
            System.err.println(errorMessage);
        } else {
            String result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8).replaceFirst("@", "");
            System.out.println(result);
            System.out.println(JSON.parseObject(result).toString(SerializerFeature.PrettyFormat));
        }
    }

    private static void post(String uri, String bodyStr) throws IOException, URISyntaxException {
//        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//        credentialsProvider.setCredentials(
//                new AuthScope("192.168.8.171", 8080, "VMC RESTful Web Services"),
//                new UsernamePasswordCredentials("admin", "Dsg_123456!")
//        );
//        CloseableHttpClient client = HttpClients.custom()
//                .setDefaultCredentialsProvider(credentialsProvider).build();
        CloseableHttpClient client = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost("http://192.168.8.171:8080" + uri);
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.addHeader("accept", "application/json");
        httpPost.addHeader("Accept-Encoding", "UTF-8");
        httpPost.setEntity(new StringEntity(bodyStr, StandardCharsets.UTF_8));
        CloseableHttpResponse response = client.execute(httpPost, login());

        if (response.getStatusLine().getStatusCode() == 409) {
            System.err.println(Arrays.toString(response.getHeaders("Error-Code")));
            String errorMessage = Arrays.toString(response.getHeaders("Error-Message"));
            // 原始为 GBK，这里使用 ISO_8859_1 解析了，反向还原
            errorMessage = new String(errorMessage.getBytes(StandardCharsets.ISO_8859_1), "GBK");
            System.err.println(errorMessage);
        } else {
            System.out.println(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
        }
    }

    private static void put(String uri, String str) throws IOException, URISyntaxException {
//        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//        credentialsProvider.setCredentials(
//                new AuthScope("192.168.8.171", 8080, "VMC RESTful Web Services"),
//                new UsernamePasswordCredentials("admin", "Dsg_123456!")
//        );
//        CloseableHttpClient client = HttpClients.custom()
//                .setDefaultCredentialsProvider(credentialsProvider).build();

        CloseableHttpClient client = HttpClients.createDefault();

        HttpPut httpPut = new HttpPut("http://192.168.8.171:8080" + uri);
        httpPut.addHeader("Content-Type", "application/json");
        httpPut.addHeader("accept", "application/json");
        httpPut.addHeader("Accept-Encoding", "UTF-8");
        httpPut.setEntity(new StringEntity(str, StandardCharsets.UTF_8));
        CloseableHttpResponse response = client.execute(httpPut, login());

        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println(statusCode);
        if (statusCode == 409) {
            System.err.println(Arrays.toString(response.getHeaders("Error-Code")));
            String errorMessage = Arrays.toString(response.getHeaders("Error-Message"));
            // 原始为 GBK，这里使用 ISO_8859_1 解析了，反向还原
            errorMessage = new String(errorMessage.getBytes(StandardCharsets.ISO_8859_1), "GBK");
            System.err.println(errorMessage);
        } else {
            System.out.println(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
        }
    }

    private static void addVSwitch() throws IOException, URISyntaxException {
        JSONObject vSwitchInfo = new JSONObject();
        vSwitchInfo.put("hostId", 1);
        vSwitchInfo.put("name", "vs96"); // 名称的最大输入长度为15个字符，且只允许输入字母、数字、减号和下划线。
        vSwitchInfo.put("networkType", 32); // 其他网络，这里与接口文档说法不一致，实际应该是按全排列编号的
        vSwitchInfo.put("mode", 0); // VEB
        vSwitchInfo.put("mtu", 1500);
        vSwitchInfo.put("multicast", false);
        vSwitchInfo.put("enableDpdk", false);
        vSwitchInfo.put("portNum", 32);
        post("/cas/casrs/vswitch", vSwitchInfo.toString());
    }

    private static void deleteVSwitch() throws IOException {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                new AuthScope("192.168.8.171", 8080, "VMC RESTful Web Services"),
                new UsernamePasswordCredentials("admin", "Dsg_123456!")
        );
        CloseableHttpClient client = HttpClients.custom()
                .setDefaultCredentialsProvider(credentialsProvider).build();

        HttpDelete httpDelete = new HttpDelete("http://192.168.8.171:8080/cas/casrs/vswitch/13");
        httpDelete.addHeader("accept", "application/json");
        CloseableHttpResponse response = client.execute(httpDelete);

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 204) {
            if (statusCode == 409) {
                String errorCode = Arrays.toString(response.getHeaders("Error-Code"));
                String errorMessage = Arrays.toString(response.getHeaders("Error-Message"));
                // 原始为 GBK，这里使用 ISO_8859_1 解析了，反向还原
                errorMessage = new String(errorMessage.getBytes(StandardCharsets.ISO_8859_1), "GBK");
                throw new RuntimeException("发生业务性错误：" + errorCode + ", " + errorMessage);
            } else {
                throw new RuntimeException("发生未知错误，http状态码：" + statusCode);
            }
        }
    }

    private static void addNfsStoragePool() throws IOException, URISyntaxException {
        JSONObject nfsStoragePoolAdd = new JSONObject();
        nfsStoragePoolAdd.put("hostId", 1);
        nfsStoragePoolAdd.put("type", "netfs");
        nfsStoragePoolAdd.put("name", "testNfs");
        nfsStoragePoolAdd.put("title", "testNfs");
        nfsStoragePoolAdd.put("path", "/vms/testNfs");
        nfsStoragePoolAdd.put("hostIp", "192.168.8.152");
        nfsStoragePoolAdd.put("remoteDir", "/storage/mnt/arv/casuis");
        nfsStoragePoolAdd.put("autoStart", true); // 这个字段经验证不起作用
        post("/cas/casrs/storage/add", nfsStoragePoolAdd.toString());
    }

    private static void addVm() throws IOException, URISyntaxException {
        // 按照平台新增虚拟机窗口默认配置和7.0接口文档生成，可能不适配5.0
        JSONObject vmAddInfo = new JSONObject();
        vmAddInfo.put("hostId", 1);
        vmAddInfo.put("title", "api");
        vmAddInfo.put("name", "api");
        vmAddInfo.put("system", 1);
        vmAddInfo.put("osVersion", "CentOS 6/7(64-bit)");
        vmAddInfo.put("autoMigrate", 0);
        vmAddInfo.put("autoTools", 1);

        vmAddInfo.put("cpuSockets", 1);
        vmAddInfo.put("cpuCores", 1);
        vmAddInfo.put("cpuMode", "custom");
        vmAddInfo.put("osBit", "x86_64");
        vmAddInfo.put("cpuShares", 512);
        vmAddInfo.put("cpuGurantee", 0);
        vmAddInfo.put("domainCpuGlobalQuota", false);
        vmAddInfo.put("enableReduceCPU", false);
        vmAddInfo.put("blkiotune", 300);

        vmAddInfo.put("memory", 1024);
        vmAddInfo.put("memoryInit", 1);
        vmAddInfo.put("memoryUnit", "GB");
        vmAddInfo.put("memoryBacking", 0);
        vmAddInfo.put("memoryPriority", 0);
        vmAddInfo.put("autoMem", 0);
        vmAddInfo.put("hugepage", false);

        // 除上述窗口配置项以外的必填字段
        vmAddInfo.put("formatEnable", 1);
        vmAddInfo.put("maxCpuSocket", 20);

        List<JSONObject> vmNetworkAddInfos = new ArrayList<>();

        JSONObject vmNetworkAddInfo = new JSONObject();
        vmNetworkAddInfo.put("vsId", 1);
        vmNetworkAddInfo.put("vsName", "vswitch0");
        vmNetworkAddInfo.put("profileId", 1);
        vmNetworkAddInfo.put("deviceModel", "virtio");
        vmNetworkAddInfo.put("isKernelAccelerated", 1);
        vmNetworkAddInfo.put("mtu", 1500);
        vmNetworkAddInfos.add(vmNetworkAddInfo);

        JSONObject vmNetworkAddInfo1 = new JSONObject();
        vmNetworkAddInfo1.put("vsId", 16);
        vmNetworkAddInfo1.put("vsName", "vswitch0-rs");
        vmNetworkAddInfo1.put("profileId", 1);
        vmNetworkAddInfo1.put("deviceModel", "virtio");
        vmNetworkAddInfo1.put("isKernelAccelerated", 1);
        vmNetworkAddInfo1.put("mtu", 1500);
        vmNetworkAddInfos.add(vmNetworkAddInfo1);

        vmAddInfo.put("network", vmNetworkAddInfos);

        List<JSONObject> vmStorageAddInfos = new ArrayList<>();
        JSONObject vmDiskAddInfo = new JSONObject();
        vmDiskAddInfo.put("device", "disk");
        vmDiskAddInfo.put("capacity", 1024 * 4); // MB
        vmDiskAddInfo.put("type", "file");
        vmDiskAddInfo.put("poolName", "VBP_ARV_192.168.8.152");
        vmDiskAddInfo.put("mode", 2);
        vmDiskAddInfo.put("clusterSize", 262144);
        vmDiskAddInfo.put("targetBus", "virtio");
        vmDiskAddInfo.put("cacheType", "directsync");
        vmDiskAddInfo.put("diskMode", "subordinate");
        vmDiskAddInfo.put("driveType", "qcow2");
//        vmDiskAddInfo.put("priority", 3);
        vmDiskAddInfo.put("assignType", 0);
        vmStorageAddInfos.add(vmDiskAddInfo);
        JSONObject vmCdromAddInfo = new JSONObject();
        vmCdromAddInfo.put("device", "cdrom");
        vmCdromAddInfo.put("storeFile", "/vms/VBP_ARV_192.168.8.152/d22c8e1c-cbdf-487c-b6d9-67522a9af0d9.iso");
        vmStorageAddInfos.add(vmCdromAddInfo);
        vmAddInfo.put("storage", vmStorageAddInfos);

        post("/cas/casrs/vm/add", vmAddInfo.toString());
    }

    public static HttpClientContext login() throws URISyntaxException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost();
        HttpClientContext context = new HttpClientContext();
        httpPost.setURI(new URI("http://192.168.10.2:8080/cas/spring_check?t="
                + System.currentTimeMillis() + "&encrypt=false&lang=cn&name=admin&password=Dsg_123456!"));
        httpPost.addHeader("accept", "application/json");
        CloseableHttpResponse response = httpClient.execute(httpPost, context);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {
            String reason = "";
            if (statusCode == 409) {
                reason = new String(Arrays.toString(response.getHeaders("Error-Message")).getBytes(StandardCharsets.ISO_8859_1), "GBK");
                reason = new String(reason.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
                reason = new String(reason.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            }
            httpClient.close();
            throw new HttpResponseException(statusCode, "Http response error occurs: [" + statusCode + ", " + reason + "]");
        }
        String result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        httpClient.close();
        if (!result.contains("\"loginFailErrorCode\":0")) {
            return null;
        }
        return context;
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        // 虚拟机
//        get("/cas/casrs/vm/vmList?hostId=1");
        get("/cas/casrs/vm/detail/4");
//        addVm();
//        get("/cas/casrs/vm/89");
//        get("/cas/casrs/vm/net/info?hostId=1");
//        get("/cas/domain/27/domainDetail");
//        modifyVm();
//        delete("/cas/casrs/vm/delete/36");
//        put("/cas/casrs/vm/start/1", "{}");
//        put("/cas/casrs/vm/stop/41", "{}");

        // 查询任务详细信息
//        get("/cas/casrs/message/1745313615736");

        // 存储池
//        get("/cas/casrs/storage/pool?hostId=1");
//        get("/cas/casrs/storage/info?hostId=1&poolName=VBP_ARV_192.168.8.152_FUSE");
//        addNfsStoragePool();
//        get("/cas/casrs/storage/start?id=1&poolName=testNfs&hostName=cvknode");
//        get("/cas/casrs/storage/volume?hostId=1&poolName=VBP_ARV_192.168.8.152_FUSE");
//        get("/cas/casrs/storage/volume?hostId=1&poolName=VBP_ARV_192.168.8.152&offset=1&pageSize=1");
//        delete("/cas/casrs/storage/delete?id=1&poolName=sp_192.168.8.17_0");

        // 虚拟交换机
//        get("/cas/casrs/host/id/1/vswitch");
//        get("/cas/casrs/host/id/1/vswitch/99");
//        get("/cas/casrs/vswitch?hostId=1");
//        get("/cas/casrs/vswitch/vport?hostId=1&vsName=vswitch0");
//        deleteVSwitch();

        // 增加虚拟交换机
//        addVSwitch();

        // 主机
//        get("/cas/casrs/host");
//        get("/cas/casrs/host/id/1");
    }
}
