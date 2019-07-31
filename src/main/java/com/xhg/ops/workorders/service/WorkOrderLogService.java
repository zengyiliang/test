package com.xhg.ops.workorders.service;

import java.util.List;

import com.xhg.ops.workorders.dto.WorkOrderUserDTO;
import com.xhg.ops.workorders.enums.WorkOrderStatus;
import com.xhg.ops.workorders.model.WorkOrderLog;

/**
 * 工单日志逻辑处理
 * 
 * @author 刘涛
 * @date 2018年7月12日
 */
public interface WorkOrderLogService {

	/**
	 * 新增订单日志
	 * @param orderId
	 * @param status
	 * @param remark
	 * @param user
	 */
	void addWorkOrderLog(int orderId, WorkOrderStatus status, String remark, WorkOrderUserDTO user);

	/**
	 * 查询订单日志列表
	 * @param orderId
	 * @return
	 */
	List<WorkOrderLog> queryWorkOrderLogList(int orderId);
}
