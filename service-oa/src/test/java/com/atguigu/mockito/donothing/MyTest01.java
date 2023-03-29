package com.atguigu.mockito.donothing;

import com.atguigu.common.http.gpt01.HttpUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({HttpUtils.class,MyClassB.class})
public class MyTest01 {


    /**
     * powermockito解决静态方法 静态工具类 http调用案例
     * 1.必须导入@RunWith(PowerMockRunner.class) @PrepareForTest({HttpUtils.class,MyClassB.class})
     * 2. PowerMockito.when(HttpUtils.post(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn("mock result");
     * @throws Exception
     */
    @Test
    public void testMockStaticMethod02() throws Exception {
        PowerMockito.mockStatic(HttpUtils.class);
        PowerMockito.mockStatic(MyClassB.class);
        PowerMockito.when(HttpUtils.post(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn("mock result");


        MyClass myClass = new MyClass();
        PowerMockito.when(MyClassB.myStaticMethodB()).thenReturn("mock MyClassB");

        String s = myClass.myStaticMethod();// 这里可以看出已经拼接
        System.out.println(s);
        Assert.assertEquals("actual resultmock MyClassB", s);
    }

}