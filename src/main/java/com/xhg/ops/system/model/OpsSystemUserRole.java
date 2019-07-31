package com.xhg.ops.system.model;

import com.xhg.ops.common.BasePojo;

import java.io.Serializable;

public class OpsSystemUserRole extends BasePojo implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer roleId;

    private Integer userId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
