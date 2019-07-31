package com.xhg.ops.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "用户添加传参对象")
@Data
public class SystemUserAddDTO {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "用户名称")
    private String name;

    @ApiModelProperty(value = "用户电话")
    private String phone;

    @ApiModelProperty(value = "工号")
    private String userNo;

    @ApiModelProperty(value = "部门id")
    private String deptId;

    @ApiModelProperty(value = "职位id")
    private String positionId;

    @ApiModelProperty(value = "地区id")
    private String areaZoneId;

    @ApiModelProperty(value = "角色id")
    private String roleId;

    @ApiModelProperty(value = "省级编号")
    private String province;

    @ApiModelProperty(value = "城市编号")
    private String city;

    @ApiModelProperty(value = "城区编号")
    private String cityArea;

    @ApiModelProperty(value = "街道编号")
    private String cityStreet;



}

