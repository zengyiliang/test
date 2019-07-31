package com.xhg.ops.system.vo.rolemeun;

import java.io.Serializable;

import com.xhg.core.base.BasePojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * 
 * @ClassName: OpsSystemMenuDO
 * @Description: TODO
 * @author lilun
 * @date 2018-07-11
 */ 
@ApiModel
public class OpsSystemMenuDeleteReqVo  implements Serializable{
	
	private static final long serialVersionUID=1L;
	
	@ApiModelProperty(value="菜单id")
	private Integer id;//   菜单名称

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}	
}