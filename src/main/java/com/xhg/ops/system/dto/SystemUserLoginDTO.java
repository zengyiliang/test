package com.xhg.ops.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "用户登录传参对象")
@Data
public class SystemUserLoginDTO {

    @ApiModelProperty(value = "用户手机号")
    private String phone;

    @ApiModelProperty(value = "用户密码")
    private String password;
}
