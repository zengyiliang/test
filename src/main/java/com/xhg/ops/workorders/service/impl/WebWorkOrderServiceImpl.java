package com.xhg.ops.workorders.service.impl;

import com.github.pagehelper.PageInfo;
import com.xhg.core.web.vo.PagerResult;
import com.xhg.ops.common.ComConstant;
import com.xhg.ops.enums.WorkFlowTaskEnum;
import com.xhg.ops.system.filter.UserContext;
import com.xhg.ops.system.model.SystemUser;
import com.xhg.ops.workorders.dto.WebWorkOrderPlatformQueryDTO;
import com.xhg.ops.workorders.dto.WebWorkOrderQueryDTO;
import com.xhg.ops.workorders.dto.WorkOrderBaseDTO;
import com.xhg.ops.workorders.dto.WorkOrderFindConditionDto;
import com.xhg.ops.workorders.dto.WorkOrderFindResultDto;
import com.xhg.ops.workorders.dto.WorkOrderUserDTO;
import com.xhg.ops.workorders.enums.WorkOrderDataSource;
import com.xhg.ops.workorders.enums.WorkOrderFailType;
import com.xhg.ops.workorders.enums.WorkOrderLevel;
import com.xhg.ops.workorders.enums.WorkOrderStatus;
import com.xhg.ops.workorders.enums.WorkOrderStatusCondition;
import com.xhg.ops.workorders.enums.WorkOrderStatusMapping;
import com.xhg.ops.workorders.enums.WorkOrderType;
import com.xhg.ops.workorders.model.WorkOrder;
import com.xhg.ops.workorders.model.WorkOrderLog;
import com.xhg.ops.workorders.model.WorkOrderMaterielApply;
import com.xhg.ops.workorders.model.WorkOrderResult;
import com.xhg.ops.workorders.service.WebWorkOrderService;
import com.xhg.ops.workorders.service.WorkOrderCommonService;
import com.xhg.ops.workorders.service.WorkOrderFlowService;
import com.xhg.ops.workorders.service.WorkOrderLogService;
import com.xhg.ops.workorders.service.WorkOrderService;
import com.xhg.ops.workorders.vo.WebWorkOrderDealMaterialVo;
import com.xhg.ops.workorders.vo.WebWorkOrderDealRecordVo;
import com.xhg.ops.workorders.vo.WebWorkOrderDetailShowVo;
import com.xhg.ops.workorders.vo.WebWorkOrderDetailVo;
import com.xhg.ops.workorders.vo.WebWorkOrderOperationVo;
import com.xhg.ops.workorders.vo.WebWorkOrderPlatformListVo;
import com.xhg.ops.workorders.vo.WebWorkOrderPlatformMainStateVo;
import com.xhg.ops.workorders.vo.WebWorkOrderPlatformStaticsVo;
import com.xhg.ops.workorders.vo.WebWorkOrderPlatformSubStateVo;
import com.xhg.ops.workorders.vo.WebWorkOrderProgressVo;
import com.xhg.ops.workorders.vo.WebWorkOrderVo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("webWorkOrderService")
public class WebWorkOrderServiceImpl implements WebWorkOrderService {

	@Autowired
	private WorkOrderService workOrderService;
	
	@Autowired
	private WorkOrderLogService workOrderLogService;
	
	@Autowired
	private WorkOrderFlowService workOrderFlowService;
	
	@Autowired
	private WorkOrderCommonService workOrderCommonService;
	
