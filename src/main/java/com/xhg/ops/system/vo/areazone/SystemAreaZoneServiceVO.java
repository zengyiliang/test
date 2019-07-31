package com.xhg.ops.system.vo.areazone;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 区域列表
 */
@ApiModel(value = "区域对象")
@Data
public class SystemAreaZoneServiceVO {

    @ApiModelProperty(value = "区域名称")
    private String areaName;

    @ApiModelProperty(value = "区域编码")
    private String areaId;
}
