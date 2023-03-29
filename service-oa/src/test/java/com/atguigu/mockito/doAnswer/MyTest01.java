package com.atguigu.mockito.doAnswer;

import com.atguigu.common.http.gpt01.HttpUtils;
import com.atguigu.mockito.donothing.MyClassB;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest({HttpUtils.class, MyClassB.class})
public class MyTest01 {

    @Test
    public void testDoSomething() throws Exception {
        MyService myService = Mockito.spy(new MyService());
        //  PowerMockito.mockStatic(HttpUtils.class);
       //  Mockito.when(HttpUtils.sendRequest(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenReturn("mock http请求");
        Mockito.doAnswer(invocation -> {
            try {
                return invocation.callRealMethod();
            } catch (Exception e) {
                throw new Exception("mocked exception");
            }
        }).when(myService).doSomething();

        String result = myService.doSomething();

        // assert the exception message
        assertEquals("mocked exception", result);
    }
    @Test
    public void testDoSomething2() throws Exception {
        MyService myService = Mockito.spy(new MyService());
        Mockito.doAnswer(invocation -> {
            try {
                return invocation.callRealMethod();
            } catch (Exception e) {
                throw new Exception("mocked exception");
            }
        }).when(myService).doSomething();

        String result = myService.doSomething();

        // assert the exception message
        assertEquals("mocked exception", result);
    }



}