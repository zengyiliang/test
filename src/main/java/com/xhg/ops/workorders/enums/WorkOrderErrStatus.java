package com.xhg.ops.workorders.enums;

/**
 * 工单错误枚举
 * 
 * @author 刘涛
 * @date 2018年7月12日
 */
public enum WorkOrderErrStatus {

	ERROR("-1001", "工单出错"),
	PUSH_ERROR("-1010", "消息推送失败");

	private final String code;
	private final String msg;

	private WorkOrderErrStatus(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return this.code;
	}

	public String getMsg() {
		return this.msg;
	}

}
