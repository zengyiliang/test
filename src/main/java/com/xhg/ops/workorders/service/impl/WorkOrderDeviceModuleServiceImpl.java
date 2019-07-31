package com.xhg.ops.workorders.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xhg.core.web.vo.PagerResult;
import com.xhg.ops.common.ComConstant;
import com.xhg.ops.workorders.dao.WorkOrderDeviceModuleDao;
import com.xhg.ops.workorders.dto.WorkOrderDeviceModuleDto;
import com.xhg.ops.workorders.model.WorkOrderDeviceModule;
import com.xhg.ops.workorders.service.WorkOrderDeviceModuleService;

/**
 * 工单设备模块逻辑处理接口实现
 * @author 刘涛
 * @date 2018年7月23日
 */
@Service
public class WorkOrderDeviceModuleServiceImpl implements WorkOrderDeviceModuleService {

	@Autowired
	private WorkOrderDeviceModuleDao workOrderDeviceModuleDao;
	
	@Override
	public void insert(WorkOrderDeviceModule module, Integer userId) {
		module.setEnable(ComConstant.ENABLE_YES);
		module.setCreatedTime(new Date());
		module.setUpdatedTime(new Date());
		module.setCreatedUserId(userId == null ? 0 : userId);
		module.setUpdatedUserId(userId);
		workOrderDeviceModuleDao.insert(module);
	}

	@Override
	public void update(WorkOrderDeviceModule module, Integer userId) {
		module.setUpdatedTime(new Date());
		module.setUpdatedUserId(userId == null ? 0 : userId);
		workOrderDeviceModuleDao.update(module);
	}

	@Override
	public void delete(int id, Integer userId) {
		workOrderDeviceModuleDao.delete(id, userId);
	}

	@Override
	public List<WorkOrderDeviceModule> findDeviceModuleList() {
		return workOrderDeviceModuleDao.findDeviceModuleList();
	}

	@Override
	public PagerResult<WorkOrderDeviceModuleDto> findDeviceModuleDtoListPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<WorkOrderDeviceModuleDto> list = workOrderDeviceModuleDao.findDeviceModuleDtoList();
		return new PagerResult<>(new PageInfo<>(list));
	}
	
	@Override
	public List<WorkOrderDeviceModuleDto> findDeviceModuleDtoList() {
		List<WorkOrderDeviceModuleDto> list = workOrderDeviceModuleDao.findDeviceModuleDtoList();
		return list;
	}

}
