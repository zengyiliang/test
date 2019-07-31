package com.xhg.ops.workorders.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhg.core.web.vo.RsBody;
import com.xhg.core.web.vo.Status;
import com.xhg.ops.common.ComConstant;
import com.xhg.ops.enums.RoleEnum;
import com.xhg.ops.system.model.CyclingAreaDO;
import com.xhg.ops.system.service.SystemAreaDistrictService;
import com.xhg.ops.system.service.SystemUserService;
import com.xhg.ops.system.vo.user.SystemUserPositionVO;
import com.xhg.ops.workorders.dto.AppWorkOrderDealMaterialDTO;
import com.xhg.ops.workorders.dto.AppWorkOrderDealRecordDTO;
import com.xhg.ops.workorders.dto.AppWorkOrderFlowAssignConfirmDTO;
import com.xhg.ops.workorders.dto.AppWorkOrderFlowAssignRejectDTO;
import com.xhg.ops.workorders.dto.AppWorkOrderFlowDepartRejectDTO;
import com.xhg.ops.workorders.dto.AppWorkOrderFlowFirstReviewCloseDTO;
import com.xhg.ops.workorders.dto.AppWorkOrderFlowFirstReviewPassDTO;
import com.xhg.ops.workorders.dto.AppWorkOrderFlowSignInDTO;
import com.xhg.ops.workorders.dto.AppWorkOrderFlowSignRejectDTO;
import com.xhg.ops.workorders.dto.WorkOrderBaseDTO;
import com.xhg.ops.workorders.dto.WorkOrderPullDownDTO;
import com.xhg.ops.workorders.dto.WorkOrderUserDTO;
import com.xhg.ops.workorders.enums.WorkOrderFailType;
import com.xhg.ops.workorders.enums.WorkOrderLevel;
import com.xhg.ops.workorders.enums.WorkOrderType;
import com.xhg.ops.workorders.model.WorkOrder;
import com.xhg.ops.workorders.model.WorkOrderDeviceModule;
import com.xhg.ops.workorders.model.WorkOrderDeviceParts;
import com.xhg.ops.workorders.model.WorkOrderMaterielApply;
import com.xhg.ops.workorders.model.WorkOrderResult;
import com.xhg.ops.workorders.service.AppWorkOrderFlowService;
import com.xhg.ops.workorders.service.WorkOrderCommonService;
import com.xhg.ops.workorders.service.WorkOrderDeviceModuleService;
import com.xhg.ops.workorders.service.WorkOrderDevicePartsService;
import com.xhg.ops.workorders.service.WorkOrderFlowService;
import com.xhg.ops.workorders.service.WorkOrderService;
import com.xhg.ops.workorders.util.datadict.DataDict;
import com.xhg.ops.workorders.util.datadict.DataDictUtil;
import com.xhg.ops.workorders.vo.AppWorkOrderAssignOpsVo;
import com.xhg.ops.workorders.vo.AppWorkOrderAssignPageVo;
import com.xhg.ops.workorders.vo.AppWorkOrderDealMaterialPageVo;
import com.xhg.ops.workorders.vo.AppWorkOrderDealRecordPageVo;
import com.xhg.ops.workorders.vo.AppWorkOrderFirstReviewOpsManagerVo;
import com.xhg.ops.workorders.vo.AppWorkOrderFirstReviewPageVo;

@Service("appWorkOrderFlowService")
public class AppWorkOrderFlowServiceImpl implements AppWorkOrderFlowService {

	@Autowired
	private WorkOrderService workOrderService;
	
	@Autowired
	private WorkOrderFlowService workOrderFlowService;
	
	@Autowired
	private SystemUserService systemUserService;
	
	@Autowired
	private WorkOrderCommonService workOrderCommonService;
	
    @Autowired
    private WorkOrderDeviceModuleService workOrderDeviceModuleService;
    
    @Autowired
    private WorkOrderDevicePartsService workOrderDevicePartsService;
    
    @Autowired
    private SystemAreaDistrictService systemAreaDistrictService;
	
