package com.xhg.ops.workorders.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhg.core.web.vo.RsBody;
import com.xhg.core.web.vo.Status;
import com.xhg.ops.workorders.dto.TerminalWorkOrderSubmitDTO;
import com.xhg.ops.workorders.dto.WorkOrderUserDTO;
import com.xhg.ops.workorders.enums.WorkOrderDataSource;
import com.xhg.ops.workorders.enums.WorkOrderType;
import com.xhg.ops.workorders.model.WorkOrder;
import com.xhg.ops.workorders.service.TerminalWorkOrderService;
import com.xhg.ops.workorders.service.WorkOrderFlowService;

@Service("terminalWorkOrderService")
public class TerminalWorkOrderServiceImpl implements TerminalWorkOrderService {

	@Autowired
	private WorkOrderFlowService workOrderFlowService;
	
	private WorkOrderUserDTO user;
	
	@Override
	public RsBody<Map<String, Object>> batchSubmitSiteFault(List<TerminalWorkOrderSubmitDTO> dtoList) {
		RsBody<Map<String,Object>> rsBody = new RsBody<>();
		
		if(CollectionUtils.isNotEmpty(dtoList)) {
			dtoList.forEach(dto -> {
				this.createWorkOrder(dto);
			});
		}
		
		
		rsBody.setCode(Status.Bu_SUCCESS.getName());
		rsBody.setMessage(Status.Bu_SUCCESS.getValue());
		return rsBody;
	}
	
	private WorkOrder assembleWorkOrder(TerminalWorkOrderSubmitDTO dto) {
		WorkOrder workOrder = new WorkOrder();
		//workOrder.setAttachments(dto.getAttachmentUrls());
		//workOrder.setContactInfo(dto.getContactInfo());
		workOrder.setCreatedUser(user.getUserName());
		workOrder.setCreatedUserId(user.getUserId());
		workOrder.setDataSource(WorkOrderDataSource.BREAKDOWN_REPORT.getCode());
		workOrder.setDeviceId(dto.getDeviceId());
		workOrder.setOrderType(WorkOrderType.BREAKDOWN_REPAIR.getCode());
		workOrder.setOrderTitle(dto.getDescription());
		workOrder.setSiteAddress(dto.getDeviceAddress());
		workOrder.setSiteCode(dto.getSiteCode());
		workOrder.setSiteAreaCode(dto.getAreaCode());
		workOrder.setSiteLongitude(dto.getLongitude());
		workOrder.setSiteLatitude(dto.getLatitude());
		workOrder.setProcUserId(user.getUserId());
		workOrder.setLevel(0);
		workOrder.setFaultId(dto.getFaultId());
		
		return workOrder;
	}
	
	private void initUser() {
		if(user!=null) {
			return;
		}
		
		user = new WorkOrderUserDTO();
		user.setPhone("");
		user.setRoleId(0);
		user.setRoleName("");
		user.setUserId(0);
		user.setUserName("工单系统");
	}
	
	private void createWorkOrder(TerminalWorkOrderSubmitDTO dto) {
		this.initUser();
		WorkOrder workOrder = this.assembleWorkOrder(dto);
		workOrderFlowService.createWorkOrder(workOrder, user);
	}

	@Override
	public RsBody<Map<String, Object>> submitSiteFault(TerminalWorkOrderSubmitDTO dto) {
		RsBody<Map<String,Object>> rsBody = new RsBody<>();
		
		this.createWorkOrder(dto);
		
		rsBody.setCode(Status.Bu_SUCCESS.getName());
		rsBody.setMessage(Status.Bu_SUCCESS.getValue());
		return rsBody;
	}
	
}
