package com.atguigu.mapstruct;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel(description = "分配菜单")
@Data
public class TestVo {

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "角色id列表")
    private List<Long> roleIdList;

    private String status;

    private String certType;

}
