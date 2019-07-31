package com.xhg.ops.workorders.dao;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.xhg.ops.workorders.model.WorkOrderResult;

/**
 * 工单处理结果数据操作接口
 * @author 刘涛
 * @date 2018年7月12日
 */
@Repository
public interface WorkOrderResultDao {

	/**
	 * 新增工单处理结果
	 * @param workOrderResult
	 */
	void insert(WorkOrderResult workOrderResult);
	
	/**
	 * 查询工单处理结果
	 * @param order_id
	 * @return
	 */
	WorkOrderResult queryOne(@RequestParam("orderId") int orderId);
	
	/**
	 * 根据orderId查询工单处理数量
	 * @param orderId
	 * @return
	 */
	int countByOrderId(@RequestParam("orderId") int orderId);
}
