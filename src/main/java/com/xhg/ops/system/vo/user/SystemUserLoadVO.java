package com.xhg.ops.system.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "用户修改加载对象")
@Data
public class SystemUserLoadVO {

    @ApiModelProperty(value = "id")
    private Integer id=0;

    @ApiModelProperty(value = "用户新密码")
    private String password;

    @ApiModelProperty(value = "用户旧密码")
    private String oldPassword;


}
