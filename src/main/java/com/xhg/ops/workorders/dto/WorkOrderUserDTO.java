package com.xhg.ops.workorders.dto;

/**
 * 工单用户
 * 
 * @author 刘涛
 * @date 2018年7月12日
 */
public class WorkOrderUserDTO {

	private Integer userId;
	private String userName;
	private String phone;
	private Integer roleId;
	private String roleName;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return "{userId:" + userId + ", userName:" + userName + ", phone:" + phone + ", roleId:" + roleId
				+ ", roleName:" + roleName + "}";
	}

}
