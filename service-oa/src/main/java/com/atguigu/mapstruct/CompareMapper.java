package com.atguigu.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.control.DeepClone;
import org.mapstruct.factory.Mappers;
 
/**
 * @author moxiao
 */
@Mapper
public interface CompareMapper {
 
    CompareMapper INSTANCE = Mappers.getMapper(CompareMapper.class);
 
    @Mapping(target = "day", source = "days")
    @Mapping(target = "createTime", source = "createTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "childCompare", source = "childCompare", mappingControl = DeepClone.class)
    CompareB mapper(CompareA compareA);
}