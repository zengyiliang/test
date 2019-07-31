package com.xhg.ops.workorders;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xhg.core.web.vo.PagerResult;
import com.xhg.ops.OpsApplication;
import com.xhg.ops.workflow.api.ActivitiService;
import com.xhg.ops.workflow.vo.WorkflowProcessInstanceVO;
import com.xhg.ops.workflow.vo.WorkflowTaskVO;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OpsApplication.class)
public class WorkOrderProcessTest {

	@Autowired
	private ActivitiService activitiService;

	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private RuntimeService runtimeService;

	// 启动流程实例
	@Test
	public void startVac() {
		String userId = "doudou";
		String businessKey = UUID.randomUUID().toString().replaceAll("-", "");
		WorkflowProcessInstanceVO workVO = activitiService.createWorkOrder(userId, businessKey);
		System.out.println("启动流程完成，流程实例ID：" + workVO.getProcessInstanceId());
	}

	// 查询待处理的工单列表
	@Test
	public void queryTest() {

		String userId = "hubowei";
		userId = "21";// 城市运维主管
		userId = "23";// 运维专员
		userId = "20";// 总部运维主管
		PagerResult<WorkflowTaskVO> result = activitiService.queryProcessUndo(userId, "ALL", 20, 0);
		System.err.println("total总数："+result.getTotal());
		System.err.println("当前第几页："+result.getPageSize());
		System.err.println("每页展示数量"+result.getCurrentPage());
		List<WorkflowTaskVO> workList = result.getList();
		System.err.println("workListSize:"+workList.size());
		workList.forEach(p -> {
			System.err.println(p);
		});

	}
	// 进行中的工单
	@Test
	public void queryProcessing() {
		String userName = "hubowei";
		String userId = "hubowei";
		userId = "23";// 运维专员
		userId = "21";// 城市运维主管
		userId = "20";// 总部运维主管
		userId = "hubowei";// 总部运维主管
		PagerResult<WorkflowProcessInstanceVO> result = activitiService.queryProcessing(userId, "ALL", 20, 0);
		System.err.println("返回总数："+result.getTotal());
		System.err.println("当前第几页："+result.getCurrentPage());
		System.err.println("每页展示数量"+result.getPageSize());
		List<WorkflowProcessInstanceVO> workList = result.getList();
		workList.forEach(p -> {
			System.err.println(p);
		});
		
	}

	// 查询 已结束 的流程
	@Test
	public void queryProcessed() {
		
		String userName = "hubowei";
		String userId = "hubowei";
		userId = "23";// 运维专员
		userId = "21";// 城市运维主管
		userId = "hubowei";// 总部运维主管
		userId = "20";// 总部运维主管
		PagerResult<WorkflowProcessInstanceVO> result = activitiService.queryProcessed(userId, 20, 0);
		System.err.println("返回总数："+result.getTotal());
		System.err.println("当前第几页："+result.getCurrentPage());
		System.err.println("每页展示数量"+result.getPageSize());
		List<WorkflowProcessInstanceVO> workList = result.getList();
		workList.forEach(p -> {
			System.err.println(p);
		});
//		String userName = "20";
//		List<HistoricProcessInstance> hiinstanceList = historyService.createHistoricProcessInstanceQuery()
//				.processDefinitionKey(ProcessEnum.WORK_ORDER_PROCESS.getProcessKey()).involvedUser(userName).finished().list();
//		for (HistoricProcessInstance hi : hiinstanceList) {
//			System.err.println(hi.getId() + "___" + hi.getBusinessKey()+"__是否结束"+hi.getEndTime());
//		}
	}

	/**
	 * 工单初审，总部运维主管进行审核
	 */
	@Test
	public void auditWorkOderOrderTest() {
		String userId = "hubowei";
		userId = "23";// 运维专员
		userId = "21";// 城市运维主管
		userId = "20";// 总部运维主管
		String nextUserId = "21";
		String taskId = "1227511";
		WorkflowProcessInstanceVO workVO = activitiService.auditWorkOderOrder(taskId, userId, nextUserId);
		System.out.println(workVO.getProcessInstanceId() + "__" + workVO.getTaskId());

	}

	/**
	 * 工单初审，总部运维主管关闭工单
	 */
	@Test
	public void closeWorkOrderTest() {
		String userId = "hubowei";
		userId = "23";// 运维专员
		userId = "21";// 城市运维主管
		userId = "20";// 总部运维主管
		String nextUserId = "21";
		String taskId = "1155019";
		WorkflowProcessInstanceVO workVO = activitiService.closeWorkOrder(taskId, userId, nextUserId);
		System.out.println(workVO.getProcessInstanceId() + "__" + workVO.getStartUserId());

	}

