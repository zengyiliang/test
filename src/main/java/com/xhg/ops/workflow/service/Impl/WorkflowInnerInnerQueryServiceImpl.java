package com.xhg.ops.workflow.service.Impl;

import com.xhg.core.web.exception.BusinessException;
import com.xhg.ops.workflow.dao.WorkflowBusinessDao;
import com.xhg.ops.workflow.dao.WorkflowCustomQueryDao;
import com.xhg.ops.workflow.dto.WorkflowQueryReq;
import com.xhg.ops.workflow.dto.WorkflowQueryResp;
import com.xhg.ops.workflow.enums.ProcessStatusEnum;
import com.xhg.ops.workflow.model.AuditRecord;
import com.xhg.ops.workflow.model.PageBean;
import com.xhg.ops.workflow.model.ProcessProgress;
import com.xhg.ops.workflow.model.WorkflowProcessStaticInfo;
import com.xhg.ops.workflow.service.WorkflowInnerQueryService;
import com.xhg.ops.workflow.utils.ActivitiUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.identity.User;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class WorkflowInnerInnerQueryServiceImpl implements WorkflowInnerQueryService {

	protected static final Logger logger = LoggerFactory.getLogger(WorkflowInnerInnerQueryServiceImpl.class);

	@Resource
	private RuntimeService runtimeService;
	@Resource
	private IdentityService identityService;
	@Resource
	private TaskService taskService;
	@Resource
	private HistoryService historyService;
	@Resource
	private WorkflowBusinessDao workflowBusinessDao;
	@Resource
	private RepositoryService repositoryService;
	@Resource
	private WorkflowCustomQueryDao workflowCustomQueryDao;


	@Value("${xhg.activiti.process-definitioin-path}")
	private  String workflowProcessLocation; //流程定义的路径

	//流程文件静态的流程
	private static ConcurrentMap<String,List<ProcessProgress>> staticProcessConcurrentMap = new ConcurrentHashMap<String,List<ProcessProgress>>();

	//流程文件中静态的相关属性
	private static ConcurrentMap<String,List<WorkflowProcessStaticInfo>> workflowProcessStaticInfoConcurrentMap = new ConcurrentHashMap<String,List<WorkflowProcessStaticInfo>>();



	@PostConstruct
	private  void parseWorkflowStaicProcess() {
		try {

			if(StringUtils.isEmpty(workflowProcessLocation)){
				logger.error("xhg.activiti.process-definitioin-path 为空！");
				return;
			}

			String path = ActivitiUtil.getWorkflowProcessPath(workflowProcessLocation);
			//logger.info("\n===========path:"+path);


			File myFile = new File(path);
			String[] extensions = new String[]{"txt"};
			Collection<File> fileCollection =  FileUtils.listFiles(myFile,extensions,true);
			if(CollectionUtils.isEmpty(fileCollection)){
				return ;
			}

			for(File item:fileCollection){
				String fileName = item.getName();
				int index = fileName.lastIndexOf(".");
				fileName = fileName.substring(0,index);
				List<String> fileContents =   FileUtils.readLines(new File(item.getAbsolutePath()),"utf-8");
				//logger.info("fileContents:"+fileContents);

				if(CollectionUtils.isNotEmpty(fileContents)){
					List<ProcessProgress> ppList = new ArrayList<>();
					for(String line :fileContents ){
						String[] strArr = line.split(",");
						ProcessProgress processProgress = new ProcessProgress();
						processProgress.setActId(strArr[0]);
						processProgress.setActName(strArr[1]);
						ppList.add(processProgress);
					}
					//list转map
					staticProcessConcurrentMap.put(fileName,ppList);
				}

				//logger.info("\n\n===staticProcessConcurrentMap:"+staticProcessConcurrentMap);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}




	@Override
	public List<AuditRecord> queryAuditRecord(WorkflowQueryReq workflowQueryReq) {
		logger.info("\n---------queryAuditRecord:{}", workflowQueryReq);

		List<AuditRecord> resultList = new ArrayList<AuditRecord>();

		List<HistoricProcessInstance> hisProInstanceList = historyService.createHistoricProcessInstanceQuery()
				.processInstanceId(workflowQueryReq.getProcessInstanceId())
				// .startedBy(workflowQueryReq.getUserId())
				// .finished()
				.orderByProcessInstanceEndTime().desc().list();

		logger.info("\n---------hisProInstanceList:{}", hisProInstanceList);

		for (HistoricProcessInstance hisInstance : hisProInstanceList) {
			logger.info("\n---------hisInstance:{}", hisInstance);

			AuditRecord auditRecord = new AuditRecord();
			auditRecord.setProcessInstanceId(workflowQueryReq.getProcessInstanceId());
			auditRecord.setApplyUser(hisInstance.getStartUserId());
			auditRecord.setApplyTime(hisInstance.getStartTime());

			// select RES.* from ACT_HI_VARINST RES WHERE RES.PROC_INST_ID_ = ?
			// order by RES.ID_ asc

			List<HistoricVariableInstance> varInstanceList = historyService.createHistoricVariableInstanceQuery()
					.processInstanceId(hisInstance.getId()).list();

			auditRecord.setVarInstanceList(varInstanceList);

			if (!CollectionUtils.isEmpty(varInstanceList)) {
				for (HistoricVariableInstance var : varInstanceList) {
					logger.info("\n---------var:{}", var);
				}

			}

			resultList.add(auditRecord);

		}

		return resultList;

	}

	@Override
	public PageBean<Task> queryProcessUndo(WorkflowQueryReq workflowQueryReq) {
		logger.info("\n---------queryProcessUndo workflowQueryReq:" + workflowQueryReq);
		/*
		 * List<Task> taskList = taskService.createTaskQuery()
		 * //.taskCandidateUser(workflowQueryReq.getUserId())
		 * .taskCandidateOrAssigned(workflowQueryReq.getUserId())
		 * .orderByTaskCreateTime().desc().list();
		 * 
		 */

		/**
		 *
		 * SELECT DISTINCT RES.* FROM ACT_RU_TASK RES LEFT JOIN
		 * ACT_RU_IDENTITYLINK I ON I.TASK_ID_ = RES.ID_ WHERE (RES.ASSIGNEE_ =
		 * 11 OR (RES.ASSIGNEE_ IS NULL AND I.TYPE_ = 'candidate' AND
		 * (I.USER_ID_ = 11 OR I.GROUP_ID_ IN ( 'manager' ) ))) ORDER BY
		 * RES.CREATE_TIME_ DESC LIMIT 2147483647 OFFSET 0
		 */

		/**
		 * List<Task> taskList = taskService.createTaskQuery()
		 * .taskCandidateOrAssigned(workflowQueryReq.getUserId()) //指定自己处理
		 * .taskCandidateGroupIn(workflowQueryReq.getRoleCodeList())
		 * //自己的角色在候选组里面 .orderByTaskCreateTime().desc().list();
		 * 
		 * 
		 * logger.info("\n---------queryProcessUndo:"+taskList);
		 * 
		 * if(!CollectionUtils.isEmpty(taskList)){
		 * 
		 * for (Task task : taskList) {
		 * 
		 * String instanceId = task.getProcessInstanceId(); //ProcessInstance
		 * instance =
		 * runtimeService.createProcessInstanceQuery().processInstanceId(instanceId).singleResult();
		 * 
		 * Map<String, Object> taskMap =
		 * runtimeService.getVariables(instanceId);
		 * 
		 * logger.info("\n---------taskMap:{}",taskMap);
		 * 
		 * }
		 * 
		 * }
		 * 
		 **/

		TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned(workflowQueryReq.getUserId()) // 指定自己处理
				.taskCandidateGroupIn(workflowQueryReq.getRoleCodeList());

		if (!StringUtils.isEmpty(workflowQueryReq.getBusinessKey())) {
			taskQuery = taskQuery.processInstanceBusinessKey(workflowQueryReq.getBusinessKey());
		}
		if (!StringUtils.isEmpty(workflowQueryReq.getProcessDefineKey())) {
			taskQuery = taskQuery.processDefinitionKey(workflowQueryReq.getProcessDefineKey());
		}
		if (!StringUtils.isEmpty(workflowQueryReq.getTaskDoc())) {
			taskQuery = taskQuery.taskDescriptionLike(workflowQueryReq.getTaskDoc());
		}

		long count = taskQuery.count();

		logger.info("\n count:{}", count);

		List<Task> taskList = new ArrayList<>();
		if (count > 0) {
			taskList = taskQuery.orderByTaskCreateTime().desc().listPage(workflowQueryReq.getStartIndex(),
					workflowQueryReq.getPageSize());

			logger.info("\n taskList:{}", taskList);
		}

		PageBean<Task> pageBean = new PageBean<Task>(count, taskList);
		return pageBean;
	}

	@Override
	public PageBean<Task> queryProcessUndoByCandidateGroup(WorkflowQueryReq workflowQueryReq) {
		logger.info("\n---------queryProcessUndoByCandidateGroup workflowQueryReq:" + workflowQueryReq);

		/**
		 * SELECT DISTINCT RES.* FROM ACT_RU_TASK RES INNER JOIN
		 * ACT_RU_IDENTITYLINK I ON I.TASK_ID_ = RES.ID_ WHERE RES.ASSIGNEE_ IS
		 * NULL AND I.TYPE_ = 'candidate' AND ( I.GROUP_ID_ IN ('manager' ) )
		 * ORDER BY RES.ID_ ASC LIMIT 2147483647 OFFSET 0
		 * 
		 */
		// 根据用户组查询任务
		TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateGroupIn(workflowQueryReq.getRoleCodeList());

		if (!StringUtils.isEmpty(workflowQueryReq.getBusinessKey())) {
			taskQuery = taskQuery.processInstanceBusinessKey(workflowQueryReq.getBusinessKey());
		}
		if (!StringUtils.isEmpty(workflowQueryReq.getProcessDefineKey())) {
			taskQuery = taskQuery.processDefinitionKey(workflowQueryReq.getProcessDefineKey());
		}
		if (!StringUtils.isEmpty(workflowQueryReq.getTaskDoc())) {
			taskQuery = taskQuery.taskDescriptionLike(workflowQueryReq.getTaskDoc());
		}

		long count = taskQuery.count();

		logger.info("\n count:{}", count);

		List<Task> taskList = new ArrayList<>();
		if (count > 0) {
			taskList = taskQuery.orderByTaskCreateTime().desc().listPage(workflowQueryReq.getStartIndex(),
					workflowQueryReq.getPageSize());

			logger.info("\n taskList:{}", taskList);
		}

		PageBean<Task> pageBean = new PageBean<Task>(count, taskList);
		return pageBean;
	}

	@Override
	public PageBean<Task> queryProcessUndoByCandidateUser(WorkflowQueryReq workflowQueryReq) {
		logger.info("\n---------queryProcessUndoByCandidateUser workflowQueryReq:" + workflowQueryReq);
		// 根据用户组查询任务
		TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned(workflowQueryReq.getUserId()); // 指定自己处理

		if (!StringUtils.isEmpty(workflowQueryReq.getBusinessKey())) {
			taskQuery = taskQuery.processInstanceBusinessKey(workflowQueryReq.getBusinessKey());
		}
		if (!StringUtils.isEmpty(workflowQueryReq.getProcessDefineKey())) {
			taskQuery = taskQuery.processDefinitionKey(workflowQueryReq.getProcessDefineKey());
		}
		if (!StringUtils.isEmpty(workflowQueryReq.getTaskDoc())) {
			taskQuery = taskQuery.taskDescriptionLike(workflowQueryReq.getTaskDoc());
		}


		long count = taskQuery.count();

		logger.info("\n count:{}", count);

		List<Task> taskList = new ArrayList<>();

		if (count > 0) {
			taskList = taskQuery.orderByTaskCreateTime().desc().listPage(workflowQueryReq.getStartIndex(),
					workflowQueryReq.getPageSize());

			logger.info("\n taskList:{}", taskList);
		}

		PageBean<Task> pageBean = new PageBean<Task>(count, taskList);
		pageBean.setPageNo(workflowQueryReq.getPageNo());
		pageBean.setPageSize(workflowQueryReq.getPageSize());
		return pageBean;

	}

	@Override
	public PageBean<WorkflowQueryResp> queryProcessUndoByCandidateUserIncludeBK(WorkflowQueryReq workflowQueryReq) {
		List<WorkflowQueryResp> list = new ArrayList<>();
		long count = this.workflowCustomQueryDao.countProcessUndoByCandidateUserIncludeBK(workflowQueryReq);
		if(count>0){
			list = this.workflowCustomQueryDao.listProcessUndoByCandidateUserIncludeBK(workflowQueryReq);
		}

		PageBean<WorkflowQueryResp> pageBean = new PageBean<WorkflowQueryResp>(count, list);
		pageBean.setPageNo(workflowQueryReq.getPageNo());
		pageBean.setPageSize(workflowQueryReq.getPageSize());
		return pageBean;

	}

	@Override
	public PageBean<HistoricProcessInstance> queryProcessing(WorkflowQueryReq workflowQueryReq) {

		HistoricProcessInstanceQuery hpiq = historyService.createHistoricProcessInstanceQuery()
				.processDefinitionKey(workflowQueryReq.getProcessDefineKey()).orderByProcessInstanceStartTime().desc()
				.involvedUser(workflowQueryReq.getUserId()).unfinished(); // 此时使用unfinished

		long count = hpiq.count();

		List<HistoricProcessInstance> list = new ArrayList<>();

		if (count > 0) {
			list = hpiq.listPage(workflowQueryReq.getStartIndex(), workflowQueryReq.getPageSize());

		}

		PageBean<HistoricProcessInstance> pageBean = new PageBean<HistoricProcessInstance>(count, list);
		pageBean.setPageNo(workflowQueryReq.getPageNo());
		pageBean.setPageSize(workflowQueryReq.getPageSize());
		return pageBean;
	}

	@Override
	public PageBean<WorkflowQueryResp> queryProcessingByDB(WorkflowQueryReq workflowQueryReq) {
		Long count = this.workflowBusinessDao.countWorkOrderProcessing(workflowQueryReq);
		List<WorkflowQueryResp> list = new ArrayList<>();
		if (count > 0) {
			list = this.workflowBusinessDao.listWorkOrderProcessing(workflowQueryReq);

		}

		PageBean<WorkflowQueryResp> pageBean = new PageBean<>(count, list);
		pageBean.setPageNo(workflowQueryReq.getPageNo());
		pageBean.setPageSize(workflowQueryReq.getPageSize());
		return pageBean;
	}

	@Override
	public PageBean<HistoricProcessInstance> queryProcessed(WorkflowQueryReq workflowQueryReq) {
		logger.info("\n queryProcessed workflowQueryReq:{}", workflowQueryReq);

		HistoricProcessInstanceQuery hpiq = historyService.createHistoricProcessInstanceQuery()
				.involvedUser(workflowQueryReq.getUserId()).finished(); // 此时使用
																		// finished

		if (!StringUtils.isEmpty(workflowQueryReq.getProcessDefineKey())) {
			hpiq = hpiq.processDefinitionKey(workflowQueryReq.getProcessDefineKey());
		}

		long count = hpiq.count();

		List<HistoricProcessInstance> list = new ArrayList<>();

		if (count > 0) {
			list = hpiq.orderByProcessInstanceEndTime().desc().listPage(workflowQueryReq.getStartIndex(),
					workflowQueryReq.getPageSize());
		}

		PageBean<HistoricProcessInstance> pageBean = new PageBean<HistoricProcessInstance>(count, list);
		pageBean.setPageNo(workflowQueryReq.getPageNo());
		pageBean.setPageSize(workflowQueryReq.getPageSize());
		return pageBean;

	}

	@Override
	public Long statProcessUndo(WorkflowQueryReq workflowQueryReq) {

		TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned(workflowQueryReq.getUserId()) // 指定自己处理
				;

		if (CollectionUtils.isNotEmpty(workflowQueryReq.getRoleCodeList())) {
			taskQuery = taskQuery.taskCandidateGroupIn(workflowQueryReq.getRoleCodeList());
		}
		if (!StringUtils.isEmpty(workflowQueryReq.getBusinessKey())) {
			taskQuery = taskQuery.processInstanceBusinessKey(workflowQueryReq.getBusinessKey());
		}

		if (!StringUtils.isEmpty(workflowQueryReq.getTaskDoc())) {
			taskQuery = taskQuery.taskDescriptionLike(workflowQueryReq.getTaskDoc());
		}

		long count = taskQuery.count();

		return count;
	}

	@Override
	public Long statProcessing(WorkflowQueryReq workflowQueryReq) {

		logger.info("\n statProcessing workflowQueryReq:{}", workflowQueryReq);
		HistoricProcessInstanceQuery hpiq = historyService.createHistoricProcessInstanceQuery()
				.processDefinitionKey(workflowQueryReq.getProcessDefineKey()).involvedUser(workflowQueryReq.getUserId())
				.unfinished(); // 此时使用unfinished
		if (!StringUtils.isEmpty(workflowQueryReq.getBusinessKey())) {
			hpiq = hpiq.processInstanceBusinessKey(workflowQueryReq.getBusinessKey());
		}

		long count = hpiq.count();
		return count;
	}

	@Override
	public Long statProcessed(WorkflowQueryReq workflowQueryReq) {
		logger.info("\n statProcessed workflowQueryReq:{}", workflowQueryReq);
		HistoricProcessInstanceQuery hpiq = historyService.createHistoricProcessInstanceQuery()
				.processDefinitionKey(workflowQueryReq.getProcessDefineKey()).involvedUser(workflowQueryReq.getUserId())
				.finished();

		if (!StringUtils.isEmpty(workflowQueryReq.getBusinessKey())) {
			hpiq = hpiq.processInstanceBusinessKey(workflowQueryReq.getBusinessKey());
		}

		long count = hpiq.count();

		return count;

	}

	@Override
	public ProcessStatusEnum queryProcessStatus(String processInstanceId) {
		ProcessInstance pi = runtimeService // 获取运行时Service
				.createProcessInstanceQuery() // 创建流程实例查询
				.processInstanceId(processInstanceId) // 用流程实例id查询
				.singleResult();
		if (pi != null) {
			return ProcessStatusEnum.PROCESS_STATUS_DOING;
		}

		HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();

		if (null != historicProcessInstance) {
			Date endTime = historicProcessInstance.getEndTime();
			if (null != endTime) { // 如果endTime不为空，说明流程执行结束
				return ProcessStatusEnum.PROCESS_STATUS_DONE;
			}
		} else {
			// 从正在执行和历史表中都找不到，只能够说明流程实例不存在!
			return ProcessStatusEnum.PROCESS_STATUS_NOEXISTS;
		}

		return ProcessStatusEnum.PROCESS_STATUS_NOEXISTS;
	}

	/**
	 * 获得任务中的办理候选人
	 * 
	 * @param workflowQueryReq
	 * @return
	 */
	@Override
	public Set<User> getTaskCandidate(WorkflowQueryReq workflowQueryReq) {

		Task task = taskService.createTaskQuery().taskId(workflowQueryReq.getTaskId()).singleResult();
		if (null == task) {
			throw new BusinessException("任务节点" + workflowQueryReq.getTaskId() + "查询流程任务失败，查无该任务或已被处理");
		}
		Set<User> users = new HashSet<User>();
		List<IdentityLink> identityLinkList = taskService.getIdentityLinksForTask(workflowQueryReq.getTaskId());
		identityLinkList.forEach(identityLink -> {
			if (identityLink.getUserId() != null) {
				User user = getUser(identityLink.getUserId());
				if (user != null)
					users.add(user);
			}
			if (identityLink.getGroupId() != null) {
				// 根据组获得对应人员
				List<User> userList = identityService.createUserQuery().memberOfGroup(identityLink.getGroupId()).list();
				if (userList != null && userList.size() > 0) {
					users.addAll(userList);
				}

			}
		});
		return users;
	}

	private User getUser(String userId) {
		User user = (User) identityService.createUserQuery().userId(userId).singleResult();
		return user;
	}

	/**
	 * 获取流程实例的最新节点(待处理节点)
	 * 
	 * @param workflowQueryReq
	 * @return
	 */
	@Override
	public Task getCurrentTaskByProcessInstanceId(WorkflowQueryReq workflowQueryReq) {
		Task currentTask = taskService.createTaskQuery().processDefinitionKey(workflowQueryReq.getProcessDefineKey())
				.processInstanceId(workflowQueryReq.getProcessInstanceId()).singleResult();
		return currentTask;
	}

	/**
	 * 查询指定人指定任务task详情（判定某人是否有处理任务的权限 ）
	 */
	@Override
	public Boolean queryProcessUndoContainTaskId(WorkflowQueryReq workflowQueryReq) {
		List<Task> taskList = taskService.createTaskQuery().processDefinitionKey(workflowQueryReq.getProcessDefineKey())
				.taskCandidateUser(workflowQueryReq.getUserId()).taskId(workflowQueryReq.getTaskId()).list();
		if (CollectionUtils.isNotEmpty(taskList)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<ProcessProgress> queryProcessProgress(WorkflowQueryReq workflowQueryReq) {
		List<ProcessProgress> resultList = new ArrayList<>();

		String processInstanceId = workflowQueryReq.getProcessInstanceId();

		//查询某一次流程的执行一共经历了多少个活动
		List<HistoricActivityInstance> historicActivityInstanceList = historyService
				.createHistoricActivityInstanceQuery()
				.processInstanceId(processInstanceId)
				.list();

		if(null == historicActivityInstanceList || historicActivityInstanceList.size()==0){
			return resultList;
		}

		this.historicActivityInstance2ProcessProgress(historicActivityInstanceList,resultList);

		List<HistoricVariableInstance> varlist = historyService
				.createHistoricVariableInstanceQuery()
				.processInstanceId(processInstanceId)
				.list();

		this.variableInstance2ProcessProgress(varlist,resultList);

		//审核意见
		List<Comment> commentList = taskService.getProcessInstanceComments(processInstanceId);

		this.comment2ProcessProgress(commentList,resultList);

		return resultList;
	}

	@Override
	public List<ProcessProgress> queryProcessProgressMinInfo(WorkflowQueryReq workflowQueryReq) {
		List<ProcessProgress> resultList = new ArrayList<>();

		String processInstanceId = workflowQueryReq.getProcessInstanceId();

		//查询某一次流程的执行一共经历了多少个活动
		List<HistoricActivityInstance> historicActivityInstanceList = historyService
				.createHistoricActivityInstanceQuery()
				.processInstanceId(processInstanceId)
				.orderByHistoricActivityInstanceStartTime()
				.asc()
				.list();

		if(null == historicActivityInstanceList || historicActivityInstanceList.size()==0){
			return resultList;
		}
		this.historicActivityInstance2ProcessProgress(historicActivityInstanceList,resultList);

		resultList = resultList.stream().filter(
			item ->
					(  item.getActType().equalsIgnoreCase("startEvent")
					 ||  item.getActType().equalsIgnoreCase("endEvent")
					 ||  item.getActType().equalsIgnoreCase("userTask")
					)
		).collect(Collectors.toList());

		return resultList;

	}

	@Override
	public List<ProcessProgress> queryProcessProgressStatic(WorkflowQueryReq workflowQueryReq) {
		workflowQueryReq.getProcessDefineKey();
		String processDefineKey = workflowQueryReq.getProcessDefineKey();
		if( !staticProcessConcurrentMap.containsKey(processDefineKey)){
			this.parseWorkflowStaicProcess();
		}
		List<ProcessProgress> list = staticProcessConcurrentMap.get(processDefineKey);

		return list;
	}

	@Override
	public List<WorkflowProcessStaticInfo> queryWorkflowProcessStaticInfo(WorkflowQueryReq workflowQueryReq) {
		List<WorkflowProcessStaticInfo> list = new ArrayList<>();
		/**
		ProcessInstance instance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(workflowQueryReq.getProcessInstanceId())
				.processDefinitionKey(workflowQueryReq.getProcessDefineKey())
				.singleResult();

		 //logger.info("instance:"+instance);

		 **/

		//从历史表中查询，因为当流程结束时，从runtimeService查询不了数据
		HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
				.processInstanceId(workflowQueryReq.getProcessInstanceId())
				.processDefinitionKey(workflowQueryReq.getProcessDefineKey())
				.singleResult();

		logger.debug("historicProcessInstance:"+historicProcessInstance);
		logger.debug("historicProcessInstance getProcessDefinitionId:"+historicProcessInstance.getProcessDefinitionId());



		String pdefId = historicProcessInstance.getProcessDefinitionId();

		if( !this.workflowProcessStaticInfoConcurrentMap.containsKey(pdefId)){
			this.pareseWorkflowProcessStaticInfo(pdefId);
		}
		list = this.workflowProcessStaticInfoConcurrentMap.get(pdefId);

		return list;
	}

	@Override
	public WorkflowProcessStaticInfo getCurrentTaskStaticInfo(WorkflowQueryReq workflowQueryReq) {
		WorkflowProcessStaticInfo workflowProcessStaticInfo = new WorkflowProcessStaticInfo();
		Task currentTask = taskService.createTaskQuery().processInstanceId(workflowQueryReq.getProcessInstanceId()).singleResult();
		if(null == currentTask){
			return  null;
		}

		String pdefId = currentTask.getProcessDefinitionId();

		if( !this.workflowProcessStaticInfoConcurrentMap.containsKey(pdefId)){
			this.pareseWorkflowProcessStaticInfo(pdefId);
		}
		List<WorkflowProcessStaticInfo> list = this.workflowProcessStaticInfoConcurrentMap.get(pdefId);

		String taskDefKey = currentTask.getTaskDefinitionKey();

		workflowProcessStaticInfo.setActName(currentTask.getName());

		if(CollectionUtils.isNotEmpty(list)){
			for(WorkflowProcessStaticInfo item:list){
				if(item.getActId().equals(taskDefKey)){
					workflowProcessStaticInfo.setActId(taskDefKey);
					workflowProcessStaticInfo.setCandidateGroups(item.getCandidateGroups());
					workflowProcessStaticInfo.setCandidateUsers(item.getCandidateUsers());
					workflowProcessStaticInfo.setDocument(item.getDocument());
					workflowProcessStaticInfo.setAssignee(item.getAssignee());
					break;
				}
			}
		}

		return workflowProcessStaticInfo;
	}

	@Override
	public PageBean<WorkflowQueryResp> queryProcessedForUser(WorkflowQueryReq workflowQueryReq) {
		long count = this.workflowCustomQueryDao.countProcessedForUser(workflowQueryReq);
		List<WorkflowQueryResp> list = new ArrayList<>();
		if(count >0){
			list = this.workflowCustomQueryDao.queryProcessedForUser(workflowQueryReq);
		}
		PageBean<WorkflowQueryResp> pageBean = new PageBean<>(count, list);
		pageBean.setPageNo(workflowQueryReq.getPageNo());
		pageBean.setPageSize(workflowQueryReq.getPageSize());
		return pageBean;
	}

	/**
	 * historicActivityInstance 转换 为  processProgress
	 * @param historicActivityInstanceList
	 * @param processProgressList
	 */
	private void historicActivityInstance2ProcessProgress(List<HistoricActivityInstance> historicActivityInstanceList,List<ProcessProgress> processProgressList){
		if(CollectionUtils.isNotEmpty(historicActivityInstanceList)){
			for(HistoricActivityInstance hai : historicActivityInstanceList){
				ProcessProgress processProgress = new ProcessProgress();

				processProgress.setProcessInstanceId(hai.getProcessInstanceId());
				processProgress.setActId(hai.getActivityId());
				processProgress.setActName(hai.getActivityName());
				processProgress.setActType(hai.getActivityType());
				processProgress.setAssIgnee(hai.getAssignee());
				processProgress.setTaskId(hai.getTaskId());
				processProgress.setOperateTime(hai.getEndTime());

				//通过这2个字段 判断任务的情况
				//endTime is null :任务未结束
				// 如果 (endTime is null) && (assignee is null ) 任务没有被领取

				//String assignee = hai.getAssignee();
				//Date endTime = hai.getEndTime();


				processProgressList.add(processProgress);
			}
		}
	}

	/**
	 * 流程实例中的参数 放到对应的 task任务中
	 * @param varlist
	 * @param processProgressList
	 */
	private void variableInstance2ProcessProgress(List<HistoricVariableInstance> varlist,List<ProcessProgress> processProgressList){
		logger.info("\n---variableInstance2ProcessProgress varlist:"+varlist);
		if( CollectionUtils.isEmpty(varlist)) return;

		for(ProcessProgress processProgress : processProgressList){
			Map<String,Object> taskParamLocal = new HashMap<>();
			if( !StringUtils.isEmpty(processProgress.getTaskId())){
				for(HistoricVariableInstance varItem:varlist){
					if( !StringUtils.isEmpty(varItem.getTaskId()) && varItem.getTaskId().equals(processProgress.getTaskId())){
						taskParamLocal.put(varItem.getVariableName(),varItem.getValue());
					}
				}
			}
			processProgress.setTaskParamLocal(taskParamLocal);
		}

		Map<String,Object> taskParam = new HashMap<>();
		for(HistoricVariableInstance varItem:varlist){
			if( StringUtils.isEmpty(varItem.getTaskId()) ){
				taskParam.put(varItem.getVariableName(),varItem.getValue());
			}
		}

		//全局变量放在第一条记录中
		if(!CollectionUtils.isEmpty(processProgressList) && null != taskParam && taskParam.size()>0){
			processProgressList.get(0).setTaskParam(taskParam);

		}

	}

	/**
	 * 审核意见  放到对应的 task任务中
	 * @param commentList
	 * @param processProgressList
	 */
	private void comment2ProcessProgress(List<Comment> commentList,List<ProcessProgress> processProgressList){
		if( CollectionUtils.isEmpty(commentList)) return;

		for(ProcessProgress processProgress : processProgressList){
			if( !StringUtils.isEmpty(processProgress.getTaskId())){
				for(Comment comment:commentList){
					if( !StringUtils.isEmpty(comment.getTaskId()) && comment.getTaskId().equals(processProgress.getTaskId())){
						processProgress.setComment(comment.getFullMessage());
					}
				}
			}
		}
	}

	/**
	 * 进行中的工单(查询数据库)
	 */
	@Override
	public Long statProcessingByDB(WorkflowQueryReq workflowQueryReq) {
		return workflowBusinessDao.countWorkOrderProcessing(workflowQueryReq).longValue();
	}


	/**
	 * 根据流程定义id解析出WorkflowProcessStaticInfo对象
	 * @param processDefinitionId
	 */
	private void pareseWorkflowProcessStaticInfo(String processDefinitionId){
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);

		List<org.activiti.bpmn.model.Process> processList = bpmnModel.getProcesses();

		List<WorkflowProcessStaticInfo> list = new ArrayList<>();

		if(processList != null && processList.size() > 0) {
			for (org.activiti.bpmn.model.Process process : processList) {
				if (process != null) {
					Collection<FlowElement> flowElementCollection = process.getFlowElements();
					if (flowElementCollection != null && flowElementCollection.size() > 0) {
						for (FlowElement flowElement : flowElementCollection) {
							if (flowElement instanceof UserTask) {
								WorkflowProcessStaticInfo workflowProcessStaticInfo = new WorkflowProcessStaticInfo();
								UserTask userTask = (UserTask) flowElement;
								workflowProcessStaticInfo.setActId(userTask.getId());
								workflowProcessStaticInfo.setActName(userTask.getName());
								workflowProcessStaticInfo.setAssignee(userTask.getAssignee());
								workflowProcessStaticInfo.setDocument(userTask.getDocumentation());
								workflowProcessStaticInfo.setCandidateUsers(userTask.getCandidateUsers());
								workflowProcessStaticInfo.setCandidateGroups(userTask.getCandidateGroups());

								list.add(workflowProcessStaticInfo);
							}
						}
					}
				}
			}
		} //if

		workflowProcessStaticInfoConcurrentMap.put(processDefinitionId,list);

	}

}