	@Override
	public AppWorkOrderFirstReviewPageVo firstReviewPage(WorkOrderBaseDTO dto) throws Exception {
		//查询工单所在区编码
		int orderId = Integer.valueOf(dto.getOrderId());
		WorkOrder workOrder = workOrderService.queryWorkOrderDetail(orderId);
		String siteAreaCode = workOrder.getSiteAreaCode();
		
		//查询市级编码
		CyclingAreaDO area = systemAreaDistrictService.queryCityByCode(siteAreaCode);
		String cityCode = "";
		if(null!=area) {
			cityCode = area.getAreaCode();
		}
		
		List<SystemUserPositionVO> userInfo = systemUserService.loadSystemUserInfosByParams(ComConstant.AREACODE_COUNTRY, cityCode, RoleEnum.CITYOPSMANAGE.getCode());
		List<AppWorkOrderFirstReviewOpsManagerVo> cityOpsManagerList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(userInfo)) {
			for(SystemUserPositionVO user : userInfo) {
		    	AppWorkOrderFirstReviewOpsManagerVo manager = new AppWorkOrderFirstReviewOpsManagerVo();
		    	manager.setCityOpsManagerName(user.getName());
		    	manager.setCityOpsManagerUserId(user.getUserId());
		    	manager.setPhone(user.getPhone());
		    	cityOpsManagerList.add(manager);
			}
		}
    	
		AppWorkOrderFirstReviewPageVo vo = new AppWorkOrderFirstReviewPageVo();
    	WorkOrderPullDownDTO<WorkOrderLevel> pullDown = new WorkOrderPullDownDTO<>();
    	List<WorkOrderPullDownDTO<WorkOrderLevel>> level = pullDown.getPullDownList(WorkOrderLevel.class);
    	
		vo.setCityOpsManagerList(cityOpsManagerList);
		vo.setLevel(level);
		
