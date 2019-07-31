package com.xhg.ops.workorders.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 订单状查询态条件枚举
 * 
 * @author 刘涛
 * @date 2018年7月12日
 */
public enum WorkOrderStatusCondition {
	
	ALL("0" ,"ALL", "全部"),
	FIRSTTRIAL("1" ,"FIRSTTRIAL", "待初审"),
	ASSIGN("2" ,"ASSIGN", "待分配"),
	SIGNBILL("3" ,"SIGNBILL", "待签单"),
	START("4" ,"START", "待出发"),
	SIGNIN("5" ,"SIGNIN", "待签到"),
	PROCESS("6" ,"PROCESS", "待处理"),
	SUSPENDED("7" ,"MATERIAL", "挂起"),
	AUDIT("8" ,"AUDIT", "待审核"),
	REVIEW("9" ,"REVIEW", "待复核"),
	;
	
	WorkOrderStatusCondition(String key, String code, String desc){
		this.key = key;
		this.code = code;
		this.desc = desc;
	}
	
	/**
	 * 获取工单状态条件
	 * @param key
	 * @return
	 */
	public static WorkOrderStatusCondition getConditionByKey(String key){
		if(StringUtils.isEmpty(key)){
			return null;
		}
	    for(WorkOrderStatusCondition condition : WorkOrderStatusCondition.values()){
	    	if(condition.key.equals(key)) {
	    		return condition;
	    	}
	    }
	    return null;
	}
	
	
	public static String getKeyByCode(String code){
		if(StringUtils.isEmpty(code)){
			return "";
		}
		
	    for(WorkOrderStatusCondition condition : WorkOrderStatusCondition.values()){
	      if(StringUtils.equals(code, condition.getCode())){
	        return condition.getKey();
	      }
	    }
	    return null;
	}
	
	private String key;
	private String code;
	private String desc;
	
	public String getKey() {
		return key;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getDesc() {
		return desc;
	}
	
	
}
