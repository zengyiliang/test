package com.xhg.ops.workflow.api;

import com.xhg.core.web.vo.ResponseBean;
import com.xhg.ops.workflow.dto.WorkflowQueryReq;
import com.xhg.ops.workflow.dto.WorkflowQueryResp;
import com.xhg.ops.workflow.model.AuditRecord;
import com.xhg.ops.workflow.model.PageBean;
import com.xhg.ops.workflow.model.ProcessProgress;
import com.xhg.ops.workflow.model.WorkflowProcessStaticInfo;
import com.xhg.ops.workorders.model.WorkOrder;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.identity.User;
import org.activiti.engine.task.Task;

import java.util.List;
import java.util.Set;

/**
 * 流程查询方面的接口
 */
public interface WorkflowQueryService {

    /**
     * 查询某个流程实例的审核记录/进度
     * @param workflowQueryReq
     *   使用到的参数
     *   userId
     *   processInstanceId
     * @return
     */
    ResponseBean<List<AuditRecord>> queryAuditRecord(WorkflowQueryReq workflowQueryReq);

    /**
     * 查询 待处理 的流程
     * @param workflowQueryReq
     *  userId
     *  roleCodeList
     *  businessKey(可选)
     *  pageNo
     *  pageSize
     * @return
     */
    ResponseBean<PageBean<WorkflowQueryResp>>   queryProcessUndo(WorkflowQueryReq workflowQueryReq);


    /**
     * 查询 正在处理  的流程
     * @param workflowQueryReq
     *  processDefineKey
     *  userId
     *  pageNo
     *  pageSize
     * @return
     */
    ResponseBean<PageBean<WorkflowQueryResp>>   queryProcessing(WorkflowQueryReq workflowQueryReq);


    /**
     * 查询  已结束  的流程
     * @param workflowQueryReq
     *  processDefineKey
     *  userId
     *  pageNo
     *  pageSize
     * @return
     */
    ResponseBean<PageBean<WorkflowQueryResp>>   queryProcessed(WorkflowQueryReq workflowQueryReq);


    /**
     * 统计待处理的工单
     * @param workflowQueryReq
     *  userId
     *  roleCodeList
     *  businessKey（可选）
     *  taskDoc(可选)
     * @return
     */
    ResponseBean<Long>  statProcessUndo(WorkflowQueryReq workflowQueryReq);

    /**
     * 统计 进行中的工单
     * @param workflowQueryReq
     *   processDefineKey
     *   userId
     *   businessKey(可选)
     * @return
     */
    ResponseBean<Long>  statProcessing(WorkflowQueryReq workflowQueryReq);


    /**
     * 统计 已结束  的流程
     * @param workflowQueryReq
     *   processDefineKey
     *   userId
     *   businessKey(可选)
     * @return
     */
    ResponseBean<Long>   statProcessed(WorkflowQueryReq workflowQueryReq);

	/**
	 * 获得任务中的办理候选人
	 * 
	 * @param workflowQueryReq
	 * @return
	 */
    ResponseBean<Set<User>> getTaskCandidate(WorkflowQueryReq workflowQueryReq);
	
	/**
	 * 获取流程实例的最新节点(待处理节点)
	 * @param workflowQueryReq
	 * @return
	 */
    ResponseBean<Task> getCurrentTaskByProcessInstanceId(WorkflowQueryReq workflowQueryReq);
	
	/**
	 * 查询指定人指定任务task详情（判定某人是否有处理任务的权限 ）
	 * @param workflowQueryReq
	 * @return
	 */
	ResponseBean<Boolean> queryProcessUndoContainTaskId(WorkflowQueryReq workflowQueryReq);


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
	 *  processDefineKey
	 * @return
	 */
	List<ProcessProgress> queryProcessProgressStatic(WorkflowQueryReq workflowQueryReq);


	/**
	 * 查询 进行中 的流程(通过数据库查询)
	 *
	 * @param workflowQueryReq
	 * @return
	 */
	ResponseBean<PageBean<WorkflowQueryResp>>  queryProcessingByDB(WorkflowQueryReq workflowQueryReq);
	
    /**
     * 统计 进行中的工单
     * @param workflowQueryReq
     *   processDefineKey
     *   userId
     *   businessKey(可选)
     * @return
     */
    ResponseBean<Long>  statProcessingByDB(WorkflowQueryReq workflowQueryReq);


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
	 *  根据流程实例id获取当前流程的静态信息
	 * @param workflowQueryReq
	 *  processInstanceId
	 * @return
	 */
	WorkflowProcessStaticInfo getCurrentTaskStaticInfo(WorkflowQueryReq workflowQueryReq);



	/**
	 *  根据流程实例id获取流程文件的静态信息
	 *  processInstanceId+processDefineKey 确保是唯一，因为同一个流程文件（里面的内容会修改）可能会部署多次
	 * @param workflowQueryReq
	 *  processInstanceId
	 *  processDefineKey
	 * @return
	 */
	List<WorkflowProcessStaticInfo> queryWorkflowProcessStaticInfo(WorkflowQueryReq workflowQueryReq);



	/**
	 * 查询某个用户已经处理过的任务，如果同一个流程有多个任务审批过，也会返回多条记录；
	 * 默认排序：处理时间倒序
	 * @param workflowQueryReq
	 * 		userId
	 * 		processDefineKey
	 * @return
	 */
	ResponseBean<WorkflowQueryResp> queryProcessedForUser(WorkflowQueryReq workflowQueryReq);


	/**
	 * 解析流程文件的bpmnModel，获得第一个节点的信息，通常都是申请信息
	 * 一般只会有一个开始申请节点
	 * @param workflowQueryReq
	 * 	processDefineKey
	 * @return
	 */
	List<WorkflowProcessStaticInfo> parseBpmnModelForApplyInfo(WorkflowQueryReq workflowQueryReq);

}
