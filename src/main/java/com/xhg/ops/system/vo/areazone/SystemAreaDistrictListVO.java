package com.xhg.ops.system.vo.areazone;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel(value = "地区对象集合")
@Data
public class SystemAreaDistrictListVO {

    @ApiModelProperty(value = "地区编码")
    private String areaCode;

    @ApiModelProperty(value = "地区名称")
    private String areaName;

    @ApiModelProperty(value = "节点级别")
    private String nodeLevel;

    @ApiModelProperty(value = "子集合")
    private List<SystemAreaDistrictListVO> childrent;
}
