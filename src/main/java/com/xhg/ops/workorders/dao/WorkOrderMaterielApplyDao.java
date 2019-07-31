package com.xhg.ops.workorders.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.xhg.ops.workorders.model.WorkOrderMaterielApply;

/**
 * 物料审核数据处理接口
 * @author 刘涛
 * @date 2018年7月12日
 */
@Repository
public interface WorkOrderMaterielApplyDao {

	/**
	 * 新增
	 * @param workOrderMaterielApply
	 */
	void insert (WorkOrderMaterielApply workOrderMaterielApply);
	
	/**
	 * 查询单个
	 * @param order_id
	 * @return
	 */
	List<WorkOrderMaterielApply> queryWorkOrderMaterielList(@RequestParam("orderId") int orderId);
	
	/**
	 * 查询数量
	 * @param orderId
	 * @return
	 */
	int countByOrderId(@RequestParam("orderId") int orderId);
}
