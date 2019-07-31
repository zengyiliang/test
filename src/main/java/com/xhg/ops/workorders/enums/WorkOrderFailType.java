package com.xhg.ops.workorders.enums;

/**
 * 故障类别
 * 
 * @author 区涛
 * @date 2018年7月17日
 */
public enum WorkOrderFailType {

	SOFTWARE(1, "软件"),
	HARDWARE(2, "硬件"),
	STRUCTURE(3, "结构 "),
	NETWORK(4, "网络"),
	OTHERS(5, "其他 "),
	;

	WorkOrderFailType(int code, String name) {
		this.code = code;
		this.name = name;
	}

	/**
	 * 值
	 */
	private int code;

	/**
	 * 名称
	 */
	private String name;

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
	public static WorkOrderFailType getObject(int code) {
		WorkOrderFailType[] failTypes = WorkOrderFailType.values();
		for (WorkOrderFailType type : failTypes) {
			if (type.getCode() == code) {
				return type;
			}
		}
		return null;
	}
	
	public static String getNameByCode(int code) {
		WorkOrderFailType obj = WorkOrderFailType.getObject(code);
		if(obj!=null) {
			return obj.getName();
		}
		
		return null;
	}

}
