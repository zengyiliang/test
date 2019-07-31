package com.xhg.ops.workorders.service.impl;

import com.xhg.core.web.vo.RsBody;
import com.xhg.core.web.vo.Status;
import com.xhg.ops.common.ComConstant;
import com.xhg.ops.enums.RoleEnum;
import com.xhg.ops.system.dto.SystemUserLoadDTO;
import com.xhg.ops.system.model.CyclingAreaDO;
import com.xhg.ops.system.service.SystemAreaDistrictService;
import com.xhg.ops.system.service.SystemUserService;
import com.xhg.ops.system.vo.user.SystemUserPositionVO;
import com.xhg.ops.workorders.dto.WebWorkOrderCityOpsManagerSearchDTO;
import com.xhg.ops.workorders.dto.WebWorkOrderCityOpsSearchDTO;
import com.xhg.ops.workorders.dto.WebWorkOrderCreateDTO;
import com.xhg.ops.workorders.dto.WebWorkOrderDealMaterialDTO;
import com.xhg.ops.workorders.dto.WebWorkOrderDealRecordDTO;
import com.xhg.ops.workorders.dto.WebWorkOrderDeviceAddressQueryDTO;
import com.xhg.ops.workorders.dto.WebWorkOrderFlowAssignConfirmDTO;
import com.xhg.ops.workorders.dto.WebWorkOrderFlowAssignRejectDTO;
import com.xhg.ops.workorders.dto.WebWorkOrderFlowDepartRejectDTO;
import com.xhg.ops.workorders.dto.WebWorkOrderFlowFirstReviewCloseDTO;
import com.xhg.ops.workorders.dto.WebWorkOrderFlowFirstReviewPassDTO;
import com.xhg.ops.workorders.dto.WebWorkOrderFlowSignRejectDTO;
import com.xhg.ops.workorders.dto.WorkOrderBaseDTO;
import com.xhg.ops.workorders.dto.WorkOrderPullDownDTO;
import com.xhg.ops.workorders.dto.WorkOrderUserDTO;
import com.xhg.ops.workorders.enums.WorkOrderDataSource;
import com.xhg.ops.workorders.enums.WorkOrderFailType;
import com.xhg.ops.workorders.enums.WorkOrderLevel;
import com.xhg.ops.workorders.enums.WorkOrderType;
import com.xhg.ops.workorders.model.WorkOrder;
import com.xhg.ops.workorders.model.WorkOrderDeviceModule;
import com.xhg.ops.workorders.model.WorkOrderDeviceParts;
import com.xhg.ops.workorders.model.WorkOrderMaterielApply;
import com.xhg.ops.workorders.model.WorkOrderResult;
import com.xhg.ops.workorders.service.WebWorkOrderFlowService;
import com.xhg.ops.workorders.service.WorkOrderCommonService;
import com.xhg.ops.workorders.service.WorkOrderDeviceModuleService;
import com.xhg.ops.workorders.service.WorkOrderDevicePartsService;
import com.xhg.ops.workorders.service.WorkOrderFlowService;
import com.xhg.ops.workorders.service.WorkOrderService;
import com.xhg.ops.workorders.util.datadict.DataDict;
import com.xhg.ops.workorders.util.datadict.DataDictUtil;
import com.xhg.ops.workorders.vo.WebWorkOrderAssignOpsVo;
import com.xhg.ops.workorders.vo.WebWorkOrderAssignPageVo;
import com.xhg.ops.workorders.vo.WebWorkOrderCreatePageVo;
import com.xhg.ops.workorders.vo.WebWorkOrderDealMaterialPageVo;
import com.xhg.ops.workorders.vo.WebWorkOrderDealRecordPageVo;
import com.xhg.ops.workorders.vo.WebWorkOrderDeviceAddressVo;
import com.xhg.ops.workorders.vo.WebWorkOrderFirstReviewOpsManagerVo;
import com.xhg.ops.workorders.vo.WebWorkOrderFirstReviewPageVo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("webWorkOrderFlowService")
public class WebWorkOrderFlowServiceImpl implements WebWorkOrderFlowService {

	@Autowired
	private WorkOrderService workOrderService;
	
	@Autowired
	private WorkOrderFlowService workOrderFlowService;
	
    @Autowired
    private SystemAreaDistrictService systemAreaDistrictService;
    
	@Autowired
	private SystemUserService systemUserService;
	
	@Autowired
	private WorkOrderCommonService workOrderCommonService;
	
    @Autowired
    private WorkOrderDeviceModuleService workOrderDeviceModuleService;
    
    @Autowired
    private WorkOrderDevicePartsService workOrderDevicePartsService;
	
