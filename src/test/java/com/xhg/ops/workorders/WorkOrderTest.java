package com.xhg.ops.workorders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import com.xhg.core.util.crypto.MD5Utils;
import com.xhg.ops.system.service.OpsSystemMessagePushService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xhg.core.web.vo.PagerResult;
import com.xhg.ops.OpsApplication;
import com.xhg.ops.workorders.dto.WorkOrderFindResultDto;
import com.xhg.ops.workorders.dto.WorkOrderUserDTO;
import com.xhg.ops.workorders.enums.WorkOrderDataSource;
import com.xhg.ops.workorders.enums.WorkOrderStatusCondition;
import com.xhg.ops.workorders.enums.WorkOrderType;
import com.xhg.ops.workorders.model.WorkOrder;
import com.xhg.ops.workorders.model.WorkOrderLog;
import com.xhg.ops.workorders.model.WorkOrderMessage;
import com.xhg.ops.workorders.model.WorkOrderResult;
import com.xhg.ops.workorders.service.WorkOrderFlowService;
import com.xhg.ops.workorders.service.WorkOrderLogService;
import com.xhg.ops.workorders.service.WorkOrderMessageService;
import com.xhg.ops.workorders.service.WorkOrderService;

import java.util.Map;
import java.util.HashMap;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OpsApplication.class)
public class WorkOrderTest {

//	@Autowired
//	private WorkOrderFlowService workOrderFlowService;
//
//	@Autowired
//	private WorkOrderService workOrderService;
//
//	@Autowired
//	private WorkOrderLogService workOrderLogService;
//
//	@Autowired
//	private WorkOrderMessageService workOrderMessageService;
//
//	 @Test
//	public void createOrder() {
//		for(int i = 510;i < 511;i++) {
//			WorkOrderUserDTO user = new WorkOrderUserDTO();
//			user.setRoleId(30);
//			user.setRoleName("城市主管");
//			user.setUserId(24);
//			user.setUserName("黄金龙");
//			WorkOrder order = new WorkOrder();
//			order.setOrderType(WorkOrderType.BREAKDOWN_REPAIR.getCode());
//			order.setOrderTitle("测试工单龙哥专用" + i);
//			order.setDataSource(WorkOrderDataSource.MARKET_FEEDBACK.getCode());
//			order.setLevel(0);
//			order.setContactInfo("15818743775");
//			order.setDeviceId("123");
//			order.setSiteCode("057100001");
//			order.setSiteAreaCode("330106000000");
//			order.setSiteLatitude("22.551777886284722");
//			order.setSiteLongitude("113.95542046440973");
//			order.setSiteAddress("北山街道曙光路118号，上街区，21栋");
//			order.setAttachments(Arrays.asList("28", "33", "44"));
//			int order_id = workOrderFlowService.createWorkOrder(order, user);
//			System.out.println("create Order id : " + order_id);
//		}
//	}
//
////	@Test
//	public void doProcessOrder() {
//		WorkOrderUserDTO user = new WorkOrderUserDTO();
//		user.setRoleId(1);
//		user.setRoleName("系统管理员");
//		user.setUserId(1);
//		user.setUserName("张三");
//		WorkOrder order = new WorkOrder();
//		order.setOrderType(WorkOrderType.BREAKDOWN_REPAIR.getCode());
//		order.setOrderTitle("流程测试");
//		order.setDataSource(WorkOrderDataSource.MARKET_FEEDBACK.getCode());
//		order.setLevel(0);
//		order.setDeviceId("sss");
//		order.setSiteCode("sss");
//		order.setSiteAreaCode("ssssss");
//		order.setSiteLatitude("100");
//		order.setSiteLongitude("100");
//		order.setSiteAddress("ssss");
//		order.setAttachments(Arrays.asList("11", "33", "44"));
//		int order_id = workOrderFlowService.createWorkOrder(order, user);
//		System.out.println("create Order id : " + order_id);
//		final int orderId = order.getId();
//		workOrderFlowService.auditWorkOderOrder(orderId, 1, 19, user);
//		workOrderFlowService.assignWorkOrder(orderId, 19, user);
//		workOrderFlowService.agreeWorkOderOrder(orderId, user);
//		workOrderFlowService.startWorkOrder(orderId, user);
//		workOrderFlowService.signWorkOrder(orderId,100,100, user);
//		WorkOrderResult result = new WorkOrderResult();
//		result.setOrderType(1);
//		result.setFaultModule("");
//		result.setFaultType(1);
//		result.setChangeParts("");
//		result.setSolution("解决办法");
//		result.setReason("原因分析");
//		result.setPhenomena("实际现象");
//		result.setRemark("备注");
//
//		workOrderFlowService.processWorkOrder(orderId, result , user);
//		workOrderFlowService.confirmWorkOrder(orderId, user);
//		workOrderFlowService.reviewWorkOrder(orderId, user);
//	}
//
//	// 处理工单流程
////	@Test
//	public void processOrder() {
//		WorkOrderUserDTO user = new WorkOrderUserDTO();
//		user.setRoleId(1);
//		user.setRoleName("系统管理员");
//		user.setUserId(1);
//		user.setUserName("张三");
//		workOrderFlowService.agreeWorkOderOrder(44, user);
//	}
//
//	// 待处理工单
////	@Test
//	public void findUnProcessWorkOrderListPage() {
//		WorkOrderUserDTO user = new WorkOrderUserDTO();
//		user.setRoleId(31);
//		user.setRoleName("城市运维经理");
//		user.setUserId(25);
//		user.setUserName("唐亚东");
//		PagerResult<WorkOrderFindResultDto> pager = workOrderFlowService.findUnprocessWorkOrderListPage(1, 20, WorkOrderStatusCondition.SUSPENDED, user);
//		System.out.println(pager.getTotal());
//	}
//
////	@Test
//	public void queryWorkOrderDetail() {
//		WorkOrder order = workOrderService.queryWorkOrderDetail(10);
//		List<WorkOrderLog> workOrderLogs = workOrderLogService.queryWorkOrderLogList(10);
//		System.out.println(order);
//		System.out.println(workOrderLogs.size());
//	}
//
////	@Test
//	public void sendWorkOrderMessage() {
//		WorkOrderUserDTO user = new WorkOrderUserDTO();
//		user.setRoleId(1);
//		user.setRoleName("系统管理员");
//		user.setUserId(1);
//		user.setUserName("张三");
//		workOrderMessageService.sendSiteMessage(1, "XHG20180303", "测试内容", new HashSet<>(Arrays.asList(1, 3)), user);
//	}
//
////	@Test
//	public void pushWorkOrderMessage() {
//		WorkOrderUserDTO user = new WorkOrderUserDTO();
//		user.setRoleId(1);
//		user.setRoleName("系统管理员");
//		user.setUserId(1);
//		user.setUserName("张三");
//		workOrderMessageService.pushMessage(1, "XHG20180303", "测试123", new HashSet<>(Arrays.asList(10)), user);
//	}
//
////	@Test
//	public void updateMessageRead() {
//		WorkOrderUserDTO user = new WorkOrderUserDTO();
//		user.setRoleId(1);
//		user.setRoleName("系统管理员");
//		user.setUserId(1);
//		user.setUserName("张三");
//		workOrderMessageService.updateMessageRead(10, user);
//	}
//
////	@Test
//	public void findNewestMessageList() {
//		PagerResult<WorkOrderMessage> result = workOrderMessageService.findNewestMessageList(1, 10, 3, WorkOrderMessageService.TYPE_MESSAGE_SITE);
//		System.out.println(result);
//	}
//
//
//	@Autowired
//	private OpsSystemMessagePushService opsSystemMessagePushService;
//
//
//
//	@Test
//	public void testMessagePush() {
//		List<String> alias = new ArrayList<>();
//		alias.add(MD5Utils.md5("13145856300"));
//		Map<String,String> params = new HashMap<String,String>();
//		params.put("url","XHG://native?type=8");
//		int result = opsSystemMessagePushService.sendToAliasWithParams(alias,"生产环境的测试内容3","标题2","C",params);
//		System.out.println("value:"+result);
//	}
	@Autowired
	private OpsSystemMessagePushService opsSystemMessagePushService;

	@Test
	public void test(){
		List<String> alias = new ArrayList<>();
		alias.add("603e5d12fb1c9da59188120082ff75b6");
		opsSystemMessagePushService.sendToAliasWithParams(alias,  "测试。。。。。。。。。。。。。。。",  "测试标题",  "Ops", null);
	}

}
