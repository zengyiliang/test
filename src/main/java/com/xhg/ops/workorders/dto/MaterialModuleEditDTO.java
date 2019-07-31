package com.xhg.ops.workorders.dto;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "编辑物料模块传参")
@Data
public class MaterialModuleEditDTO {

    @ApiModelProperty(value = "模块id", required=true)
    @NotBlank(message = "id不能为空")
    private String id;
    
    @ApiModelProperty(value = "模块名称", required=true)
    @NotBlank(message = "name不能为空")
    private String name;
    
}
