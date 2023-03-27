package com.atguigu.mockito;

import com.atguigu.common.http.gpt01.HttpUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.util.Map;

public class MyTest {

    @Test
    public void testMockHttpGet() throws Exception {
        // 创建 HttpClient 的 Mock 对象
        HttpClient httpClient = Mockito.mock(HttpClient.class);

        // 创建 HttpResponse 的 Mock 对象
        HttpResponse httpResponse = Mockito.mock(HttpResponse.class);

        // 创建 HttpEntity 的 Mock 对象
        HttpEntity httpEntity = Mockito.mock(HttpEntity.class);

        // 创建 StatusLine 的 Mock 对象
        StatusLine statusLine = Mockito.mock(StatusLine.class);

        // 设置 HttpResponse 的状态行
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(statusLine.getStatusCode()).thenReturn(200);

        // 设置 HttpResponse 的实体
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        Mockito.when(httpEntity.getContent()).thenReturn(new ByteArrayInputStream("Hello, world!".getBytes()));

        // 创建 HttpGet 对象并设置 URL
        HttpGet httpGet = new HttpGet("http://example.com");

        // 设置 HttpClient 的 execute 方法返回 Mock 的 HttpResponse 对象
        Mockito.when(httpClient.execute(httpGet)).thenReturn(httpResponse);

        // 发送 Mock 请求并获取响应内容
        String response = EntityUtils.toString(httpClient.execute(httpGet).getEntity());

        // 验证响应内容是否正确
        Assert.assertEquals("Hello, world!", response);
    }

    @Test
    public void testMockHttpPost() throws Exception {
        // 创建 HttpClient 的 Mock 对象
        HttpClient httpClient = Mockito.mock(HttpClient.class);

        // 创建 HttpResponse 的 Mock 对象
        HttpResponse httpResponse = Mockito.mock(HttpResponse.class);

        // 创建 HttpEntity 的 Mock 对象
        HttpEntity httpEntity = Mockito.mock(HttpEntity.class);

        // 设置 HttpResponse 的状态行和实体
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
        Mockito.when(httpEntity.getContent()).thenReturn(new ByteArrayInputStream("Hello, world!".getBytes()));

        // 创建 HttpPost 对象并设置 URL 和请求体
        HttpPost httpPost = new HttpPost("http://example.com/api/something");
        httpPost.addHeader("content-type", "application/json");
        httpPost.setEntity(new StringEntity("{\"param1\":\"value1\", \"param2\":\"value2\"}"));

        // 设置 HttpClient 的 execute 方法返回 Mock 的 HttpResponse 对象
        Mockito.when(httpClient.execute(httpPost)).thenReturn(httpResponse);

        // 发送 Mock 请求并获取响应内容
        String response = EntityUtils.toString(httpClient.execute(httpPost).getEntity());
        String body = "{\"key\": \"value\"}";
        Map<String, String> headers = HttpUtils.getDefaultHeaders();
        String response2 = HttpUtils.post("https://www.example.com", headers, body);
        // 验证响应内容是否正确
        Assert.assertEquals("Hello, world!", response);
    }
}
