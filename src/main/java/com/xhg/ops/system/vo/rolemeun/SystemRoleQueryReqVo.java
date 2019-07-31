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
public class SystemRoleQueryReqVo  implements Serializable{
	
	private static final long serialVersionUID=1L;
	
		@ApiModelProperty(value="角色id")	private Integer roleId;//   


	public Integer getRoleId() {
		return roleId;
	}


	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}	
	
}