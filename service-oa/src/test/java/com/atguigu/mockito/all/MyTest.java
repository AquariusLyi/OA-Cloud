package com.atguigu.mockito.all;

import com.atguigu.auth.mapper.SysMenuMapper;
import com.atguigu.auth.service.impl.SysMenuServiceImpl;
import com.atguigu.common.http.gpt01.HttpUtils;
import com.atguigu.model.system.ProdInfo;
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
import java.util.*;

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
     *
     * @throws Exception 这个测试用例主要是mock静态类 且 进行异常处理
     */
    @Test
    public void testMockNestedMethod() throws Exception {
        // 1. mock静态方法
        PowerMockito.mockStatic(HttpUtils.class);
        PowerMockito.when(HttpUtils.post(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn("返回测试json");
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
            Assertions.assertTrue(e instanceof IOException);
        }

    }

    /**
     * 这个测试用例主要是mock异常方法 且mock无返回值方法
     */
    @Test
    public void testMockThrow() throws Exception {
        // 1. mock如果
        Long id = 7l;
        SysMenuServiceImpl mock = Mockito.mock(SysMenuServiceImpl.class);
        Mockito.doNothing().when(mock).removeMenuById(id);
        mock.removeMenuById(id);
        Mockito.verify(mock, Mockito.times(1)).removeMenuById(id);

    }

    /**
     * 去重String类型的List
     */
    @Test
    public void testList去重() throws Exception {

        // 1. 根据审核ids查询出所有产品申请表数据 ->获取newData数据转成对象，组装List<产品协议名称> 调用查询接口
        // 2. 如果存在一样的协议名称 则进行去重，不重复的进行审核操作
        List<String> applyList = Arrays.asList("A协议", "B协议", "C协议");//待申请的所有协议名称
        List<String> exitList = Arrays.asList("B协议");//已存在的协议名称
        List<String> distinctList = compareLists(applyList, exitList);
        // 3. 过滤掉已存在的协议名称后进行审核操作

    }

    public static List<String> compareLists(List<String> list1, List<String> list2) {
        List<String> result = new ArrayList<>(list1);
        result.removeAll(list2);
        return result;
    }

    /**
     * 去重Object类型的List
     *
     */
    @Test
    public void testListString() throws Exception {

        // 1. 根据审核ids查询出所有产品申请表数据 -> 组装List<产品ID> 调用查询接口
        // 2. 如果存在一样的协议名称 则进行去重，不重复的进行审核操作
        List<ProdInfo> applyList = new ArrayList<>(Arrays.asList(
                new ProdInfo("Alice"),
                new ProdInfo("Bob"),
                new ProdInfo("Charlie")
        ));
//        List<ProdInfo> exitList = new ArrayList<>(Arrays.asList(
//                new ProdInfo("Alice"),
//                new ProdInfo("Bob"),
//                new ProdInfo("Charlie")
//        ));//已存在的协议名称
        List<ProdInfo> exitList = new ArrayList<>();//已存在的协议名称

         List<ProdInfo> distinctList = testListObject(null, exitList);
        //List<ProdInfo> distinctList = removeDuplicates(applyList, null);
        System.out.println();
        // 3. 过滤掉已存在的协议名称后进行审核操作

    }

    /**
     * 对比2个list 去掉相同协议名称的对象 并返回一个新数组
     * @param list1
     * @param list2
     * @return
     */
    public static List<ProdInfo> testListObject(List<ProdInfo> list1, List<ProdInfo> list2) {
        List<ProdInfo> resultList = new ArrayList<>();
        if(list1 != null && list2 == null){
            return list1;
        }else if (list1 != null ) {
            for (ProdInfo obj1 : list1) {
                boolean isDuplicate = false;
                for (ProdInfo obj2 : list2) {
                    if (obj1.getProdName().equals(obj2.getProdName())) {
                        isDuplicate = true;
                        break;
                    }
                }
                if (!isDuplicate) {
                    resultList.add(obj1);
                }
            }
        }
        return resultList;
    }


    /**
     * 同一个产品id名称一样无所谓
     * 传入name+id where name = "a" and prodIds!="p7721"
     *
     * 传入List<newData>  接口方遍历 where name = "a" and prodIds!="p7721" 如果有
     *
     * 本来批量是根据ids进行批量更新，现在存在几条通过 几条不通过 就得单条进行更新了
     * api那边批量更新是他们自己管理的，1条通过4条存在同样的名称 算审核失败还是审核成功
     * 批量审核通过 填了审核意见 存在一样的协议名称 审核不通过 审核意见怎么填，是否直接显示：协议名称重复
     *
     *
     */
    @Test
    public void testListProd() throws Exception {

        // 1. 根据审核ids查询出所有产品申请表数据 -> 组装List<newDataInfo> 调用查询接口 查询接口遍历 根绝 name与id进行查询
        // 2. 返回的数据是需要审核的数据 根据id与name查询出如果有 就丢弃，没有就存入list返回给我
        List<ProdInfo> applyList = new ArrayList<>(Arrays.asList(
                new ProdInfo("Alice"),
                new ProdInfo("Bob"),
                new ProdInfo("Charlie")
        ));
        // 根据CRUD类型 落盘正式表
        List<String> applyUpdateList = new ArrayList<>();
        for (ProdInfo prodInfo : applyList) {
            // insertProd(prodInfo);
            // 记录审核通过坐标
            applyUpdateList.add(prodInfo.getProdName());
        }
        // 申请表 再次遍历申请表 如果prodid相等
        for (ProdInfo prodInfo : applyList) {
            for (String prodId : applyUpdateList) {
               if( prodInfo.equals(prodId)){
                   // updateApply（）
               }else{

               }
            }
        }
        // 则更新为：审核通过
        // 如果不相等则更新为：审核通过

//        List<ProdInfo> exitList = new ArrayList<>(Arrays.asList(
//                new ProdInfo("Alice"),
//                new ProdInfo("Bob"),
//                new ProdInfo("Charlie")
//        ));//已存在的协议名称
        List<ProdInfo> exitList = new ArrayList<>();//返回的数据是不需要审核的数据

        List<ProdInfo> distinctList = testListObject(null, exitList);
        // 2. 如果存在一样的协议名称 则进行去重，不重复的进行审核操作


    }

}
