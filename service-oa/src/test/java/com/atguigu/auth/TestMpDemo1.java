package com.atguigu.auth;

import com.atguigu.auth.mapper.SysRoleMapper;
import com.atguigu.model.system.SysRole;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.google.common.base.Function;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@SpringBootTest
public class TestMpDemo1 {

    //注入
    @Autowired
    private SysRoleMapper mapper;

    //查询所有记录
    @Test
    public void getAll() {
        List<SysRole> list = mapper.selectList(null);
        System.out.println(list);
    }

    //添加操作
    @Test
    public void add() {
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("角色管理员1");
        sysRole.setRoleCode("role1");
        sysRole.setDescription("角色管理员1");

        int rows = mapper.insert(sysRole);
        System.out.println(rows);
        System.out.println(sysRole.getId());
    }

    //修改操作
    @Test
    public void update() {
        //根据id查询
        SysRole role = mapper.selectById(9);
        //设置修改值
        role.setRoleName("atguigu角色管理员");
        //调用方法实现最终修改
        int rows = mapper.updateById(role);
        System.out.println(rows);
    }

    //删除操作
    @Test
    public void deleteId() {
        int rows = mapper.deleteById(10);
    }

    //批量删除
    @Test
    public void testDeleteBatchIds() {
        int result = mapper.deleteBatchIds(Arrays.asList(1, 2));
        System.out.println(result);
    }

    //条件查询
    @Test
    public void testQuery1() {
        //创建QueryWrapper对象，调用方法封装条件
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.eq("role_name","atguigu角色管理员");
        //调用mp方法实现查询操作
        List<SysRole> list = mapper.selectList(wrapper);
        System.out.println(list);
    }

    @Test
    public void testQuery2() {
        //LambdaQueryWrapper，调用方法封装条件
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getRoleName,"atguigu角色管理员");
        List<SysRole> list = mapper.selectList(wrapper);
        System.out.println(list);
    }

    /**
     * Mybatis-plus 自定义数组字段查询
     */
    @Test
    public void testQueryBatchX() {
        //LambdaQueryWrapper，调用方法封装条件
        List<String> roleCode = new ArrayList<>();
        roleCode.add("role1");
        roleCode.add("role2");
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SysRole::getRoleCode,roleCode);
        //调用mp方法实现查询操作
       // List<SysRole> sysRoles = mapper.selectList(wrapper);
        // 方法二 lameda表达式
        List<SysRole> sysRoles2 = mapper.selectList(new QueryWrapper<SysRole>()
                .lambda()
                .in(SysRole::getRoleCode, roleCode));
        System.out.println(sysRoles2);
    }
    /**
     * Mybatis-plus 根据指定数组字段批量更新数据
     */
    @Test
    public void testUpdateBatchX() {
        //LambdaQueryWrapper，调用方法封装条件
        List<String> roleCodeList = new ArrayList<>();
        roleCodeList.add("role1");
        roleCodeList.add("role2");
        //调用mp方法实现查询操作
        SysRole sysRole = new SysRole();
        sysRole.setDescription("测试管理员");

        int rows = mapper.update(sysRole, new UpdateWrapper<SysRole>()
                .in("role_code", roleCodeList));
        System.out.println(rows);
        // 方案二
        UpdateWrapper<SysRole> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("role_code", roleCodeList)
                .set("description", "2天更新就对了")
                .set("role_name", "角色管理员");
        int rows2 = mapper.update(null, updateWrapper);
    }






}
