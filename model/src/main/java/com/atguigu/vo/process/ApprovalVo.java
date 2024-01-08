package com.atguigu.vo.process;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.converters.bigdecimal.BigDecimalNumberConverter;
import com.alibaba.excel.converters.bigdecimal.BigDecimalStringConverter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ApprovalVo {

    //        @ExcelProperty(value = "Number", index = 0, converter = BigDecimalConverter.class)
    @ExcelProperty(value = "Number", index = 0, converter = BigDecimalNumberConverter.class)
    private Long processId;
    @ExcelProperty(value = "Number", index = 0, converter = BigDecimalStringConverter.class)
    private String taskId;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "审批描述")
    private String description;
}
