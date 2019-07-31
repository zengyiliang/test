package com.xhg.ops.workorders.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhg.ops.common.ComConstant;
import com.xhg.ops.system.dto.SystemUserLoadDTO;
import com.xhg.ops.system.entity.SystemRoleDO;
import com.xhg.ops.system.filter.RoleContext;
import com.xhg.ops.system.filter.UserContext;
import com.xhg.ops.system.model.SystemUser;
import com.xhg.ops.workorders.dto.WorkOrderUserDTO;
import com.xhg.ops.workorders.service.WorkOrderCommonService;
import com.xhg.ops.workorders.service.WorkOrderService;

@Service("workOrderCommonService")
public class WorkOrderCommonServiceImpl implements WorkOrderCommonService {

	private Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	private WorkOrderService workOrderService;

	@Override
	public WorkOrderUserDTO assembleUserDTO() {
		SystemUser sysUser = UserContext.getUser();
		WorkOrderUserDTO user = new WorkOrderUserDTO();
		if(sysUser!=null) {
			List<SystemRoleDO> roleList = RoleContext.getRole();
			if(CollectionUtils.isNotEmpty(roleList)) {
				SystemRoleDO role = roleList.get(0);
				if(null!=role) {
					user.setRoleId(role.getRoleId());
					user.setRoleName(role.getRoleName());
				}
			}
			user.setUserId(sysUser.getId());
			user.setUserName(sysUser.getName());
			user.setPhone(sysUser.getPhone());
		}
		
		return user;
	}
	
	@Override
	public SystemUserLoadDTO assembleSystemUserLoadDTO(String countryCode, String areaCode, String name, String roleCode, Integer currentPage, Integer pageSize) {
		SystemUserLoadDTO systemUserLoadDTO = new SystemUserLoadDTO();
		systemUserLoadDTO.setAreaCode(areaCode);
		systemUserLoadDTO.setCountryCode(countryCode);
		systemUserLoadDTO.setName(name);
		systemUserLoadDTO.setRoleCode(roleCode);
		if(currentPage!=null) {
			systemUserLoadDTO.setCurrentPage(currentPage);
		}
		if(pageSize!=null) {
			systemUserLoadDTO.setPageSize(pageSize);
		}
		
		return systemUserLoadDTO;
	}
	
	@Override
	public String getShowMaterialApplyInfo(int orderId) {
		int count = workOrderService.countWorkOrderMaterielApply(orderId);
		if(count>0) {
			return ComConstant.SHOW_Y;
		} else {
			return ComConstant.SHOW_N;
		}
	}
	
	@Override
	public String getShowOrderRecord(int orderId) {
		int count = workOrderService.countWorkOrderResult(orderId);
		if(count>0) {
			return ComConstant.SHOW_Y;
		} else {
			return ComConstant.SHOW_N;
		}
	}

}
