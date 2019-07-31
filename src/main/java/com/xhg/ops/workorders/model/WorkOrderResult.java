package com.xhg.ops.workorders.model;

import java.io.Serializable;
import java.util.List;

import com.xhg.ops.common.BasePojo;

/**
 * 工单处理结果表
 * 
 * @author 刘涛
 * @date 2018年7月12日
 */
public class WorkOrderResult extends BasePojo implements Serializable {

	private static final long serialVersionUID = 5059725062471093685L;

	private Integer orderId; // 工单号
	private Integer orderType; // 工单类型
	private String faultModule; // 故障模块
	private Integer faultType; // 故障类型
	private String changeParts; // 更换配件
	private String solution; // 解决办法
	private String reason; // 原因分析
	private String phenomena; // 实际现象
	private String remark; // 备注
	private String createdUser; // 创建人名称

	private List<String> attachments; // 附件列表

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public String getFaultModule() {
		return faultModule;
	}

	public void setFaultModule(String faultModule) {
		this.faultModule = faultModule;
	}

	public Integer getFaultType() {
		return faultType;
	}

	public void setFaultType(Integer faultType) {
		this.faultType = faultType;
	}

	public String getChangeParts() {
		return changeParts;
	}

	public void setChangeParts(String changeParts) {
		this.changeParts = changeParts;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getPhenomena() {
		return phenomena;
	}

	public void setPhenomena(String phenomena) {
		this.phenomena = phenomena;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public List<String> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<String> attachments) {
		this.attachments = attachments;
	}

}
