package com.xhg.ops.system.entity;

import java.io.Serializable;
import java.util.List;

import com.xhg.core.base.BasePojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * 
 * @ClassName: OpsSystemMenuDO
 * @Description: TODO
 * @author lilun
 * @date 2018-07-12
 */ 
@ApiModel
public class OpsSystemMenuDO extends BasePojo implements Serializable{
	
	private static final long serialVersionUID=1L;
	
	
	@ApiModelProperty(value="创建人")
	private String createdUserName;//  
	private List<OpsSystemMenuDO> menus;
	
    public List<OpsSystemMenuDO> getMenus() {
		return menus;
	}
	public void setMenus(List<OpsSystemMenuDO> menus) {
		this.menus = menus;
	}
	
	public String getCreatedUserName() {
		return createdUserName;
	}
	public void setCreatedUserName(String createdUserName) {
		this.createdUserName = createdUserName;
	}
	@Override
    public String toString() {
	  return "OpsSystemMenuDO[ id:"+getId()+" name:"+getName()+" code:"+getCode()+" parentId:"+getParentId()+" permission:"+getPermission()+" url:"+getUrl()+" type:"+getType()+" sequence:"+getSequence()+" icon:"+getIcon()+" createdUserId:"+getCreatedUserId()+" updatedUserId:"+getUpdatedUserId()+" createdTime:"+getCreatedTime()+" updatedTime:"+getUpdatedTime()+" enable:"+getEnable()+"]"; 
    }
}