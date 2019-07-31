package com.xhg.ops.system.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "职位用户对象")
@Data
public class SystemUserPositionVO {

    @ApiModelProperty(value = "用户id")
    private String userId;


    @ApiModelProperty(value = "工号")
    private String userNo;


    @ApiModelProperty(value = "姓名")
    private String name;


    @ApiModelProperty(value = "电话号码")
    private String phone;



    @ApiModelProperty(value = "角色名称")
    private String roleName;
}
