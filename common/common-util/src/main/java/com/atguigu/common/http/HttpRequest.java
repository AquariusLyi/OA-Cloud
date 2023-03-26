package com.atguigu.common.http;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.net.HttpURLConnection;


public class HttpRequest {
        /**
         * 向指定URL发送GET方法的请求
         *
         * @param url   发送请求的URL
         * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
         * @return URL 所代表远程资源的响应结果
         */
        public static String sendGet(String url, String param) {
            String result = "";
            try {
                String urlNameString = url + param;
               // LoggerUtil.info("请求：" + urlNameString);
                URL realUrl = new URL(urlNameString);
                // 打开和URL之间的连接
                URLConnection connection = realUrl.openConnection();
                // 设置通用的请求属性
                connection.setRequestProperty("accept", "*/*");
                connection.setRequestProperty("connection", "Keep-Alive");
                connection.setRequestProperty("user-agent",
                        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
                // 建立实际的连接
                connection.connect();
                // 获取所有响应头字段
                Map<String, List<String>> map = connection.getHeaderFields();
                // 遍历所有的响应头字段
                /*for (String key : map.keySet()) {
                  LoggerUtil.info(key + "--->" + map.get(key));
                }*/
                InputStream inStream = null;
                ByteArrayOutputStream outSteam = null;
                try {
                    inStream = connection.getInputStream();
                    outSteam = new ByteArrayOutputStream();
                    byte[] buffer = new byte[200];
                    int len = -1;
                    while ((len = inStream.read(buffer)) != -1) {
                        outSteam.write(buffer, 0, len);
                    }
                    outSteam.close();
                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        outSteam.close();
                        inStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (outSteam != null) {
                        result = new String(outSteam.toByteArray(), "utf-8");
                    }
                }
    
            } catch (Exception e) {
               // LoggerUtil.info("发送GET请求出现异常！" + e);
                e.printStackTrace();
            }
            return result;
        }
    
        /**
         * 向指定URL发送POST方法的请求，请求参数为json字符串
         *
         * @param urlPath
         * @param Json
         * @return
         */
        public static String sendPOST(String urlPath, String Json) {
            String result = "";
            BufferedReader reader = null;
            try {
                URL url = new URL(urlPath);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                // 请求方式
                conn.setRequestMethod("POST");
                // 超时时间
                conn.setConnectTimeout(3000);
                // 设置是否输出
                conn.setDoOutput(true);
                // 设置是否读入
                conn.setDoInput(true);
                // 设置是否使用缓存
                conn.setUseCaches(false);
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("Charset", "UTF-8");
                // 设置文件类型:
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                // 设置接收类型否则返回415错误
                //conn.setRequestProperty("accept","*/*")此处为暴力方法设置接受所有类型，以此来防范返回415;
                conn.setRequestProperty("accept", "application/json");
                // 往服务器里面发送数据
                if (Json != null && !StringUtils.isEmpty(Json)) {
                    byte[] writebytes = Json.getBytes();
                    // 设置文件长度
                    conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
                    OutputStream outwritestream = conn.getOutputStream();
                    outwritestream.write(Json.getBytes());
                    outwritestream.flush();
                    outwritestream.close();
                    // LoggerUtil.info("doJsonPost: conn" + conn.getResponseCode());
                }
                if (conn.getResponseCode() == 200) {
                    reader = new BufferedReader(
                            new InputStreamReader(conn.getInputStream()));
                    result = reader.readLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return result;
        }
    
        /**
         * 以form表单的形式发送POST方法的请求，请求参数为Map集合
         *
         * @param urlPath
         * @param requestMap
         * @return
         */
        public static String sendFormPOST(String urlPath, Map<String, String> requestMap) {
            String parmas = getRequestParamString(requestMap);
            String result = "";
            BufferedReader reader = null;
            try {
                URL url = new URL(urlPath);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                // 请求方式
                conn.setRequestMethod("POST");
                // 超时时间
                conn.setConnectTimeout(3000);
                // 设置是否输出
                conn.setDoOutput(true);
                // 设置是否读入
                conn.setDoInput(true);
                // 设置是否使用缓存
                conn.setUseCaches(false);
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("Charset", "UTF-8");
                // 设置文件类型:
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
                // 设置接收类型否则返回415错误
                //conn.setRequestProperty("accept","*/*")此处为暴力方法设置接受所有类型，以此来防范返回415;
                // 往服务器里面发送数据
                if (parmas != null && !StringUtils.isEmpty(parmas)) {
                    byte[] writebytes = parmas.getBytes();
                    // 设置文件长度
                    conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
                    OutputStream outwritestream = conn.getOutputStream();
                    outwritestream.write(parmas.getBytes());
                    outwritestream.flush();
                    outwritestream.close();
                    // LoggerUtil.info("doJsonPost: conn" + conn.getResponseCode());
                }
                if (conn.getResponseCode() == 200) {
                    reader = new BufferedReader(
                            new InputStreamReader(conn.getInputStream()));
                    result = reader.readLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return result;
        }
    
        @SuppressWarnings("rawtypes")
        private static String getRequestParamString(Map<String, String> requestParam) {
            String coder = "UTF-8";//如果系统是其它编码，这里应该随之改动
            StringBuffer sf = new StringBuffer("");
            String reqstr = "";
            if ((null != requestParam) && (0 != requestParam.size())) {
                for (Map.Entry en : requestParam.entrySet()) {
                    try {
                        sf.append(new StringBuilder()
                                .append((String) en.getKey())
                                .append("=")
                                .append((null == en.getValue())
                                        || ("".equals(en.getValue())) ? ""
                                        : URLEncoder.encode((String) en.getValue(),
                                        coder)).append("&").toString());
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        return "";
                    }
                }
                reqstr = sf.substring(0, sf.length() - 1);
            }
           //  LoggerUtil.info(new StringBuilder().append("请求报文:[").append(reqstr).append("]").toString());
            return reqstr;
        }
    
    }