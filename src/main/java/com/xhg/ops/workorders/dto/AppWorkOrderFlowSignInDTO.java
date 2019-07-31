package com.xhg.ops.workorders.dto;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "app签到传参")
@Data
public class AppWorkOrderFlowSignInDTO extends WorkOrderBaseDTO {

    @ApiModelProperty(value = "签到坐标经度", required=true)
    @NotBlank(message = "signInLongitude不能为空")
    private String signInLongitude;
    
    @ApiModelProperty(value = "签到坐标纬度", required=true)
    @NotBlank(message = "signInLatitude不能为空")
    private String signInLatitude;
    
    @ApiModelProperty(value = "设备坐标经度", required=true)
    @NotBlank(message = "deviceLongitude不能为空")
    private String deviceLongitude;
    
    @ApiModelProperty(value = "设备坐标纬度", required=true)
    @NotBlank(message = "deviceLatitude不能为空")
    private String deviceLatitude;
    
}
