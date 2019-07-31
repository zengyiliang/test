package com.xhg.ops.workorders.model;

import java.io.Serializable;

import com.xhg.ops.common.BasePojo;
import com.xhg.ops.workorders.util.datadict.IDataDict;

/**
 * 工单设备配件
 * 
 * @author 刘涛
 * @date 2018年7月21日
 */
public class WorkOrderDeviceParts extends BasePojo implements Serializable, IDataDict {

	private static final long serialVersionUID = 6401573571398487367L;

	private Integer moduleId; // 模块ID
	private String name; // 名称
	private String remark; // 描述

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String getDictCode() {
		return String.valueOf(getId());
	}

	@Override
	public String getDictName() {
		return name;
	}

}