	@Override
	public WebWorkOrderDetailShowVo detail(WorkOrderBaseDTO dto) {
		int orderId = Integer.valueOf(dto.getOrderId());
		WorkOrder workOrder = workOrderService.queryWorkOrderDetail(orderId);
		int status = workOrder.getStatus();
		
		WebWorkOrderDetailShowVo vo = new WebWorkOrderDetailShowVo();
		vo.setOrderId(dto.getOrderId());
		vo.setOrderNo(workOrder.getOrderNo());
		vo.setStatusCode(String.valueOf(status));
		WorkOrderStatus orderStatus = WorkOrderStatus.getOrderStatus(status);
		vo.setStatus(orderStatus.getNext_desc());
		
		String showMaterialApplyInfo = workOrderCommonService.getShowMaterialApplyInfo(orderId);
		vo.setShowMaterialApplyInfo(showMaterialApplyInfo);
		
		String showOrderRecord = workOrderCommonService.getShowOrderRecord(orderId);
		vo.setShowOrderRecord(showOrderRecord);
		
		vo.setShowBaseInfo(ComConstant.SHOW_Y);
		
		vo.setShowProgress(ComConstant.SHOW_Y);
		
		return vo;
	}
	
	@Override
	public WebWorkOrderDetailVo baseInfo(WorkOrderBaseDTO dto) {
		int orderId = Integer.valueOf(dto.getOrderId());
		WorkOrder workOrder = workOrderService.queryWorkOrderDetail(orderId);
		WebWorkOrderDetailVo vo = null;
		if(workOrder!=null) {
			vo = new WebWorkOrderDetailVo();
			vo.setAttachmentUrls(workOrder.getAttachments());
			vo.setContactInfo(workOrder.getContactInfo());
			//DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			vo.setCreateTime(workOrder.getCreatedTime());
			vo.setCreateUserName(workOrder.getCreatedUser());
			vo.setDescription(workOrder.getOrderTitle());
			vo.setDeviceAddress(workOrder.getSiteAddress());
			vo.setDeviceId(workOrder.getDeviceId());
			vo.setSiteCode(workOrder.getSiteCode());
			vo.setDeviceLatitude(workOrder.getSiteLatitude());
			vo.setDeviceLongitude(workOrder.getSiteLongitude());
			
			SystemUser user = UserContext.getUser();
			String userId = String.valueOf(user.getId());
			//WebWorkOrderOperationVo operation = this.getWebWorkOrderOperation(userId, workOrder);
			vo.setOrderNo(dto.getOrderNo());
			vo.setOrderId(dto.getOrderId());
			int status = workOrder.getStatus();
			Integer level = workOrder.getLevel();
			
			vo.setLevel(WorkOrderLevel.getNameByCode(level));
			WorkOrderStatusMapping mapping = WorkOrderStatusMapping.getByCode(status);
			vo.setStatus(mapping.getDesc());
			
			Integer dataSource = workOrder.getDataSource();
			vo.setSource(WorkOrderDataSource.getNameByCode(dataSource));
			Integer orderType = workOrder.getOrderType();
			String type = WorkOrderType.getNameByCode(orderType);
			vo.setType(type);
		}
		
		return vo;
	}
	
	@Override
	public List<WebWorkOrderProgressVo> progress(WorkOrderBaseDTO dto) {
		int orderId = Integer.valueOf(dto.getOrderId());
		List<WorkOrderLog> workOrderLogList = workOrderLogService.queryWorkOrderLogList(orderId);
		List<WebWorkOrderProgressVo> voList = new ArrayList<WebWorkOrderProgressVo>();
		if(CollectionUtils.isNotEmpty(workOrderLogList)) {
			WorkOrderLog latestLog = workOrderLogList.get(0);
			Integer latestStatus = latestLog.getOrderStatus();
			if(WorkOrderStatus.isFinished(latestStatus)) {
				WebWorkOrderProgressVo vo = new WebWorkOrderProgressVo();
				vo.setStatus("结束");
				voList.add(vo);
			}
			
			//DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			for(WorkOrderLog log : workOrderLogList) {
				WebWorkOrderProgressVo vo = new WebWorkOrderProgressVo();
				vo.setDate(log.getCreatedTime());
				vo.setPositionName(log.getCreatedUserDesc());
				Integer status = log.getOrderStatus();
				WorkOrderStatusMapping statusMapping = WorkOrderStatusMapping.getByCode(status);
				vo.setStatus(statusMapping.getDesc());
				vo.setUserName(log.getCreatedUser());
				vo.setOperation(statusMapping.getOperateName());
        vo.setRemark(log.getRemark());
				voList.add(vo);
			}
		}
		
