package com.xhg.ops.workorders.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.xhg.core.web.vo.PagerResult;
import com.xhg.core.web.vo.RsBody;
import com.xhg.core.web.vo.Status;
import com.xhg.ops.common.ComConstant;
import com.xhg.ops.system.filter.UserContext;
import com.xhg.ops.system.model.SystemPosition;
import com.xhg.ops.system.model.SystemUser;
import com.xhg.ops.system.service.SystemPositonService;
import com.xhg.ops.workorders.dto.AppWorkOrderMessageQueryDTO;
import com.xhg.ops.workorders.dto.AppWorkOrderMessageUpdateDTO;
import com.xhg.ops.workorders.dto.AppWorkOrderQueryDTO;
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
import com.xhg.ops.workorders.model.WorkOrderMessage;
import com.xhg.ops.workorders.model.WorkOrderResult;
import com.xhg.ops.workorders.service.AppWorkOrderService;
import com.xhg.ops.workorders.service.WorkOrderCommonService;
import com.xhg.ops.workorders.service.WorkOrderFlowService;
import com.xhg.ops.workorders.service.WorkOrderLogService;
import com.xhg.ops.workorders.service.WorkOrderMessageService;
import com.xhg.ops.workorders.service.WorkOrderService;
import com.xhg.ops.workorders.vo.AppWorkOrderDealMaterialVo;
import com.xhg.ops.workorders.vo.AppWorkOrderDealRecordVo;
import com.xhg.ops.workorders.vo.AppWorkOrderDetailVo;
import com.xhg.ops.workorders.vo.AppWorkOrderMessageVo;
import com.xhg.ops.workorders.vo.AppWorkOrderOperationVo;
import com.xhg.ops.workorders.vo.AppWorkOrderProgressVo;
import com.xhg.ops.workorders.vo.AppWorkOrderVo;

@Service("appWorkOrderService")
public class AppWorkOrderServiceImpl implements AppWorkOrderService {

	@Autowired
	private WorkOrderService workOrderService;
	
	@Autowired
	private WorkOrderLogService workOrderLogService;
	
	@Autowired
	private WorkOrderFlowService workOrderFlowService;
	
	@Autowired
	private WorkOrderMessageService workOrderMessageService;
	
	@Autowired
	private SystemPositonService systemPositonService;
	
