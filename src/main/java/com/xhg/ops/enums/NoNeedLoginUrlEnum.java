package com.xhg.ops.enums;

import com.alibaba.druid.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public enum NoNeedLoginUrlEnum {

    //登录
    LOGINAPP_URL("/ops/system/login/loginapp", "用户登录"),
    LOGINPC_URL("/ops/system/login/loginpc", "用户登录"),
    LOGOUTAPP_URL("/ops/system/login/logoutapp", "用户登出"),
    LOGOUTPC_URL("/ops/system/login/logoutpc", "用户登出"),
    REFRESH_TOKEN_URL("/ops/system/login/refresh_token", "刷新token"),
    SWAGGER_UI("/swagger-ui.html", "swagger-ui访问"),

    
    //内部系统调用
    WORKORDER_SUBMIT_URL("/ops/terminal/submitSiteFault", "单个故障上报"),
    WORKORDER_BATCHSUBMIT_URL("/ops/terminal/batchSubmitSiteFault", "批量故障上报"),
    ;

    //访问的url
    private String url;

    //描述
    private String description;

    private static List<String> urls = new ArrayList<>();

    static {
        for (NoNeedLoginUrlEnum noNeedLoginUrl : NoNeedLoginUrlEnum.values()) {
            urls.add(noNeedLoginUrl.getUrl());
        }
    }


    // 构造方法
    private NoNeedLoginUrlEnum(String url, String description) {

        this.url = url;
        this.description = description;
    }

    public static boolean isUnNeedLogin(String url) {
        if (StringUtils.isEmpty(url)) {
            return false;
        }
        return urls.contains(url);
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
