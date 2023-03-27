package com.atguigu.common.http.gpt01;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpUtils {
    private static final Logger logger = LogManager.getLogger(HttpUtils.class);

    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";

    public static String sendRequest(String method, String url, Map<String, String> headers, String body) throws IOException {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL requestUrl = new URL(url);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod(method);

            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Keep-Alive", "timeout=5");

            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            if (body != null) {
                connection.setDoOutput(true);
                connection.getOutputStream().write(body.getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = connection.getResponseCode();
            if (responseCode >= 200 && responseCode < 300) {
                StringBuilder responseBuilder = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                String line;
                while ((line = reader.readLine()) != null) {
                    responseBuilder.append(line);
                }
                String response = responseBuilder.toString();

                logger.info("HTTP request succeeded with status code: {}, url: {}, method: {}, headers: {}, body: {}, response: {}", responseCode, url, method, headers, body, response);

                return response;
            } else {
                logger.warn("HTTP request failed with status code: {}, url: {}, method: {}, headers: {}, body: {}", responseCode, url, method, headers, body);

                throw new IOException("HTTP request failed with status code: " + responseCode);
            }
        } catch (IOException e) {
            logger.error("HTTP request failed with exception, url: {}, method: {}, headers: {}, body: {}", url, method, headers, body, e);
            throw e;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    logger.warn("Failed to close BufferedReader", e);
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public static String get(String url, Map<String, String> headers) throws IOException {
        return sendRequest(GET, url, headers, null);
    }

    public static String post(String url, Map<String, String> headers, String body) throws IOException {
        return sendRequest(POST, url, headers, body);
    }

    public static String put(String url, Map<String, String> headers, String body) throws IOException {
        return sendRequest(PUT, url, headers, body);
    }

    public static String delete(String url, Map<String, String> headers) throws IOException {
        return sendRequest(DELETE, url, headers, null);
    }
    /**
     * 发送 GET 请求
     * @param url 请求的 URL
     * @return 响应结果
     * @throws IOException 请求发送失败或响应状态码不为 2xx
     */
    public static String doGet(String url) throws IOException {
        return get(url, null);
    }

    /**
     * 发送 POST 请求
     * @param url 请求的 URL
     * @param params 请求参数，格式为 key-value 的键值对
     * @return 响应结果
     * @throws IOException 请求发送失败或响应状态码不为 2xx
     */
    public static String doPost(String url, Map<String, String> params) throws IOException {
        StringBuilder bodyBuilder = new StringBuilder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                bodyBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        String body = bodyBuilder.toString();
        if (body.length() > 0) {
            body = body.substring(0, body.length() - 1);
        }
        return post(url, getDefaultHeaders(), body);
    }

    public static Map<String, String> getDefaultHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json;charset=utf-8");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36 Edge/16.16299");
        return headers;
    }
    public static void main(String[] args) {
        try {
            // 发送 GET 请求
            String response = HttpUtils.get("https://www.example.com", null);
            logger.info(response);

            // 发送 POST 请求
            String body = "{\"key\": \"value\"}";
            Map<String, String> headers = HttpUtils.getDefaultHeaders();
            String response2 = HttpUtils.post("https://www.example.com", headers, body);
            logger.info(response2);
        } catch (IOException e) {
            logger.error("Failed to send HTTP request", e);
        }
    }
}
