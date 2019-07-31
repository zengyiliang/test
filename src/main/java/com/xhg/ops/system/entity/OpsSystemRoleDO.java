package com.xhg.ops.system.entity;

import java.io.Serializable;

import com.xhg.core.base.BasePojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * 
 * @ClassName: OpsSystemRoleDO
 * @Description: TODO
 * @author lilun
 * @date 2018-07-12
 */ 
@ApiModel
public class OpsSystemRoleDO extends BasePojo implements Serializable{
	
	private static final long serialVersionUID=1L;
	
		@ApiModelProperty(value="角色编码")	private String roleCode;//   角色编码	@ApiModelProperty(value="角色名称")	private String name;//   角色名称
	@ApiModelProperty(value="创建人")
	private String createdUserName;//  	public String getRoleCode() {	    return this.roleCode;	}	public void setRoleCode(String roleCode) {	    this.roleCode=roleCode;	}	public String getName() {	    return this.name;	}	public void setName(String name) {	    this.name=name;	}
	
    public String getCreatedUserName() {
		return createdUserName;
	}
	public void setCreatedUserName(String createdUserName) {
		this.createdUserName = createdUserName;
	}
	@Override
    public String toString() {
	  return "OpsSystemRoleDO[ id:"+getId()+" roleCode:"+getRoleCode()+" name:"+getName()+" createdUserId:"+getCreatedUserId()+" updatedUserId:"+getUpdatedUserId()+" createdTime:"+getCreatedTime()+" updatedTime:"+getUpdatedTime()+" enable:"+getEnable()+"]"; 
    }
}