package com.xhg.ops.system.entity;

import java.io.Serializable;

import com.xhg.ops.common.BasePojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * 
 * @ClassName: OpsSystemRoleMenuDO
 * @Description: TODO
 * @author liun
 * @date 2018-07-12
 */ 
@ApiModel
public class OpsSystemRoleMenuDO extends BasePojo implements Serializable{
	
	private static final long serialVersionUID=1L;
	
		@ApiModelProperty(value="菜单id")	private Integer menuId;//   菜单id	@ApiModelProperty(value="角色id")	private Integer roleId;//   角色id
	@ApiModelProperty(value="角色编码")
	private String roleCode;//   角色编码
	@ApiModelProperty(value="角色名称")
	private String roleName;//   角色名称
	@ApiModelProperty(value="菜单名称")
	private String meunName;//   菜单名称
	@ApiModelProperty(value="菜单编码")
	private String meunCode;//   菜单编码
	@ApiModelProperty(value="父类id")
	private Integer parentId;//   父类id
	@ApiModelProperty(value="权限")
	private String permission;//   权限
	@ApiModelProperty(value="链接")
	private String url;//   链接
	@ApiModelProperty(value="类型:1/菜单，2/按钮，3/超链接")
	private Integer type;//   类型:1/菜单，2/按钮，3/超链接
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
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
	public String getMeunName() {
		return meunName;
	}
	public void setMeunName(String meunName) {
		this.meunName = meunName;
	}
	public String getMeunCode() {
		return meunCode;
	}
	public void setMeunCode(String meunCode) {
		this.meunCode = meunCode;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}	
}