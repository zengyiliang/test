package com.xhg.ops.system.vo.rolemeun;

import java.io.Serializable;
import java.util.Date;

import com.xhg.core.base.BasePojo;

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
public class OpsSystemRoleRespVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "角色编码")
	private String roleCode;// 角色编码
	@ApiModelProperty(value = "角色名称")
	private String name;// 角色名称
	@ApiModelProperty(value = "角色id")
	private String id;
	@ApiModelProperty(value = "创建人")
	private String createdUserName;
	@ApiModelProperty(value = "创建时间")
	private String createdTime;


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



	public String getCreatedUserName() {
		return createdUserName;
	}

	public void setCreatedUserName(String createdUserName) {
		this.createdUserName = createdUserName;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	
}