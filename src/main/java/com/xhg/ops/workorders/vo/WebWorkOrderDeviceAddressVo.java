package com.xhg.ops.workorders.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "web设备地址")
@Data
public class WebWorkOrderDeviceAddressVo {

    @ApiModelProperty(value = "设备地址")
    private String deviceAddress;
    
}
