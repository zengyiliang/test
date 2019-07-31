package com.xhg.ops.system.entity;

import com.xhg.ops.common.BasePojo;

import java.io.Serializable;

public class SystemRoleDO  implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer roleId;
    private String roleCode;
    private String roleName;


    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

   
}
