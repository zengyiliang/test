package com.xhg.ops.workorders.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xhg.core.util.crypto.MD5Utils;
import com.xhg.core.web.exception.BusinessException;
import com.xhg.core.web.vo.PagerResult;
import com.xhg.ops.common.ComConstant;
import com.xhg.ops.system.model.SystemUser;
import com.xhg.ops.system.service.OpsSystemMessagePushService;
import com.xhg.ops.system.service.SystemUserService;
import com.xhg.ops.workorders.dao.WorkOrderMessageDao;
import com.xhg.ops.workorders.dto.WorkOrderUserDTO;
import com.xhg.ops.workorders.enums.WorkOrderErrStatus;
import com.xhg.ops.workorders.model.WorkOrderMessage;
import com.xhg.ops.workorders.service.WorkOrderMessageService;

@Service("workOrderMessageService")
public class WorkOrderMessageServiceImpl implements WorkOrderMessageService {

	@Autowired
	private WorkOrderMessageDao workOrderMessageDao;
	
	@Autowired
	private OpsSystemMessagePushService messagePushService;
	
	@Autowired
	private SystemUserService systemUserService;
	
	/**
	 * 发站内消息
	 */
	@Override
	public void sendSiteMessage(int orderId, String title, String content, Set<Integer> recevieIds, WorkOrderUserDTO user) {
		if(recevieIds != null && recevieIds.size() > 0) {
			Date now = new Date();
			List<WorkOrderMessage> messageList = new ArrayList<WorkOrderMessage>();
			for(int receviedId : recevieIds) {
				WorkOrderMessage message = new WorkOrderMessage();
				message.setOrderId(orderId);
				message.setTitle(title);
				message.setType(TYPE_MESSAGE_SITE);
				message.setContent(content);
				message.setStatus(STATUS_UNREAD);
				message.setReceiveId(receviedId);
				message.setCreatedTime(now);
				message.setUpdatedTime(now);
				message.setCreatedUserId(user.getUserId());
				message.setUpdatedUserId(user.getUserId());
				message.setEnable(ComConstant.ENABLE_YES);
				messageList.add(message);
			}
			workOrderMessageDao.insert(messageList);
		}
	}

	/**
	 * 推送消息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void pushMessage(int orderId, String title, String content, Set<Integer> recevieIds, WorkOrderUserDTO user) {
		if(recevieIds != null && recevieIds.size() > 0) {
			Date now = new Date();
			List<WorkOrderMessage> messageList = new ArrayList<WorkOrderMessage>();
			List<String> phoneList = new ArrayList<>();
			recevieIds.forEach(recevieId -> {
				SystemUser systemUser = systemUserService.selectByPrimaryKey(recevieId);
				if(systemUser != null) {
					WorkOrderMessage message = new WorkOrderMessage();
					message.setOrderId(orderId);
					message.setTitle(title);
					message.setType(TYPE_MESSAGE_PUSH);
					message.setContent(content);
					message.setStatus(STATUS_UNREAD);
					message.setReceiveId(recevieId);
					message.setCreatedTime(now);
					message.setUpdatedTime(now);
					message.setCreatedUserId(user.getUserId());
					message.setUpdatedUserId(user.getUserId());
					message.setEnable(ComConstant.ENABLE_YES);
					messageList.add(message);
					phoneList.add(MD5Utils.md5(systemUser.getPhone()));
				}
			});
			if(messageList.size() > 0) {
				workOrderMessageDao.insert(messageList);
				//推送消息
				Map<String, String> extMap = new HashMap<>();
				extMap.put("type", PUSH_TYPE_SHOW_ORDER_UNPROCESS_LIST);
				
				Map<String, String> dataMap = new HashMap<>();
				dataMap.put("orderId", String.valueOf(orderId));
				extMap.put("params", JSONObject.toJSONString(dataMap));
				int i = messagePushService.sendToAliasWithParams(phoneList, content, title, "Ops", extMap);
				if(i != 200) {
					throw new BusinessException(WorkOrderErrStatus.PUSH_ERROR.getMsg(), WorkOrderErrStatus.PUSH_ERROR.getCode(), WorkOrderErrStatus.PUSH_ERROR.getMsg());
				}
			}
		}
	}
	
	/**
	 * 更新消息为已读
	 */
	@Override
	public boolean updateMessageRead(int messageId, WorkOrderUserDTO user) {
		WorkOrderMessage message = new WorkOrderMessage();
		message.setId(messageId);
		message.setStatus(STATUS_READ);
		message.setUpdatedTime(new Date());
		message.setUpdatedUserId(user.getUserId());
		int flag = workOrderMessageDao.update(message);
		return flag > 0;
	}

	/**
	 * 查询工单最新消息列表
	 */
	@Override
	public PagerResult<WorkOrderMessage> findNewestMessageList(int pageNum, int pageSize, int receiveId, int type) {
		PageHelper.startPage(pageNum, pageSize);
		List<WorkOrderMessage> list = workOrderMessageDao.queryNewestMessageList(receiveId, type);
		return new PagerResult<>(new PageInfo<>(list));
	}

}
