package com.xhg.ops.system.vo.rolemeun;

import java.io.Serializable;
import java.util.List;

import com.xhg.ops.common.BasePojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @ClassName: OpsSystemRoleMenuDO
 * @Description: TODO
 * @author liun
 * @date 2018-07-11
 */
@ApiModel
public class SystemRoleMenuQueryRespVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "菜单名称")
	private String name;// 菜单名称
	@ApiModelProperty(value = "菜单编码")
	private String code;// 菜单编码
	@ApiModelProperty(value = "父类id")
	private String parentId;// 父类id
	@ApiModelProperty(value = "权限(true是false否)")
	private Boolean permission;// 权限
	@ApiModelProperty(value = "链接")
	private String url;// 链接
	@ApiModelProperty(value = "类型:1/菜单，2/按钮，3/超链接")
	private String type;// 类型:1/菜单，2/按钮，3/超链接
	@ApiModelProperty(value = "排序")
	private String sequence;// 排序
	@ApiModelProperty(value = "菜单icon")
	private String icon;// 菜单icon
	@ApiModelProperty(value = "子菜单")
	private List<SystemRoleMenuQueryRespVo> respVos;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Boolean getPermission() {
		return permission;
	}

	public void setPermission(Boolean permission) {
		this.permission = permission;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSequence() {
		return this.sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<SystemRoleMenuQueryRespVo> getRespVos() {
		return respVos;
	}

	public void setRespVos(List<SystemRoleMenuQueryRespVo> respVos) {
		this.respVos = respVos;
	}

}