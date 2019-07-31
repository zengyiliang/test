package com.xhg.ops.workflow.service;

import com.xhg.ops.workflow.dto.WorkflowQueryReq;
import com.xhg.ops.workflow.dto.WorkflowQueryResp;
import com.xhg.ops.workflow.enums.ProcessStatusEnum;
import com.xhg.ops.workflow.model.AuditRecord;
import com.xhg.ops.workflow.model.PageBean;
import com.xhg.ops.workflow.model.ProcessProgress;
import com.xhg.ops.workflow.model.WorkflowProcessStaticInfo;
import com.xhg.ops.workorders.model.WorkOrder;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.identity.User;
import org.activiti.engine.task.Task;

import java.util.List;
import java.util.Set;

public interface WorkflowInnerQueryService {
	/**
	 * 查询某个流程实例的审核记录/进度
	 * 
	 * @param workflowQueryReq
	 * @return
	 */
	List<AuditRecord> queryAuditRecord(WorkflowQueryReq workflowQueryReq);
	/**
	 * 查询 待处理 的流程 包含指定给自己，自己所在的角色
	 * 
	 * @param workflowQueryReq
	 * @return
	 */
	PageBean<Task> queryProcessUndo(WorkflowQueryReq workflowQueryReq);

	/**
	 * 根据用户所在的角色，找到任务中候选组包含用户角色的记录
	 * 
	 * @param workflowQueryReq
	 * @return
	 */
	PageBean<Task> queryProcessUndoByCandidateGroup(WorkflowQueryReq workflowQueryReq);

	/**
	 * 根据userId，找到任务中候选人或者处理人是自己
	 * 
	 * @param workflowQueryReq
	 * @return
	 */
	PageBean<Task> queryProcessUndoByCandidateUser(WorkflowQueryReq workflowQueryReq);

	/**
	 * 根据userId，找到任务中候选人或者处理人是自己
	 *
	 * @param workflowQueryReq
	 * 	processDefineKey
	 * 	userId
	 * 	roleCodeList
	 * @return 返回的内容中包含了businessKey
	 */
	PageBean<WorkflowQueryResp> queryProcessUndoByCandidateUserIncludeBK(WorkflowQueryReq workflowQueryReq);


	/**
	 * 查询 正在处理 的流程
	 * 
	 * @param workflowQueryReq
	 * @return
	 */
	PageBean<HistoricProcessInstance> queryProcessing(WorkflowQueryReq workflowQueryReq);


	/**
	 * 查询 正在处理 的流程(通过数据库查询)
	 *
	 * @param workflowQueryReq
	 * @return
	 */
	PageBean<WorkflowQueryResp> queryProcessingByDB(WorkflowQueryReq workflowQueryReq);

	/**
	 * 查询 已结束 的流程
	 * 
	 * @param workflowQueryReq
	 * @return
	 */
	PageBean<HistoricProcessInstance> queryProcessed(WorkflowQueryReq workflowQueryReq);

	/**
	 * 统计待处理的工单
	 * 
	 * @param workflowQueryReq
	 * @return
	 */
	Long statProcessUndo(WorkflowQueryReq workflowQueryReq);

	/**
	 * 统计 进行中的工单
	 * 
	 * @param workflowQueryReq
	 * @return
	 */
	Long statProcessing(WorkflowQueryReq workflowQueryReq);
	
	/**
	 * 统计进行中的工单(查询数据库)
	 * 
	 * @param workflowQueryReq
	 * @return
	 */
	Long statProcessingByDB(WorkflowQueryReq workflowQueryReq);

	/**
	 * 统计 已结束 的流程
	 * 
	 * @param workflowQueryReq
	 * @return
	 */
	Long statProcessed(WorkflowQueryReq workflowQueryReq);

	/**
	 * 查看流程的状态
	 * 
	 * @param processInstanceId
	 * @return
	 */
	ProcessStatusEnum queryProcessStatus(String processInstanceId);

	/**
	 * 获得任务中的办理候选人
	 * 
	 * @param workflowQueryReq
	 * 	taskId
	 * @return
	 */
	Set<User> getTaskCandidate(WorkflowQueryReq workflowQueryReq);
	
	/**
	 * 获取流程实例的最新节点(待处理节点)
	 * @param workflowQueryReq
	 *  processDefineKey
	 *  processInstanceId
	 * @return
	 */
	Task getCurrentTaskByProcessInstanceId(WorkflowQueryReq workflowQueryReq);
	
	/**
	 * 查询指定人指定任务task详情（判定某人是否有处理任务的权限 ）
	 * @param workflowQueryReq
	 * @return
	 */
	Boolean queryProcessUndoContainTaskId(WorkflowQueryReq workflowQueryReq);


	/**
	 * 查询工作流进度，包含每个任务的参数，审核的结果，审核意见
	 * 全局变量放在第一条记录中
	 * @param workflowQueryReq
	 * 		processInstanceId
	 * @return
	 */
	List<ProcessProgress> queryProcessProgress(WorkflowQueryReq workflowQueryReq);

	/**
	 * 查询工作流进度
	 * @param workflowQueryReq
	 * 		processInstanceId
	 * @return
	 */
	List<ProcessProgress> queryProcessProgressMinInfo(WorkflowQueryReq workflowQueryReq);

	/**
	 * 查询工作流言的静态进度（页面上显示）
	 * @param workflowQueryReq
	 *
	 * @return
	 */
	List<ProcessProgress> queryProcessProgressStatic(WorkflowQueryReq workflowQueryReq);

    /**
     *  根据流程实例id获取流程文件的静态信息
     * @param workflowQueryReq
     *  processInstanceId
     *  processDefineKey
     * @return
     */
    List<WorkflowProcessStaticInfo> queryWorkflowProcessStaticInfo(WorkflowQueryReq workflowQueryReq);

    /**
     *  根据流程实例id获取当前节点的静态信息
     * @param workflowQueryReq
     *  processInstanceId
     * @return
     */
    WorkflowProcessStaticInfo getCurrentTaskStaticInfo(WorkflowQueryReq workflowQueryReq);

	/**
	 * 查询某个用户已经处理过的任务，如果同一个流程有多个任务审批过，也会返回多条记录；
	 * 默认排序：处理时间倒序
	 * @param workflowQueryReq
	 * 		userId
	 * 		processDefineKey
	 * @return
	 */
	PageBean<WorkflowQueryResp> queryProcessedForUser(WorkflowQueryReq workflowQueryReq);
}
