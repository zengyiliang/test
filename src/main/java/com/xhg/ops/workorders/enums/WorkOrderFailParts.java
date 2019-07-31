package com.xhg.ops.workorders.enums;

/**
 * 故障模块
 * 
 * @author 区涛
 * @date 2018年7月17日
 */
public enum WorkOrderFailParts {

	LED(1, "LED灯"),
	OZONE_GENERATOR(2, "臭氧发生器"),
	VISION(3, "视觉模块"),
	;

	WorkOrderFailParts(int code, String name) {
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
	
	public static WorkOrderFailParts getObject(int code) {
		WorkOrderFailParts[] failParts = WorkOrderFailParts.values();
		for (WorkOrderFailParts part : failParts) {
			if (part.getCode() == code) {
				return part;
			}
		}
		return null;
	}
	
	public static String getNameByCode(int code) {
		WorkOrderFailParts obj = WorkOrderFailParts.getObject(code);
		if(obj!=null) {
			return obj.getName();
		}
		
		return null;
	}

}
