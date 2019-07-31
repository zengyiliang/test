package com.xhg.ops.workorders.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "物料配件Vo")
@Data
public class MaterialPartsVo {

    @ApiModelProperty(value = "配件id")
    private String id;
    
    @ApiModelProperty(value = "配件名称 ")
    private String name;

    @ApiModelProperty(value = "创建人")
    private String createdUserName;
    
    @ApiModelProperty(value = "所属模块ID ")
    private String moduleId;
    
    @ApiModelProperty(value = "所属模块名称 ")
    private String moduleName;
}
