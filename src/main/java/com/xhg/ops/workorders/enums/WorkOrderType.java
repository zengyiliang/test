package com.xhg.ops.workorders.enums;

/**
 * 订单类型
 * @author 刘涛
 * @date 2018年7月12日
 */
public enum WorkOrderType {

	BREAKDOWN_REPAIR(1, "维修"),
//	BREAKDOWN_DEBUGGING(2, "调试"),
//	BREAKDOWN_INSPECTION(3, "巡检"),
	;
	
	WorkOrderType(int code, String name) {
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
		return this.code;
	}

	public String getName() {
		return this.name;
	}
	
	public static WorkOrderType getOrderType(int code) {
		WorkOrderType[] orderTypes = WorkOrderType.values();
		for (WorkOrderType type : orderTypes) {
			if (type.getCode() == code) {
				return type;
			}
		}
		return null;
	}
	
	public static String getNameByCode(int code) {
		WorkOrderType orderType = WorkOrderType.getOrderType(code);
		if(orderType != null) {
			return orderType.getName();
		}
		return null;
	}
	
}
