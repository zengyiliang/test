package com.xhg.ops.system.vo.rolemeun;

import java.io.Serializable;

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
public class SystemRoleQueryRespVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "角色编码")
	private String roleCode;// 角色编码
	@ApiModelProperty(value = "角色名称")
	private String name;// 角色名称
	@ApiModelProperty(value = "角色id")
	private String id;
	@ApiModelProperty(value = "菜单树")
	private SystemRoleMenuQueryRespVo menuQueryRespVo;


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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SystemRoleMenuQueryRespVo getMenuQueryRespVo() {
		return menuQueryRespVo;
	}

	public void setMenuQueryRespVo(SystemRoleMenuQueryRespVo menuQueryRespVo) {
		this.menuQueryRespVo = menuQueryRespVo;
	}

	
	
}