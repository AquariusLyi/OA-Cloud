package com.atguigu.mockito.all;

import com.atguigu.auth.mapper.SysMenuMapper;
import com.atguigu.auth.service.impl.SysMenuServiceImpl;
import com.atguigu.common.http.gpt01.HttpUtils;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
@RunWith(PowerMockRunner.class)
@PrepareForTest(HttpUtils.class)
public class MyTest {

    @InjectMocks
    private SysMenuServiceImpl sysMenuService;
    @Mock
    private SysMenuMapper sysMenuMapper;



    /**
     * powermockito解决静态方法 静态工具类 http调用案例
     * 1.必须导入@RunWith(PowerMockRunner.class) @PrepareForTest({HttpUtils.class,MyClassB.class})
     * 2. PowerMockito.when(HttpUtils.post(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn("mock result");
     * @throws Exception
     *
     * 这个测试用例主要是mock静态类 且 进行异常处理
     */
    @Test
    public void testMockNestedMethod() throws Exception {
        // 1. mock静态方法
        PowerMockito.mockStatic(HttpUtils.class);
        PowerMockito.when(HttpUtils.post(Mockito.any(), Mockito.any(),Mockito.any())).thenReturn("返回测试json");
        Long id1 = 7l;
        // 2. mock持久层代码
        PowerMockito.when(sysMenuMapper.selectCount(Mockito.any())).thenReturn(0);
        sysMenuService.removeMenuById(id1);
        try {
            // 3. mock异常类
            Long id = null;
            sysMenuService.removeMenuById(id);
            Assertions.fail("这里会挂掉");
        } catch (Exception e) {
           Assertions.assertTrue( e instanceof IOException);
        }

    }
    /**
     *
     * 这个测试用例主要是mock异常方法 且mock无返回值方法
     */
    @Test
    public void testMockThrow() throws Exception {
        // 1. mock如果
        Long id = 7l;
        SysMenuServiceImpl mock = Mockito.mock(SysMenuServiceImpl.class);
        Mockito.doNothing().when(mock).removeMenuById(id);
        mock.removeMenuById(id);
        Mockito.verify(mock,Mockito.times(1)).removeMenuById(id);

    }




}
