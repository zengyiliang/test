package com.xhg.ops.system.vo.rolemeun;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @ClassName: OpsSystemRoleDO
 * @Description: TODO
 * @author lilun
 * @date 2018-07-11
 */
@ApiModel
public class SystemRoleUpdateReqVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "角色id")
	private String id;//
	@ApiModelProperty(value = "角色编码")
	private String roleCode;// 角色编码
	@ApiModelProperty(value = "角色名称")
	private String name;// 角色名称
	@ApiModelProperty(value = "菜单集合")
	private List<SystemRoleMenuUpdateReqVo> RoleMenu;// 权限

	public String getRoleCode() {
		return this.roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SystemRoleMenuUpdateReqVo> getRoleMenu() {
		return RoleMenu;
	}

	public void setRoleMenu(List<SystemRoleMenuUpdateReqVo> roleMenu) {
		RoleMenu = roleMenu;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}