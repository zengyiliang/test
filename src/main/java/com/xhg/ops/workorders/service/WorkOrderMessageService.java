package com.xhg.ops.workorders.service;

import java.util.Set;

import com.xhg.core.web.vo.PagerResult;
import com.xhg.ops.workorders.dto.WorkOrderUserDTO;
import com.xhg.ops.workorders.model.WorkOrderMessage;

/**
 * 工单消息逻辑接口
 * 
 * @author 刘涛
 * @date 2018年7月17日
 */
public interface WorkOrderMessageService {

	static final int STATUS_UNREAD = 0; // 消息未读
	static final int STATUS_READ = 1; // 消息已读

	public static final int TYPE_MESSAGE_SITE = 1; // 站内
	public static final int TYPE_MESSAGE_PUSH = 2; // 推送
	
	public static final String PUSH_TYPE_SHOW_ORDER_UNPROCESS_LIST = "SHOW_ORDER_UNPROCESS_LIST";	// 推送类型-显示订单待处理列表
	
	/**
	 * 发送站内消息
	 * @param orderId 工单编号
	 * @param title 标题
	 * @param content 内容
	 * @param recevieIds 接收人编号
	 * @param user 操作用户
	 */
	void sendSiteMessage(int orderId, String title, String content, Set<Integer> recevieIds, WorkOrderUserDTO user);
	
	/**
	 * 推送工单消息
	 * @param orderId 工单编号
	 * @param title 标题
	 * @param content 内容
	 * @param recevieIds 接收人编号
	 * @param user 操作用户
	 */
	void pushMessage(int orderId, String title, String content, Set<Integer> recevieIds, WorkOrderUserDTO user);
	
	/**
	 * 更新消息为已读
	 * @param messageId
	 * @param user
	 * @return
	 */
	boolean updateMessageRead(int messageId,  WorkOrderUserDTO user);
	
	/**
	 * 查询工单最新消息列表
	 * @param pageNum 页码
	 * @param pageSize 数量
	 * @param receiveId 接收人编号
	 * @param type 消息类型
	 * @return
	 */
	PagerResult<WorkOrderMessage> findNewestMessageList(int pageNum, int pageSize, int receiveId, int type);

}
