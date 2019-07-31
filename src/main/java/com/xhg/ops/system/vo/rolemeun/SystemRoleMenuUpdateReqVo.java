package com.xhg.ops.system.vo.rolemeun;

import java.io.Serializable;

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
public class SystemRoleMenuUpdateReqVo implements Serializable{
	
	private static final long serialVersionUID=1L;
	
		@ApiModelProperty(value="菜单id")	private Integer menuId;//   菜单id	@ApiModelProperty(value="功能或者菜单id")
	private Integer id;//   菜单id	public Integer getMenuId() {	    return this.menuId;	}	public void setMenuId(Integer menuId) {	    this.menuId=menuId;	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
   
}