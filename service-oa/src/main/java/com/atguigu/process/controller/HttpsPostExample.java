package com.atguigu.process.controller;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

public class HttpsPostExample {

    public static void main(String[] args) {
        String url = "https://www.example.com/api";

        try (CloseableHttpClient httpClient = createHttpClient()) {
            HttpPost httpPost = new HttpPost(url);
            // 设置请求头信息和请求体信息

            try (CloseableHttpResponse httpResponse = httpClient.execute(httpPost)) {
                // 获取响应结果
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static CloseableHttpClient createHttpClient() throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, IOException {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        X509TrustManager trustManager = new NoopTrustManager();
        sslContext.init(null, new TrustManager[] { trustManager }, null);

        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
                sslContext,
                NoopHostnameVerifier.INSTANCE);

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)
                .setSocketTimeout(5000)
                .build();

        return HttpClients.custom()
                .setSSLSocketFactory(sslConnectionSocketFactory)
                .setDefaultRequestConfig(requestConfig)
                .build();
    }

    private static class NoopTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
        }

        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }

    }

}
