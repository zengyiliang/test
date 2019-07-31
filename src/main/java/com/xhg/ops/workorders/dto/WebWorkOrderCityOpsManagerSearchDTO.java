package com.xhg.ops.workorders.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "web搜索城市运维经理传参")
@Data
public class WebWorkOrderCityOpsManagerSearchDTO extends WorkOrderBaseDTO {
    
    @ApiModelProperty(value = "城市运维经理名称")
    private String name;
    
}
