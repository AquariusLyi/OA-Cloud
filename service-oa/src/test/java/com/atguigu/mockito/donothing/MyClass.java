package com.atguigu.mockito.donothing;

import com.atguigu.common.http.gpt01.HttpUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MyClass {
    public  String myStaticMethod() {
        String s = MyClassB.myStaticMethodB();
        try {
            String body = "{\"key\": \"value\"}";
            Map<String, String> headers = new HashMap<>();
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json;charset=utf-8");
            String response2 = HttpUtils.post("https://www.example.com", headers, body);
            System.out.println(response2);
        } catch (IOException e) {
        }
        return "actual result" + s;
    }
}
