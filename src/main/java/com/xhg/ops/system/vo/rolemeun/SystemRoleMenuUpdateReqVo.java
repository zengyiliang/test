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
	
	
	private Integer id;//   菜单id
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
   
}