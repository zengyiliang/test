package com.xhg.ops.workorders.model;

import java.io.Serializable;

import com.xhg.ops.common.BasePojo;

/**
 * 工单消息实体
 * 
 * @author 刘涛
 * @date 2018年7月17日
 */
public class WorkOrderMessage extends BasePojo implements Serializable {

	private static final long serialVersionUID = 4458984667809712048L;

	private Integer orderId; // 订单编号
	private String title; // 标题
	private Integer type; // 消息类型：1站内, 2推送
	private String content; // 消息内容
	private Integer status; // 状态：0未读,1已读
	private Integer receiveId; // 接收人

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getReceiveId() {
		return receiveId;
	}

	public void setReceiveId(Integer receiveId) {
		this.receiveId = receiveId;
	}

}
