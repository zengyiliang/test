package com.xhg.ops.workflow.service.Impl;

import com.xhg.core.web.exception.BusinessException;
import com.xhg.ops.workflow.dto.WorkflowRequestEntity;
import com.xhg.ops.workflow.dto.WorkflowResponseEntity;
import com.xhg.ops.workflow.service.WorkflowInnerProcessService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class WorkflowInnerProcessServiceImpl implements WorkflowInnerProcessService {
	protected static final Logger logger = LoggerFactory.getLogger(WorkflowInnerProcessServiceImpl.class);

	@Resource
	private RuntimeService runtimeService;
	@Resource
	private IdentityService identityService;
	@Resource
	private TaskService taskService;

	@Value("${xhg.activiti.use.tenant}")
	private boolean useTenant;

	@Override
	public WorkflowResponseEntity startProcessInstance(WorkflowRequestEntity workflowRequestEntity) {

		identityService.setAuthenticatedUserId(workflowRequestEntity.getUserId());
		// 开始流程
		// ProcessInstance processInstance =
		// runtimeService.startProcessInstanceByKey(workflowRequestEntity.getProcessDefineKey());
		// 启动流程实例

		// ProcessInstance processInstance =
		// runtimeService.startProcessInstanceByKey(workflowRequestEntity.getProcessDefineKey(),workflowRequestEntity.getBusinessKey(),workflowRequestEntity.getParamMap());

		// 启动流程实例
		ProcessInstance processInstance = null;
		/**
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(
				workflowRequestEntity.getProcessDefineKey(), workflowRequestEntity.getBusinessKey(),
				workflowRequestEntity.getParamMap());
		**/

		if(useTenant){
			//设置TenantId
			processInstance = runtimeService.startProcessInstanceByKeyAndTenantId(
					workflowRequestEntity.getProcessDefineKey(), workflowRequestEntity.getBusinessKey(),
					workflowRequestEntity.getParamMap(),workflowRequestEntity.getClientSource()
			);
		}else{
			processInstance = runtimeService.startProcessInstanceByKey(
					workflowRequestEntity.getProcessDefineKey(), workflowRequestEntity.getBusinessKey(),
					workflowRequestEntity.getParamMap());
		}


		// 查询当前任务
		Task currentTask = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

		// 申明任务
		taskService.claim(currentTask.getId(), workflowRequestEntity.getUserId());

		Map<String, Object> paramMap = workflowRequestEntity.getParamMap();
		if (null != paramMap && paramMap.size() > 0) {
			taskService.setVariables(currentTask.getId(), paramMap);
		}

		Map<String, Object> paramMapLocal = workflowRequestEntity.getParamMapLocal();
		if (null != paramMapLocal && paramMapLocal.size() > 0) {
			taskService.setVariablesLocal(currentTask.getId(), paramMapLocal);
		}

		if (StringUtils.isNotEmpty(workflowRequestEntity.getMessage())) {
			taskService.addComment(currentTask.getId(), processInstance.getId(), workflowRequestEntity.getMessage());
		}

		taskService.complete(currentTask.getId());

		WorkflowResponseEntity workflowResponseEntity = new WorkflowResponseEntity();
		workflowResponseEntity.setProcessInstanceId(processInstance.getId());

		workflowResponseEntity.setTaskId(currentTask.getId());

		return workflowResponseEntity;
	}

	@Override
	public WorkflowResponseEntity claimAndCompleteTask(WorkflowRequestEntity workflowRequestEntity) {
		WorkflowResponseEntity workflowResponseEntity = new WorkflowResponseEntity();
		String currentTaskId = workflowRequestEntity.getTaskId();
		if (StringUtils.isEmpty(currentTaskId)) {
			throw new BusinessException("操作流程节点taskId不能为空");
		}
		String userId = workflowRequestEntity.getUserId();
		if (StringUtils.isEmpty(userId)) {
			throw new BusinessException("操作流程用户ID不能为空");
		}
		Task task = taskService.createTaskQuery().taskId(currentTaskId).singleResult();
		if (null == task) {
			throw new BusinessException("任务节点" + currentTaskId + "查询流程任务失败，查无该任务或已被处理");
		}

		if (!StringUtils.isEmpty(workflowRequestEntity.getMessage())) {
			identityService.setAuthenticatedUserId(userId);
			taskService.addComment(currentTaskId, task.getProcessInstanceId(), workflowRequestEntity.getMessage());
		}

		Map<String, Object> paramMap = workflowRequestEntity.getParamMap();
		if (null != paramMap && paramMap.size() > 0) {
			taskService.setVariables(task.getId(), paramMap);
		}

		Map<String, Object> paramMapLocal = workflowRequestEntity.getParamMapLocal();
		if (null != paramMapLocal && paramMapLocal.size() > 0) {
			taskService.setVariablesLocal(task.getId(), paramMapLocal);
		}

		taskService.claim(currentTaskId, userId);
		taskService.complete(currentTaskId);

		workflowResponseEntity.setProcessInstanceId(task.getProcessInstanceId());
		workflowResponseEntity.setTaskId(currentTaskId);
		return workflowResponseEntity;
	}

	@Override
	public WorkflowResponseEntity claimTask(WorkflowRequestEntity workflowRequestEntity) {

		// 查询当前任务
		Task currentTask = taskService.createTaskQuery().processInstanceId(workflowRequestEntity.getProcessInstanceId())
				.singleResult();

		// 申明任务
		taskService.claim(currentTask.getId(), workflowRequestEntity.getUserId());

		WorkflowResponseEntity workflowResponseEntity = new WorkflowResponseEntity();
		workflowResponseEntity.setProcessInstanceId(workflowRequestEntity.getProcessInstanceId());

		workflowResponseEntity.setTaskId(currentTask.getId());

		return workflowResponseEntity;
	}

	@Override
	public WorkflowResponseEntity completeTask(WorkflowRequestEntity workflowRequestEntity) {

		// 查询当前任务
		Task currentTask = taskService.createTaskQuery().processInstanceId(workflowRequestEntity.getProcessInstanceId())
				.singleResult();

		// 注意顺序，先addComment后complete
		//
		if(!StringUtils.isNotEmpty(workflowRequestEntity.getMessage()) ){
			taskService.addComment(currentTask.getId(), workflowRequestEntity.getProcessInstanceId(),
					workflowRequestEntity.getMessage());
		}

		Map<String, Object> paramMap = workflowRequestEntity.getParamMap();
		if (null != paramMap && paramMap.size() > 0) {
			taskService.setVariables(currentTask.getId(), paramMap);
		}

		Map<String, Object> paramMapLocal = workflowRequestEntity.getParamMapLocal();
		if (null != paramMapLocal && paramMapLocal.size() > 0) {
			taskService.setVariablesLocal(currentTask.getId(), paramMapLocal);
		}

		taskService.complete(currentTask.getId());

		WorkflowResponseEntity workflowResponseEntity = new WorkflowResponseEntity();
		workflowResponseEntity.setProcessInstanceId(workflowRequestEntity.getProcessInstanceId());

		workflowResponseEntity.setTaskId(currentTask.getId());

		return workflowResponseEntity;
	}
}
