package com.xhg.ops.workflow.enums;

import lombok.ToString;

/**
 * 流程状态
 */
@ToString
public enum ProcessStatusEnum {
	PROCESS_STATUS_NOEXISTS(-1, "流程不存在"), 
	PROCESS_STATUS_DOING(1, "正在进行中"), 
	PROCESS_STATUS_DONE(2, "已经结束");

	/**
	 * 流程状态
	 */
	private int processStatus;
	/**
	 * 状态描述
	 */
	private String processDesc;

	ProcessStatusEnum(int processStatus, String processDesc) {
		this.processStatus = processStatus;
		this.processDesc = processDesc;
	}

	public String getProcessDesc() {
		return processDesc;
	}

	public void setProcessDesc(String processDesc) {
		this.processDesc = processDesc;
	}

	public int getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(int processStatus) {
		this.processStatus = processStatus;
	}
}
