package com.xhg.ops.workorders.service;

import java.util.List;
import java.util.Map;

import com.xhg.core.web.vo.PagerResult;
import com.xhg.ops.workorders.dto.WorkOrderFindResultDto;
import com.xhg.ops.workorders.dto.WorkOrderUserDTO;
import com.xhg.ops.workorders.enums.WorkOrderStatusCondition;
import com.xhg.ops.workorders.model.WorkOrder;
import com.xhg.ops.workorders.model.WorkOrderMaterielApply;
import com.xhg.ops.workorders.model.WorkOrderResult;

/**
 * 工单流程逻辑处理接口
 * @author 刘涛
 * @date 2018年7月12日
 */
public interface WorkOrderFlowService {

	/** 签到的距离范围 **/
	public static final double SIGN_DISTANCE = 500;
	
	/**
	 * 创建工单-流程开始
	 * @param workOrder 工单
	 * @param user 处理用户信息
	 * @return 工单号
	 */
	Integer createWorkOrder(WorkOrder workOrder, WorkOrderUserDTO user);
	
	/**
	 * 工单初审，总部运维主管进行审核
	 * @param taskId 流程任务ID
	 * @param level 紧身程度
	 * @param nextUserId 下一步处理人
	 * @param user 当前处理人信息
	 */
	void auditWorkOderOrder(int orderId, int level, int nextUserId, WorkOrderUserDTO user);
	
	/**
	 * 工单关闭，主管审核进行关闭
	 * @param taskId 流程任务ID
	 * @param reason 原因
	 * @param user 当前处理人信息
	 */
	void closeWorkOrder(int orderId, String reason, WorkOrderUserDTO user);
	
	/**
	 * 工单分配，运维经理分配工单
	 * @param taskId 流程任务ID
	 * @param nextUserId 下一步处理人
	 * @param user 当前处理人信息
	 */
	void assignWorkOrder(int orderId, int nextUserId, WorkOrderUserDTO user);
	
	/**
	 * 工单拒绝，运维经理审核拒绝，重新流回上一级进行处理
	 * @param taskId 流程任务ID
	 * @param reason 原因
	 * @param user 当前处理人信息
	 */
	void rejectWorkOrder(int orderId, String reason, WorkOrderUserDTO user);
	
	/**
	 * 运维专员进行签单操作
	 * @param taskId 流程任务ID
	 * @param user 当前处理人信息
	 */
	void agreeWorkOderOrder(int orderId, WorkOrderUserDTO user);
	
	/**
	 * 运维专员撤单操作
	 * @param taskId 流程任务ID
	 * @param reason 原因
	 * @param user 当前处理人信息
	 */
	void revokeWorkOderOrder(int orderId, String reason, WorkOrderUserDTO user);
	
	/**
	 * 运维专员进行出发操单操作
	 * @param taskId 流程任务ID
	 * @param user 当前处理人信息
	 */
	void startWorkOrder(int orderId, WorkOrderUserDTO user);
	
	/**
	 * 运维专员进行转单操作
	 * @param taskId 流程任务ID
	 * @param reason 原因
	 * @param user 当前处理人信息
	 */
	void transferWorkOrder(int orderId, String reason, WorkOrderUserDTO user);
	
	/**
	 * 运维专员进行签到操单操作
	 * @param taskId 流程任务ID
	 * @param longitude 坐标经度
	 * @param latitude 坐标纬度
	 * @param user 当前处理人信息
	 */
	void signWorkOrder(int orderId, double longitude, double latitude, WorkOrderUserDTO user);
	
	/**
	 * 运维专员提交工单操作
	 * @param taskId 流程任务ID
	 * @param workOrderResult 处理结果实体
	 * @param user 当前处理人信息
	 */
	void processWorkOrder(int orderId, WorkOrderResult workOrderResult, WorkOrderUserDTO user);
	
	/**
	 * 运维专员提交物料申请
	 * @param orderId
	 * @param taskId 流程任务ID
	 * @param workOrderMaterielApply
	 * @param user
	 */
	void submitMaterielApply(int orderId, WorkOrderMaterielApply workOrderMaterielApply, WorkOrderUserDTO user);
	
	/**
	 * 运维经理 - 审批物料操作
	 * @param taskId 流程任务ID
	 * @param user 当前处理人信息
	 */
	void approveMaterielApply(int orderId, WorkOrderUserDTO user);
	
	/**
	 * 物控 - 审核物料操作
	 * @param taskId 流程任务ID
	 * @param user 当前处理人信息
	 */
	void auditMaterielApply(int orderId, WorkOrderUserDTO user);
	
	/**
	 * 运维经理物料发货操作
	 * @param taskId 流程任务ID
	 * @param user 当前处理人信息
	 */
	void deliveryMateriel(int orderId, WorkOrderUserDTO user);
	
	/**
	 * 运维经理物料收货操作
	 * @param taskId 流程任务ID
	 * @param user 当前处理人信息
	 */
	void recevieMateriel(int orderId, WorkOrderUserDTO user);
	
	/**
	 * 运维经理进行工单确认
	 * @param taskId 流程任务ID
	 * @param user 当前处理人信息
	 */
	void confirmWorkOrder(int orderId, WorkOrderUserDTO user);
	
	/**
	 * 运维主管进行工单复核-流程结束
	 * @param taskId 流程任务ID
	 * @param user 当前处理人信息
	 */
	void reviewWorkOrder(int orderId, WorkOrderUserDTO user);

	/**
	 * 当前登录人是否能处理此节点订单
	 * @param procTaskId
	 * @param user
	 * @return
	 */
	boolean isCanProcess(final String procTaskId, WorkOrderUserDTO user);
	
	/**
	 * 查询待处理工单
	 * @param pageNum 页码
	 * @param pageSize 数量
	 * @param condition 状态条件
	 * @param user 当前处理人信息
	 * @return
	 */
	PagerResult<WorkOrderFindResultDto> findUnprocessWorkOrderListPage(int pageNum, int pageSize, WorkOrderStatusCondition condition, WorkOrderUserDTO user);
	
	/**
	 * 查询进行中的订单
	 * @param pageNum 页码
	 * @param pageSize 数量
	 * @param condition 状态条件
	 * @param user 当前处理人信息
	 * @return
	 */
	PagerResult<WorkOrderFindResultDto> findProcessWorkOrderListPage(int pageNum, int pageSize, WorkOrderStatusCondition condition, WorkOrderUserDTO user);
	
	/**
	 * 查询已结束工单
	 * @param pageNum 页码
	 * @param pageSize 数量
	 * @param user 当前处理人信息
	 * @return
	 */
	PagerResult<WorkOrderFindResultDto> findFinishedWorkOrderListPage(int pageNum, int pageSize, WorkOrderUserDTO user);
	
	/**
	 * 统计待处理工单数
	 * 根据不同角色，查询角色可处理的工单数
	 * @param user
	 * @return
	 */
	Map<String, Long> statUnProcessCount(WorkOrderUserDTO user);
	
	/**
	 * 统计进行中工单数
	 * @param user
	 * @return
	 */
	long statProcessingCount(WorkOrderUserDTO user);
	
	/***
	 * 统计已结束工单数
	 * @param user
	 * @return
	 */
	long statFinishedCount(WorkOrderUserDTO user);
	
}
