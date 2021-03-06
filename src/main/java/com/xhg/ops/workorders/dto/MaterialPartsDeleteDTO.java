package com.xhg.ops.workorders.dto;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "删除物料配件传参")
@Data
public class MaterialPartsDeleteDTO {

    @ApiModelProperty(value = "模块id", required=true)
    @NotBlank(message = "id不能为空")
    private String id;
    
}