	/**
	 * 工单分配，运维经理分配工单
	 */
	@Test
	public void assignWorkOrderTest() {
		String userId = "hubowei";
		userId = "23";// 运维专员
		userId = "20";// 总部运维主管
		userId = "21";// 城市运维主管
		String nextUserId = "23";
		String taskId = "1235004";
		WorkflowProcessInstanceVO workVO = activitiService.assignWorkOrder(taskId, userId, nextUserId);
		System.out.println(workVO.getProcessInstanceId() + "__" + workVO.getStartUserId());
	}
	/**
	 * 城市运维经理拒绝工单，重新流回上一级进行处理
	 */
	@Test
	public void rejectWorkOrderTest() {
		String userId = "hubowei";
		userId = "23";// 运维专员
		userId = "20";// 总部运维主管
		userId = "21";// 城市运维主管
		String taskId = "1222511";
		WorkflowProcessInstanceVO workVO = activitiService.rejectWorkOrder(taskId, userId, "我是城市运维经理我拒绝处理该工单");
		System.out.println(workVO.getProcessInstanceId() + "__" + workVO.getStartUserId());
	}

	/**
	 * 运维专员进行签单操作
	 */
	@Test
	public void agreeWorkOderOrderTest() {
		String userId = "hubowei";
		userId = "21";// 城市运维主管
		userId = "20";// 总部运维主管
		userId = "23";// 运维专员
		String taskId = "1237504";
		WorkflowProcessInstanceVO workVO = activitiService.agreeWorkOderOrder(taskId, userId);
		System.out.println(workVO.getProcessInstanceId() + "__" + workVO.getStartUserId());

	}
	/**
	 * 运维专员撤单操作
	 */
	@Test
	public void revokeWorkOderOrderTest() {
		String userId = "hubowei";
		userId = "21";// 城市运维主管
		userId = "20";// 总部运维主管
		userId = "23";// 运维专员
		String taskId = "1232504";
		WorkflowProcessInstanceVO workVO = activitiService.revokeWorkOderOrder(taskId, userId,"我是运维专员，我申请撤单");
		System.out.println(workVO.getProcessInstanceId() + "__" + workVO.getStartUserId());
		
	}

	/**
	 * 运维专员进行出发操单操作
	 */
	@Test
	public void startWorkOrderTest() {
		String userId = "hubowei";
		userId = "21";// 城市运维主管
		userId = "20";// 总部运维主管
		userId = "23";// 运维专员
		String taskId = "1240003";
		WorkflowProcessInstanceVO workVO = activitiService.startWorkOrder(taskId, userId);
		System.out.println(workVO.getProcessInstanceId() + "__" + workVO.getStartUserId());

	}
	/**
	 * 运维专员进行转单操作
	 */
	@Test
	public void transferWorkOrderTest() {
		String userId = "hubowei";
		userId = "21";// 城市运维主管
		userId = "20";// 总部运维主管
		userId = "23";// 运维专员
		String taskId = "1217504";
		WorkflowProcessInstanceVO workVO = activitiService.transferWorkOrder(taskId, userId,"不想走流程了，申请转单");
		System.out.println(workVO.getProcessInstanceId() + "__" + workVO.getStartUserId());
		
	}

	/**
	 * 运维专员进行签到操单操作
	 */
	@Test
	public void signWorkOrderTest() {
		String userId = "hubowei";
		userId = "21";// 城市运维主管
		userId = "20";// 总部运维主管
		userId = "23";// 运维专员
		String taskId = "1242503";
		WorkflowProcessInstanceVO workVO = activitiService.signWorkOrder(taskId, userId);
		System.out.println(workVO.getProcessInstanceId() + "__" + workVO.getStartUserId());

	}

