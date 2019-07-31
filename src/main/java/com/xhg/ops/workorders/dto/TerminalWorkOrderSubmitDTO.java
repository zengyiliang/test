package com.xhg.ops.workorders.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(value = "终端上报故障工单传参")
@Data
public class TerminalWorkOrderSubmitDTO {

    @ApiModelProperty(value = "品类箱标识")
    private String boxType;
    
    @ApiModelProperty(value = "品类箱名称")
    private String boxTypeName;
    
/*    @ApiModelProperty(value = "箱子编码")
    private String boxCode;*/
    
    @ApiModelProperty(value = "故障id", required=true)
    @NotNull(message = "faultId不能为空")
    private Integer faultId;
    
    @ApiModelProperty(value = "故障码", required=true)
    @NotBlank(message = "faultType不能为空")
    private String faultType;
    
    @ApiModelProperty(value = "故障描述", required=true)
    @NotBlank(message = "description不能为空")
    private String description;
    
    @ApiModelProperty(value = "设备ID", required=true)
    @NotBlank(message = "deviceId不能为空")
    private String deviceId;
    
    @ApiModelProperty(value = "设备地址", required=true)
    @NotBlank(message = "deviceAddress不能为空")
    private String deviceAddress;
    
    @ApiModelProperty(value = "区编码", required=true)
    @NotBlank(message = "areaCode不能为空")
    private String areaCode;
    
    @ApiModelProperty(value = "站点编码", required=true)
    @NotBlank(message = "siteCode不能为空")
    private String siteCode;
    
    @ApiModelProperty(value = "坐标经度", required=true)
    @NotBlank(message = "longitude不能为空")
    private String longitude;
    
    @ApiModelProperty(value = "坐标纬度", required=true)
    @NotBlank(message = "latitude不能为空")
    private String latitude;

}
