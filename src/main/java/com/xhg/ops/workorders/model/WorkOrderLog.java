package com.xhg.ops.workorders.model;

import java.io.Serializable;

import com.xhg.ops.common.BasePojo;

/**
 * 工单日志表
 * 
 * @author 刘涛
 * @date 2018年7月12日
 */
public class WorkOrderLog extends BasePojo implements Serializable {

	private static final long serialVersionUID = 6411936871728467625L;

	private Integer orderId; // 工单编号
	private Integer orderStatus; // 处理后订单状态
	private String remark; // 备注
	private String createdUser; // 操作人名称
	private String createdUserDesc; // 操作人描述（职位）

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
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

	public String getCreatedUserDesc() {
		return createdUserDesc;
	}

	public void setCreatedUserDesc(String createdUserDesc) {
		this.createdUserDesc = createdUserDesc;
	}

}
