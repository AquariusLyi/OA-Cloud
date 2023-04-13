package com.atguigu.process.controller.api;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

public class HttpsPostExample {

    public static void main(String[] args) {
        String url = "https://www.example.com/api";

        Map<String, String> parameters = new HashMap<>();
        parameters.put("name", "John");
        parameters.put("age", "30");

        extractedhttps(url, parameters);
    }

    private static void extractedhttps(String url, Map<String, String> parameters) {
        try  {
            SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
            sslContextBuilder.loadTrustMaterial(null, TrustAllStrategy.INSTANCE);
            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
                    sslContextBuilder.build(),
                    NoopHostnameVerifier.INSTANCE);

            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(5000)
                    .setSocketTimeout(5000)
                    .build();

            CloseableHttpClient httpClient = HttpClients.custom()
                    .setSSLSocketFactory(sslConnectionSocketFactory)
                    .setDefaultRequestConfig(requestConfig)
                    .build();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "application/json");

            StringEntity requestEntity = new StringEntity(JSON.toJSONString(parameters), ContentType.APPLICATION_JSON);
            httpPost.setEntity(requestEntity);

            try (CloseableHttpResponse httpResponse = httpClient.execute(httpPost)) {
                HttpEntity responseEntity = httpResponse.getEntity();
                String responseString = EntityUtils.toString(responseEntity, "UTF-8");
                System.out.println(responseString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static CloseableHttpClient createHttpClient() throws Exception {
        SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
        sslContextBuilder.loadTrustMaterial(null, TrustAllStrategy.INSTANCE);
        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
                sslContextBuilder.build(),
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

}
