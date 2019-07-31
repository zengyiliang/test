package com.xhg.ops.workorders.service;

import java.util.List;

import com.xhg.core.web.vo.PagerResult;
import com.xhg.ops.workorders.dto.WorkOrderDevicePartsDto;
import com.xhg.ops.workorders.model.WorkOrderDeviceParts;

/**
 * 工单设备配件逻辑处理接口
 * @author 刘涛
 * @date 2018年7月21日
 */
public interface WorkOrderDevicePartsService {

	/**
	 * 新增
	 * @param parts
	 * @param userId
	 */
	void insert(WorkOrderDeviceParts parts, Integer userId);
	
	/**
	 * 更新数据
	 * @param module
	 * @param userId
	 */
	void update(WorkOrderDeviceParts parts, Integer userId);
	
	/**
	 * 删除
	 * @param id
	 * @param userId
	 */
	void delete(int id, Integer userId);
	
	/**
	 * 获取数据列表
	 * @param moduleId
	 * @return
	 */
	List<WorkOrderDeviceParts> findDevicePartsList(int moduleId);
	
	/**
	 * 
	 * @return
	 */
	List<WorkOrderDeviceParts> findAllDevicePartsList();
	
	/**
	 * 获取对象分页列表
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PagerResult<WorkOrderDevicePartsDto> findDevicePartsDtoListPage(int pageNum, int pageSize);
	
}
