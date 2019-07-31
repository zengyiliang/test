package com.xhg.ops.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "用户查询传参对象")
@Data
public class SystemUserQueryDTO {


    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "用户名称")
    private String name;

    @ApiModelProperty(value = "用户电话")
    private String phone;

    @ApiModelProperty(value = "工号")
    private String userNo;

    @ApiModelProperty(value = "地区编码")
    private String areaZoneId;

    @ApiModelProperty(value = "当前页码")
    private Integer currentPage=1;

    @ApiModelProperty(value = "每一页显示的数目")
    private Integer pageSize = 10;
}
