package com.xhg.ops.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "省市区街道传参对象")
@Data
public class SystemAreaDTO {

    @ApiModelProperty(value = "父级areaCode")
    private String areaCode;
}
