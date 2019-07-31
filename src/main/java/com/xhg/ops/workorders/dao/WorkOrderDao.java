package com.xhg.ops.workorders.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.xhg.ops.workorders.dto.WorkOrderFindResultDto;
import com.xhg.ops.workorders.model.WorkOrder;

/**
 * 工单数据操作接口
 * @author 刘涛
 * @date 2018年7月12日
 */
@Repository
public interface WorkOrderDao {
	
	/**
	 * 获取当天工单总数
	 * @param orderNoPrefix
	 * @return
	 */
	String selectOrderMaxOrderNo(@Param("orderNoPrefix") String orderNoPrefix);
	
	/**
	 * 新增
	 * @param workOrder
	 * @return
	 */
	int insert(WorkOrder workOrder);
	
	/**
	 * 查询单个
	 * @param order_id
	 * @return
	 */
	WorkOrder queryOne(@Param("id") int id);
	
	/**
	 * 根据参数查询工单列表
	 * @param params
	 * @return
	 */
	List<WorkOrderFindResultDto> queryWorkOrderList(Map<String, Object> params);
	
	/**
	 * 更新订单信息
	 * @param params
	 * @return
	 */
	int updateWorkOrder(Map<String, Object> params);
}
