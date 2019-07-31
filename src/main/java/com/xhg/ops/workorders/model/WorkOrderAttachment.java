package com.xhg.ops.workorders.model;

import java.io.Serializable;

import com.xhg.ops.common.BasePojo;

/**
 * 工单附件表
 * 
 * @author 刘涛
 * @date 2018年7月12日
 */
public class WorkOrderAttachment extends BasePojo implements Serializable {

	private static final long serialVersionUID = -5816857867542178733L;
	private Integer buesinessId; // 业务编号
	private Integer businessType; // 业务类型
	private String url; // url地址

	public Integer getBuesinessId() {
		return buesinessId;
	}

	public void setBuesinessId(Integer buesinessId) {
		this.buesinessId = buesinessId;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
