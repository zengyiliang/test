package com.xhg.ops.workflow.api;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xhg.core.web.vo.PagerResult;
import com.xhg.ops.workflow.vo.WorkflowProcessInstanceVO;
import com.xhg.ops.workflow.vo.WorkflowTaskVO;

/**
 * activiti工单系统service
 * 
 * @Name:
 * @Description:
 * @Copyright: Copyright (c) 2018
 * @Author hubowei
 * @Create 2018年7月16日 下午5:57:33
 * @Version 1.0.0
 */
public interface ActivitiService {

	/**
	 * 查询某个流程实例的审核记录/进度
	 * 
	 * @param processInstanceId
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	List<WorkflowTaskVO> queryAuditRecord(String processInstanceId, Integer pageSize, Integer pageNo);

	/**
	 * 查询 待处理 的流程
	 * 
	 * @param userId
	 * @param taskDoc
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	PagerResult<WorkflowTaskVO> queryProcessUndo(String userId, String taskDoc, Integer pageSize, Integer pageNo);

	/**
	 * 查询 正在处理 的流程 进行中的工单包含待处理
	 * 
	 * @param userId
	 * @param taskDoc
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	PagerResult<WorkflowProcessInstanceVO> queryProcessing(String userId, String taskDoc, Integer pageSize,
			Integer pageNo);

	/**
	 * 查询 已结束 的流程
	 * 
	 * @param userId
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	PagerResult<WorkflowProcessInstanceVO> queryProcessed(String userId, Integer pageSize, Integer pageNo);

	/**
	 * 统计待处理的工单
	 * 
	 * @param userId
	 * @param taskDoc
	 * @return
	 */
	Long statProcessUndoCount(String userId, String taskDoc);

	/**
	 * 该用户所有待处理工单
	 * 
	 * @param userId
	 * @return
	 */
	Map<String, Long> statProcessUndoAllCount(String userId);

	/**
	 * 该用户指定类型待处理工单
	 * 
	 * @param userId
	 * @param taskList
	 * @return
	 */
	Map<String, Long> statProcessUndoAllCount(String userId, List<String> taskList);

	/**
	 * 该用户单个待处理工单
	 * 
	 * @param userId
	 * @return
	 */
	Map<String, Long> statProcessUndoAllCount(String userId, String taskDoc);

	/**
	 * 统计 进行中的工单
	 * 
	 * @param userId
	 * @return
	 */
	Long statProcessingCount(String userId);
	/**
	 * 统计 已结束 的流程
	 * 
	 * @param userId
	 * @return
	 */
	Long statProcessedCount(String userId);

	/**
	 * 启动工作流
	 * 
	 * @param userId
	 * @param businessKey
	 * @return
	 */
	WorkflowProcessInstanceVO createWorkOrder(String userId, String businessKey);

	/**
	 * 工单初审，总部运维主管进行审核
	 * 
	 * @param taskId
	 *            任务ID
	 * @param userId
	 *            当前登录运维主管用户ID
	 * @param nextUserId
	 *            城市运维经理id
	 */
	WorkflowProcessInstanceVO auditWorkOderOrder(String taskId, String userId, String nextUserId);

	/**
	 * 工单初审，总部运维主管关闭工单
	 * 
	 * @param taskId
	 *            任务ID
	 * @param userId
	 *            当前登录运维主管用户ID
	 * @param comment
	 *            审核意见
	 */
	WorkflowProcessInstanceVO closeWorkOrder(String taskId, String userId, String comment);

	/**
	 * 工单分配，运维经理分配工单
	 * 
	 * @param taskId
	 *            当前工单任务ID
	 * @param userId
	 *            运维经理ID
	 * @param nextUserId
	 *            运维专员id
	 * @return
	 */
	WorkflowProcessInstanceVO assignWorkOrder(String taskId, String userId, String nextUserId);

	/**
	 * 城市运维经理拒绝工单，重新流回上一级进行处理
	 * 
	 * @param taskId
	 *            当前工单任务ID
	 * @param userId
	 *            运维经理ID
	 * @param comment
	 *            拒绝工单描述信息
	 * @return
	 */
	WorkflowProcessInstanceVO rejectWorkOrder(String taskId, String userId, String comment);

