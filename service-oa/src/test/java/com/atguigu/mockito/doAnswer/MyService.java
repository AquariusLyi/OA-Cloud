package com.atguigu.mockito.doAnswer;

import com.atguigu.common.http.HttpUtils;

public class MyService {
    public String doSomething() throws Exception {
        String xxxx = HttpUtils.sendPost("xxxx", "");
        System.out.println("输出真是方法：doSomething");
        throw new Exception("original exception");
    }
}