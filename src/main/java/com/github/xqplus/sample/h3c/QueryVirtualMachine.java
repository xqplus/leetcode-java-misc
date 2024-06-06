package com.github.xqplus.sample.h3c;

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

public class QueryVirtualMachine {

    public static void main(String[] args) throws IOException {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                new AuthScope("192.168.8.171", 8080, "VMC RESTful Web Services"),
                new UsernamePasswordCredentials("admin", "Dsg_123456!")
        );
        CloseableHttpClient client = HttpClients.custom()
                .setDefaultCredentialsProvider(credentialsProvider).build();

        HttpRequestBase requestBase = new HttpGet("http://192.168.8.171:8080/cas/casrs/host/id/2");
        requestBase.addHeader("accept", "application/json");
        CloseableHttpResponse response = client.execute(requestBase);
        System.out.println(EntityUtils.toString(response.getEntity()));
    }
}