	@Override
	public WebWorkOrderCreatePageVo createOrderPage() throws Exception {
    	WebWorkOrderCreatePageVo vo = new WebWorkOrderCreatePageVo();
    	
    	WorkOrderPullDownDTO<WorkOrderDataSource> pullDown1 = new WorkOrderPullDownDTO<>();
    	List<WorkOrderPullDownDTO<WorkOrderDataSource>> source = pullDown1.getPullDownList(WorkOrderDataSource.class);
    	vo.setSource(source);
    	
    	WorkOrderPullDownDTO<WorkOrderType> pullDown2 = new WorkOrderPullDownDTO<>();
    	List<WorkOrderPullDownDTO<WorkOrderType>> type = pullDown2.getPullDownList(WorkOrderType.class);
    	vo.setType(type);
		
		return vo;
	}
	
	@Override
	@Deprecated
	public WebWorkOrderDeviceAddressVo queryDeviceAddress(WebWorkOrderDeviceAddressQueryDTO dto) {
		//查询工单所在区编码
		String deviceId = dto.getDeviceId();
		
        //Site site = siteService.querySiteByDeviceId(deviceId);
        String deviceAddress = "北山街道曙光路118号，上街区，21栋";
/*        if (site != null) {
        	deviceAddress = site.getSiteAddress();
        }*/
    	
    	WebWorkOrderDeviceAddressVo vo = new WebWorkOrderDeviceAddressVo();
    	vo.setDeviceAddress(deviceAddress);
    	
		return vo;
	}
	
	@Override
	public RsBody<Map<String, Object>> createOrderSubmit(WebWorkOrderCreateDTO dto) {
		RsBody<Map<String,Object>> rsBody = new RsBody<>();
		
		WorkOrderUserDTO user = workOrderCommonService.assembleUserDTO();
		//查询工单所在区编码
		String deviceId = dto.getDeviceId();
		
		//MOCK数据
/*        String deviceAddress = "北山街道曙光路118号，上街区，21栋";
        String areaCode = "330106000000";
        String latitude = "30.266779";
        String longitude = "120.144321";
        String siteCode = "057100001";*/
        
		WorkOrder workOrder = new WorkOrder();
		workOrder.setAttachments(dto.getAttachmentUrls());
		workOrder.setContactInfo(dto.getContactInfo());
		workOrder.setCreatedUser(user.getUserName());
		workOrder.setCreatedUserId(user.getUserId());
		workOrder.setDataSource(Integer.valueOf(dto.getSource().getCode()));
		workOrder.setDeviceId(deviceId);
		workOrder.setOrderType(Integer.valueOf(dto.getType().getCode()));
		workOrder.setOrderTitle(dto.getDescription());
		workOrder.setSiteAddress(dto.getDeviceAddress());
		workOrder.setSiteCode(dto.getSiteCode());
		workOrder.setSiteAreaCode(dto.getAreaCode());
		workOrder.setSiteLongitude(dto.getLongitude());
		workOrder.setSiteLatitude(dto.getLatitude());
		workOrder.setProcUserId(user.getUserId());
		workOrder.setLevel(0);
		
		workOrderFlowService.createWorkOrder(workOrder, user);
		
		rsBody.setCode(Status.Bu_SUCCESS.getName());
		rsBody.setMessage(Status.Bu_SUCCESS.getValue());
		return rsBody;
	}
	
	@Override
	public WebWorkOrderFirstReviewPageVo firstReviewPage(WorkOrderBaseDTO dto) throws Exception {
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
		List<WebWorkOrderFirstReviewOpsManagerVo> cityOpsManagerList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(userInfo)) {
			for(SystemUserPositionVO user : userInfo) {
				WebWorkOrderFirstReviewOpsManagerVo manager = new WebWorkOrderFirstReviewOpsManagerVo();
		    	manager.setCityOpsManagerName(user.getName());
		    	manager.setCityOpsManagerUserId(user.getUserId());
		    	manager.setPhone(user.getPhone());
		    	cityOpsManagerList.add(manager);
			}
		}
    	
		WebWorkOrderFirstReviewPageVo vo = new WebWorkOrderFirstReviewPageVo();
    	WorkOrderPullDownDTO<WorkOrderLevel> pullDown = new WorkOrderPullDownDTO<>();
    	List<WorkOrderPullDownDTO<WorkOrderLevel>> level = pullDown.getPullDownList(WorkOrderLevel.class);
    	
		vo.setCityOpsManagerList(cityOpsManagerList);
		vo.setLevel(level);
		
