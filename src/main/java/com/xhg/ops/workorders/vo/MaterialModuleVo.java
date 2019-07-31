package com.xhg.ops.workorders.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "物料模块Vo")
@Data
public class MaterialModuleVo {

    @ApiModelProperty(value = "模块id")
    private String id;
    
    @ApiModelProperty(value = "模块名称 ")
    private String name;

    @ApiModelProperty(value = "创建人")
    private String createdUserName;
}
