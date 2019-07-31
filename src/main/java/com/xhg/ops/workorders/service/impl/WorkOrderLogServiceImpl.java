package com.xhg.ops.workorders.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhg.ops.common.ComConstant;
import com.xhg.ops.workorders.dao.WorkOrderLogDao;
import com.xhg.ops.workorders.dto.WorkOrderUserDTO;
import com.xhg.ops.workorders.enums.WorkOrderStatus;
import com.xhg.ops.workorders.model.WorkOrderLog;
import com.xhg.ops.workorders.service.WorkOrderLogService;

/**
 * 工单日志逻辑处理实现
 * @author 刘涛
 * @date 2018年7月12日
 */
@Service("workOrderLogService")
public class WorkOrderLogServiceImpl implements WorkOrderLogService {

	@Autowired
	private WorkOrderLogDao workOrderLogDao;
	
	@Override
	public void addWorkOrderLog(int orderId, WorkOrderStatus status, String remark, WorkOrderUserDTO user) {
		WorkOrderLog log = new WorkOrderLog();
		log.setOrderId(orderId);
		log.setOrderStatus(status.getCode());
		log.setRemark(remark);
		log.setCreatedUser(user.getUserName());
		log.setCreatedUserDesc(user.getRoleName());
		log.setCreatedTime(new Date());
		log.setUpdatedTime(new Date());
		log.setCreatedUserId(user.getUserId());
		log.setUpdatedUserId(user.getUserId());
		log.setEnable(ComConstant.ENABLE_YES);
		workOrderLogDao.insert(log);
	}

	@Override
	public List<WorkOrderLog> queryWorkOrderLogList(int orderId) {
		return workOrderLogDao.queryWorkOrderLogList(orderId);
	}

}
