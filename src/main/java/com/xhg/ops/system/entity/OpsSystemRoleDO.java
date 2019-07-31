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
	
	
	@ApiModelProperty(value="创建人")
	private String createdUserName;//  
	
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