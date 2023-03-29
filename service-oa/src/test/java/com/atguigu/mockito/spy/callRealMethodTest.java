package com.atguigu.mockito.spy;


import com.atguigu.common.http.gpt01.HttpUtils;
import com.atguigu.mockito.donothing.MyClassB;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({HttpUtils.class, MyClassB.class})
public class callRealMethodTest {

    @Test
    public void callRealMethodTest() {
        Jerry jerry = mock(Jerry.class);
        doCallRealMethod().when(jerry).goHome();
        // Mockito.doNothing().when(jerry).doSomeThingB();
        // 只打印doSomeThingB方法
        doCallRealMethod().when(jerry).doSomeThingB();

        jerry.goHome();

        verify(jerry).doSomeThingA();
        verify(jerry).doSomeThingB();
    }

}