package com.xhg.ops.workorders.dto;

import java.util.Date;

/**
 * 工单查询结果实体
 * 
 * @author 刘涛
 * @date 2018年7月16日
 */
public class WorkOrderFindResultDto {

	private Integer id; // 主键
	private String orderNo; // 工单号
	private Integer orderType; // 工单类型
	private String orderTitle; // 订单描述
	private Integer status; // 订单状态
	private Integer dataSource; // 订单来源
	private Integer level; // 紧急程度
	private String remark; // 备注
	private String procInstId; // 流程实例ID
	private String procTaskId; // 流程任务ID
	private String createdUser; // 创建人名称
	private Date createdTime; // 创建时间
	private Date updatedTime; // 修改时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public String getOrderTitle() {
		return orderTitle;
	}

	public void setOrderTitle(String orderTitle) {
		this.orderTitle = orderTitle;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getDataSource() {
		return dataSource;
	}

	public void setDataSource(Integer dataSource) {
		this.dataSource = dataSource;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	public String getProcTaskId() {
		return procTaskId;
	}

	public void setProcTaskId(String procTaskId) {
		this.procTaskId = procTaskId;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

}
