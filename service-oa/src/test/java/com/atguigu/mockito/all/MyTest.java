package com.atguigu.mockito.all;

import com.atguigu.auth.mapper.SysMenuMapper;
import com.atguigu.auth.service.impl.SysMenuServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
@RunWith(MockitoJUnitRunner.class)
public class MyTest {

    @InjectMocks
    @Spy
    private SysMenuServiceImpl sysMenuService;
    @Mock
    private SysMenuMapper sysMenuMapper;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }


    @Test
    public void testMockNestedMethod() throws Exception {
        Long id = null;
        try {
            sysMenuService.removeMenuById(id);
            Assertions.fail("这里会挂掉");
        } catch (Exception e) {
           Assertions.assertTrue( e instanceof IOException);
        }


    }


}
