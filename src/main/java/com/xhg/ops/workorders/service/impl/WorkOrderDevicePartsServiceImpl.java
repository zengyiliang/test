package com.xhg.ops.workorders.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xhg.core.web.vo.PagerResult;
import com.xhg.ops.common.ComConstant;
import com.xhg.ops.workorders.dao.WorkOrderDevicePartsDao;
import com.xhg.ops.workorders.dto.WorkOrderDevicePartsDto;
import com.xhg.ops.workorders.model.WorkOrderDeviceParts;
import com.xhg.ops.workorders.service.WorkOrderDevicePartsService;

/**
 * 工单设备模块逻辑处理接口实现
 * @author 刘涛
 * @date 2018年7月21日
 */
@Service
public class WorkOrderDevicePartsServiceImpl implements WorkOrderDevicePartsService {

	@Autowired
	private WorkOrderDevicePartsDao workOrderDevicePartsDao;
	
	@Override
	public void insert(WorkOrderDeviceParts parts, Integer userId) {
		parts.setEnable(ComConstant.ENABLE_YES);
		parts.setCreatedTime(new Date());
		parts.setUpdatedTime(new Date());
		parts.setCreatedUserId(userId == null ? 0 : userId);
		parts.setUpdatedUserId(userId);
		workOrderDevicePartsDao.insert(parts);
	}

	@Override
	public void update(WorkOrderDeviceParts parts, Integer userId) {
		parts.setUpdatedTime(new Date());
		parts.setUpdatedUserId(userId == null ? 0 : userId);
		workOrderDevicePartsDao.update(parts);
	}

	@Override
	public void delete(int id, Integer userId) {
		workOrderDevicePartsDao.delete(id, userId);
	}

	@Override
	public List<WorkOrderDeviceParts> findDevicePartsList(int moduleId) {
		return workOrderDevicePartsDao.findDevicePartsListByModuleId(moduleId);
	}


	@Override
	public PagerResult<WorkOrderDevicePartsDto> findDevicePartsDtoListPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<WorkOrderDevicePartsDto> list = workOrderDevicePartsDao.findDeviceModuleDtoListByModuleId();
		return new PagerResult<>(new PageInfo<>(list));
	}

	@Override
	public List<WorkOrderDeviceParts> findAllDevicePartsList() {
		List<WorkOrderDeviceParts> list = workOrderDevicePartsDao.findAllDevicePartsList();
		return list;
	}
	
}
