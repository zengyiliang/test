package com.xhg.ops.workorders.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.xhg.ops.workorders.model.WorkOrderLog;

/**
 * 工单日志数据操作
 * @author 刘涛
 * @date 2018年7月12日
 */
@Repository
public interface WorkOrderLogDao {

	/**
	 * 新增
	 * @param workOrderLog
	 * @return
	 */
	void insert(WorkOrderLog workOrderLog);
	
	/**
	 * 查询工单日志列表
	 * @param orderId
	 * @return
	 */
	List<WorkOrderLog> queryWorkOrderLogList(@Param("orderId") int orderId);
	
	
}
