package com.xhg.ops.system.vo.rolemeun;

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
 * @date 2018-07-11
 */
@ApiModel
public class SystemParentMenuRespVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "菜单名称")
	private String name;// 菜单名称
	@ApiModelProperty(value = "菜单编码")
	private String code;// 菜单编码
	@ApiModelProperty(value = "id")
	private Integer id;//
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	

}