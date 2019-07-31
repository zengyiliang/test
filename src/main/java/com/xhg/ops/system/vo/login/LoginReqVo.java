package com.xhg.ops.system.vo.login;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("登录请求信息")
public class LoginReqVo {

    @ApiModelProperty("登录用户名")
    private String loginName;

    @ApiModelProperty("密码")
    private String password;

   

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    
}
