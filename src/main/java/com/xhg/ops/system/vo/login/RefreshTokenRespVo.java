package com.xhg.ops.system.vo.login;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("刷新token返回")
public class RefreshTokenRespVo {

    @ApiModelProperty("新的token")
    private String token;

    @ApiModelProperty("设备id")
    private String deviceId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
