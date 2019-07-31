package com.xhg.ops.workorders.enums;

import com.xhg.ops.enums.Colors;

/**
 * 订单紧急程度
 * 
 * @author 刘涛
 * @date 2018年7月12日
 */
public enum WorkOrderLevel {

	NORMAL(1, "一般", Colors.YELLOW.getRgb()), INTENSIFY(2, "紧急", Colors.ORANGE.getRgb()), URGENT(3, "加急", Colors.RED.getRgb()),;

	WorkOrderLevel(int code, String name, String color) {
		this.code = code;
		this.name = name;
		this.color = color;
	}

	/**
	 * 值
	 */
	private int code;

	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 颜色
	 */
	private String color;

	public int getCode() {
		return this.code;
	}

	public String getName() {
		return this.name;
	}
	
	public String getColor() {
		return this.color;
	}

	public static WorkOrderLevel getOrderLevel(int code) {
		WorkOrderLevel[] levels = WorkOrderLevel.values();
		for (WorkOrderLevel level : levels) {
			if (level.getCode() == code) {
				return level;
			}
		}
		return null;
	}
	
	public static String getNameByCode(int code) {
		WorkOrderLevel obj = WorkOrderLevel.getOrderLevel(code);
		if(obj!=null) {
			return obj.getName();
		}
		
		return null;
	}

}