		return vo;
	}
	
	@Override
	public RsBody<Map<String, Object>> firstReviewPass(WebWorkOrderFlowFirstReviewPassDTO dto) {
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
	public RsBody<Map<String, Object>> firstReviewClose(WebWorkOrderFlowFirstReviewCloseDTO dto) {
		RsBody<Map<String,Object>> rsBody = new RsBody<>();
		
		WorkOrderUserDTO user = workOrderCommonService.assembleUserDTO();
		int orderId = Integer.valueOf(dto.getOrderId());
		workOrderFlowService.closeWorkOrder(orderId, dto.getReason(), user);
		
		rsBody.setCode(Status.Bu_SUCCESS.getName());
		rsBody.setMessage(Status.Bu_SUCCESS.getValue());
		return rsBody;
	}
	
	@Override
	public WebWorkOrderAssignPageVo assignPage(WorkOrderBaseDTO dto) throws Exception {
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
		List<WebWorkOrderAssignOpsVo> cityOpsList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(userInfo)) {
			for(SystemUserPositionVO user : userInfo) {
				WebWorkOrderAssignOpsVo ops = new WebWorkOrderAssignOpsVo();
		    	ops.setCityOpsName(user.getName());
		    	ops.setCityOpsUserId(user.getUserId());
		    	ops.setPhone(user.getPhone());
		    	cityOpsList.add(ops);
			}
		}
    	
    	WebWorkOrderAssignPageVo vo = new WebWorkOrderAssignPageVo();
    	vo.setCityOpsList(cityOpsList);
		
		return vo;
	}
	
	@Override
	public RsBody<Map<String, Object>> assignConfirm(WebWorkOrderFlowAssignConfirmDTO dto) {
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
	public RsBody<Map<String, Object>> assignReject(WebWorkOrderFlowAssignRejectDTO dto) {
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
	public RsBody<Map<String, Object>> signReject(WebWorkOrderFlowSignRejectDTO dto) {
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
	public RsBody<Map<String, Object>> departReject(WebWorkOrderFlowDepartRejectDTO dto) {
		RsBody<Map<String,Object>> rsBody = new RsBody<>();
		
		WorkOrderUserDTO user = workOrderCommonService.assembleUserDTO();
		int orderId = Integer.valueOf(dto.getOrderId());
		workOrderFlowService.transferWorkOrder(orderId, dto.getReason(), user);
		
		rsBody.setCode(Status.Bu_SUCCESS.getName());
		rsBody.setMessage(Status.Bu_SUCCESS.getValue());
		return rsBody;
	}
	
	@Override
	public WebWorkOrderDealRecordPageVo dealRecordPage() throws Exception {
    	WebWorkOrderDealRecordPageVo vo = new WebWorkOrderDealRecordPageVo();
        
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
	public RsBody<Map<String, Object>> dealRecordSubmit(WebWorkOrderDealRecordDTO dto) {
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
	public WebWorkOrderDealMaterialPageVo dealMaterialPage() {
		WebWorkOrderDealMaterialPageVo vo = new WebWorkOrderDealMaterialPageVo();
		
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
	public RsBody<Map<String, Object>> dealMaterialSubmit(WebWorkOrderDealMaterialDTO dto) {
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

	@Override
	public List<WebWorkOrderFirstReviewOpsManagerVo> firstReviewSearchCityOpsManager(
			WebWorkOrderCityOpsManagerSearchDTO dto) throws Exception {
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
		
		SystemUserLoadDTO systemUserLoadDTO = workOrderCommonService.assembleSystemUserLoadDTO(ComConstant.AREACODE_COUNTRY, cityCode, dto.getName(), 
				RoleEnum.CITYOPSMANAGE.getCode(), null, null);
		
		List<SystemUserPositionVO> userInfo = systemUserService.querySystemUserInfosByParams(systemUserLoadDTO);
		List<WebWorkOrderFirstReviewOpsManagerVo> cityOpsManagerList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(userInfo)) {
			for(SystemUserPositionVO user : userInfo) {
				WebWorkOrderFirstReviewOpsManagerVo manager = new WebWorkOrderFirstReviewOpsManagerVo();
		    	manager.setCityOpsManagerName(user.getName());
		    	manager.setCityOpsManagerUserId(user.getUserId());
		    	manager.setPhone(user.getPhone());
		    	cityOpsManagerList.add(manager);
			}
		}
    	
		return cityOpsManagerList;
	}

	@Override
	public List<WebWorkOrderAssignOpsVo> assignSearchOps(WebWorkOrderCityOpsSearchDTO dto) throws Exception {
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
		
		SystemUserLoadDTO systemUserLoadDTO = workOrderCommonService.assembleSystemUserLoadDTO(ComConstant.AREACODE_COUNTRY, cityCode, dto.getName(), 
				RoleEnum.OPSDIRECTOR.getCode(), null, null);
		
		List<SystemUserPositionVO> userInfo = systemUserService.querySystemUserInfosByParams(systemUserLoadDTO);
		List<WebWorkOrderAssignOpsVo> cityOpsList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(userInfo)) {
			for(SystemUserPositionVO user : userInfo) {
				WebWorkOrderAssignOpsVo ops = new WebWorkOrderAssignOpsVo();
		    	ops.setCityOpsName(user.getName());
		    	ops.setCityOpsUserId(user.getUserId());
		    	ops.setPhone(user.getPhone());
		    	cityOpsList.add(ops);
			}
		}
    	
		return cityOpsList;
	}

}
