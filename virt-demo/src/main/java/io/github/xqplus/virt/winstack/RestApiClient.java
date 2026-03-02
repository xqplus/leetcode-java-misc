package io.github.xqplus.virt.winstack;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * Rest Api 客户端，基于 RestTemplate 封装
 *
 * @author chenq
 * @since 2.7.0
 */
public class RestApiClient {
    private final CloseableHttpClient httpClient;
    private final RestTemplate rt;

    public RestApiClient(ClientHttpRequestInterceptor headerInterceptor) throws GeneralSecurityException {
        this(5, 60, headerInterceptor);
    }

    public RestApiClient(int connTimeoutSeconds, int readTimeoutSeconds, ClientHttpRequestInterceptor headerInterceptor) throws GeneralSecurityException {
        // 创建信任所有证书的策略
        SSLContext sslContext = new SSLContextBuilder()
                .loadTrustMaterial(null, (certificate, authType) -> true)
                .build();
        // 配置SSL套接字工厂，同时跳过主机名验证
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
        // 超时配置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connTimeoutSeconds * 1000)
                .setSocketTimeout(readTimeoutSeconds * 1000)
                .setConnectionRequestTimeout(connTimeoutSeconds * 1000)
                .build();
        // 使用该工厂创建 HttpClient
        httpClient = HttpClients.custom()
                .setSSLSocketFactory(socketFactory)
                .setMaxConnTotal(200) // 最大连接数
                .setMaxConnPerRoute(50) // 每个主机最大连接数
                .setDefaultRequestConfig(requestConfig)
                .setRetryHandler(new DefaultHttpRequestRetryHandler(2, false)) // 重试策略
                .evictIdleConnections(60, TimeUnit.SECONDS) // 清理空闲线程间隔
                .evictExpiredConnections()
                .build();
        // 将 HttpClient 绑定到 RestTemplate
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        rt = new RestTemplate(factory);
        rt.setInterceptors(Collections.singletonList(headerInterceptor));
    }

    public <T> T get(String url, Class<T> responseClazz) {
        return rt.getForObject(url, responseClazz);
    }

    public <T> T post(String url, Object body, Class<T> responseClazz) {
        return rt.postForObject(url, body, responseClazz);
    }

    public void close() throws IOException {
        if (httpClient != null) httpClient.close();
    }
}