	@Autowired
	private WorkOrderCommonService workOrderCommonService;
	
	
	@Override
	public PagerResult<AppWorkOrderMessageVo> message(AppWorkOrderMessageQueryDTO dto) {
		SystemUser user = UserContext.getUser();
		int userId = user.getId();
		PagerResult<WorkOrderMessage> pageMessage = workOrderMessageService.findNewestMessageList(dto.getCurrentPage(), dto.getPageSize(), userId, WorkOrderMessageService.TYPE_MESSAGE_SITE);
		List<WorkOrderMessage> list = pageMessage.getList();
		List<AppWorkOrderMessageVo> voList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(list)) {
			//DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			list.forEach(message -> {
				Integer orderId = message.getOrderId();
				AppWorkOrderMessageVo vo = new AppWorkOrderMessageVo();
				vo.setDate(message.getCreatedTime());
				vo.setOrderNo(message.getTitle());
				vo.setOrderId(String.valueOf(orderId));
				vo.setMessageId(String.valueOf(message.getId()));
				Integer positionId = user.getPositionId();
				SystemPosition position = systemPositonService.selectByPrimaryKey(positionId);
				if(null!=position) {
					vo.setPositionName(position.getName());
				}
				WorkOrder workOrder = workOrderService.queryWorkOrderDetail(orderId);
				if(null!=workOrder) {
					Integer orderStatus = workOrder.getStatus();
					WorkOrderStatusMapping statusMapping = WorkOrderStatusMapping.getByCode(orderStatus);
					if(statusMapping!=null) {
						vo.setStatus(statusMapping.getDesc());
					}
				}
				
				vo.setUserName(user.getName());
				vo.setRead(String.valueOf(message.getStatus()));
				voList.add(vo);
			});
		}
		
		//vo分页
		PageInfo<AppWorkOrderMessageVo> pageInfo = new PageInfo<AppWorkOrderMessageVo>(voList);
		PagerResult<AppWorkOrderMessageVo> pagerResult=new PagerResult<AppWorkOrderMessageVo>(pageInfo);
        pagerResult.setTotal(pageMessage.getTotal());
        pagerResult.setTotalPages(pageMessage.getTotalPages());
        pagerResult.setCurrentPage(pageMessage.getCurrentPage());
        pagerResult.setPageSize(pageMessage.getPageSize());
        
        return pagerResult;
	}
	
	@Override
	public RsBody<Map<String, Object>> readMessage(AppWorkOrderMessageUpdateDTO dto) {
		RsBody<Map<String,Object>> rsBody = new RsBody<>();
		
		WorkOrderUserDTO user = workOrderCommonService.assembleUserDTO();
		workOrderMessageService.updateMessageRead(Integer.valueOf(dto.getMessageId()), user);
		
		rsBody.setCode(Status.Bu_SUCCESS.getName());
		rsBody.setMessage(Status.Bu_SUCCESS.getValue());
		return rsBody;
	}
	
	@Override
	public AppWorkOrderDetailVo detail(WorkOrderBaseDTO dto) {
		int orderId = Integer.valueOf(dto.getOrderId());
		WorkOrder workOrder = workOrderService.queryWorkOrderDetail(orderId);
		AppWorkOrderDetailVo vo = null;
		if(workOrder!=null) {
			vo = new AppWorkOrderDetailVo();
			vo.setAttachmentUrls(workOrder.getAttachments());
			vo.setContactInfo(workOrder.getContactInfo());
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
			AppWorkOrderOperationVo operation = this.getAppWorkOrderOperation(userId, workOrder);
			vo.setOperation(operation);
			vo.setOrderNo(dto.getOrderNo());
			vo.setOrderId(dto.getOrderId());
			String showMaterialApplyInfo = workOrderCommonService.getShowMaterialApplyInfo(orderId);
			vo.setShowMaterialApplyInfo(showMaterialApplyInfo);
			
			if(null!=operation) {
				if(ComConstant.ISFORBIDDEN_N.equals(operation.getIsForbidden())) {
					vo.setShowOperationButton(ComConstant.SHOW_Y);
				} else if(ComConstant.ISFORBIDDEN_Y.equals(operation.getIsForbidden())) {
					vo.setShowOperationButton(ComConstant.SHOW_N);
				}
			} else {
				vo.setShowOperationButton(ComConstant.SHOW_N);
			}

			
			String showOrderRecord = workOrderCommonService.getShowOrderRecord(orderId);
			vo.setShowOrderRecord(showOrderRecord);
			
			Integer dataSource = workOrder.getDataSource();
			vo.setSource(WorkOrderDataSource.getNameByCode(dataSource));
			Integer orderType = workOrder.getOrderType();
			String type = WorkOrderType.getNameByCode(orderType);
			vo.setType(type);
		}
		
		return vo;
	}
	
	@Override
	public List<AppWorkOrderProgressVo> progress(WorkOrderBaseDTO dto) {
		int orderId = Integer.valueOf(dto.getOrderId());
		List<WorkOrderLog> workOrderLogList = workOrderLogService.queryWorkOrderLogList(orderId);
		List<AppWorkOrderProgressVo> voList = new ArrayList<AppWorkOrderProgressVo>();
		if(CollectionUtils.isNotEmpty(workOrderLogList)) {
			WorkOrderLog latestLog = workOrderLogList.get(0);
			Integer latestStatus = latestLog.getOrderStatus();
			if(WorkOrderStatus.isFinished(latestStatus)) {
				AppWorkOrderProgressVo vo = new AppWorkOrderProgressVo();
				vo.setStatus("结束");
				voList.add(vo);
			}
			
			//DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			for(WorkOrderLog log : workOrderLogList) {
				AppWorkOrderProgressVo vo = new AppWorkOrderProgressVo();
				vo.setDate(log.getCreatedTime());
				vo.setPositionName(log.getCreatedUserDesc());
				Integer status = log.getOrderStatus();
				WorkOrderStatusMapping statusMapping = WorkOrderStatusMapping.getByCode(status);
				vo.setStatus(statusMapping.getDesc());
				vo.setUserName(log.getCreatedUser());
				vo.setRemark(log.getRemark());
				voList.add(vo);
			}
		}
		
		return voList;
	}
	
	@Override
	public PagerResult<AppWorkOrderVo> listOrder(AppWorkOrderQueryDTO dto) {
		String mainState = dto.getMainState();
		String subState = dto.getSubState();
		
		//配置用户信息
		WorkOrderUserDTO user = workOrderCommonService.assembleUserDTO();
		
		PagerResult<WorkOrderFindResultDto> dtoPagerResult = null;
		if(ComConstant.WORKORDER_MAIN_STATE_TODO.equals(mainState)) {
			//待处理
			WorkOrderFindConditionDto condition = null;
			WorkOrderStatusCondition conditionStatus = null;
			if(StringUtils.isNotBlank(subState)) {
				List<WorkOrderStatusMapping> toDoList = WorkOrderStatusMapping.getListByNextDescCode2(subState);
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
		}
		
		if(dtoPagerResult!=null) {
			//dto转vo
			List<WorkOrderFindResultDto> dtoList = dtoPagerResult.getList();
			List<AppWorkOrderVo> voList = new ArrayList<AppWorkOrderVo>();
			if(CollectionUtils.isNotEmpty(dtoList)) {
				//DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				for(WorkOrderFindResultDto resultDto : dtoList) {
					AppWorkOrderVo vo = new AppWorkOrderVo();
					Date updatedTime = resultDto.getUpdatedTime();
					vo.setDate(updatedTime);
					vo.setDescription(resultDto.getOrderTitle());
					
					//紧急程度
					Integer level = resultDto.getLevel();
					WorkOrderLevel orderLevel = WorkOrderLevel.getOrderLevel(level);
					if(orderLevel!=null) {
						vo.setLevel(orderLevel.getName());
						vo.setLevelColor(orderLevel.getColor());
					}
					vo.setOrderNo(resultDto.getOrderNo());
					vo.setOrderId(String.valueOf(resultDto.getId()));
					
					//状态
					int status = resultDto.getStatus();
					WorkOrderStatusMapping statusMapping = WorkOrderStatusMapping.getByCode(status);
					String statusCode = statusMapping.getNext_desc_code();
					String statusName = statusMapping.getNext_desc();
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
			PageInfo<AppWorkOrderVo> pageInfo = new PageInfo<AppWorkOrderVo>(voList);
			PagerResult<AppWorkOrderVo> pagerResult=new PagerResult<AppWorkOrderVo>(pageInfo);
	        pagerResult.setTotal(dtoPagerResult.getTotal());
	        pagerResult.setTotalPages(dtoPagerResult.getTotalPages());
	        pagerResult.setCurrentPage(dtoPagerResult.getCurrentPage());
	        pagerResult.setPageSize(dtoPagerResult.getPageSize());
	        
	        return pagerResult;
		}
		
		return null;
	}
	
	@Override
	public AppWorkOrderDealRecordVo dealRecord(WorkOrderBaseDTO dto) {
		int orderId = Integer.valueOf(dto.getOrderId());
		WorkOrderResult orderResult = workOrderService.queryWorkOrderResult(orderId);
		
		AppWorkOrderDealRecordVo vo = new AppWorkOrderDealRecordVo();
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
	public AppWorkOrderDealMaterialVo materialApplyInfo(WorkOrderBaseDTO dto) {
		int orderId = Integer.valueOf(dto.getOrderId());
		WorkOrder workOrder = workOrderService.queryWorkOrderDetail(orderId);
		
		if(workOrder==null) {
			return null;
		}
		
		AppWorkOrderDealMaterialVo vo = null;
		List<WorkOrderMaterielApply> materielList = workOrder.getMaterielList();
		if(CollectionUtils.isNotEmpty(materielList)) {
			WorkOrderMaterielApply workOrderMaterielApply = materielList.get(0);
			vo = new AppWorkOrderDealMaterialVo();
			vo.setAddress(workOrderMaterielApply.getReceiveAddress());
			vo.setContactPersonName(workOrderMaterielApply.getReceivePerson());
			vo.setContactPersonPhone(workOrderMaterielApply.getReceivePhone());
			vo.setMaterialName(workOrderMaterielApply.getMaterielName());
			vo.setModuleName(workOrderMaterielApply.getModuleName());
			vo.setOrderNo(dto.getOrderNo());
			vo.setOrderId(dto.getOrderId());
		}
		
		return vo;
	}
	
	@Override
	public List<AppWorkOrderDealMaterialVo> materialApplyInfoList(WorkOrderBaseDTO dto) {
		int orderId = Integer.valueOf(dto.getOrderId());
		WorkOrder workOrder = workOrderService.queryWorkOrderDetail(orderId);
		
		if(workOrder==null) {
			return null;
		}
		
		List<AppWorkOrderDealMaterialVo> voList = new ArrayList<>();
		List<WorkOrderMaterielApply> materielList = workOrder.getMaterielList();
		if(CollectionUtils.isNotEmpty(materielList)) {
			for(WorkOrderMaterielApply workOrderMaterielApply : materielList) {
				AppWorkOrderDealMaterialVo vo = new AppWorkOrderDealMaterialVo();
				vo.setAddress(workOrderMaterielApply.getReceiveAddress());
				vo.setContactPersonName(workOrderMaterielApply.getReceivePerson());
				vo.setContactPersonPhone(workOrderMaterielApply.getReceivePhone());
				vo.setMaterialName(workOrderMaterielApply.getMaterielName());
				vo.setModuleName(workOrderMaterielApply.getModuleName());
				vo.setOrderNo(dto.getOrderNo());
				vo.setOrderId(dto.getOrderId());
				voList.add(vo);
			}
		}
		
		return voList;
	}
	
	/**
	 * 获取操作按钮
	 * @param userId 用户id
	 * @param status 工单状态
	 * @return
	 */
	private AppWorkOrderOperationVo getAppWorkOrderOperation(String userId, WorkOrder workOrder) {
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
		AppWorkOrderOperationVo appWorkOrderOperationVo = new AppWorkOrderOperationVo();
		if(canProcess) {
			appWorkOrderOperationVo.setIsForbidden(ComConstant.ISFORBIDDEN_N);
		} else {
			appWorkOrderOperationVo.setIsForbidden(ComConstant.ISFORBIDDEN_Y);
		}
		WorkOrderStatusMapping statusMapping = WorkOrderStatusMapping.getByCode(status);
		appWorkOrderOperationVo.setCode(statusMapping.getNextOperateCode());
		appWorkOrderOperationVo.setName(statusMapping.getNextOperateName());
		
		return appWorkOrderOperationVo;
	}

}
