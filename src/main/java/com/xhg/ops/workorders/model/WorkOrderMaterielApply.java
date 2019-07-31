package com.xhg.ops.workorders.model;

import java.io.Serializable;

import com.xhg.ops.common.BasePojo;

/**
 * 工单物料申请表
 * 
 * @author 刘涛
 * @date 2018年7月12日
 */
public class WorkOrderMaterielApply extends BasePojo implements Serializable {

	private static final long serialVersionUID = -5653120240629784876L;

	private Integer orderId; // 工单号
	private Integer moduleId; // 模块编号
	private String moduleName; // 模块名称
	private String materielNo; // 物料编号
	private String materielName; // 物料名称
	private String receivePerson; // 接收人
	private String receivePhone; // 接收人电话
	private String receiveProvinceCode; // 接收地址省份
	private String receiveCityCode; // 接收地址城市
	private String receiveDistrictCode; // 接收地址区县
	private String receiveAddress; // 接收地址
	private String remark; // 备注

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getMaterielNo() {
		return materielNo;
	}

	public void setMaterielNo(String materielNo) {
		this.materielNo = materielNo;
	}

	public String getMaterielName() {
		return materielName;
	}

	public void setMaterielName(String materielName) {
		this.materielName = materielName;
	}

	public String getReceivePerson() {
		return receivePerson;
	}

	public void setReceivePerson(String receivePerson) {
		this.receivePerson = receivePerson;
	}

	public String getReceivePhone() {
		return receivePhone;
	}

	public void setReceivePhone(String receivePhone) {
		this.receivePhone = receivePhone;
	}

	public String getReceiveProvinceCode() {
		return receiveProvinceCode;
	}

	public void setReceiveProvinceCode(String receiveProvinceCode) {
		this.receiveProvinceCode = receiveProvinceCode;
	}

	public String getReceiveCityCode() {
		return receiveCityCode;
	}

	public void setReceiveCityCode(String receiveCityCode) {
		this.receiveCityCode = receiveCityCode;
	}

	public String getReceiveDistrictCode() {
		return receiveDistrictCode;
	}

	public void setReceiveDistrictCode(String receiveDistrictCode) {
		this.receiveDistrictCode = receiveDistrictCode;
	}

	public String getReceiveAddress() {
		return receiveAddress;
	}

	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
