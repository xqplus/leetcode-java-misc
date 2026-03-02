package io.github.xqplus.virt.winstack;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.github.xqplus.virt.winstack.domain.LoginBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RestApi {
    private static String prefix = "https://192.168.7.3/api";
    private static String sessionId = "f577025e-d150-403b-83c4-c775dbb2c733";

    public static void main(String[] args) throws Exception {
//        login();

//        createNasStore("17ffb591-dc95-42e1-9f08-b1f0298748fc", "NAS_192_168_8_152", "192.168.8.152");
//        JSONObject object = get("/storage/storagePools?type=3");
//        JSONObject object = get("/storage/storagePools/69dce9da-5d60-4e86-a3c9-17adf8048a84");

//        JSONObject body = new JSONObject();
//        body.put("bizStoreType", 3);
//        post("/storage/stores", body.toString());

//        post("/storage/storagePools/bf672bee-4a0e-477e-8288-9d64d295309f/delete", new JSONObject().toString());

//        JSONObject object = get("/notify/tasks/e0cd6d49-a524-4370-9738-80c8f4092a25");

//        System.out.println(object.toString(SerializerFeature.PrettyFormat));

//        JSONObject post = post("/logout", null);

//        createNfsStoragePool();

//        JSONArray diskDevices = new JSONArray();
//        JSONObject diskDevice = new JSONObject();
//        diskDevice.put("bus", "virtio");
//        diskDevice.put("dev", "vda");
//        diskDevices.add(diskDevice);
//        migrateVm("bf216b01-caac-4bfb-add2-d6d49f87b466", diskDevices,
//                "17ffb591-dc95-42e1-9f08-b1f0298748fc", "325b50ce-426e-4b2f-aa85-4a1e35cd3368");

//        get("/storage/storagePools/21495214-6141-4599-9662-eb720113f2d3/storageVolumes");

//        get("/compute/domains?storagePoolId=d5301fbf-dab3-48fe-be86-25db1f88466f");
//        get("/compute/domains/6df5a453-b1e4-44fb-9e66-0cde03e387d0");

        get("/storage/storageVolumes/d98b3d2b-c122-44f8-ab6d-659c8d2e4e16");

//        get("/compute/domains/1a1854bd-acf8-433e-9109-256ea00b2358/domainDiskInfo");
    }

    public static void migrateVm(String vmId, JSONArray diskDevices, String hostId, String storagePoolId) throws IOException, InterruptedException, GeneralSecurityException {
        JSONArray migrateVols = new JSONArray();
        for (int i = 0; i < diskDevices.size(); i++) {
            JSONObject diskDevice = diskDevices.getJSONObject(i);
            JSONObject migrateVol = new JSONObject();
            migrateVol.put("bus", diskDevice.getString("bus"));
            migrateVol.put("dev", diskDevice.getString("dev"));
            migrateVol.put("destStoragePoolId", storagePoolId);
            migrateVols.add(migrateVol);
        }
        JSONObject body = new JSONObject();
        body.put("migrateVols", migrateVols);
        body.put("destHostId", hostId);

        JSONObject resp = patch("/compute/domains/" + vmId + "/migrate/to/storage_pools/" + storagePoolId, body.toString());
//        waitTask(resp.getString("taskId"));
    }

    public static JSONObject createNfsStoragePool() throws IOException, InterruptedException, GeneralSecurityException {
        JSONObject body = new JSONObject();
        body.put("hostIdList", Collections.singletonList("17ffb591-dc95-42e1-9f08-b1f0298748fc"));
        body.put("name", "x3");
        body.put("remark", "created by vbp");
        body.put("useType", 1); // 存储池
        body.put("storeId", "f7cb35e0-37c8-47b9-9905-acd84d38eee8");
        body.put("shareDirPath", "/storage/mnt/fuse/087eee4f94askjdhkajdh");
//        body.put("shareDirPath", "/storage/mnt/nfs/test");

        JSONObject resp = post("/storage/storagePools/nfs", body.toString());
        String taskId = resp.getJSONObject("data").getString("taskId");
        JSONObject taskInfo = waitTask(taskId);
        if (taskInfo.getByte("status") != 3) throw new IOException("create nfs storage pool failed");

        return null;
    }

    private static void login() throws GeneralSecurityException, IOException {
        JSONObject loginBody = new JSONObject();
        loginBody.put("user", "admin");
        loginBody.put("pwd", "Dsg_123456!");
        JSONObject loginResp = post("/login", loginBody.toString());
        System.out.println(loginResp.toString(SerializerFeature.PrettyFormat));
    }

    public static void createNasStore(String hostId, String storeName, String remoteIp) throws IOException, InterruptedException, GeneralSecurityException {
        JSONObject body = new JSONObject();
        body.put("hostIdList", Collections.singletonList(hostId));
        body.put("name", storeName);
        body.put("remark", "created by vbp");
        body.put("ip", remoteIp);
        body.put("proto", "tcp");

        JSONObject resp = post("/storage/stores/nfs", body.toString());
        waitTask(resp.getJSONObject("data").getString("taskId"));
    }

    private static JSONObject waitTask(String taskId) throws IOException, InterruptedException, GeneralSecurityException {
        JSONObject task = get("/notify/tasks/" + taskId);
        System.out.println(task.toString(SerializerFeature.PrettyFormat));
        int count = 200; // 最大轮询200次，间隔3s，理论上最小等待为10分钟
        while (count > 0 && task.getByte("status") == 1) {
            Thread.sleep(3 * 1000);
            task = get("/notify/tasks/" + taskId);
            System.out.println(task.toString(SerializerFeature.PrettyFormat));
            count--;
        }
        if (count == 0 && task.getByte("status") == 1) {
            throw new IOException("Task wait timeout: " + taskId);
        }
        return task;
    }

    private static CloseableHttpClient createTrustedHttpClient() throws GeneralSecurityException {
        // 创建信任所有证书的策略
        SSLContext sslContext = new SSLContextBuilder()
                .loadTrustMaterial(null, (certificate, authType) -> true)
                .build();
        // 配置SSL套接字工厂，同时跳过主机名验证
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
        // 超时配置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5 * 1000)
                .setSocketTimeout(60 * 1000)
                .setConnectionRequestTimeout(5 * 1000)
                .build();
        // 使用该工厂创建 HttpClient
        return HttpClients.custom()
                .setSSLSocketFactory(socketFactory)
                .setMaxConnTotal(200) // 最大连接数
                .setMaxConnPerRoute(50) // 每个主机最大连接数
                .setDefaultRequestConfig(requestConfig)
                .setRetryHandler(new DefaultHttpRequestRetryHandler(2, false)) // 重试策略
                .evictIdleConnections(60, TimeUnit.SECONDS) // 清理空闲线程间隔
                .evictExpiredConnections()
                .build();
    }

    private static JSONObject post(String uri, String body) throws IOException, GeneralSecurityException {
        HttpPost httpPost = new HttpPost(prefix + uri);
        if (body != null) httpPost.setEntity(new StringEntity(body));
        return execute(httpPost);
    }

    private static JSONObject get(String uri) throws IOException, GeneralSecurityException {
        return execute(new HttpGet(prefix + uri));
    }

    private static JSONObject patch(String uri, String body) throws IOException, GeneralSecurityException {
        HttpPatch httpPatch = new HttpPatch(prefix + uri);
        if (body != null) httpPatch.setEntity(new StringEntity(body));
        return execute(httpPatch);
    }

    private static JSONObject execute(HttpRequestBase request) throws IOException, GeneralSecurityException {
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
//        request.setHeader(HttpHeaders.ACCEPT_CHARSET, "UTF-8");
        String url = request.getURI().toString();
        if (!url.endsWith("/api/login")) {
            request.setHeader("Cookie", "SESSION=" + sessionId);
        }

        CloseableHttpResponse response = createTrustedHttpClient().execute(request);
        String str = EntityUtils.toString(response.getEntity());
        System.err.println(str);
        if (StringUtils.isBlank(str)) {
            return null;
        }

        JSONObject obj = JSON.parseObject(str);
        if (obj.getInteger("errorCode") != null) {
            throw new IOException("WinStack: " + obj.getString("message"));
        }
        return obj;
    }
}
