package io.github.xqplus.virt.incloudsphere;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Main {

//    private static final String ADDR = "https://192.168.7.2";
//    private static final String ICS_AK_SK = "ICS CXeaC4ohQnWT0JgdlJ1U:3GwBdLEJu6sH8yg0If69q2w1ipY58BvJ03oAFSOh";

    private static final String ADDR = "https://192.168.8.173";
    private static final String ICS_AK_SK = "ICS 7IE1X5s5JCG0oqVT4Q41:lFwk2Ehb6Q6Bs1I4rHq68vS0gJVt6wpAsXqeYha5";

    public static void main(String[] args) throws Exception {
//        login();

        // 系统
//        httpGet("/system/version");

        // iCenter
//        httpGet("/icenter/icenter-1");
//        httpGet("/icenter?type=abstract");

        // 数据中心
//        httpGet("/datacenters");

        // 集群
//        httpGet("/datacenters/8e8c78c35bac11f09ef54626a4152aa8/clusters");
//        httpGet("/datacenters/541a47d79ce311f08ca49e1d14ce357e/clusters");

        // 主机
//        httpGet("/datacenters/541a47d79ce311f08ca49e1d14ce357e/hosts");
//        httpGet("/clusters/8e8c78c35bac11f09ef54626a4152aa8/hosts");
//        httpGet("/hosts");
//        httpGet("/hosts/120dd08c-0286-40ec-b527-3a6aafebe70d");

        // 虚拟机
//        httpGet("/hosts/120dd08c-0286-40ec-b527-3a6aafebe70d/vms");
//        httpPut("/vms/4028878297e81a330197e92a907600d2?action=poweron", null);
//        httpPut("/vms/4028878297e81a330197e92a907600d2?action=poweroff&kill=false", null);
//        httpPut("/vms/4028878297e81a330197e92a907600d2?action=pause", null);
//        httpGet("/vms/402887829951bcbc01997aea00ea14d1");
//        httpGet("/vms");
        createVm();
//        httpDelete("/vms/402887829902d6760199137ca7ee0190?deleteFile=true&removeData=true");
//        relocateVm();
//        httpGet("/vms/ostypes");

        // 快照
//        httpGet("/vms/snapshots/edededed-eded-4ced-bced-edededededed");

        // 存储
//        httpGet("/hosts/f19628fe-252b-4263-a8ae-64c636d7eac3/storages");
//        httpGet("/hosts/120dd08c-0286-40ec-b527-3a6aafebe70d/storages?type=all");
//        httpGet("/storages/402887829902d67601992c378a7c01f3xxx");
//        createStorage();
//        httpGet("/storages");
//        httpGet("/storages/402887829951bcbc019951f12953006b/vms");
//        httpGet("/storages/402887829902d67601992c378a7c01f3/volumes");
//        httpGet("/storages/402887829902d67601992c378a7c01f3/hosts");

        // 虚拟磁盘
//        setVolumeFileOpenMode("7dd9a133-1f2a-4f4f-b3df-2a163695c6f0", "CLOSED");
//        httpGet("/volumes?params=uuid&uuid=95216157-9a84-48ff-a1e8-dcd6ad920759");
//        httpGet("/volumes/402887829951bcbc01998e3e4ecb1a36");

        // 网络
//        httpGet("/hosts/120dd08c-0286-40ec-b527-3a6aafebe70d/networks?type=vmnetwork");
//        httpGet("/networks/4028878297e81a330197e8edc6a8009c");

        // 任务
//        httpGet("/tasks/402887829951bcbc01997f652ece163c");
//        httpGet("/tasks/402887829902d6760199519ef6f408ba?action=cancel");


//        String vmId = "402887829951bcbc019994d9a6341ce2";
//        String volumeId = "402887829951bcbc019994d9a7471ce8";
//        String backupVolumeUuid = "local_snapshot_2_increment";
//        String backupType = "INCREMENT";

        // 创建本地卷快照
//        createLocalVolumeSnapshot(vmId, backupType, backupVolumeUuid, volumeId);

        // 检查卷本地快照是否存在
//        httpGet("/vmbackups?action=check_local_backup_exist&vmId=" + vmId + "&id=" + volumeId + "&backupVolumeUuid=" + backupVolumeUuid);

        // 获取全量有效块信息
//        httpGet("/vmbackups?type=blockinfo&vmId=" + vmId + "&id=" + volumeId + "&backupVolumeUuid="
//                + backupVolumeUuid + "&startBlock=0&blockNum=100");

        // 获取增量位图信息
//        httpGet("/vmbackups?type=local&vmId=" + vmId + "&id=" + volumeId + "&backupVolumeUuid=" + backupVolumeUuid + "&startBlock=0&blockNum=100");

        // 删除卷本地快照
//        httpDelete("/vmbackups?type=local&vmId=" + vmId + "&id=" + volumeId + "&backupVolumeUuid=" + backupVolumeUuid);
    }

    private static void createVmSnapshot(String vmId, String name) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("name", name);
        obj.put("description", "Created by VBP");
        httpPost("/vms/" + vmId + "/snapshots", obj.toString());
    }


    // 生成虚拟机本地卷快照
    private static void createLocalVolumeSnapshot(String vmId, String backupType, String backupVolumeUuid, String... volumeIds) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("vmId", vmId);
        obj.put("backupType", backupType);
        JSONArray backupedVolumes = new JSONArray();
        for (String volumeId : volumeIds) {
            JSONObject backupedVolume = new JSONObject();
            backupedVolume.put("id", volumeId);
            backupedVolume.put("backupType", backupType);
            backupedVolume.put("backupVolumeUuid", backupVolumeUuid); // 多次盘一致
            backupedVolumes.add(backupedVolume);
        }
        obj.put("backupedVolumes", backupedVolumes);
        httpPost("/vmbackups?type=local", obj.toString());
    }

    public static void setVolumeFileOpenMode(String uuid, String openMode) throws Exception {
        JSONObject openModeInfo = new JSONObject();
        openModeInfo.put("uuid", uuid);
        openModeInfo.put("openMode", openMode);
        httpPut("/volumes?action=setOpenMode", openModeInfo.toString());
    }

    private static void relocateVm() throws Exception {
        JSONArray volumes = new JSONArray();
        JSONObject volume1 = new JSONObject();
        volume1.put("id", "402887829902d67601992d7d17350243");
        volume1.put("dataStoreId", "4028878297e81a330197e8f1fffd00a5");
        volumes.add(volume1);
        JSONObject volume2 = new JSONObject();
        volume2.put("id", "402887829902d67601992d7d23730246");
        volume2.put("dataStoreId", "4028878297e81a330197e8f1fffd00a5");
        volumes.add(volume2);
        httpPut("/vms/402887829902d67601992d7d2fdc024a?action=relocate&hostId=120dd08c-0286-40ec-b527-3a6aafebe70d", volumes.toString());
    }

    private static void createStorage() throws Exception {
        JSONObject createInfo = new JSONObject();
        createInfo.put("name", "nt1");
        createInfo.put("dataStoreType", "NFS");
        createInfo.put("serverIp", "192.168.8.169");
        createInfo.put("exportDir", "/storage/mnt/nfs");
        createInfo.put("nfsVersion", "4");
        JSONObject dataCenterDto = new JSONObject();
        dataCenterDto.put("id", "8e8c78c35bac11f09ef54626a4152aa8");
        createInfo.put("dataCenterDto", dataCenterDto);
        JSONArray hostDtos = new JSONArray();
        JSONObject hostDto = new JSONObject();
        hostDto.put("id", "120dd08c-0286-40ec-b527-3a6aafebe70d");
        hostDtos.add(hostDto);
        createInfo.put("hostDtos", hostDtos);
        httpPost("/storages?type=nfs", createInfo.toString());
    }

    private static void createVm() throws Exception {
        JSONObject vmInfo = new JSONObject();
        vmInfo.put("name", "rcp4");
//        vmInfo.put("description", "");
//        vmInfo.put("version", "V2");
        vmInfo.put("guestosType", "Other Windows");
        vmInfo.put("guestosLabel", "Other Windows(64 bit)");
        vmInfo.put("hostId", "b4f44c90-34f0-43c9-a237-3250e6ecfab8");
        vmInfo.put("cpuNum", 1);
        vmInfo.put("cpuCore", 1);
        vmInfo.put("cpuSocket", 1);
//        vmInfo.put("cpuShares", 1024);
//        vmInfo.put("cpuReservation", 0);
//        vmInfo.put("awareNumaEnabled", false);
//        vmInfo.put("secretLevel", "SECRET_NO");
//        vmInfo.put("cpuArchType", "X86_64");
//        vmInfo.put("cpuLimit", -1);
//        vmInfo.put("hostBinded", false);
//        vmInfo.put("vcpuPin", "all");
        vmInfo.put("memoryInBytes", 2147483648L);
//        vmInfo.put("enableHugeMemPage", false);
//        vmInfo.put("memBalloonEnabled", false);
//        vmInfo.put("memReservationInByte", 0);
//        vmInfo.put("encryptFlag", false);
////        vmInfo.put("secretKeyId", );
//        vmInfo.put("clockModel", "UTC");
//        vmInfo.put("enableReplicate", false);
////        vmInfo.put("replicationDatastoreId", );
//        vmInfo.put("splashTime", 0);
//        vmInfo.put("vncPasswd", "root");
//        vmInfo.put("vncSharePolicy", "FORCE_SHARED");
//        vmInfo.put("cpuModelType", "SELF_ADAPTING");
//        vmInfo.put("cpuHotplugEnabled", true);
//        vmInfo.put("memHotplugEnabled", true);
//        vmInfo.put("cpuModelEnabled", true);
//        vmInfo.put("panickPolicy", "RESTART");

        JSONArray nics = new JSONArray();
        JSONObject nic = new JSONObject();
        nic.put("deviceId", "4028882d9adcf760019adcfb8d5f004f");
//        nic.put("networkId", "4028878297e81a330197e8edc6a8009c");
        nic.put("deviceType", "NETWORK");
        nic.put("switchType", "NORMALSWITCH");
//        nic.put("ebable", false);
//        nic.put("model", "VIRTIO");
        nic.put("autoGenerated", true);
//        nic.put("mac", "");
//        nic.put("priorityEnabled", false);
////        nic.put("netPriority", "");
//        nic.put("connectionStatus", "UP");
        nic.put("dhcp", false);
//        nic.put("dhcpIp", "");
//        nic.put("uplinkRate", 0);
//        nic.put("downlinkRate", 0);
//        nic.put("downlinkBurst", 0);
        nics.add(nic);
        vmInfo.put("nics", nics);

        JSONArray disks = new JSONArray();
        JSONObject disk = new JSONObject();
        JSONObject volume = new JSONObject();
//        volume.put("sizeInBytes", 20971520);
        volume.put("size", 10); // GB
        volume.put("bootable", true);
        volume.put("format", "RAW");
        volume.put("volumePolicy", "THIN");
        volume.put("dataStoreId", "4028882d9adcf760019adcfe9b05005d");
//        volume.put("dataStoreType", "LOCAL");
//        volume.put("shared", false);
//        volume.put("clusterSize", 262144);
        // vvSourceDto
        disk.put("volume", volume);
        disk.put("busModel", "VIRTIO");
        disk.put("targetDev", "vda");
//        disk.put("enable", false);
//        disk.put("readWriteModel", "NONE");
//        disk.put("enableNativeIO", false);
//        disk.put("enableKernelIO", false);
//        disk.put("l2CacheSize", 0);
        disks.add(disk);
        vmInfo.put("disks", disks);

//        JSONObject cloudInit = new JSONObject();
//        cloudInit.put("userdata", "user-data");
//        cloudInit.put("metadata", "meta-data");
//        cloudInit.put("dataSourceType", "OPENSTACK");
//        vmInfo.put("cloudInit", cloudInit);

//        JSONObject cdrom = new JSONObject();
//        cdrom.put("type", "IMAGE_FILE");
//        cdrom.put("path", "CentOS-7-x86_64-Minimal-2009.iso");
//        cdrom.put("startConnected", false);
//        JSONObject dataStore = new JSONObject();
//        dataStore.put("id", "4028878297e81a330197e90d8adb00ac");
//        cdrom.put("dataStore", dataStore);
//        vmInfo.put("cdrom", cdrom);

//        JSONArray graphicsCards = new JSONArray();
//        JSONObject graphicsCard = new JSONObject();
//        graphicsCard.put("graphicsCardMemory", 16384);
//        graphicsCard.put("graphicsCardModel", "CIRRUS");
//        graphicsCard.put("screenNumbers", 1);
//        graphicsCards.add(graphicsCard);
//        vmInfo.put("graphicsCards", graphicsCards);

        String res = httpPost("/vms", vmInfo.toString());
        String taskId = JSON.parseObject(res).getString("taskId");
        JSONObject task;
        do {
            Thread.sleep(2000);
            res = httpGet("/tasks/" + taskId);
            task = JSON.parseObject(res);
        } while (!"FINISHED".equals(task.getString("state")) && !"ERROR".equals(task.getString("state")));
    }

    private static void login() throws NoSuchAlgorithmException, KeyManagementException, IOException {
        HttpPost httpPost = new HttpPost(ADDR + "/system/user/login");
        JSONObject jo = new JSONObject();
        jo.put("username", "admin");
        jo.put("password", "Dsg_123456!");
        jo.put("domain", "internal");
        jo.put("locale", "cn");
        httpPost.setEntity(new StringEntity(jo.toString(), StandardCharsets.UTF_8));
        CloseableHttpResponse response = createHttpClient().execute(httpPost);
        System.out.println(response.getStatusLine().getStatusCode());
        String res = EntityUtils.toString(response.getEntity());
        if (res.startsWith("[")) {
            JSONArray ja = JSON.parseArray(res);
            System.out.println(ja.toString(SerializerFeature.PrettyFormat));
        } else {
            JSONObject jo1 = JSON.parseObject(res);
            System.out.println(jo1.toString(SerializerFeature.PrettyFormat));
        }
    }

    private static String httpGet(String uri) throws Exception {
        HttpGet httpGet = new HttpGet(ADDR + uri);
        httpGet.addHeader("Authorization", ICS_AK_SK);
        CloseableHttpResponse response = createHttpClient().execute(httpGet);
        System.out.println(response.getStatusLine().getStatusCode());
        String res = EntityUtils.toString(response.getEntity());
        if (res.startsWith("[")) {
            JSONArray ja = JSON.parseArray(res);
            System.out.println(ja.toString(SerializerFeature.PrettyFormat));
        } else if (res.startsWith("{")) {
            JSONObject jo = JSON.parseObject(res);
            System.out.println(jo.toString(SerializerFeature.PrettyFormat));
        } else {
            System.out.println(res);
        }
        return res;
    }

    private static void httpDelete(String uri) throws Exception {
        HttpDelete httpDelete = new HttpDelete(ADDR + uri);
        httpDelete.addHeader("Authorization", ICS_AK_SK);
        CloseableHttpResponse response = createHttpClient().execute(httpDelete);
        System.out.println(response.getStatusLine().getStatusCode());
        System.out.println(EntityUtils.toString(response.getEntity()));
    }

    private static String httpPost(String uri, String requestBody) throws Exception {
        HttpPost httpPost = new HttpPost(ADDR + uri);
        httpPost.addHeader("Authorization", ICS_AK_SK);
        httpPost.setEntity(new StringEntity(requestBody, StandardCharsets.UTF_8));
        CloseableHttpResponse response = createHttpClient().execute(httpPost);
        System.out.println(response.getStatusLine().getStatusCode());
        String res = EntityUtils.toString(response.getEntity());
        System.out.println(res);
        return res;
    }

    private static void httpPut(String uri, String requestBody) throws Exception {
        HttpPut put = new HttpPut(ADDR + uri);
        put.addHeader("Authorization", ICS_AK_SK);
        if (requestBody != null) {
            put.setEntity(new StringEntity(requestBody, StandardCharsets.UTF_8));
        }
        CloseableHttpResponse response = createHttpClient().execute(put);
        System.out.println(response.getStatusLine().getStatusCode());
        System.out.println(EntityUtils.toString(response.getEntity()));
    }

    private static CloseableHttpClient createHttpClient() throws NoSuchAlgorithmException, KeyManagementException {
        // 跳过SSL证书校验
        SSLContext sslContext = SSLContext.getInstance(SSLConnectionSocketFactory.TLS);
        sslContext.init(null, new TrustManager[]{new TrustAllManager()}, new SecureRandom());
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
                sslContext, new String[]{"TLSv1.1", "TLSv1.2"}, null, NoopHostnameVerifier.INSTANCE
        );

        // 超时配置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)           // 建立连接超时
                .setSocketTimeout(250_000)         // 数据传输超时
                .setConnectionRequestTimeout(3000) // 从连接池获取连接超时
                .build();

        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Content-Type", "application/json"));
        headers.add(new BasicHeader("version", "5.8"));

        // 创建HttpClient（包含重试和连接回收）
        return HttpClients.custom()
                .setSSLSocketFactory(socketFactory)
                .setMaxConnTotal(200)   // 最大连接数
                .setMaxConnPerRoute(50) // 每个路由最大并发数
                .setDefaultRequestConfig(requestConfig)
                .setDefaultHeaders(headers)
                .setRetryHandler(new DefaultHttpRequestRetryHandler(2, false))

                // 自动清理过期/空闲连接
                .evictIdleConnections(60, TimeUnit.SECONDS)
                .evictExpiredConnections()

                // 启用Keep-Alive策略
                .setKeepAliveStrategy((response, context) -> 30_000)
                .build();
    }

    private static class TrustAllManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }
}
