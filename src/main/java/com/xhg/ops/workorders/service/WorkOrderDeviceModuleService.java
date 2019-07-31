package com.xhg.ops.workorders.service;

import java.util.List;

import com.xhg.core.web.vo.PagerResult;
import com.xhg.ops.workorders.dto.WorkOrderDeviceModuleDto;
import com.xhg.ops.workorders.model.WorkOrderDeviceModule;

/**
 * 工单设备模块逻辑处理接口
 * @author 刘涛
 * @date 2018年7月21日
 */
public interface WorkOrderDeviceModuleService {

	/**
	 * 新增
	 * @param module
	 * @param userId
	 */
	void insert(WorkOrderDeviceModule module, Integer userId);
	
	/**
	 * 更新数据
	 * @param module
	 * @param userId
	 */
	void update(WorkOrderDeviceModule module, Integer userId);
	
	/**
	 * 删除
	 * @param id
	 * @param userId
	 */
	void delete(int id, Integer userId);
	
	/**
	 * 获取数据列表
	 * @param dataType
	 * @return
	 */
	List<WorkOrderDeviceModule> findDeviceModuleList();
	
	/**
	 * 获取对象分页列表
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PagerResult<WorkOrderDeviceModuleDto> findDeviceModuleDtoListPage(int pageNum, int pageSize);

	/**
	 * 获取对象列表
	 * @return
	 */
	List<WorkOrderDeviceModuleDto> findDeviceModuleDtoList();
	
}
