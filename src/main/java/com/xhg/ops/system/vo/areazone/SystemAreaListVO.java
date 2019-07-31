package com.xhg.ops.system.vo.areazone;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 区域列表
 */
@ApiModel(value = "省市区县显示对象")
@Data
public class SystemAreaListVO {

    @ApiModelProperty(value = "编码")
    private String areaCode;

    @ApiModelProperty(value = "名称")
    private String areaName;


}
