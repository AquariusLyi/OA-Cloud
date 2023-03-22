package com.atguigu.mapstruct;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
//第一个拷贝对象
public class CompareA {
 
    private String name;
 
    private Integer age;
 
    private LocalDateTime createTime;
 
    private double score;
 
    private Double totalScore;
 
    private Date updateTIme;
 
    private ChildCompare childCompare;
 
    private Long days;
    
    //省略其get/set方法，也可使用Lombok,以后讲解lombok与mapstruct结合使用
 }