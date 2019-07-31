package com.xhg.ops.system.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "用户登录显示对象")
@Data
public class SystemUserLoginVO {

    @ApiModelProperty(value = "用户手机号")
    private String phone;

    @ApiModelProperty(value = "用户密码")
    private String password;
}
