package com.atguigu.mockito;

import com.atguigu.auth.mapper.SysMenuMapper;
import com.atguigu.auth.service.impl.SysMenuServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
public class MockitoTest {
    // 需要测试的类，会创建真实实例
    @InjectMocks
    private SysMenuServiceImpl sysMenuService;
    // 方法中依赖的类，会创建虚拟的mock
    @Mock
    private SysMenuMapper sysMenuMapper;

//    @BeforeAll
//    public void setUp() throws Exception {
//        log.info("测试开始");
//        // 初始化Mockito，或者junit4中使用@Runwith(MockitoJunitRunner.class)
//        MockitoAnnotations.openMocks(this);
//    }
    @AfterAll
    public void tearDown() {
        log.info("测试结束");
    }
    @Test
    public void testMockHttpGet() throws Exception {
        // 创建HttpClient的Mock对象
        HttpClient httpClient = Mockito.mock(HttpClient.class);

        // 创建HttpResponse的Mock对象
        HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
        // 创建HttpEntity的Mock对象
        HttpEntity httpEntity = Mockito.mock(HttpEntity.class);
        StatusLine statusLine = Mockito.mock(StatusLine.class);

        // 设置HttpResponse的状态码和响应内容
       // Mockito.when(httpResponse.getStatusLine().getStatusCode()).thenReturn(200); httpResponse.getStatusLine()=null 导致空指针异常
        // 解决方案：分成2部分进行
        //1.
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        //2.
        Mockito.when(httpResponse.getStatusLine().getStatusCode()).thenReturn(200);

        Mockito.when(httpEntity.getContent()).thenReturn(new ByteArrayInputStream("Hello, world!".getBytes()));

        // 设置HttpResponse的实体
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);

        // 创建HttpGet对象并设置URL
        HttpGet httpGet = new HttpGet("http://example.com");

        // 设置HttpClient的execute方法返回Mock的HttpResponse对象
        Mockito.when(httpClient.execute(httpGet)).thenReturn(httpResponse);

        // 发送Mock请求并获取响应内容
        String response = EntityUtils.toString(httpClient.execute(httpGet).getEntity());

        // 验证响应内容是否正确
        Assert.assertEquals("Hello, world!", response);
    }


    @Test
    public void testServiceSignIn() throws IOException {
        long myLong = 123456789L; // 通过"L"后缀指定long类型

        // 创建返回的模拟用户数据：王五

        // 对方法调用dao时进行mock处理
        Mockito.when( sysMenuMapper.selectCount(any())).thenReturn(1);

        // 测试的方法执行登录操作获得mock数据返回
        sysMenuService.removeMenuById(myLong);
    }
}
