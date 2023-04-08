package com.atguigu.mybatisplus;

import com.atguigu.auth.mapper.SysRoleMapper;
import com.atguigu.auth.service.SysRoleService;
import com.atguigu.model.system.SysRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * 此类是用于分页查询
 */
@SpringBootTest
public class PageWrapperTest {

    //注入
    @Autowired
    private SysRoleService service;
    @Autowired
    private SysRoleMapper sysRoleMapper;

    //无条件分页查询
    @Test
    public void getAll() {
        List<SysRole> list = service.list();
        System.out.println(list);
    }

}
