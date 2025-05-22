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

        HttpGet httpGet = new HttpGet("http://192.168.8.171:8080" + uri);
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
        httpPost.setURI(new URI("http://192.168.8.171:8080/cas/spring_check?t="
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

    public static void modifyVm() throws IOException, URISyntaxException {
//        JSONObject vmModifyInfo = new JSONObject();
//        vmModifyInfo.put("id", 24);
//        JSONObject vmBasicModifyInfo = new JSONObject();
//        vmBasicModifyInfo.put("pae", 1);
//        vmBasicModifyInfo.put("acpi", 1);
//        vmBasicModifyInfo.put("apic", 1);
//        vmBasicModifyInfo.put("clock", "utc");
//        vmBasicModifyInfo.put("autoMigrate", 0);
//        vmBasicModifyInfo.put("blkiotune", 300);
//        vmBasicModifyInfo.put("autoMem", 0);
//        vmBasicModifyInfo.put("machineVersion", "pc-i440fx-1.5");
//        vmModifyInfo.put("basic", vmBasicModifyInfo);
//
//        put("/cas/casrs/vm/modify", vmModifyInfo);

//        JSONObject vmModifyInfo = new JSONObject();
//        vmModifyInfo.put("id", 24);
//        JSONObject summary = new JSONObject();
//        JSONObject detail = new JSONObject();
//        detail.put("machineVersion", "pc-i440fx-1.5");
//        summary.put("detail", detail);
//        vmModifyInfo.put("summary", summary);

        String s = "{\n" +
                "\t\t\"cvkKernelType\":16,\n" +
                "\t\t\"pciList\":[],\n" +
                "\t\t\"kaasVm\":0,\n" +
                "\t\t\"floppyList\":[],\n" +
                "\t\t\"clusterId\":1,\n" +
                "\t\t\"manageExistVm\":0,\n" +
                "\t\t\"operType\":0,\n" +
                "\t\t\"title\":\"rs-173\",\n" +
                "\t\t\"mouseList\":[\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"tabIndex\":\"8\",\n" +
                "\t\t\t\t\"detail\":{\n" +
                "\t\t\t\t\t\"bus\":\"ps2\",\n" +
                "\t\t\t\t\t\"model\":\"相对移动\",\n" +
                "\t\t\t\t\t\"type\":\"PS2鼠标\"\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t\"dispName\":\"鼠标\"\n" +
                "\t\t\t}\n" +
                "\t\t],\n" +
                "\t\t\"tabletList\":[\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"tabIndex\":\"7\",\n" +
                "\t\t\t\t\"detail\":{\n" +
                "\t\t\t\t\t\"bus\":\"usb\",\n" +
                "\t\t\t\t\t\"model\":\"绝对光标移动\",\n" +
                "\t\t\t\t\t\"type\":\"USB写字板\"\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t\"dispName\":\"写字板\"\n" +
                "\t\t\t}\n" +
                "\t\t],\n" +
                "\t\t\"vmType\":1,\n" +
                "\t\t\"netusbList\":[],\n" +
                "\t\t\"hostStatus\":1,\n" +
                "\t\t\"mem\":{\n" +
                "\t\t\t\"tabIndex\":\"2\",\n" +
                "\t\t\t\"detail\":{\n" +
                "\t\t\t\t\"hugepage\":false,\n" +
                "\t\t\t\t\"hotMem\":false,\n" +
                "\t\t\t\t\"memLimitUnit\":\"GB\",\n" +
                "\t\t\t\t\"maxValue\":60134,\n" +
                "\t\t\t\t\"memoryPriority\":0,\n" +
                "\t\t\t\t\"memoryLocked\":0,\n" +
                "\t\t\t\t\"curMemoryUnit\":\"GB\",\n" +
                "\t\t\t\t\"maxMemory\":60134,\n" +
                "\t\t\t\t\"curValue\":1.0,\n" +
                "\t\t\t\t\"autoMem\":false\n" +
                "\t\t\t},\n" +
                "\t\t\t\"dispName\":\"内存\"\n" +
                "\t\t},\n" +
                "\t\t\"videoList\":[\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"tabIndex\":\"10\",\n" +
                "\t\t\t\t\"detail\":{\n" +
                "\t\t\t\t\t\"devType\":\"cirrus\",\n" +
                "\t\t\t\t\t\"number\":1,\n" +
                "\t\t\t\t\t\"pciView\":\"0000.00.02.0\",\n" +
                "\t\t\t\t\t\"storage\":1,\n" +
                "\t\t\t\t\t\"heads\":\"1\"\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t\"dispName\":\"显卡 Vga\"\n" +
                "\t\t\t}\n" +
                "\t\t],\n" +
                "\t\t\"safety\":{\n" +
                "\t\t\t\"enableAntivirus\":false,\n" +
                "\t\t\t\"redirUsb\":0,\n" +
                "\t\t\t\"enableRedirectUsb\":false,\n" +
                "\t\t\t\"spiceChannels\":[],\n" +
                "\t\t\t\"tabIndex\":\"17\",\n" +
                "\t\t\t\"supportAntivirus\":false,\n" +
                "\t\t\t\"dispName\":\"高级设置\",\n" +
                "\t\t\t\"spiceTls\":false,\n" +
                "\t\t\t\"secretLevel\":1,\n" +
                "\t\t\t\"ifSupSpice\":false,\n" +
                "\t\t\t\"antivirusType\":\"0\"\n" +
                "\t\t},\n" +
                "\t\t\"watchdogList\":[],\n" +
                "\t\t\"id\":24,\n" +
                "\t\t\"boot\":{\n" +
                "\t\t\t\"tabIndex\":\"3\",\n" +
                "\t\t\t\"detail\":{\n" +
                "\t\t\t\t\"autoRun\":false,\n" +
                "\t\t\t\t\"bootList\":[\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"bootDevName\":\"高速磁盘(Virtio) vda\",\n" +
                "\t\t\t\t\t\t\"bootdev\":\"vda\",\n" +
                "\t\t\t\t\t\t\"order\":100\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"bootDevName\":\"光驱 hda\",\n" +
                "\t\t\t\t\t\t\"bootdev\":\"hda\",\n" +
                "\t\t\t\t\t\t\"order\":130\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"bootDevName\":\"网络0c:da:41:1d:c0:7e\",\n" +
                "\t\t\t\t\t\t\"bootdev\":\"0c:da:41:1d:c0:7e\",\n" +
                "\t\t\t\t\t\t\"order\":200\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"bootDevName\":\"网络0c:da:41:1d:f7:55\",\n" +
                "\t\t\t\t\t\t\"bootdev\":\"0c:da:41:1d:f7:55\",\n" +
                "\t\t\t\t\t\t\"order\":201\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"bootFirmware\":0\n" +
                "\t\t\t},\n" +
                "\t\t\t\"dispName\":\"引导设备\"\n" +
                "\t\t},\n" +
                "\t\t\"clusterInSafeArea\":false,\n" +
                "\t\t\"summary\":{\n" +
                "\t\t\t\"tabIndex\":\"0\",\n" +
                "\t\t\t\"detail\":{\n" +
                "\t\t\t\t\"autoTools\":false,\n" +
                "\t\t\t\t\"title\":\"rs-173\",\n" +
                "\t\t\t\t\"clockSetting\":\"utc\",\n" +
                "\t\t\t\t\"enableIntegrityCheck\":false,\n" +
                "\t\t\t\t\"pae\":true,\n" +
                "\t\t\t\t\"autoMigrate\":false,\n" +
                "\t\t\t\t\"haManage\":true,\n" +
                "\t\t\t\t\"castoolsType\":\"virtio\",\n" +
                "\t\t\t\t\"ioWeight\":300,\n" +
                "\t\t\t\t\"architecture\":\"X86_64\",\n" +
                "\t\t\t\t\"manager\":\"kvm\",\n" +
                "\t\t\t\t\"apic\":true,\n" +
                "\t\t\t\t\"timeSync\":false,\n" +
                "\t\t\t\t\"osha\":0,\n" +
                "\t\t\t\t\"machineVersion\":\"pc-i440fx-1.5\",\n" +
                "\t\t\t\t\"priority\":-1,\n" +
                "\t\t\t\t\"existController\":[\n" +
                "\t\t\t\t\t\"1\"\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"system\":1,\n" +
                "\t\t\t\t\"acpi\":true,\n" +
                "\t\t\t\t\"protectModel\":false,\n" +
                "\t\t\t\t\"name\":\"f45cc33d-a24f-4f32-bca8-f614a9e3c777\",\n" +
                "\t\t\t\t\"location\":\"/usr/bin/kvm\",\n" +
                "\t\t\t\t\"status\":\"shutOff\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"dispName\":\"概要\"\n" +
                "\t\t},\n" +
                "\t\t\"antivirusConfigured\":false,\n" +
                "\t\t\"numa\":{\n" +
                "\t\t\t\"tabIndex\":\"16\",\n" +
                "\t\t\t\"detail\":{\n" +
                "\t\t\t\t\"nodeSize\":1,\n" +
                "\t\t\t\t\"associate\":false\n" +
                "\t\t\t},\n" +
                "\t\t\t\"dispName\":\"NUMA\"\n" +
                "\t\t},\n" +
                "\t\t\"hostId\":1,\n" +
                "\t\t\"cpu\":{\n" +
                "\t\t\t\"cpudetail\":{\n" +
                "\t\t\t\t\"cpuShares\":512,\n" +
                "\t\t\t\t\"cpuGuarantee\":0,\n" +
                "\t\t\t\t\"bindPhysicalCpu\":[],\n" +
                "\t\t\t\t\"cpuMax\":2100.0,\n" +
                "\t\t\t\t\"cpuMaxRate\":2100,\n" +
                "\t\t\t\t\"cpuMode\":\"custom\",\n" +
                "\t\t\t\t\"cpuMin\":10.0,\n" +
                "\t\t\t\t\"cpuSocket\":1,\n" +
                "\t\t\t\t\"cpuOriMinRate\":10,\n" +
                "\t\t\t\t\"hostValue\":20,\n" +
                "\t\t\t\t\"cpuCore\":1,\n" +
                "\t\t\t\t\"enableEditReduce\":false,\n" +
                "\t\t\t\t\"maxValue\":20,\n" +
                "\t\t\t\t\"cpuGuaranMinRate\":0,\n" +
                "\t\t\t\t\"cpuMinRate\":10,\n" +
                "\t\t\t\t\"enableReduce\":false,\n" +
                "\t\t\t\t\"cpuArch\":\"x86_64\",\n" +
                "\t\t\t\t\"cpuQuotaType\":0,\n" +
                "\t\t\t\t\"curValue\":1,\n" +
                "\t\t\t\t\"cpuGuaranMaxRate\":2100,\n" +
                "\t\t\t\t\"cpuQuotaUnit\":\"MHz\",\n" +
                "\t\t\t\t\"cpuFeature\":false,\n" +
                "\t\t\t\t\"enableIncreaseCPU\":true,\n" +
                "\t\t\t\t\"maxCpuSocket\":20\n" +
                "\t\t\t},\n" +
                "\t\t\t\"tabIndex\":\"1\",\n" +
                "\t\t\t\"dispName\":\"CPU\"\n" +
                "\t\t},\n" +
                "\t\t\"displayList\":[\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"tabIndex\":\"9\",\n" +
                "\t\t\t\t\"detail\":{\n" +
                "\t\t\t\t\t\"enableVncProxy\":false,\n" +
                "\t\t\t\t\t\"address\":\"0.0.0.0\",\n" +
                "\t\t\t\t\t\"port\":\"-1\",\n" +
                "\t\t\t\t\t\"vncPasswdMode\":\"disable\",\n" +
                "\t\t\t\t\t\"enableSpiceCompress\":false,\n" +
                "\t\t\t\t\t\"type\":\"vnc\",\n" +
                "\t\t\t\t\t\"portIpv6\":\"-1\"\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t\"dispName\":\"控制台 VNC\"\n" +
                "\t\t\t}\n" +
                "\t\t],\n" +
                "\t\t\"diskList\":[\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"tabIndex\":\"5\",\n" +
                "\t\t\t\t\"detail\":{\n" +
                "\t\t\t\t\t\"enableEditDiskMode\":true,\n" +
                "\t\t\t\t\t\"usage\":31.29,\n" +
                "\t\t\t\t\t\"devObj\":\"高速磁盘(Virtio) vda\",\n" +
                "\t\t\t\t\t\"diskMode\":\"subordinate\",\n" +
                "\t\t\t\t\t\"deviceBus\":\"virtio\",\n" +
                "\t\t\t\t\t\"type\":\"file\",\n" +
                "\t\t\t\t\t\"deviceName\":\"vda\",\n" +
                "\t\t\t\t\t\"mode\":2,\n" +
                "\t\t\t\t\t\"minValue\":4096.0,\n" +
                "\t\t\t\t\t\"showSize\":4.0,\n" +
                "\t\t\t\t\t\"dev\":\"disk\",\n" +
                "\t\t\t\t\t\"readonly\":false,\n" +
                "\t\t\t\t\t\"share\":false,\n" +
                "\t\t\t\t\t\"distribute\":\"1.26GB\",\n" +
                "\t\t\t\t\t\"poolName\":\"VBP_ARV_192.168.8.152\",\n" +
                "\t\t\t\t\t\"enableEditDisk\":true,\n" +
                "\t\t\t\t\t\"allocation\":1281,\n" +
                "\t\t\t\t\t\"maxValue\":191353.75,\n" +
                "\t\t\t\t\t\"srcPath\":\"/vms/VBP_ARV_192.168.8.152/f45...\",\n" +
                "\t\t\t\t\t\"format\":\"qcow2\",\n" +
                "\t\t\t\t\t\"enableRbd\":false,\n" +
                "\t\t\t\t\t\"poolType\":\"netfs\",\n" +
                "\t\t\t\t\t\"diskSecret\":false,\n" +
                "\t\t\t\t\t\"srcPathAll\":\"/vms/VBP_ARV_192.168.8.152/f45cc33d-a24f-4f32-bca8-f614a9e3c777\",\n" +
                "\t\t\t\t\t\"showUnit\":\"GB\",\n" +
                "\t\t\t\t\t\"l2cache\":false,\n" +
                "\t\t\t\t\t\"disk\":true,\n" +
                "\t\t\t\t\t\"size\":4096.0,\n" +
                "\t\t\t\t\t\"hotPluggable\":true,\n" +
                "\t\t\t\t\t\"iothreads\":false,\n" +
                "\t\t\t\t\t\"clustersize\":\"262144\",\n" +
                "\t\t\t\t\t\"enableModify\":true,\n" +
                "\t\t\t\t\t\"enableConverFmt\":true,\n" +
                "\t\t\t\t\t\"cacheModel\":\"directsync\"\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t\"dispName\":\"高速磁盘(Virtio) vda\"\n" +
                "\t\t\t}\n" +
                "\t\t],\n" +
                "\t\t\"domainName\":\"f45cc33d-a24f-4f32-bca8-f614a9e3c777\",\n" +
                "\t\t\"cDROMList\":[\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"tabIndex\":\"4\",\n" +
                "\t\t\t\t\"detail\":{\n" +
                "\t\t\t\t\t\"srcPath\":\"/vms/VBP_ARV_192.168.8.152/f45...\",\n" +
                "\t\t\t\t\t\"devObj\":\"光驱 hda\",\n" +
                "\t\t\t\t\t\"deviceBus\":\"ide\",\n" +
                "\t\t\t\t\t\"deviceName\":\"hda\",\n" +
                "\t\t\t\t\t\"srcPathAll\":\"/vms/VBP_ARV_192.168.8.152/f45cc33d-a24f-4f32-bca8-f614a9e3c777.iso\",\n" +
                "\t\t\t\t\t\"disk\":false,\n" +
                "\t\t\t\t\t\"dev\":\"cdrom\",\n" +
                "\t\t\t\t\t\"readonly\":true,\n" +
                "\t\t\t\t\t\"share\":false,\n" +
                "\t\t\t\t\t\"enableModify\":true\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t\"dispName\":\"光驱 hda\"\n" +
                "\t\t\t}\n" +
                "\t\t],\n" +
                "\t\t\"securityModeEnable\":\"0\",\n" +
                "\t\t\"sriovNetworkList\":[],\n" +
                "\t\t\"networkList\":[\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"tabIndex\":\"6\",\n" +
                "\t\t\t\t\"detail\":{\n" +
                "\t\t\t\t\t\"devType\":{\n" +
                "\t\t\t\t\t\t\"id\":\"virtio\",\n" +
                "\t\t\t\t\t\t\"value\":\"Virtio网卡\"\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t\"kernelAcceleration\":true,\n" +
                "\t\t\t\t\t\"mac\":\"0c:da:41:1d:c0:7e\",\n" +
                "\t\t\t\t\t\"ipv4Dhcp\":false,\n" +
                "\t\t\t\t\t\"casIpv6Config\":false,\n" +
                "\t\t\t\t\t\"profile\":{\n" +
                "\t\t\t\t\t\t\"vsiEnable\":false,\n" +
                "\t\t\t\t\t\t\"vlanId\":1,\n" +
                "\t\t\t\t\t\t\"name\":\"Default\",\n" +
                "\t\t\t\t\t\t\"vlanType\":0,\n" +
                "\t\t\t\t\t\t\"id\":1\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t\"casConfig\":false,\n" +
                "\t\t\t\t\t\"mtu\":1500,\n" +
                "\t\t\t\t\t\"vswitch\":{\n" +
                "\t\t\t\t\t\t\"mode\":0,\n" +
                "\t\t\t\t\t\t\"name\":\"vswitch0\",\n" +
                "\t\t\t\t\t\t\"id\":1,\n" +
                "\t\t\t\t\t\t\"isDpdk\":false\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t\"bindIp\":false,\n" +
                "\t\t\t\t\t\"ipv6Dhcp\":false,\n" +
                "\t\t\t\t\t\"hotPluggable\":true,\n" +
                "\t\t\t\t\t\"queues\":1,\n" +
                "\t\t\t\t\t\"bindIpv6\":false\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t\"dispName\":\"网络0c:da:41:1d:c0:7e\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"tabIndex\":\"6\",\n" +
                "\t\t\t\t\"detail\":{\n" +
                "\t\t\t\t\t\"devType\":{\n" +
                "\t\t\t\t\t\t\"id\":\"virtio\",\n" +
                "\t\t\t\t\t\t\"value\":\"Virtio网卡\"\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t\"kernelAcceleration\":true,\n" +
                "\t\t\t\t\t\"mac\":\"0c:da:41:1d:f7:55\",\n" +
                "\t\t\t\t\t\"ipv4Dhcp\":false,\n" +
                "\t\t\t\t\t\"casIpv6Config\":false,\n" +
                "\t\t\t\t\t\"profile\":{\n" +
                "\t\t\t\t\t\t\"vsiEnable\":false,\n" +
                "\t\t\t\t\t\t\"vlanId\":1,\n" +
                "\t\t\t\t\t\t\"name\":\"Default\",\n" +
                "\t\t\t\t\t\t\"vlanType\":0,\n" +
                "\t\t\t\t\t\t\"id\":1\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t\"casConfig\":false,\n" +
                "\t\t\t\t\t\"mtu\":1500,\n" +
                "\t\t\t\t\t\"vswitch\":{\n" +
                "\t\t\t\t\t\t\"mode\":0,\n" +
                "\t\t\t\t\t\t\"name\":\"vswitch0-rs\",\n" +
                "\t\t\t\t\t\t\"id\":15,\n" +
                "\t\t\t\t\t\t\"isDpdk\":false\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t\"bindIp\":false,\n" +
                "\t\t\t\t\t\"ipv6Dhcp\":false,\n" +
                "\t\t\t\t\t\"hotPluggable\":true,\n" +
                "\t\t\t\t\t\"queues\":1,\n" +
                "\t\t\t\t\t\"bindIpv6\":false\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t\"dispName\":\"网络0c:da:41:1d:f7:55\"\n" +
                "\t\t\t}\n" +
                "\t\t],\n" +
                "\t\t\"serialList\":[\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"tabIndex\":\"13\",\n" +
                "\t\t\t\t\"detail\":{\n" +
                "\t\t\t\t\t\"monitor\":false,\n" +
                "\t\t\t\t\t\"type\":\"pty\",\n" +
                "\t\t\t\t\t\"targetPort\":0\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t\"dispName\":\"串口 0\"\n" +
                "\t\t\t}\n" +
                "\t\t],\n" +
                "\t\t\"currentTabId\":\"overview\",\n" +
                "\t\t\"usbList\":[],\n" +
                "\t\t\"soundList\":[],\n" +
                "\t\t\"gpuList\":[]\n" +
                "\t}";

        put("/cas/domain", s);
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        // 虚拟机
//        get("/cas/casrs/vm/vmList?hostId=1");
//        get("/cas/casrs/vm/detail/24");
//        addVm();
        get("/cas/casrs/vm/1");
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
//        get("/cas/casrs/storage/info?hostId=1&poolName=VBP_ARV_192.168.8.152");
//        addNfsStoragePool();
//        get("/cas/casrs/storage/start?id=1&poolName=testNfs&hostName=cvknode");
//        get("/cas/casrs/storage/volume?hostId=1&poolName=testNfs&offset=1&pageSize=1");
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
