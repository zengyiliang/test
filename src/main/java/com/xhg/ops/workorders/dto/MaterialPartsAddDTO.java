package com.xhg.ops.workorders.dto;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "新增物料配件传参")
@Data
public class MaterialPartsAddDTO {

    @ApiModelProperty(value = "配件名称", required=true)
    @NotBlank(message = "name不能为空")
    private String name;
    
    @ApiModelProperty(value = "模块id", required=true)
    @NotBlank(message = "moduleId不能为空")
    private String moduleId;
    
}