		return voList;
	}
	
	@Override
	public PagerResult<WebWorkOrderVo> listOrder(WebWorkOrderQueryDTO dto) {
		//配置用户信息
		//WorkOrderUserDTO user = workOrderCommonService.assembleUserDTO();
		
		WorkOrderFindConditionDto condition = new WorkOrderFindConditionDto();
		condition.setCreatedUser(dto.getCreateUserName());
		condition.setOrderNo(dto.getOrderNo());
		if (dto.getType() != null && StringUtils.isNotEmpty(dto.getType().getCode())) {
			condition.setOrderType(Integer.valueOf(dto.getType().getCode()));
		}
		//condition.setStatus(dto.ge);
		
		PagerResult<WorkOrderFindResultDto> dtoPagerResult = workOrderService.queryAllWorkOrderListPage(dto.getCurrentPage(), dto.getPageSize(), condition);
		
		if(dtoPagerResult!=null) {
			//dto转vo
			List<WorkOrderFindResultDto> dtoList = dtoPagerResult.getList();
			List<WebWorkOrderVo> voList = new ArrayList<WebWorkOrderVo>();
			if(CollectionUtils.isNotEmpty(dtoList)) {
				//DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				for(WorkOrderFindResultDto resultDto : dtoList) {
					WebWorkOrderVo vo = new WebWorkOrderVo();
					vo.setOrderNo(resultDto.getOrderNo());
					vo.setOrderId(String.valueOf(resultDto.getId()));
					vo.setCreateUserName(resultDto.getCreatedUser());
					vo.setCreateDate(resultDto.getCreatedTime());
					
					//状态
					int status = resultDto.getStatus();
					WorkOrderStatusMapping statusMapping = WorkOrderStatusMapping.getByCode(status);
					String statusCode = null;
					String statusName = null;
					statusCode = statusMapping.getNext_desc_code();
					statusName = statusMapping.getNext_desc();
					vo.setStatus(statusName);
					vo.setStatusCode(statusCode);
					
					//类型
					Integer orderType = resultDto.getOrderType();
					WorkOrderType type = WorkOrderType.getOrderType(orderType);
					if(type!=null) {
						vo.setType(type.getName());
					}
					
					voList.add(vo);
				}
			}
			 
			//vo分页
			PageInfo<WebWorkOrderVo> pageInfo = new PageInfo<WebWorkOrderVo>(voList);
			PagerResult<WebWorkOrderVo> pagerResult=new PagerResult<WebWorkOrderVo>(pageInfo);
	        pagerResult.setTotal(dtoPagerResult.getTotal());
	        pagerResult.setTotalPages(dtoPagerResult.getTotalPages());
	        pagerResult.setCurrentPage(dtoPagerResult.getCurrentPage());
	        pagerResult.setPageSize(dtoPagerResult.getPageSize());
	        
	        return pagerResult;
		}
		
		return null;
	}
	
	@Override
	public PagerResult<WebWorkOrderPlatformListVo> listPlatformOrderList(WebWorkOrderPlatformQueryDTO dto) {
		String mainState = dto.getMainState();
		String subState = dto.getSubState();
		
		//配置用户信息
		WorkOrderUserDTO user = workOrderCommonService.assembleUserDTO();
		
		PagerResult<WorkOrderFindResultDto> dtoPagerResult = null;
		if(ComConstant.WORKORDER_MAIN_STATE_TODO.equals(mainState)) {
			//待处理
			WorkOrderFindConditionDto condition = null;
			WorkOrderStatusCondition conditionStatus = null;
			if(StringUtils.isNotEmpty(subState)) {
				//List<WorkOrderStatusMapping> toDoList = WorkOrderStatusMapping.getListByNextDescCode2(subState);
				//List<Integer> statusList = toDoList.stream().map(WorkOrderStatusMapping :: getCode).collect(Collectors.toList());
				condition = new WorkOrderFindConditionDto();
				condition.setStatus(Integer.valueOf(subState));
				conditionStatus = WorkOrderStatusCondition.getConditionByKey(subState);
			} else {
				conditionStatus = WorkOrderStatusCondition.ALL;
			}
			
			dtoPagerResult = workOrderFlowService
			.findUnprocessWorkOrderListPage(dto.getCurrentPage(), dto.getPageSize(), conditionStatus, user);
			
		} else if(ComConstant.WORKORDER_MAIN_STATE_INPROGRESS.equals(mainState)) {
			//进行中
			dtoPagerResult = workOrderFlowService
					.findProcessWorkOrderListPage(dto.getCurrentPage(), dto.getPageSize(), WorkOrderStatusCondition.ALL, user);
		} else if(ComConstant.WORKORDER_MAIN_STATE_OVER.equals(mainState)) {
			//已结束
			dtoPagerResult = workOrderFlowService.findFinishedWorkOrderListPage(dto.getCurrentPage(), dto.getPageSize(), user);
		}
		
		if(dtoPagerResult!=null) {
			//dto转vo
			List<WorkOrderFindResultDto> dtoList = dtoPagerResult.getList();
			List<WebWorkOrderPlatformListVo> voList = new ArrayList<WebWorkOrderPlatformListVo>();
			if(CollectionUtils.isNotEmpty(dtoList)) {
				//DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				for(WorkOrderFindResultDto resultDto : dtoList) {
					WebWorkOrderPlatformListVo vo = new WebWorkOrderPlatformListVo();
					Date createdTime = resultDto.getCreatedTime();
					vo.setCreateDate(createdTime);
					vo.setCreateUserName(resultDto.getCreatedUser());
					vo.setOrderNo(resultDto.getOrderNo());
					vo.setOrderId(String.valueOf(resultDto.getId()));
					
					//状态
					int status = resultDto.getStatus();
					WorkOrderStatusMapping statusMapping = WorkOrderStatusMapping.getByCode(status);
					String statusCode = null;
					String statusName = null;
					if(ComConstant.WORKORDER_MAIN_STATE_TODO.equals(mainState) || ComConstant.WORKORDER_MAIN_STATE_INPROGRESS.equals(mainState)) {
						statusCode = statusMapping.getNext_desc_code();
						statusName = statusMapping.getNext_desc();
					} else if(ComConstant.WORKORDER_MAIN_STATE_OVER.equals(mainState)) {
						statusCode = statusMapping.getDescCode();
						statusName = statusMapping.getDesc();
					}
					vo.setStatus(statusName);
					vo.setStatusCode(statusCode);
					
					if(ComConstant.WORKORDER_MAIN_STATE_TODO.equals(mainState)) {
						//操作标识列表
						WorkOrderStatus orderStatus = WorkOrderStatus.getOrderStatus(status);
						List<String> operationCode = orderStatus.operations();
						vo.setOperationCode(operationCode);
					}
					
					//类型
					Integer orderType = resultDto.getOrderType();
					WorkOrderType type = WorkOrderType.getOrderType(orderType);
					vo.setType(type.getName());
					
					voList.add(vo);
				}
			}
			 
			//vo分页
			PageInfo<WebWorkOrderPlatformListVo> pageInfo = new PageInfo<WebWorkOrderPlatformListVo>(voList);
			PagerResult<WebWorkOrderPlatformListVo> pagerResult=new PagerResult<WebWorkOrderPlatformListVo>(pageInfo);
	        pagerResult.setTotal(dtoPagerResult.getTotal());
	        pagerResult.setTotalPages(dtoPagerResult.getTotalPages());
	        pagerResult.setCurrentPage(dtoPagerResult.getCurrentPage());
	        pagerResult.setPageSize(dtoPagerResult.getPageSize());
	        
	        return pagerResult;
		}
		
		return null;
	}
	
	@Override
	public WebWorkOrderDealRecordVo dealRecord(WorkOrderBaseDTO dto) {
		int orderId = Integer.valueOf(dto.getOrderId());
		WorkOrderResult orderResult = workOrderService.queryWorkOrderResult(orderId);
		
		WebWorkOrderDealRecordVo vo = new WebWorkOrderDealRecordVo();
		vo.setActualSituation(orderResult.getPhenomena());
		vo.setAnalysis(orderResult.getReason());
		
		vo.setChangeParts(orderResult.getChangeParts());
		vo.setFailModule(orderResult.getFaultModule());
		Integer faultType = orderResult.getFaultType();
		vo.setFailType(WorkOrderFailType.getNameByCode(faultType));
		
		vo.setOrderNo(dto.getOrderNo());
		vo.setOrderId(dto.getOrderId());
		vo.setSolution(orderResult.getSolution());
		
		Integer orderType = orderResult.getOrderType();
		vo.setType(WorkOrderType.getNameByCode(orderType));
		
		List<String> attachments = orderResult.getAttachments();
		vo.setUrls(attachments);
		
		return vo;
	}
	
	@Override
	public WebWorkOrderDealMaterialVo materialApplyInfo(WorkOrderBaseDTO dto) {
		int orderId = Integer.valueOf(dto.getOrderId());
		WorkOrder workOrder = workOrderService.queryWorkOrderDetail(orderId);
		
		if(workOrder==null) {
			return null;
		}
		
		WebWorkOrderDealMaterialVo vo = null;
		List<WorkOrderMaterielApply> materielList = workOrder.getMaterielList();
		if(CollectionUtils.isNotEmpty(materielList)) {
			WorkOrderMaterielApply workOrderMaterielApply = materielList.get(0);
			vo = new WebWorkOrderDealMaterialVo();
			vo.setAddress(workOrderMaterielApply.getReceiveAddress());
			vo.setContactPersonName(workOrderMaterielApply.getReceivePerson());
			vo.setContactPersonPhone(workOrderMaterielApply.getReceivePhone());
			vo.setMaterialName(workOrderMaterielApply.getMaterielName());
			vo.setModuleName(workOrderMaterielApply.getModuleName());
			vo.setMaterialName(workOrderMaterielApply.getMaterielName());
			vo.setOrderNo(dto.getOrderNo());
			vo.setOrderId(dto.getOrderId());
		}
		
		return vo;
	}
	
	@Override
	public List<WebWorkOrderDealMaterialVo> materialApplyInfoList(WorkOrderBaseDTO dto) {
		int orderId = Integer.valueOf(dto.getOrderId());
		WorkOrder workOrder = workOrderService.queryWorkOrderDetail(orderId);
		
		if(workOrder==null) {
			return null;
		}
		
		List<WebWorkOrderDealMaterialVo> voList = new ArrayList<>();
		List<WorkOrderMaterielApply> materielList = workOrder.getMaterielList();
		if(CollectionUtils.isNotEmpty(materielList)) {
			WorkOrderMaterielApply workOrderMaterielApply = materielList.get(0);
			WebWorkOrderDealMaterialVo vo = new WebWorkOrderDealMaterialVo();
			vo.setAddress(workOrderMaterielApply.getReceiveAddress());
			vo.setContactPersonName(workOrderMaterielApply.getReceivePerson());
			vo.setContactPersonPhone(workOrderMaterielApply.getReceivePhone());
			vo.setModuleName(workOrderMaterielApply.getModuleName());
			vo.setMaterialName(workOrderMaterielApply.getMaterielName());
			vo.setOrderNo(dto.getOrderNo());
			vo.setOrderId(dto.getOrderId());
			
			voList.add(vo);
		}
		
		return voList;
	}
	
	/**
	 * 获取操作按钮
	 * @param userId 用户id
	 * @param status 工单状态
	 * @return
	 */
	private WebWorkOrderOperationVo getWebWorkOrderOperation(String userId, WorkOrder workOrder) {
		int status = workOrder.getStatus();
		//工单已关闭, 没有任何操作
		if(status==WorkOrderStatusMapping.CLOSED.getCode() || status==WorkOrderStatusMapping.REVIEWED.getCode()) {
			return null;
		}
		
		//配置用户信息
		WorkOrderUserDTO user = workOrderCommonService.assembleUserDTO();
		
		//判断是否轮到该用户来处理 
		boolean canProcess = workOrderFlowService.isCanProcess(workOrder.getProcTaskId(), user);
		
		
		//生成操作按钮
		WebWorkOrderOperationVo webWorkOrderOperationVo = new WebWorkOrderOperationVo();
		if(canProcess) {
			webWorkOrderOperationVo.setIsForbidden(ComConstant.ISFORBIDDEN_N);
		} else {
			webWorkOrderOperationVo.setIsForbidden(ComConstant.ISFORBIDDEN_Y);
		}
		WorkOrderStatusMapping statusMapping = WorkOrderStatusMapping.getByCode(status);
		webWorkOrderOperationVo.setCode(statusMapping.getNextOperateCode());
		webWorkOrderOperationVo.setName(statusMapping.getNextOperateName());
		
		return webWorkOrderOperationVo;
	}

	@Override
	public WebWorkOrderPlatformStaticsVo platformStatics() {
		//配置用户信息
		WorkOrderUserDTO user = workOrderCommonService.assembleUserDTO();
		
		Map<String, Long> toToMap = workOrderFlowService.statUnProcessCount(user);
		
		long processingCount = workOrderFlowService.statProcessingCount(user);
		long finishedCount = workOrderFlowService.statFinishedCount(user);
		
		//页面类
		WebWorkOrderPlatformStaticsVo vo = new WebWorkOrderPlatformStaticsVo();
		List<WebWorkOrderPlatformMainStateVo> mainSateList = new ArrayList<WebWorkOrderPlatformMainStateVo>();
		vo.setList(mainSateList);
		
		//待处理
		WebWorkOrderPlatformMainStateVo toDoMainState = new WebWorkOrderPlatformMainStateVo();
		mainSateList.add(toDoMainState);
		toDoMainState.setMainState(ComConstant.WORKORDER_MAIN_STATE_TODO);
		List<WebWorkOrderPlatformSubStateVo> toDoSubStateList = new ArrayList<WebWorkOrderPlatformSubStateVo>();
		toDoMainState.setList(toDoSubStateList);
		
		List<String> processCodeList = WorkFlowTaskEnum.getProcessCodeList();
		processCodeList.forEach(code -> {
			WebWorkOrderPlatformSubStateVo toToSubState = new WebWorkOrderPlatformSubStateVo();
			toToSubState.setSubState(WorkOrderStatusCondition.getKeyByCode(code));
			toToSubState.setNum(toToMap.get(code));
			toDoSubStateList.add(toToSubState);
		});
		
		//进行中
		WebWorkOrderPlatformMainStateVo inProgressMainState = new WebWorkOrderPlatformMainStateVo();
		mainSateList.add(inProgressMainState);
		inProgressMainState.setMainState(ComConstant.WORKORDER_MAIN_STATE_INPROGRESS);
		List<WebWorkOrderPlatformSubStateVo> inProgressSubStateList = new ArrayList<WebWorkOrderPlatformSubStateVo>();
		inProgressMainState.setList(inProgressSubStateList);
		
		WebWorkOrderPlatformSubStateVo inProgressSubState = new WebWorkOrderPlatformSubStateVo();
		inProgressSubState.setNum(processingCount);
		inProgressSubStateList.add(inProgressSubState);
		
		//已结束
		WebWorkOrderPlatformMainStateVo finishedMainState = new WebWorkOrderPlatformMainStateVo();
		mainSateList.add(finishedMainState);
		finishedMainState.setMainState(ComConstant.WORKORDER_MAIN_STATE_OVER);
		List<WebWorkOrderPlatformSubStateVo> finishedSubStateList = new ArrayList<WebWorkOrderPlatformSubStateVo>();
		finishedMainState.setList(finishedSubStateList);
		
		WebWorkOrderPlatformSubStateVo finishedSubState = new WebWorkOrderPlatformSubStateVo();
		finishedSubState.setNum(finishedCount);
		finishedSubStateList.add(finishedSubState);
		
		return vo;
	}


}
