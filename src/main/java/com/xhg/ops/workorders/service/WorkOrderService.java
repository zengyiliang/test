package com.xhg.ops.workorders.service;

import com.xhg.core.web.vo.PagerResult;
import com.xhg.ops.workorders.dto.WorkOrderFindConditionDto;
import com.xhg.ops.workorders.dto.WorkOrderFindResultDto;
import com.xhg.ops.workorders.model.WorkOrder;
import com.xhg.ops.workorders.model.WorkOrderResult;

/**
 * 工单逻辑接口
 * @author 刘涛
 * @date 2018年7月13日
 */
public interface WorkOrderService {
	
	/**
	 * 查询工单详情
	 * @param orderId
	 * @return
	 */
	WorkOrder queryWorkOrderDetail(int orderId);
	
	/**
	 * 查询所有工单信息
	 * @param pageNum
	 * @param pageSize
	 * @param condition
	 * @return
	 */
	PagerResult<WorkOrderFindResultDto> queryAllWorkOrderListPage(int pageNum, int pageSize, WorkOrderFindConditionDto condition);
	
	/**
	 * 查询工单处理结果信息
	 * @param orderId
	 * @return
	 */
	WorkOrderResult queryWorkOrderResult(int orderId);

	/**
	 * 查询工单处理结果数量
	 * @param orderId
	 * @return
	 */
	int countWorkOrderResult(int orderId);

	/**
	 * 查询物料申请数量
	 * @param orderId
	 * @return
	 */
	int countWorkOrderMaterielApply(int orderId);
}