	/**
	 * 运维专员处理工单完成
	 */
	@Test
	public void processWorkOrderTest() {
		String userId = "hubowei";
		userId = "21";// 城市运维主管
		userId = "20";// 总部运维主管
		userId = "23";// 运维专员
		String taskId = "1245002";
		WorkflowProcessInstanceVO workVO = activitiService.processWorkOrder(taskId, userId);
		System.out.println(workVO.getProcessInstanceId() + "__" + workVO.getStartUserId());

	}
	/**
	 * 运维专员处理工单申请物料
	 */
	@Test
	public void submitMaterielApplyTest() {
		String userId = "hubowei";
		userId = "21";// 城市运维主管
		userId = "20";// 总部运维主管
		userId = "23";// 运维专员
		String taskId = "1185009";
		WorkflowProcessInstanceVO workVO = activitiService.submitMaterielApply(taskId, userId);
		System.out.println(workVO.getProcessInstanceId() + "__" + workVO.getStartUserId());
		
	}
	/**
	 * 城市运维经理审核物料操作
	 */
	@Test
	public void auditMaterielApplyTest() {
		String userId = "hubowei";
		userId = "20";// 总部运维主管
		userId = "23";// 运维专员
		userId = "21";// 城市运维主管
		String taskId = "1195016";
		WorkflowProcessInstanceVO workVO = activitiService.auditMaterielApply(taskId, userId);
		System.out.println(workVO.getProcessInstanceId() + "__" + workVO.getStartUserId());
		
	}
	/**
	 * 物料审核-物控
	 */
	@Test
	public void checkMaterielTest() {
		String userId = "hubowei";
		userId = "20";// 总部运维主管
		userId = "23";// 运维专员
		userId = "21";// 城市运维主管
		userId = "22";// 物控
		String taskId = "1197502";
		WorkflowProcessInstanceVO workVO = activitiService.checkMateriel(taskId, userId);
		System.out.println(workVO.getProcessInstanceId() + "__" + workVO.getStartUserId());
		
	}
	/**
	 * 物料审核-物控
	 */
	@Test
	public void deliveryMaterielTest() {
		String userId = "hubowei";
		userId = "20";// 总部运维主管
		userId = "23";// 运维专员
		userId = "21";// 城市运维主管
		userId = "22";// 物控
		String taskId = "1205005";
		WorkflowProcessInstanceVO workVO = activitiService.deliveryMateriel(taskId, userId);
		System.out.println(workVO.getProcessInstanceId() + "__" + workVO.getStartUserId());
		
	}
	/**
	 * 物料查收-运维专员
	 */
	@Test
	public void recevieMaterielTest() {
		String userId = "hubowei";
		userId = "20";// 总部运维主管
		userId = "21";// 城市运维主管
		userId = "22";// 物控
		userId = "23";// 运维专员
		String taskId = "1210009";
		WorkflowProcessInstanceVO workVO = activitiService.recevieMateriel(taskId, userId);
		System.out.println(workVO.getProcessInstanceId() + "__" + workVO.getStartUserId());
		
	}

	/**
	 * 工单审核-城市运维经理
	 */
	@Test
	public void confirmWorkOrderTest() {
		String userId = "21";// 城市运维主管
		String taskId = "1247504";
		WorkflowProcessInstanceVO workVO = activitiService.confirmWorkOrder(taskId, userId);
		System.out.println(workVO.getProcessInstanceId() + "__" + workVO.getStartUserId());

	}

	/**
	 * 工单复核-总部运维主管-流程结束
	 */
	@Test
	public void reviewWorkOrderTest() {
		String userId = "20"; // 总部运维主管
		String taskId = "1250002";
		WorkflowProcessInstanceVO workVO = activitiService.reviewWorkOrder(taskId, userId);
		System.out.println(workVO.getProcessInstanceId() + "__" + workVO.getStartUserId());

	}
	
	/**
	 * 获得任务中的办理候选人
	 */
	@Test
	public void getTaskCandidateTest() {
		String taskId = "857513";
		Set<String> userSet = activitiService.getTaskCandidate(taskId);
		System.out.println(userSet);

	}
	
	/**
	 * 获取流程实例的最新节点(待处理节点)
	 */
	@Test
	public void getCurrentTaskByProcessInstanceIdTest() {
		String processInstanceId = "4775011";
		WorkflowTaskVO vo = activitiService.getCurrentTaskByProcessInstanceId(processInstanceId);
		System.out.println("vo:"+vo);
		
	}
	/**
	 * 查询指定人指定任务task详情（判定某人是否有处理任务的权限 ）
	 */
	@Test
	public void queryProcessUndoContainUserIdTest() {
		String taskId="450012";
		String userId="20";
		Boolean flag = activitiService.queryProcessUndoContainUserId(taskId,userId);
		System.out.println("返回值:"+flag);
		
	}
	/**
	 *该用户所有待处理工单
	 */
	@Test
	public void statProcessUndoAllCountTest() {
		String userId="20";
		Map<String,Long> map = activitiService.statProcessUndoAllCount(userId);
		System.out.println("返回值:"+map);
		
	}
	/**
	 * 统计 已结束 的流程
	 */
	@Test
	public void statProcessedCountTest() {
		String userId="20";
		Long count = activitiService.statProcessedCount(userId);
		System.out.println("返回值:"+count);
		
	}
	/**
	 * 统计 进行中的工单
	 */
	@Test
	public void statProcessingCountTest() {
		String userId="hubowei";
		Long count = activitiService.statProcessingCount(userId);
		System.out.println("返回值:"+count);
		
	}

}
