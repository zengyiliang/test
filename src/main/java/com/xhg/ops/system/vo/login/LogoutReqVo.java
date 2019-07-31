package com.xhg.ops.system.vo.login;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("登出请求")
public class LogoutReqVo {

    @ApiModelProperty("登录")
    private String loginClient;

	public String getLoginClient() {
		return loginClient;
	}

	public void setLoginClient(String loginClient) {
		this.loginClient = loginClient;
	}

}
