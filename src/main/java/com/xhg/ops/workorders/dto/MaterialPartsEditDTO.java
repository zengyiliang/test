package com.xhg.ops.workorders.dto;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "编辑物料配件传参")
@Data
public class MaterialPartsEditDTO {

    @ApiModelProperty(value = "配件id", required=true)
    @NotBlank(message = "id不能为空")
    private String id;
    
    @ApiModelProperty(value = "模块id", required=true)
    @NotBlank(message = "moduleId不能为空")
    private String moduleId;
    
    @ApiModelProperty(value = "配件名称", required=true)
    @NotBlank(message = "name不能为空")
    private String name;
    
}