	/**
	 * 运维专员进行签单操作
	 * 
	 * @param taskId
	 *            任务节点ID
	 * @param userId
	 *            运维专员ID
	 * @return
	 */
	WorkflowProcessInstanceVO agreeWorkOderOrder(String taskId, String userId);

	/**
	 * 运维专员撤单操作
	 * 
	 * @param taskId
	 *            任务节点ID
	 * @param userId
	 *            运维专员ID
	 * @param comment
	 *            撤单原因
	 * @return
	 */
	WorkflowProcessInstanceVO revokeWorkOderOrder(String taskId, String userId, String comment);

	/**
	 * 运维专员进行出发操单操作
	 * 
	 * @param taskId
	 *            任务节点ID
	 * @param userId
	 *            运维专员ID
	 * @return
	 */
	WorkflowProcessInstanceVO startWorkOrder(String taskId, String userId);

	/**
	 * 运维专员进行转单操作
	 * 
	 * @param taskId
	 *            任务节点ID
	 * @param userId
	 *            运维专员ID
	 * @param comment
	 *            转单原因
	 * @return
	 */
	WorkflowProcessInstanceVO transferWorkOrder(String taskId, String userId, String comment);

	/**
	 * 运维专员进行签到操单操作
	 * 
	 * @param taskId
	 *            任务节点ID
	 * @param userId
	 *            运维专员ID
	 * @return
	 */
	WorkflowProcessInstanceVO signWorkOrder(String taskId, String userId);

	/**
	 * 运维专员处理工单完成
	 * 
	 * @param taskId
	 *            任务节点ID
	 * @param userId
	 *            运维专员ID
	 * @return
	 */
	WorkflowProcessInstanceVO processWorkOrder(String taskId, String userId);

	/**
	 * 运维专员处理工单申请物料
	 * 
	 * @param taskId
	 *            任务节点ID
	 * @param userId
	 *            运维专员ID
	 * @return
	 */
	WorkflowProcessInstanceVO submitMaterielApply(String taskId, String userId);

	/**
	 * 城市运维经理审核物料操作
	 * 
	 * @param taskId
	 *            任务节点ID
	 * @param userId
	 *            运维专员ID
	 * @return
	 */
	WorkflowProcessInstanceVO auditMaterielApply(String taskId, String userId);

	/**
	 * 物料审核-物控
	 * 
	 * @param taskId
	 *            任务节点ID
	 * @param userId
	 *            物控人员ID
	 */
	WorkflowProcessInstanceVO checkMateriel(String taskId, String userId);

	/**
	 * 物料发送-物控
	 * 
	 * @param taskId
	 *            任务节点ID
	 * @param userId
	 *            物控人员ID
	 */
	WorkflowProcessInstanceVO deliveryMateriel(String taskId, String userId);

	/**
	 * 物料查收-运维专员
	 * 
	 * @param taskId
	 *            任务节点ID
	 * @param userId
	 *            运维专员
	 */
	WorkflowProcessInstanceVO recevieMateriel(String taskId, String userId);

	/**
	 * 工单审核-城市运维经理
	 * 
	 * @param taskId
	 *            任务节点ID
	 * @param userId
	 *            运维专员
	 */
	WorkflowProcessInstanceVO confirmWorkOrder(String taskId, String userId);

	/**
	 * 工单复核-总部运维主管-流程结束
	 * 
	 * @param taskId
	 *            任务节点ID
	 * @param userId
	 *            运维专员
	 */
	WorkflowProcessInstanceVO reviewWorkOrder(String taskId, String userId);

	/**
	 * 处理和签收工作流
	 * 
	 * @param taskId
	 * @param userId
	 * @return
	 */
	WorkflowProcessInstanceVO claimAndCompleteTask(String taskId, String userId);

	/**
	 * 获得任务中的办理候选人
	 * 
	 * @param taskId
	 * @return 可办理候选人集合
	 */
	Set<String> getTaskCandidate(String taskId);

	/**
	 * 获取流程实例的最新节点(待处理节点)
	 * 
	 * @param processInstanceId
	 * @return 最新节点的基本信息
	 */
	WorkflowTaskVO getCurrentTaskByProcessInstanceId(String processInstanceId);

	/**
	 * 查询指定人指定任务task详情（判定某人是否有处理任务的权限 ）
	 * 
	 * @param taskId
	 * @param userId
	 * @return true有权限 false无权限
	 */
	Boolean queryProcessUndoContainUserId(String taskId, String userId);

}
