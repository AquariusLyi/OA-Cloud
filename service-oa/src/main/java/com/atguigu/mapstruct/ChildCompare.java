package com.atguigu.mapstruct;

import lombok.Data;

@Data
//这是CompareA和CompareB共有的对象
public class ChildCompare {
 
    private String childName;
 
    private long childAge;
    
    //省略其get/set方法，
}