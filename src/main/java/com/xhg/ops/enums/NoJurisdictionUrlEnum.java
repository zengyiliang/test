package com.xhg.ops.enums;

import com.alibaba.druid.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public enum NoJurisdictionUrlEnum {

	LOADLIST_URL("/ops/system/role/loadlist", "角色列表加载-曾庆财"),
  


    ;

    //访问的url
    private String url;

    //描述
    private String description;

    private static List<String> urls = new ArrayList<>();

    static {
        for (NoJurisdictionUrlEnum noNeedLoginUrl : NoJurisdictionUrlEnum.values()) {
            urls.add(noNeedLoginUrl.getUrl());
        }
    }


    // 构造方法
    private NoJurisdictionUrlEnum(String url, String description) {

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
