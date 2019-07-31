package com.xhg.ops.workorders.enums;

/**
 * 订单操作列表
 * @author 刘涛
 * @date 2018年7月18日
 */
public enum WorkOrderOperation {

	AUDIT("1", "工单初审"),
	AUDIT_REJECT("1001", "工单关闭"),
	ASSIGN("2", "工单分配"),
	ASSIGN_REJECT("2001", "工单拒绝"),
	AGREEN("3", "签单"),
	AGREEN_REVOKE("3001", "申请撤回"),
	START("4", "出发"),
	START_TRANSFER("4001", "申请转单"),
	SIGN("5", "签到"),
	PROCESS("6", "提交工单"),
	PROCESS_MATERIAL_APPLY("6001", "申请更换物料"),
	MATERIAL_APPROVE("7", "物料审批"),
	MATERIAL_AUDITE("8", "物料审核"),
	MATERIAL_DELIVERY("9", "物料发货"),
	MATERIAL_RECEIVE("10", "物料查收"),
	CONFIRM("11", "工单审核"),
	RECEIVE("12", "工单复核"),
	;
	WorkOrderOperation(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	/** 键值 **/
	private String key;

	/** 描述 **/
	private String desc;

	public String getKey() {
		return key;
	}

	public String getDesc() {
		return desc;
	}

}
