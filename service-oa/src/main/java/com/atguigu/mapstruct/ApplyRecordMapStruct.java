package com.atguigu.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
 
@Mapper
public interface ApplyRecordMapStruct {
 
    ApplyRecordMapStruct INSTANCE = Mappers.getMapper(ApplyRecordMapStruct.class);
 

    @Mappings({
            @Mapping(target = "certType", expression = "java(com.atguigu.mapstruct" +
                    ".ApplyRecordParseUtil.parseCertType(doz.getCertTypeEnd()))"),
            @Mapping(target = "status", expression = "java(com.atguigu.mapstruct" +
                    ".ApplyRecordParseUtil.parseStatus(doz.getStatusEnd()))")
    })
    TestVo dOToVO(TestEndVo doz);
 

}