		return vo;
	}
	
	@Override
	public RsBody<Map<String, Object>> firstReviewPass(AppWorkOrderFlowFirstReviewPassDTO dto) {
		RsBody<Map<String,Object>> rsBody = new RsBody<>();
		
		WorkOrderUserDTO user = workOrderCommonService.assembleUserDTO();
		int orderId = Integer.valueOf(dto.getOrderId());
		int level = Integer.valueOf(dto.getLevel().getCode());
		int nextUserId = Integer.valueOf(dto.getCityOpsManagerUserId());
		workOrderFlowService.auditWorkOderOrder(orderId, level, nextUserId, user);
		
		rsBody.setCode(Status.Bu_SUCCESS.getName());
		rsBody.setMessage(Status.Bu_SUCCESS.getValue());
		return rsBody;
	}
	
	@Override
	public RsBody<Map<String, Object>> firstReviewClose(AppWorkOrderFlowFirstReviewCloseDTO dto) {
		RsBody<Map<String,Object>> rsBody = new RsBody<>();
		
		WorkOrderUserDTO user = workOrderCommonService.assembleUserDTO();
		int orderId = Integer.valueOf(dto.getOrderId());
		workOrderFlowService.closeWorkOrder(orderId, dto.getReason(), user);
		
		rsBody.setCode(Status.Bu_SUCCESS.getName());
		rsBody.setMessage(Status.Bu_SUCCESS.getValue());
		return rsBody;
	}
	
	@Override
	public AppWorkOrderAssignPageVo assignPage(WorkOrderBaseDTO dto) throws Exception {
		//查询工单所在区编码
		int orderId = Integer.valueOf(dto.getOrderId());
		WorkOrder workOrder = workOrderService.queryWorkOrderDetail(orderId);
		String siteAreaCode = workOrder.getSiteAreaCode();
		
		//查询市级编码
		CyclingAreaDO area = systemAreaDistrictService.queryCityByCode(siteAreaCode);
		String cityCode = "";
		if(null!=area) {
			cityCode = area.getAreaCode();
		}
		
		List<SystemUserPositionVO> userInfo = systemUserService.loadSystemUserInfosByParams(ComConstant.AREACODE_COUNTRY, cityCode, RoleEnum.OPSDIRECTOR.getCode());
		List<AppWorkOrderAssignOpsVo> cityOpsList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(userInfo)) {
			for(SystemUserPositionVO user : userInfo) {
				AppWorkOrderAssignOpsVo ops = new AppWorkOrderAssignOpsVo();
		    	ops.setCityOpsName(user.getName());
		    	ops.setCityOpsUserId(user.getUserId());
		    	ops.setPhone(user.getPhone());
		    	cityOpsList.add(ops);
			}
		}
    	
    	AppWorkOrderAssignPageVo vo = new AppWorkOrderAssignPageVo();
    	vo.setCityOpsList(cityOpsList);
		
		return vo;
	}
	
	@Override
	public RsBody<Map<String, Object>> assignConfirm(AppWorkOrderFlowAssignConfirmDTO dto) {
		RsBody<Map<String,Object>> rsBody = new RsBody<>();
		
		WorkOrderUserDTO user = workOrderCommonService.assembleUserDTO();
		int orderId = Integer.valueOf(dto.getOrderId());
		int nextUserId = Integer.valueOf(dto.getCityOpsUserId());
		workOrderFlowService.assignWorkOrder(orderId, nextUserId, user);
		
		rsBody.setCode(Status.Bu_SUCCESS.getName());
		rsBody.setMessage(Status.Bu_SUCCESS.getValue());
		return rsBody;
	}
	
	@Override
	public RsBody<Map<String, Object>> assignReject(AppWorkOrderFlowAssignRejectDTO dto) {
		RsBody<Map<String,Object>> rsBody = new RsBody<>();
		
		WorkOrderUserDTO user = workOrderCommonService.assembleUserDTO();
		int orderId = Integer.valueOf(dto.getOrderId());
		workOrderFlowService.rejectWorkOrder(orderId, dto.getReason(), user);
		
		rsBody.setCode(Status.Bu_SUCCESS.getName());
		rsBody.setMessage(Status.Bu_SUCCESS.getValue());
		return rsBody;
	}
	
	@Override
	public RsBody<Map<String, Object>> signConfirm(WorkOrderBaseDTO dto) {
		RsBody<Map<String,Object>> rsBody = new RsBody<>();
		
		WorkOrderUserDTO user = workOrderCommonService.assembleUserDTO();
		int orderId = Integer.valueOf(dto.getOrderId());
		workOrderFlowService.agreeWorkOderOrder(orderId, user);
		
		rsBody.setCode(Status.Bu_SUCCESS.getName());
		rsBody.setMessage(Status.Bu_SUCCESS.getValue());
		return rsBody;
	}
	
	@Override
	public RsBody<Map<String, Object>> signReject(AppWorkOrderFlowSignRejectDTO dto) {
		RsBody<Map<String,Object>> rsBody = new RsBody<>();
		
		WorkOrderUserDTO user = workOrderCommonService.assembleUserDTO();
		int orderId = Integer.valueOf(dto.getOrderId());
		workOrderFlowService.revokeWorkOderOrder(orderId, dto.getReason(), user);
		
		rsBody.setCode(Status.Bu_SUCCESS.getName());
		rsBody.setMessage(Status.Bu_SUCCESS.getValue());
		return rsBody;
	}
	
	@Override
	public RsBody<Map<String, Object>> departConfirm(WorkOrderBaseDTO dto) {
		RsBody<Map<String,Object>> rsBody = new RsBody<>();
		
		WorkOrderUserDTO user = workOrderCommonService.assembleUserDTO();
		int orderId = Integer.valueOf(dto.getOrderId());
		workOrderFlowService.startWorkOrder(orderId, user);
		
		rsBody.setCode(Status.Bu_SUCCESS.getName());
		rsBody.setMessage(Status.Bu_SUCCESS.getValue());
		return rsBody;
	}
	
	@Override
	public RsBody<Map<String, Object>> departReject(AppWorkOrderFlowDepartRejectDTO dto) {
		RsBody<Map<String,Object>> rsBody = new RsBody<>();
		
		WorkOrderUserDTO user = workOrderCommonService.assembleUserDTO();
		int orderId = Integer.valueOf(dto.getOrderId());
		workOrderFlowService.transferWorkOrder(orderId, dto.getReason(), user);
		
		rsBody.setCode(Status.Bu_SUCCESS.getName());
		rsBody.setMessage(Status.Bu_SUCCESS.getValue());
		return rsBody;
	}
	
	@Override
	public RsBody<Map<String, Object>> signInConfirm(AppWorkOrderFlowSignInDTO dto) {
		RsBody<Map<String,Object>> rsBody = new RsBody<>();
		
		WorkOrderUserDTO user = workOrderCommonService.assembleUserDTO();
		int orderId = Integer.valueOf(dto.getOrderId());
		String signInLongitude = dto.getSignInLongitude();
		String signInLatitude = dto.getSignInLatitude();
		workOrderFlowService.signWorkOrder(orderId, Double.valueOf(signInLongitude), Double.valueOf(signInLatitude), user);
		
		rsBody.setCode(Status.Bu_SUCCESS.getName());
		rsBody.setMessage(Status.Bu_SUCCESS.getValue());
		return rsBody;
	}
	
	@Override
	public AppWorkOrderDealRecordPageVo dealRecordPage() throws Exception {
    	AppWorkOrderDealRecordPageVo vo = new AppWorkOrderDealRecordPageVo();
    	
    	List<WorkOrderDeviceModule> failModules = workOrderDeviceModuleService.findDeviceModuleList();
    	List<DataDict> failModuleDict = DataDictUtil.covertDataDict(failModules);
    	vo.setFailModule(failModuleDict);
    	
    	if(CollectionUtils.isNotEmpty(failModules)) {
    		WorkOrderDeviceModule module1 = failModules.get(0);
        	List<WorkOrderDeviceParts> parts = workOrderDevicePartsService.findDevicePartsList(module1.getId());
        	List<DataDict> changeParts = DataDictUtil.covertDataDict(parts);
        	vo.setChangeParts(changeParts);
    	}
    	
    	WorkOrderPullDownDTO<WorkOrderFailType> pullDown3 = new WorkOrderPullDownDTO<>();
    	List<WorkOrderPullDownDTO<WorkOrderFailType>> failType = pullDown3.getPullDownList(WorkOrderFailType.class);
    	vo.setFailType(failType);
    	
    	WorkOrderPullDownDTO<WorkOrderType> pullDown4 = new WorkOrderPullDownDTO<>();
    	List<WorkOrderPullDownDTO<WorkOrderType>> type = pullDown4.getPullDownList(WorkOrderType.class);
    	vo.setType(type);
		
		return vo;
	}
	
	@Override
	public RsBody<Map<String, Object>> dealRecordSubmit(AppWorkOrderDealRecordDTO dto) {
		RsBody<Map<String,Object>> rsBody = new RsBody<>();
		
		WorkOrderUserDTO user = workOrderCommonService.assembleUserDTO();
		int orderId = Integer.valueOf(dto.getOrderId());
		WorkOrderResult workOrderResult = new WorkOrderResult();
		List<String> attachments = new ArrayList<>();
		attachments.add(dto.getUrl());
		workOrderResult.setAttachments(attachments);
		workOrderResult.setChangeParts(dto.getChangeParts().getName());
		workOrderResult.setCreatedUser(user.getUserName());
		workOrderResult.setCreatedUserId(user.getUserId());
		workOrderResult.setFaultModule(dto.getFailModule().getName());
		workOrderResult.setFaultType(Integer.valueOf(dto.getFailType().getCode()));
		workOrderResult.setOrderId(orderId);
		workOrderResult.setOrderType(Integer.valueOf(dto.getType().getCode()));
		workOrderResult.setPhenomena(dto.getActualSituation());
		workOrderResult.setReason(dto.getAnalysis());
		//workOrderResult.setRemark();
		workOrderResult.setSolution(dto.getSolution());
		workOrderFlowService.processWorkOrder(orderId, workOrderResult, user);
		
		rsBody.setCode(Status.Bu_SUCCESS.getName());
		rsBody.setMessage(Status.Bu_SUCCESS.getValue());
		return rsBody;
	}
	
	@Override
	public AppWorkOrderDealMaterialPageVo dealMaterialPage() {
		AppWorkOrderDealMaterialPageVo vo = new AppWorkOrderDealMaterialPageVo();
		
    	List<WorkOrderDeviceModule> failModules = workOrderDeviceModuleService.findDeviceModuleList();
    	List<DataDict> failModuleDict = DataDictUtil.covertDataDict(failModules);
    	vo.setFailModule(failModuleDict);
    	
    	if(CollectionUtils.isNotEmpty(failModules)) {
    		WorkOrderDeviceModule module1 = failModules.get(0);
        	List<WorkOrderDeviceParts> parts = workOrderDevicePartsService.findDevicePartsList(module1.getId());
        	List<DataDict> changeParts = DataDictUtil.covertDataDict(parts);
        	vo.setChangeParts(changeParts);
    	}
		
		return vo;
	}
	
	@Override
	public RsBody<Map<String, Object>> dealMaterialSubmit(AppWorkOrderDealMaterialDTO dto) {
		RsBody<Map<String,Object>> rsBody = new RsBody<>();
		
		WorkOrderUserDTO user = workOrderCommonService.assembleUserDTO();
		int orderId = Integer.valueOf(dto.getOrderId());
		
		WorkOrderMaterielApply workOrderMaterielApply = new WorkOrderMaterielApply();
		workOrderMaterielApply.setCreatedUserId(user.getUserId());
		workOrderMaterielApply.setModuleId(Integer.valueOf(dto.getFailModule().getCode()));
		workOrderMaterielApply.setModuleName(dto.getFailModule().getName());
		workOrderMaterielApply.setMaterielName(dto.getChangeParts().getName());
		workOrderMaterielApply.setMaterielNo(dto.getChangeParts().getCode());
		workOrderMaterielApply.setOrderId(orderId);
		workOrderMaterielApply.setReceiveAddress(dto.getAddress());
		workOrderMaterielApply.setReceiveCityCode(dto.getCityCode());
		workOrderMaterielApply.setReceiveDistrictCode(dto.getDistrictCode());
		workOrderMaterielApply.setReceivePerson(dto.getContactPersonName());
		workOrderMaterielApply.setReceivePhone(dto.getContactPersonPhone());
		workOrderMaterielApply.setReceiveProvinceCode(dto.getProvinceCode());
		//workOrderMaterielApply.setRemark(dto.get);
		workOrderFlowService.submitMaterielApply(orderId, workOrderMaterielApply, user);
		
		rsBody.setCode(Status.Bu_SUCCESS.getName());
		rsBody.setMessage(Status.Bu_SUCCESS.getValue());
		return rsBody;
	}
	
	@Override
	public RsBody<Map<String, Object>> materialApprove(WorkOrderBaseDTO dto) {
		RsBody<Map<String,Object>> rsBody = new RsBody<>();
		
		WorkOrderUserDTO user = workOrderCommonService.assembleUserDTO();
		int orderId = Integer.valueOf(dto.getOrderId());
		workOrderFlowService.approveMaterielApply(orderId, user);
		
		rsBody.setCode(Status.Bu_SUCCESS.getName());
		rsBody.setMessage(Status.Bu_SUCCESS.getValue());
		return rsBody;
	}
	
	@Override
	public RsBody<Map<String, Object>> materialReview(WorkOrderBaseDTO dto) {
		RsBody<Map<String,Object>> rsBody = new RsBody<>();
		
		WorkOrderUserDTO user = workOrderCommonService.assembleUserDTO();
		int orderId = Integer.valueOf(dto.getOrderId());
		workOrderFlowService.auditMaterielApply(orderId, user);
		
		rsBody.setCode(Status.Bu_SUCCESS.getName());
		rsBody.setMessage(Status.Bu_SUCCESS.getValue());
		return rsBody;
	}
	
	@Override
	public RsBody<Map<String, Object>> materialDeliver(WorkOrderBaseDTO dto) {
		RsBody<Map<String,Object>> rsBody = new RsBody<>();
		
		WorkOrderUserDTO user = workOrderCommonService.assembleUserDTO();
		int orderId = Integer.valueOf(dto.getOrderId());
		workOrderFlowService.deliveryMateriel(orderId, user);
		
		rsBody.setCode(Status.Bu_SUCCESS.getName());
		rsBody.setMessage(Status.Bu_SUCCESS.getValue());
		return rsBody;
	}
	
	@Override
	public RsBody<Map<String, Object>> materialReceive(WorkOrderBaseDTO dto) {
		RsBody<Map<String,Object>> rsBody = new RsBody<>();
		
		WorkOrderUserDTO user = workOrderCommonService.assembleUserDTO();
		int orderId = Integer.valueOf(dto.getOrderId());
		workOrderFlowService.recevieMateriel(orderId, user);
		
		rsBody.setCode(Status.Bu_SUCCESS.getName());
		rsBody.setMessage(Status.Bu_SUCCESS.getValue());
		return rsBody;
	}
	
	@Override
	public RsBody<Map<String, Object>> audit(WorkOrderBaseDTO dto) {
		RsBody<Map<String,Object>> rsBody = new RsBody<>();
		
		WorkOrderUserDTO user = workOrderCommonService.assembleUserDTO();
		int orderId = Integer.valueOf(dto.getOrderId());
		workOrderFlowService.confirmWorkOrder(orderId, user);
		
		rsBody.setCode(Status.Bu_SUCCESS.getName());
		rsBody.setMessage(Status.Bu_SUCCESS.getValue());
		return rsBody;
	}
	
	@Override
	public RsBody<Map<String, Object>> reAudit(WorkOrderBaseDTO dto) {
		RsBody<Map<String,Object>> rsBody = new RsBody<>();
		
		WorkOrderUserDTO user = workOrderCommonService.assembleUserDTO();
		int orderId = Integer.valueOf(dto.getOrderId());
		workOrderFlowService.reviewWorkOrder(orderId, user);
		
		rsBody.setCode(Status.Bu_SUCCESS.getName());
		rsBody.setMessage(Status.Bu_SUCCESS.getValue());
		return rsBody;
	}

}
