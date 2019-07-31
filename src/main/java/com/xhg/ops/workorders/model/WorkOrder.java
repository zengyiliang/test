package com.xhg.ops.workorders.model;

import java.io.Serializable;
import java.util.List;

import com.xhg.ops.common.BasePojo;

/**
 * 工单实体
 * 
 * @author 刘涛
 * @date 2018年7月12日
 */
public class WorkOrder extends BasePojo implements Serializable {

	private static final long serialVersionUID = -4501822985278700332L;

	private String orderNo; // 工单号
	private Integer orderType; // 工单类型
	private String orderTitle; // 订单描述
	private Integer status; // 订单状态
	private Integer dataSource; // 订单来源
	private Integer level; // 紧急程度
	private String contactInfo; // 联系信息
	private String deviceId; // 设备ID
	private String siteCode; // 设备编码
	private String siteAreaCode; // 区编码
	private String siteLongitude; // 坐标经度
	private String siteLatitude; // 坐标纬度
	private String siteAddress; // 设备地址
	private String remark; // 备注
	private String procInstId; // 流程实例ID
	private Integer procUserId; // 流程处理人
	private String procTaskId; // 流程任务ID
	private String createdUser; // 创建人名称
	private Integer faultId; // 故障ID

	private List<String> attachments; // 附件列表
	private List<WorkOrderMaterielApply> materielList; // 物料列表

	public Integer getFaultId() {
		return faultId;
	}

	public void setFaultId(Integer faultId) {
		this.faultId = faultId;
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

	public String getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	public String getSiteAreaCode() {
		return siteAreaCode;
	}

	public void setSiteAreaCode(String siteAreaCode) {
		this.siteAreaCode = siteAreaCode;
	}

	public String getSiteLongitude() {
		return siteLongitude;
	}

	public void setSiteLongitude(String siteLongitude) {
		this.siteLongitude = siteLongitude;
	}

	public String getSiteLatitude() {
		return siteLatitude;
	}

	public void setSiteLatitude(String siteLatitude) {
		this.siteLatitude = siteLatitude;
	}

	public String getSiteAddress() {
		return siteAddress;
	}

	public void setSiteAddress(String siteAddress) {
		this.siteAddress = siteAddress;
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

	public Integer getProcUserId() {
		return procUserId;
	}

	public void setProcUserId(Integer procUserId) {
		this.procUserId = procUserId;
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

	public List<String> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<String> attachments) {
		this.attachments = attachments;
	}

	public List<WorkOrderMaterielApply> getMaterielList() {
		return materielList;
	}

	public void setMaterielList(List<WorkOrderMaterielApply> materielList) {
		this.materielList = materielList;
	}

}
