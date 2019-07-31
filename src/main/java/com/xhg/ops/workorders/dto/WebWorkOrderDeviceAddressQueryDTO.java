package com.xhg.ops.workorders.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "web根据设备ID查询设备地址传参")
@Data
public class WebWorkOrderDeviceAddressQueryDTO {

    @ApiModelProperty(value = "设备编号/机器编码")
    private String deviceId;
    
}
