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
	
		@ApiModelProperty(value="菜单名称")	private String name;//   菜单名称	@ApiModelProperty(value="菜单编码")	private String code;//   菜单编码	@ApiModelProperty(value="父类id")	private Integer parentId;//   父类id	@ApiModelProperty(value="权限")	private String permission;//   权限	@ApiModelProperty(value="链接")	private String url;//   链接	@ApiModelProperty(value="类型:1/菜单，2/按钮，3/超链接")	private Integer type;//   类型:1/菜单，2/按钮，3/超链接	@ApiModelProperty(value="排序")	private Integer sequence;//   排序	@ApiModelProperty(value="菜单icon")	private String icon;//   菜单icon
	@ApiModelProperty(value="创建人")
	private String createdUserName;//  	@ApiModelProperty(value="子菜单")
	private List<OpsSystemMenuDO> menus;	public String getName() {	    return this.name;	}	public void setName(String name) {	    this.name=name;	}	public String getCode() {	    return this.code;	}	public void setCode(String code) {	    this.code=code;	}	public Integer getParentId() {	    return this.parentId;	}	public void setParentId(Integer parentId) {	    this.parentId=parentId;	}	public String getPermission() {	    return this.permission;	}	public void setPermission(String permission) {	    this.permission=permission;	}	public String getUrl() {	    return this.url;	}	public void setUrl(String url) {	    this.url=url;	}	public Integer getType() {	    return this.type;	}	public void setType(Integer type) {	    this.type=type;	}	public Integer getSequence() {	    return this.sequence;	}	public void setSequence(Integer sequence) {	    this.sequence=sequence;	}	public String getIcon() {	    return this.icon;	}	public void setIcon(String icon) {	    this.icon=icon;	}
	
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