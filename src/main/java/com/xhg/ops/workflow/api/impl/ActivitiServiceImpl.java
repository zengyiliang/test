package com.xhg.ops.workflow.api.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.activiti.engine.identity.User;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhg.core.web.exception.BusinessException;
import com.xhg.core.web.vo.PagerResult;
import com.xhg.core.web.vo.ResponseBean;
import com.xhg.core.web.vo.RsBody;
import com.xhg.core.web.vo.Status;
import com.xhg.ops.enums.WorkFlowTaskEnum;
import com.xhg.ops.workflow.api.ActivitiService;
import com.xhg.ops.workflow.api.WorkflowProcessService;
import com.xhg.ops.workflow.api.WorkflowQueryService;
import com.xhg.ops.workflow.dto.WorkflowQueryReq;
import com.xhg.ops.workflow.dto.WorkflowQueryResp;
import com.xhg.ops.workflow.dto.WorkflowRequestEntity;
import com.xhg.ops.workflow.dto.WorkflowResponseEntity;
import com.xhg.ops.workflow.enums.ProcessEnum;
import com.xhg.ops.workflow.model.AuditRecord;
import com.xhg.ops.workflow.model.PageBean;
import com.xhg.ops.workflow.vo.WorkflowProcessInstanceVO;
import com.xhg.ops.workflow.vo.WorkflowTaskVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ActivitiServiceImpl implements ActivitiService {

	@Autowired
	private WorkflowQueryService workflowQueryService;

	@Autowired
	private WorkflowProcessService workflowProcessService;
	
	ExecutorService executorService = Executors.newFixedThreadPool(WorkFlowTaskEnum.getProcessCodeList().size());
	
	/**
	 * 流程操作历史记录查询
	 */
	@Override
	public List<WorkflowTaskVO> queryAuditRecord(String processInstanceId, Integer pageSize, Integer pageNo) {

		log.debug("processInstanceId：{},pageSize:{},PageNo:{}", processInstanceId, pageSize, pageNo);
		List<WorkflowTaskVO> taskList = new ArrayList<>();
		WorkflowQueryReq workflowQueryReq = new WorkflowQueryReq();
		workflowQueryReq.setProcessDefineKey(ProcessEnum.WORK_ORDER_PROCESS.getProcessKey());
		workflowQueryReq.setPageSize(pageSize);
		workflowQueryReq.setPageNo(pageNo);
		workflowQueryReq.setProcessInstanceId(processInstanceId);

		ResponseBean<List<AuditRecord>> responseBean = workflowQueryService
				.queryAuditRecord(workflowQueryReq);
		if (null != responseBean && Status.SUCCESS.getName().equals(responseBean.getCode())) {
			RsBody<List<AuditRecord>> rsList = responseBean.getResponseBody();
			rsList.getData().forEach(p -> {
				WorkflowTaskVO task = new WorkflowTaskVO();
				task.setId(p.getTaskId());
				task.setName(p.getTaskName());
				task.setUserId(p.getAuditor());
				task.setAuditTime(p.getAuditTime());
				task.setComment(p.getComment());
				task.setTaskState(p.getTaskDoc());
				task.setTaskDoc(WorkFlowTaskEnum.getWorkFlowTaskDescByCode(p.getTaskDoc()));
				task.setProcessInstanceId(p.getProcessInstanceId());
				taskList.add(task);
			});
		}
		return taskList;
	}

	/**
	 * 待处理工单
	 */
	@Override
	public PagerResult<WorkflowTaskVO> queryProcessUndo(String userId, String taskDoc, Integer pageSize,
			Integer pageNo) {
		log.debug("userId：{},taskDoc:{},pageSize:{},PageNo:{}", userId, taskDoc, pageSize, pageNo);
		WorkflowQueryReq workflowQueryReq = new WorkflowQueryReq();
		workflowQueryReq.setProcessDefineKey(ProcessEnum.WORK_ORDER_PROCESS.getProcessKey());
		workflowQueryReq.setPageSize(pageSize);
		workflowQueryReq.setPageNo(pageNo);
		workflowQueryReq.setUserId(userId);
		taskDoc=WorkFlowTaskEnum.contains(taskDoc) ? taskDoc : "";
		//物料申请 4个状态统一为挂起状态
		if(WorkFlowTaskEnum.SUSPENDED.getCode().equals(taskDoc)){
			taskDoc=taskDoc.concat("%");
		}
		workflowQueryReq.setTaskDoc(taskDoc);
		ResponseBean<PageBean<WorkflowQueryResp>> responseBean = workflowQueryService
				.queryProcessUndo(workflowQueryReq);
		return processProcessTaskData(responseBean);
	}

	/**
	 * 进行中的工单
	 */
	@Override
	public PagerResult<WorkflowProcessInstanceVO> queryProcessing(String userId,String taskDoc, Integer pageSize,
			Integer pageNo) {
		log.debug("userId：{},pageSize:{},PageNo:{}", userId, pageSize, pageNo);
		WorkflowQueryReq workflowQueryReq = new WorkflowQueryReq();
		workflowQueryReq.setProcessDefineKey(ProcessEnum.WORK_ORDER_PROCESS.getProcessKey());
		workflowQueryReq.setPageSize(pageSize);
		workflowQueryReq.setPageNo(pageNo);
		workflowQueryReq.setUserId(userId);
		workflowQueryReq.setTaskDoc(taskDoc);
		ResponseBean<PageBean<WorkflowQueryResp>> responseBean = workflowQueryService.queryProcessingByDB(workflowQueryReq);
		return processProcessInstanceResultData(responseBean);
	}

	/**
	 * 已结束的工单
	 */
	@Override
	public PagerResult<WorkflowProcessInstanceVO> queryProcessed(String userId, Integer pageSize, Integer pageNo) {
		log.debug("userId：{},pageSize:{},PageNo:{}", userId, pageSize, pageNo);
		WorkflowQueryReq workflowQueryReq = new WorkflowQueryReq();
		workflowQueryReq.setProcessDefineKey(ProcessEnum.WORK_ORDER_PROCESS.getProcessKey());
		workflowQueryReq.setPageSize(pageSize);
		workflowQueryReq.setPageNo(pageNo);
		workflowQueryReq.setUserId(userId);
		ResponseBean<PageBean<WorkflowQueryResp>> responseList = workflowQueryService.queryProcessed(workflowQueryReq);
		return processProcessInstanceResultData(responseList);
	}

	private PagerResult<WorkflowTaskVO> processProcessTaskData(ResponseBean<PageBean<WorkflowQueryResp>> responseBean) {
		List<WorkflowTaskVO> taskList = new ArrayList<>();
		PagerResult<WorkflowTaskVO> pagerResults = new PagerResult<WorkflowTaskVO>();
		if (null != responseBean && Status.SUCCESS.getName().equals(responseBean.getCode())) {
			RsBody<PageBean<WorkflowQueryResp>> rsList = responseBean.getResponseBody();
			PageBean<WorkflowQueryResp> pageBean = rsList.getData();
			if (Status.SUCCESS.getName().equals(rsList.getCode()) && null != pageBean) {
				pagerResults.setTotal(pageBean.getTotalCount());
				pagerResults.setCurrentPage(pageBean.getPageNo());
				pagerResults.setPageSize(pageBean.getPageSize());
				pageBean.getList().forEach(p -> {
					WorkflowTaskVO task = new WorkflowTaskVO();
					task.setId(p.getTaskId());
					task.setUserId(p.getUserId());
					task.setEndTime(p.getEndTime());
					task.setTaskState(p.getTaskDoc());
					task.setTaskDoc(WorkFlowTaskEnum.getWorkFlowTaskDescByCode(p.getTaskDoc()));
					task.setProcessInstanceId(p.getProcessInstanceId());
					task.setName(p.getName());
					task.setCreateTime(p.getCreateTime());
					taskList.add(task);
				});
			}
			
		}
		pagerResults.setList(taskList);
		return pagerResults;
	}
	private PagerResult<WorkflowProcessInstanceVO> processProcessInstanceResultData(ResponseBean<PageBean<WorkflowQueryResp>> responseBean) {
		List<WorkflowProcessInstanceVO> instanceList = new ArrayList<>();
		PagerResult<WorkflowProcessInstanceVO> pagerResults = new PagerResult<WorkflowProcessInstanceVO>();
		if (null != responseBean && Status.SUCCESS.getName().equals(responseBean.getCode())) {
			RsBody<PageBean<WorkflowQueryResp>> rsList = responseBean.getResponseBody();
			PageBean<WorkflowQueryResp> pageBean = rsList.getData();
			if (Status.SUCCESS.getName().equals(rsList.getCode()) && null != pageBean) {
				pagerResults.setTotal(pageBean.getTotalCount());
				pagerResults.setCurrentPage(pageBean.getPageNo());
				pagerResults.setPageSize(pageBean.getPageSize());
				pageBean.getList().forEach(p -> {
					WorkflowProcessInstanceVO instance = new WorkflowProcessInstanceVO();
					instance.setProcessInstanceId(p.getProcessInstanceId());
					instance.setBusinessKey(p.getBusinessKey());
					instance.setName(p.getName());
					instance.setStartTime(p.getCreateTime());
					instance.setEndTime(p.getEndTime());
					instance.setProcessDefinitionKey(p.getProcessDefineKey());
					instance.setStartUserId(p.getStartUserId());
					instanceList.add(instance);
				});
			}

		}
		pagerResults.setList(instanceList);
		return pagerResults;
	}

	/**
	 * 进行中 工单数量
	 */
	@Override
	public Long statProcessingCount(String userId) {
		Long count = 0L;
		WorkflowQueryReq workflowQueryReq = new WorkflowQueryReq();
		workflowQueryReq.setProcessDefineKey(ProcessEnum.WORK_ORDER_PROCESS.getProcessKey());
		workflowQueryReq.setUserId(userId);
		ResponseBean<Long> responseBean = workflowQueryService.statProcessingByDB(workflowQueryReq);
		if (null != responseBean && Status.SUCCESS.getName().equals(responseBean.getCode())) {
			RsBody<Long> rsBody = responseBean.getResponseBody();
			if (Status.SUCCESS.getName().equals(rsBody.getCode())) {
				count = responseBean.getResponseBody().getData();
			}
		}
		return count;
	}

	/**
	 * 已完成 工单数量
	 */
	@Override
	public Long statProcessedCount(String userId) {
		Long count = 0L;
		WorkflowQueryReq workflowQueryReq = new WorkflowQueryReq();
		workflowQueryReq.setProcessDefineKey(ProcessEnum.WORK_ORDER_PROCESS.getProcessKey());
		workflowQueryReq.setUserId(userId);
		ResponseBean<Long> responseBean = workflowQueryService.statProcessed(workflowQueryReq);
		if (null != responseBean && Status.SUCCESS.getName().equals(responseBean.getCode())) {
			RsBody<Long> rsBody = responseBean.getResponseBody();
			if (Status.SUCCESS.getName().equals(rsBody.getCode())) {
				count = responseBean.getResponseBody().getData();
			}
		}
		return count;
	}

	/**
	 * 待处理工单
	 */
	@Override
	public Long statProcessUndoCount(String userId, String taskDoc) {
		Long count = 0L;
		WorkflowQueryReq workflowQueryReq = new WorkflowQueryReq();
		workflowQueryReq.setProcessDefineKey(ProcessEnum.WORK_ORDER_PROCESS.getProcessKey());
		workflowQueryReq.setUserId(userId);
		taskDoc=WorkFlowTaskEnum.contains(taskDoc) ? taskDoc : "";
		//物料申请 4个状态统一为挂起状态
		if(WorkFlowTaskEnum.SUSPENDED.getCode().equals(taskDoc)){
			taskDoc=taskDoc.concat("%");
		}
		workflowQueryReq.setTaskDoc(taskDoc);
		ResponseBean<Long> responseBean = workflowQueryService.statProcessUndo(workflowQueryReq);
		if (null != responseBean && Status.SUCCESS.getName().equals(responseBean.getCode())) {
			RsBody<Long> rsBody = responseBean.getResponseBody();
			if (Status.SUCCESS.getName().equals(rsBody.getCode())) {
				count = responseBean.getResponseBody().getData();
			}
		}
		return count;
	}

	/**
	 * 待处理工单
	 */
	@Override
	public Map<String, Long> statProcessUndoAllCount(String userId) {
		return statProcessUndoAllCount(userId, WorkFlowTaskEnum.getProcessCodeList());
	}

	/**
	 * 待处理工单
	 */
	@Override
	public Map<String, Long> statProcessUndoAllCount(String userId, List<String> taskList) {
		Map<String, Long> processUndoDataMap=new HashMap<>();
		List<Future<Map<String, Long>>> results = new ArrayList<Future<Map<String, Long>>>();
		// 存储执行结果的List
		taskList.forEach(p -> {
			Future<Map<String, Long>> result = executorService.submit(new Callable<Map<String, Long>>() {
				@Override
				public Map<String, Long> call() throws Exception {
					Long count = statProcessUndoCount(userId, p);
					log.debug("状态:{}, {}, 数量:{}", p, WorkFlowTaskEnum.getWorkFlowTaskDescByCode(p), count);
					Map<String, Long> map = new HashMap<>();
					map.put(p, count);
					return map;
				}
			});
			results.add(result);
		});
		results.forEach(p -> {
			// 获取包含返回结果的future对象
			try {
				processUndoDataMap.putAll(p.get());
			} catch (InterruptedException | ExecutionException e) {
				log.error("获取数据出错:", e);
			}
		});
		return processUndoDataMap;
	}

	/**
	 * 待处理工单
	 */
	@Override
	public Map<String, Long> statProcessUndoAllCount(String userId, String taskDoc) {
		List<String> taskList = new ArrayList<>();
		taskList.add(taskDoc);
		return statProcessUndoAllCount(userId, taskList);
	}

	/**
	 * 创建订单
	 */
	@Override
	public WorkflowProcessInstanceVO createWorkOrder(String userId, String businessKey) {
		log.debug("userId:{},businessKey:{}", userId, businessKey);
		Map<String, Object> paramMap = new HashMap<>(1);
		paramMap.put("applyUserName", userId);
		WorkflowRequestEntity workflowRequestEntity = new WorkflowRequestEntity();
		workflowRequestEntity.setProcessDefineKey(ProcessEnum.WORK_ORDER_PROCESS.getProcessKey());
		workflowRequestEntity.setBusinessKey(businessKey);
		workflowRequestEntity.setUserId(userId);
		workflowRequestEntity.setParamMap(paramMap);
		ResponseBean<WorkflowResponseEntity> responseBean = workflowProcessService
				.startProcessInstance(workflowRequestEntity);

		WorkflowProcessInstanceVO processVO = new WorkflowProcessInstanceVO();
		if (null != responseBean && Status.SUCCESS.getName().equals(responseBean.getCode())) {
			RsBody<WorkflowResponseEntity> rsBody = responseBean.getResponseBody();
			if (Status.SUCCESS.getName().equals(rsBody.getCode())) {
				WorkflowResponseEntity responseEntity = rsBody.getData();
				processVO.setProcessInstanceId(responseEntity.getProcessInstanceId());
			}
		}
		return processVO;
	}

	/**
	 * 工单初审，总部运维主管进行审核
	 */
	@Override
	public WorkflowProcessInstanceVO auditWorkOderOrder(String taskId, String userId, String nextUserId) {
		log.debug("总部运维主管处理工单通过,taskId:{},userId:{},城市运维经理ID：{}", taskId, userId, nextUserId);
		Map<String, Object> paramMap = new HashMap<>(1);
		paramMap.put("headOpsSupervisor", userId);//// 最后一步总部城市运维经理复核需要该变量值
		paramMap.put("cityOpsManage", nextUserId);
		paramMap.put("isCloseTaskWork", false);
		return claimAndCompleteTask(taskId, userId, paramMap);
	}

	/**
	 * 工单初审，总部运维主管关闭工单
	 */
	@Override
	public WorkflowProcessInstanceVO closeWorkOrder(String taskId, String userId, String comment) {
		log.debug("总部运维主管 关闭工单,taskId:{},userId:{},关闭工单原因：{}", taskId, userId, comment);
		Map<String, Object> paramMap = new HashMap<>(1);
		paramMap.put("isCloseTaskWork", true);
		return claimAndCompleteTask(taskId, userId, paramMap, comment);
	}

	/**
	 * 工单分配，运维经理分配工单
	 */
	@Override
	public WorkflowProcessInstanceVO assignWorkOrder(String taskId, String userId, String nextUserId) {
		log.debug("城市运维经理分配工单,taskId:{},userId:{},运维专员ID：{}", taskId, userId, nextUserId);
		Map<String, Object> paramMap = new HashMap<>(1);
		paramMap.put("isRefuseWorkTask", false);
		paramMap.put("opsAttache", nextUserId);
		return claimAndCompleteTask(taskId, userId, paramMap);
	}

	/**
	 * 城市运维经理拒绝工单，重新流回上一级进行处理
	 */
	@Override
	public WorkflowProcessInstanceVO rejectWorkOrder(String taskId, String userId, String comment) {
		log.debug("运维经理审核拒绝工单,taskId:{},userId:{},关闭工单原因：{}", taskId, userId, comment);
		Map<String, Object> paramMap = new HashMap<>(1);
		paramMap.put("isRefuseWorkTask", true);
		return claimAndCompleteTask(taskId, userId, paramMap, comment);
	}

	/**
	 * 运维专员进行签单操作
	 */
	@Override
	public WorkflowProcessInstanceVO agreeWorkOderOrder(String taskId, String userId) {
		log.debug("运维专员进行签单操作,taskId:{},userId:{}", taskId, userId);
		Map<String, Object> paramMap = new HashMap<>(1);
		paramMap.put("isRevokeWorkTask", false);
		return claimAndCompleteTask(taskId, userId, paramMap);
	}

	/**
	 * 运维专员撤单操作
	 */
	@Override
	public WorkflowProcessInstanceVO revokeWorkOderOrder(String taskId, String userId, String comment) {
		log.debug("运维专员撤单操作,taskId:{},userId:{},撤单原因：{}", taskId, userId, comment);
		Map<String, Object> paramMap = new HashMap<>(1);
		paramMap.put("isRevokeWorkTask", true);
		return claimAndCompleteTask(taskId, userId, paramMap, comment);
	}

	/**
	 * 运维专员进行出发操单操作
	 */
	@Override
	public WorkflowProcessInstanceVO startWorkOrder(String taskId, String userId) {
		log.debug("运维专员进行签单操作,taskId:{},userId:{}", taskId, userId);
		Map<String, Object> paramMap = new HashMap<>(1);
		paramMap.put("isConvertWorkTask", false);
		return claimAndCompleteTask(taskId, userId, paramMap);
	}

	/**
	 * 运维专员进行转单操作
	 */
	@Override
	public WorkflowProcessInstanceVO transferWorkOrder(String taskId, String userId, String comment) {
		log.debug("运维专员进行转单操作,taskId:{},userId:{},转单原因：{}", taskId, userId, comment);
		Map<String, Object> paramMap = new HashMap<>(1);
		paramMap.put("isConvertWorkTask", true);
		return claimAndCompleteTask(taskId, userId, paramMap, comment);

	}

	/**
	 * 运维专员进行签到操单操作
	 */
	@Override
	public WorkflowProcessInstanceVO signWorkOrder(String taskId, String userId) {
		log.debug("运维专员进行签到操单操作,taskId:{},userId:{}", taskId, userId);
		return claimAndCompleteTask(taskId, userId);
	}

	/**
	 * 运维专员处理工单完成
	 */
	@Override
	public WorkflowProcessInstanceVO processWorkOrder(String taskId, String userId) {
		log.debug("运维专员处理工单完成,taskId:{},userId:{}", taskId, userId);
		Map<String, Object> paramMap = new HashMap<>(1);
		paramMap.put("isApplyMaterial", false);
		return claimAndCompleteTask(taskId, userId, paramMap);
	}

	/**
	 * 运维专员处理工单申请物料
	 */
	@Override
	public WorkflowProcessInstanceVO submitMaterielApply(String taskId, String userId) {
		log.debug("运维专员处理工单申请物料,taskId:{},userId:{}", taskId, userId);
		Map<String, Object> paramMap = new HashMap<>(1);
		paramMap.put("isApplyMaterial", true);
		return claimAndCompleteTask(taskId, userId, paramMap);
	}

	/**
	 * 城市运维经理审核物料操作
	 */
	@Override
	public WorkflowProcessInstanceVO auditMaterielApply(String taskId, String userId) {
		log.debug("城市运维经理审核物料操作,taskId:{},userId:{}", taskId, userId);
		return claimAndCompleteTask(taskId, userId);
	}

	/**
	 * 物料审核-物控
	 */
	@Override
	public WorkflowProcessInstanceVO checkMateriel(String taskId, String userId) {
		log.debug("物料审核-物控操作,taskId:{},userId:{}", taskId, userId);
		Map<String, Object> paramMap = new HashMap<>(1);
		paramMap.put("materielUser", userId);
		return claimAndCompleteTask(taskId, userId,paramMap);
	}

	/**
	 * 物料发送-物控
	 */
	@Override
	public WorkflowProcessInstanceVO deliveryMateriel(String taskId, String userId) {
		log.debug("物料发送-物控操作,taskId:{},userId:{}", taskId, userId);
		return claimAndCompleteTask(taskId, userId);
	}

	/**
	 * 物料查收-运维专员
	 */
	@Override
	public WorkflowProcessInstanceVO recevieMateriel(String taskId, String userId) {
		log.debug("物料查收-运维专员操作,taskId:{},userId:{}", taskId, userId);
		return claimAndCompleteTask(taskId, userId);
	}

	/**
	 * 工单审核-城市运维经理
	 */
	@Override
	public WorkflowProcessInstanceVO confirmWorkOrder(String taskId, String userId) {
		log.debug("工单审核-城市运维经理操作,taskId:{},userId:{}", taskId, userId);
		return claimAndCompleteTask(taskId, userId);
	}

	/**
	 * 工单复核-总部运维主管-流程结束
	 */
	@Override
	public WorkflowProcessInstanceVO reviewWorkOrder(String taskId, String userId) {
		log.debug("工单复核-总部运维主管-流程结束,taskId:{},userId:{}", taskId, userId);
		return claimAndCompleteTask(taskId, userId);
	}

	@Override
	public WorkflowProcessInstanceVO claimAndCompleteTask(String taskId, String userId) {
		return claimAndCompleteTask(taskId, userId, new HashMap<>());
	}

	private WorkflowProcessInstanceVO claimAndCompleteTask(String taskId, String userId, Map<String, Object> paramMap) {
		return claimAndCompleteTask(taskId, userId, paramMap, "");
	}

	private WorkflowProcessInstanceVO claimAndCompleteTask(String taskId, String userId, Map<String, Object> paramMap,
			String comment) {
		
		Boolean flag= queryProcessUndoContainUserId(taskId, userId);
		if(!flag){
			throw new BusinessException("用户ID:"+userId+"无权限处理该条工单,工单号:("+taskId+")");
		}
		
		WorkflowRequestEntity workflowRequestEntity = new WorkflowRequestEntity();
		workflowRequestEntity.setProcessDefineKey(ProcessEnum.WORK_ORDER_PROCESS.getProcessKey());
		workflowRequestEntity.setUserId(userId);
		workflowRequestEntity.setTaskId(taskId);
		workflowRequestEntity.setMessage(comment);
		workflowRequestEntity.setParamMap(paramMap);
		ResponseBean<WorkflowResponseEntity> responseBean = workflowProcessService
				.claimAndCompleteTask(workflowRequestEntity);
		WorkflowProcessInstanceVO processVO = new WorkflowProcessInstanceVO();
		if (null != responseBean && Status.SUCCESS.getName().equals(responseBean.getCode())) {
			RsBody<WorkflowResponseEntity> rsBody = responseBean.getResponseBody();
			if (Status.SUCCESS.getName().equals(rsBody.getCode())) {
				WorkflowResponseEntity responseEntity = rsBody.getData();
				processVO.setProcessInstanceId(responseEntity.getProcessInstanceId());
				processVO.setTaskId(responseEntity.getTaskId());
			}
		}
		return processVO;
	}
	/**
	 * 获得任务中的办理候选人
	 */
	@Override
	public Set<String> getTaskCandidate(String taskId) {
		log.debug(" taskId：{}", taskId);
		WorkflowQueryReq workflowQueryReq = new WorkflowQueryReq();
		workflowQueryReq.setProcessDefineKey(ProcessEnum.WORK_ORDER_PROCESS.getProcessKey());
		workflowQueryReq.setTaskId(taskId);
		
		Set<String> userIdSet=new HashSet<>();
		ResponseBean<Set<User>> responseBean=workflowQueryService.getTaskCandidate(workflowQueryReq);
		if (null != responseBean && Status.SUCCESS.getName().equals(responseBean.getCode())) {
			RsBody<Set<User>> rsTask = responseBean.getResponseBody();
			Set<User> setUser = rsTask.getData();
			if (Status.SUCCESS.getName().equals(rsTask.getCode()) && null != setUser) {
				setUser.forEach(p->{
					userIdSet.add(p.getId());
				});
			}
		}
		return userIdSet;
	}
	/**
	 * 获取流程实例的最新节点(待处理节点)
	 */
	@Override
	public WorkflowTaskVO getCurrentTaskByProcessInstanceId(String processInstanceId) {
		log.debug("processInstanceId:{}", processInstanceId);
		WorkflowQueryReq workflowQueryReq = new WorkflowQueryReq();
		workflowQueryReq.setProcessDefineKey(ProcessEnum.WORK_ORDER_PROCESS.getProcessKey());
		workflowQueryReq.setProcessInstanceId(processInstanceId);
		ResponseBean<Task> responseBean = workflowQueryService
				.getCurrentTaskByProcessInstanceId(workflowQueryReq);
		WorkflowTaskVO taskVO = new WorkflowTaskVO();
		if (null != responseBean && Status.SUCCESS.getName().equals(responseBean.getCode())) {
			RsBody<Task> rsTask = responseBean.getResponseBody();
			Task task = rsTask.getData();
			if (Status.SUCCESS.getName().equals(rsTask.getCode()) && null != task) {
				taskVO.setId(task.getId());
				taskVO.setName(task.getName());
				taskVO.setTaskDoc(WorkFlowTaskEnum.getWorkFlowTaskDescByCode(task.getDescription()));
				taskVO.setTaskState(task.getDescription());
				taskVO.setProcessInstanceId(task.getProcessInstanceId());
			}
		}
		return taskVO;
	}

	/**
	 * 查询指定人指定任务task详情（判定某人是否有处理任务的权限 ）
	 * @param taskId
	 * @param userId
	 * @return
	 */
	@Override
	public Boolean queryProcessUndoContainUserId(String taskId,String userId) {
		log.debug("taskId：{},userId:{}", taskId, userId);
		WorkflowQueryReq workflowQueryReq = new WorkflowQueryReq();
		workflowQueryReq.setProcessDefineKey(ProcessEnum.WORK_ORDER_PROCESS.getProcessKey());
		workflowQueryReq.setTaskId(taskId);
		workflowQueryReq.setUserId(userId);
		ResponseBean<Boolean> responseBean = workflowQueryService
				.queryProcessUndoContainTaskId(workflowQueryReq);

		Boolean flag=true;
		if (null != responseBean && Status.SUCCESS.getName().equals(responseBean.getCode())) {
			RsBody<Boolean> rsTask = responseBean.getResponseBody();
			if (Status.SUCCESS.getName().equals(rsTask.getCode())) {
				flag=rsTask.getData();
			}
		}
		return flag;
	}
}
