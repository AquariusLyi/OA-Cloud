package com.atguigu.mockito;

import com.atguigu.auth.service.SysRoleService;
import com.atguigu.model.system.SysRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class MockitoDemo1 {

    //注入
    @Autowired
    private SysRoleService service;

    //查询所有记录
    @Test
    public void getAll() {
        // mock creation 创建mock对象

        List mockedList = mock(List.class);

    //using mock object 使用mock对象

        mockedList.add("one");

        mockedList.clear();

   //verification 验证

        verify(mockedList).add("one");

        verify(mockedList).clear();
    }

}
