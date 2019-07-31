package com.xhg.ops.enums;

import lombok.ToString;

/**
 * 
 * @Description:初始化角色 
 * @Copyright: Copyright (c) 2018   
 * @Author hubowei  
 * @Create 2018年7月20日 上午10:46:20 
 * @Version 1.0.0
 */
@ToString
public enum RoleEnum {

	HEADOPSSUPERVISOR("headOpsSupervisor", "总部运维主管"),
	CITYOPSMANAGE("cityOpsManage", "城市运维经理"),
	MATERIELMANAGE("materielManage", "物控"),
	OPSDIRECTOR("opsDirector", "运维专员")
	;
	
	RoleEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	/**
	 * 角色编码
	 */
	private String code;
	/***
	 * 角色名
	 */
	private String name;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
