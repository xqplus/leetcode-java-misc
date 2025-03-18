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

    private static final String TOKEN = "84AC3CE9-8CF3-49FF-9C7F-264350B351A70";
    private static final String SITE_URI = "/service/sites/367C0709";
    private static final String HOST_URN = "urn:sites:367C0709:hosts:105";

    public static void main(String[] args) throws Exception {
//        System.out.println(getToken());
        get("/service/sites/367C0709/folder/223757");
    }

    private static void powerOffVm() throws IOException, NoSuchAlgorithmException, KeyManagementException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("mode", "force");
        post(SITE_URI + "/vms/i-000003E0/action/stop", requestBody.toString());
    }

    private static void getPortGroups() throws IOException, NoSuchAlgorithmException, KeyManagementException {
        get(SITE_URI + "/portgroups");
    }

    private static void getHost(String hostUrn) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        get(SITE_URI + "/hosts/105");
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

    private static void createDatastore() throws IOException, NoSuchAlgorithmException, KeyManagementException {
        JSONObject datastoreInfo = new JSONObject();
        datastoreInfo.put("storageUnitUrn", "");
        datastoreInfo.put("name", "VBP_ARV_192.168.8.151");
        datastoreInfo.put("useType", 1);
        post("/service/sites/367C0709/datastores", datastoreInfo.toString());
    }

    private static void createVm() throws IOException, NoSuchAlgorithmException, KeyManagementException {
        JSONObject vmInfo = new JSONObject();
        vmInfo.put("name", "test1");
        vmInfo.put("location", "urn:sites:367C0709:hosts:105");
        vmInfo.put("autoBoot", false);

        JSONObject vmConfig = new JSONObject();
        JSONObject cpu = new JSONObject();
        cpu.put("quantity", 1);
        cpu.put("coresPerSocket", 1);
        cpu.put("cpuBindType", "nobind");
        vmConfig.put("cpu", cpu);
        JSONObject memory = new JSONObject();
        memory.put("quantityMB", 1024);
        vmConfig.put("memory", memory);
        JSONArray disks = new JSONArray(1);
        JSONObject disk = new JSONObject();
        disk.put("sequenceNum", 1);
        disk.put("quantityGB", 5);
        disk.put("datastoreUrn", "urn:sites:367C0709:datastores:1");
        disk.put("isThin", true);
        disks.add(disk);
        vmConfig.put("disks", disks);
        JSONArray nics = new JSONArray();
        JSONObject nic = new JSONObject();
        nic.put("portGroupUrn", "urn:sites:367C0709:dvswitchs:1:portgroups:1");
        nics.add(nic);
        vmConfig.put("nics", nics);
        vmInfo.put("vmConfig", vmConfig);

        JSONObject osOption = new JSONObject();
        osOption.put("osType", "Linux");
        osOption.put("osVersion", 462);
        vmInfo.put("osOptions", osOption);

        post(SITE_URI + "/vms", vmInfo.toString());
    }

    public static void get(String uri) throws IOException, NoSuchAlgorithmException, KeyManagementException {
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
        JSONObject jsonObject = JSON.parseObject(stringBuilder.toString());
        System.out.println(JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat));
    }

    public static void post(String uri, String json) throws IOException, NoSuchAlgorithmException, KeyManagementException {
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
        JSONObject jsonObject = JSON.parseObject(stringBuilder.toString());
        System.out.println(JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat));
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
