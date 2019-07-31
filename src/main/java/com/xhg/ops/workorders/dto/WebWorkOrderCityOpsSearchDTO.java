package com.xhg.ops.workorders.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "web搜索城市运维专员传参")
@Data
public class WebWorkOrderCityOpsSearchDTO extends WorkOrderBaseDTO {
    
    @ApiModelProperty(value = "城市运维专员名称")
    private String name;
    
}
