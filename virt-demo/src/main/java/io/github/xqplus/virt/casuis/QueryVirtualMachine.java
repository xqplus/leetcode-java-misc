package io.github.xqplus.virt.casuis;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class QueryVirtualMachine {

    public static void main(String[] args) throws IOException {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                new AuthScope("192.168.8.171", 8080, "VMC RESTful Web Services"),
                new UsernamePasswordCredentials("admin", "Dsg_123456!")
        );
        CloseableHttpClient client = HttpClients.custom()
                .setDefaultCredentialsProvider(credentialsProvider).build();

        HttpRequestBase requestBase = new HttpGet("http://192.168.8.171:8080/cas/casrs/vm/vmList");
        requestBase.addHeader("accept", "application/json");
        CloseableHttpResponse response = client.execute(requestBase);
        if (response.getStatusLine().getStatusCode() == 409) {
            String reason = new String(Arrays.toString(response.getHeaders("Error-Message")).getBytes(StandardCharsets.ISO_8859_1), "GBK");
            reason = new String(reason.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            reason = new String(reason.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            String errCode = Arrays.toString(response.getHeaders("Error-Code"));
            System.err.println(reason);
            System.err.println(errCode);
        } else {
            System.out.println(EntityUtils.toString(response.getEntity()));
        }
    }
}
