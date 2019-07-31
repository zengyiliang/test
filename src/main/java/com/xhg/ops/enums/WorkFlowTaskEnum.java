package com.xhg.ops.enums;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import lombok.ToString;

/**
 * 工单任务节点描述 节点code和流程引擎节点保持一致
 * @Name:  
 * @Description: 
 * @Copyright: Copyright (c) 2018   
 * @Author hubowei  
 * @Create 2018年7月16日 下午2:51:31 
 * @Version 1.0.0
 */
@ToString
public enum WorkFlowTaskEnum {

	ALL("ALL", "全部"),
	FIRSTTRIAL("FIRSTTRIAL", "待初审"),
	ASSIGN("ASSIGN", "待分配"),
	SIGNBILL("SIGNBILL", "待签单"),
	START("START", "待出发"),
	SIGNIN("SIGNIN", "待签到"),
	PROCESS("PROCESS", "待处理"),
	SUSPENDED("MATERIAL", "挂起"),//挂起code传入物料
	AUDIT("AUDIT", "待审核"),
	REVIEW("REVIEW", "待复核"),
	
	MATERIAL_APPLY("MATERIAL_APPLY", "物料审批"),
	MATERIAL_AUDITED("MATERIAL_AUDITED", "物料审核"),
	MATERIAL_DELIVERED("MATERIAL_DELIVERED", "物料发送"),
	MATERIAL_RECEIVED("MATERIAL_RECEIVED", "物流查收"),
	;

	WorkFlowTaskEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static String getWorkFlowTaskDescByCode(String code){
		if(StringUtils.isEmpty(code)){
			return "";
		}
	    for(WorkFlowTaskEnum workFlowTaskEnum : WorkFlowTaskEnum.values()){
	      if(StringUtils.equals(code, workFlowTaskEnum.getCode())){
	        return workFlowTaskEnum.getDesc();
	      }
	    }
	    return null;
	}
	
	public static List<String> getProcessCodeList(){
		List<String> list=new ArrayList<>();
		list.add(WorkFlowTaskEnum.FIRSTTRIAL.getCode());
		list.add(WorkFlowTaskEnum.ASSIGN.getCode());
		list.add(WorkFlowTaskEnum.SIGNBILL.getCode());
		list.add(WorkFlowTaskEnum.START.getCode());
		list.add(WorkFlowTaskEnum.SIGNIN.getCode());
		list.add(WorkFlowTaskEnum.PROCESS.getCode());
		list.add(WorkFlowTaskEnum.SUSPENDED.getCode());
		list.add(WorkFlowTaskEnum.AUDIT.getCode());
		list.add(WorkFlowTaskEnum.REVIEW.getCode());
		return list;
	}
	public static boolean contains(String code) {
		if(StringUtils.isEmpty(code)){
			return false;
		}
		if(WorkFlowTaskEnum.ALL.getCode().equalsIgnoreCase(code)){
			return false;
		}
	    for (WorkFlowTaskEnum workFlowTaskEnum : WorkFlowTaskEnum.values()) {
	        if (workFlowTaskEnum.getCode().equals(code)) {
	            return true;
	        }
	    }
	    return false;
	}
	/**
	 * 状态码
	 */
	private String code;
	/***
	 * 当前状态描述
	 */
	private String desc;

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}

}
