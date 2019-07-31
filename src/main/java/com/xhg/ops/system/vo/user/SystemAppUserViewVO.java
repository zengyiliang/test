package com.xhg.ops.system.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "用户加载对象")
@Data
public class SystemAppUserViewVO {


    @ApiModelProperty(value = "用户id")
    private Integer id;

    @ApiModelProperty(value = "用户名")
    private String name;

    @ApiModelProperty(value = "用户信息")
    private String UserInfo;

    @ApiModelProperty(value = "用户手机号")
    private String phone;


}
