package com.atguigu.mapstruct;


import lombok.Data;

import java.util.Date;
@Data
public class CompareB {
 
    private String name;
 
    private Integer age;
 
    private String createTime;
 
    private double score;
 
    private Double totalScore;
 
    private Date updateTIme;
 
    private ChildCompare childCompare;
 
    private Long day;
    
    //省略其get/set方法，
}