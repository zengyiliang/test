package com.xhg.ops.workorders.enums;

/**
 * 订单来源
 * 
 * @author 刘涛
 * @date 2018年7月12日
 */
public enum WorkOrderDataSource {

	BREAKDOWN_REPORT(1, "故障上报"),
	USER_FEEDBACK(2, "用户反馈"),
	MARKET_FEEDBACK(3, "市场反馈"),
	OPERATION_FEEDBACK(4, "运维反馈"),
	;

	WorkOrderDataSource(int code, String name) {
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
	
	public static WorkOrderDataSource getDataSource(int code) {
		WorkOrderDataSource[] datasources = WorkOrderDataSource.values();
		for (WorkOrderDataSource datasource : datasources) {
			if (datasource.getCode() == code) {
				return datasource;
			}
		}
		return null;
	}
	
	public static String getNameByCode(int code) {
		WorkOrderDataSource obj = WorkOrderDataSource.getDataSource(code);
		if(obj!=null) {
			return obj.getName();
		}
		
		return null;
	}

}
