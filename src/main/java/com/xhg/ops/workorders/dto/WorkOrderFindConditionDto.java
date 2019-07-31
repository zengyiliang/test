package com.xhg.ops.workorders.dto;

/**
 * 工单查询条件
 * 
 * @author 刘涛
 * @date 2018年7月16日
 */
public class WorkOrderFindConditionDto {

	/** 订单号 **/
	private String orderNo;
	/** 创建人 **/
	private String createdUser;
	/** 工单类型 **/
	private Integer orderType;
	/** 状态 **/
	private Integer status;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
