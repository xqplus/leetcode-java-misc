package io.github.xqplus.virt.fusioncompute;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.http.client.HttpResponseException;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class VirtualMachineOperation {

    private static final String TOKEN = "2A3AC995-1305-46F9-8CBA-2371787E76F80";
    private static final String SITE_URI = "/service/sites/367C0709";
    private static final String HOST_URN = "urn:sites:367C0709:hosts:105";

    public static void main(String[] args) throws Exception {
//        System.out.println(getToken());

        // 查询指定数据存储
//        get(SITE_URI + "/datastores/85");
        // 分页查询数据存储下的虚拟机
//        get(SITE_URI + "/vms?limit=5&offset=0&scope=urn:sites:367C0709:datastores:85");
        // 分页查询数据存储下的卷
//        get(SITE_URI + "/volumes/querydatastorevolumes?limit=1&offset=0&dsUrn=urn:sites:367C0709:datastores:85");
        // 分页查新数据存储下的文件
//        get(SITE_URI + "/datastores/85/file?limit=1&offset=0");
        // 解关联数据存储和主机
//        delete(SITE_URI + "/datastores/84/disconnect?hostUrn=" + HOST_URN);
        // 删除指定数据存储
//        delete(SITE_URI + "/datastores/84/delete");

        // 查询所有端口组
//        get(SITE_URI + "/portgroups");
        // 查询指定端口组
//        get(SITE_URI + "/dvswitchs/6/portgroups/8");
        // 分页查询端口组关联的虚拟机
//        get(SITE_URI + "/vms?limit=1&offset=0&scope=urn:sites:367C0709:dvswitchs:6:portgroups:7");
        // 删除指定端口组
//        delete(SITE_URI + "/dvswitchs/6/portgroups/8");

//        get(SITE_URI + "/vms?limit=1&offset=0&scope=urn:sites:367C0709:folders:223757");
//        get(SITE_URI + "/folder?type=1&parentObjUrn=urn:sites:367C0709:folders:223757");
//        get("/service/sites/367C0709/folder/223823");

//        delete("/service/sites/367C0709/datastores/87/disconnect?hostUrn=urn:sites:367C0709:hosts:105");


        String datastoreUrn = createDatastore(
                "urn:sites:367C0709:storageunits:2CA8C46C3AD34240B4F394B6556371BA",
                "VBP_ARV_192.168.8.151"
        );
        createVm(datastoreUrn);
    }

    private static void powerOffVm() throws IOException, NoSuchAlgorithmException, KeyManagementException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("mode", "force");
        post(SITE_URI + "/vms/i-000003E0/action/stop", requestBody.toString());
    }

    private static void powerOnVm() throws IOException, NoSuchAlgorithmException, KeyManagementException {
        post(SITE_URI + "/vms/i-000003DD/action/start", "");
    }

    private static void getDatastores() throws IOException, NoSuchAlgorithmException, KeyManagementException {
        get(SITE_URI + "/datastores");
    }

    private static void attachCdrom() throws IOException, NoSuchAlgorithmException, KeyManagementException {
        JSONObject cdromInfo = new JSONObject();
        cdromInfo.put("devicePath", "1@rs.iso");
        cdromInfo.put("protocol", "file");
        post(SITE_URI + "/vms/i-000003DD/action/attachCdrom", cdromInfo.toString());
    }

    private static void getAllDvSwitch() throws IOException, NoSuchAlgorithmException, KeyManagementException {
        get(SITE_URI + "/dvswitchs");
    }

    private static void getOsVersions() throws IOException, NoSuchAlgorithmException, KeyManagementException {
        get("/service/sites/367C0709/vms/osversions");
    }

    private static void createStorageResource() throws IOException, NoSuchAlgorithmException, KeyManagementException {
        JSONObject storageResourceInfo = new JSONObject();
        storageResourceInfo.put("name", "sp_192.168.8.151");
        storageResourceInfo.put("storageType", "NAS");
        JSONArray dataChannels = new JSONArray(1);
        JSONObject dataChannel = new JSONObject(1);
        dataChannel.put("ip", "192.168.8.151");
        dataChannels.add(dataChannel);
        storageResourceInfo.put("dataChannel", dataChannels);
        storageResourceInfo.put("vender", "OTHER");
        storageResourceInfo.put("deviceType", "OTHER");
        storageResourceInfo.put("hostUrn", "urn:sites:367C0709:hosts:105");
        storageResourceInfo.put("autoscan", 1);
        post("/service/sites/367C0709/storageresources", storageResourceInfo.toString());
    }

    private static String createDatastore(String storageUnitUrn, String name) throws Exception {
        JSONObject datastoreInfo = new JSONObject();
        datastoreInfo.put("hostUrn", HOST_URN); // 设置该值表示创建数据存储并关联主机
        datastoreInfo.put("storageUnitUrn", storageUnitUrn);
        datastoreInfo.put("name", name);
        datastoreInfo.put("useType", 1);
        String result = post(SITE_URI + "/datastores", datastoreInfo.toString());
        JSONObject jsonObject = JSON.parseObject(result);
        pollingTask(jsonObject.getString("taskUri"));
        return jsonObject.getString("urn");
    }

    private static void createVm(String datastoreUrn) throws Exception {
        JSONObject vmInfo = new JSONObject();
        vmInfo.put("name", "test-vm");
        vmInfo.put("location", HOST_URN);
        vmInfo.put("autoBoot", false);
//        if (vmFolderUrn != null) {
//            vmInfo.put("parentObjUrn", vmFolderUrn);
//        }

        JSONObject vmConfig = new JSONObject();

        JSONObject cpu = new JSONObject();
        cpu.put("quantity", 1);
        cpu.put("coresPerSocket", 1);
        cpu.put("cpuBindType", "nobind");
        vmConfig.put("cpu", cpu);

        JSONObject memory = new JSONObject();
        memory.put("quantityMB", 1024);
        vmConfig.put("memory", memory);

        JSONArray disks = new JSONArray(2);
        JSONObject systemDisk = new JSONObject();
        systemDisk.put("sequenceNum", 1);
        systemDisk.put("quantityGB", 4);
        systemDisk.put("datastoreUrn", datastoreUrn);
        systemDisk.put("isThin", true);
        disks.add(systemDisk);
        JSONObject dataDisk = new JSONObject();
        dataDisk.put("sequenceNum", 2);
        dataDisk.put("quantityGB", 1);
        dataDisk.put("datastoreUrn", datastoreUrn);
        dataDisk.put("isThin", true);
        disks.add(dataDisk);
        vmConfig.put("disks", disks);

        JSONArray nics = new JSONArray();

        JSONObject nic = new JSONObject();
        nic.put("portGroupUrn", "urn:sites:367C0709:dvswitchs:7:portgroups:9");
        nics.add(nic);

        vmConfig.put("nics", nics);
        vmInfo.put("vmConfig", vmConfig);

        JSONObject osOption = new JSONObject();
        osOption.put("osType", "Linux");
        osOption.put("osVersion", 462); // CentOS 7.0 64bit
        vmInfo.put("osOptions", osOption);

        post(SITE_URI + "/vms", vmInfo.toString());
    }

    public static void pollingTask(String uri) throws Exception {
        JSONObject taskResult;
        String status;
        do {
            Thread.sleep(1000);
            String result = get(uri);
            taskResult = JSON.parseObject(result);
            status = taskResult.getString("status");
        } while (!"success".equals(status) && !"failed".equals(status));

        if ("failed".equals(status)) {
            throw new RuntimeException(
                    String.format("Failed to %s, %s, %s",
                            taskResult.getString("type"),
                            taskResult.getString("reason"),
                            taskResult.getString("reasonDes")
                    )
            );
        }
    }

    public static String get(String uri) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        trustAllManager();
        URL url = new URL("https://192.168.8.238:7443" + uri);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json;version=v6.5;charset=UTF-8");
        connection.setRequestProperty("X-Auth-Token", TOKEN);
        connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

        System.out.println(connection.getResponseCode());
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        String result = stringBuilder.toString();
        JSONObject jsonObject = JSON.parseObject(result);
        System.out.println(JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat));
        return result;
    }

    public static String post(String uri, String json) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        trustAllManager();
        URL url = new URL("https://192.168.8.238:7443" + uri);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Accept", "application/json;version=v6.5;charset=UTF-8");
        connection.setRequestProperty("X-Auth-Token", TOKEN);
        connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        connection.setDoOutput(true); // post请求需要设置这两个为true
        connection.setDoInput(true);

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
        writer.write(json);
        writer.flush();

        System.out.println(connection.getResponseCode());
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        String result = stringBuilder.toString();
        JSONObject jsonObject = JSON.parseObject(result);
        System.out.println(JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat));
        return result;
    }

    public static void delete(String uri) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        trustAllManager();
        URL url = new URL("https://192.168.8.238:7443" + uri);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("DELETE");
        connection.setRequestProperty("Accept", "application/json;version=v6.5;charset=UTF-8");
        connection.setRequestProperty("X-Auth-Token", TOKEN);
        connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

        System.out.println(connection.getResponseCode());
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        JSONObject jsonObject = JSON.parseObject(stringBuilder.toString());
        System.out.println(JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat));
    }

    private static void trustAllManager() throws NoSuchAlgorithmException, KeyManagementException {
        HostnameVerifier hv = (urlHostName, session) -> true;
        TrustManager[] trustAllCerts = {new TrustAllTrustManager()};
        SSLContext context = SSLContext.getInstance("SSL");
        SSLSessionContext sessionContext = context.getServerSessionContext();
        sessionContext.setSessionTimeout(0);
        context.init(null, trustAllCerts, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }

    public static String getToken() throws Exception {
        trustAllManager();
        OutputStream out = null;
        BufferedReader in = null;
        try {
            URL url = new URL("https://192.168.8.238:7443/service/session");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("Accept", "application/json;version=v6.5;charset=UTF-8");
            connection.addRequestProperty("Content-Type", "application/json;charset=UTF-8");
            connection.addRequestProperty("Accept-Language", "zh-CN");
            connection.addRequestProperty("X-Auth-User", "admin");
            connection.addRequestProperty("X-Auth-Key", "f0d419855c43e250441c48484097b82dffa242908cac60c129ce73ea3049fbcc");
            connection.addRequestProperty("X-Auth-UserType", "0");
            connection.setDoOutput(true); // post请求需要设置这两个为true
            connection.setDoInput(true);
            out = connection.getOutputStream();
            out.flush(); // 这里虽然是post请求，但是请求体是空，所以这里直接flush
            int responseCode = connection.getResponseCode();
            if (HttpURLConnection.HTTP_OK == responseCode) {  // HTTP/1.1 200 OK
                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String result = "";
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                }
                JSONObject jsonObject = JSON.parseObject(result);
                System.out.println(JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat));
                if (result.contains("errorCode")) {
                    System.err.println("login huawei fusion compute fail, receive response code : " + responseCode + ", reason : " + result);
                    return null;
//                    throw new ServiceException("login to huawei fusion compute fail, receive response code : "
//                            + responseCode + ", reason : " + result);
                }
                else {
                    String token = connection.getHeaderField("X-Auth-Token");
                    if (null == token || "".equals(token)) {
                        return null;
                    }
                    return token;
                }
            }
            else {
                in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                String result = "";
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                }
                throw new HttpResponseException(responseCode, "login to huawei fusion compute fail, reason : " + result);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw e;
        } finally {
            if (null != out) {
                out.close();
            }
            if (null != in) {
                in.close();
            }
        }

    }
}
