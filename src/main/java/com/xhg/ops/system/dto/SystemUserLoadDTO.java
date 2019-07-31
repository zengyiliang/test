package com.xhg.ops.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "用户添加传参对象")
@Data
public class SystemUserLoadDTO {

    @ApiModelProperty(value = "全国编码")
    private String countryCode;

    @ApiModelProperty(value = "区域编码")
    private String areaCode;

    @ApiModelProperty(value = "角色编码")
    private String roleCode;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "当前页码")
    private Integer currentPage = 1;

    @ApiModelProperty(value = "页数目")
    private Integer pageSize = 10;
}
