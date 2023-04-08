package com.atguigu.jdk;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 此类是用于编写一些常用的jdk类 基于jdk8
 */
@RunWith(SpringRunner.class)
public class Jdk01Test {



    //字符串变List集合
    @Test
    public void getListStrByStr() {
        String str = "hello";
        List<String> list = new ArrayList<>(Collections.singletonList(str));
        System.out.println();
    }

}
