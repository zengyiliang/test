package com.xhg.ops.system.vo.rolemeun;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "角色查询对象")
public class SystemRoleQueryVO {

    @ApiModelProperty(value = "id")
    private String roleId;

    @ApiModelProperty(value = "角色名称")
    private String roleName;
    
    @ApiModelProperty(value = "角色名称")
    private String roleCode;

    @ApiModelProperty(value = "创建时间")
    private String createdTime;

    @ApiModelProperty(value = "创建人")
    private String createdName;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedName() {
        return createdName;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
    
}
