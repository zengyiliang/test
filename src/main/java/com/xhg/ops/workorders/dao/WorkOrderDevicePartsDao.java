package com.xhg.ops.workorders.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.xhg.ops.workorders.dto.WorkOrderDevicePartsDto;
import com.xhg.ops.workorders.model.WorkOrderDeviceParts;

/**
 * 工单设备配件数据操作
 * @author 刘涛
 * @date 2018年7月21日
 */
@Repository
public interface WorkOrderDevicePartsDao {
	
	/**
	 * 新增
	 * @param parts
	 */
	public void insert(WorkOrderDeviceParts parts);
	
	/**
	 * 更新数据
	 * @param parts
	 */
	public void update(WorkOrderDeviceParts parts);
	
	/**
	 * 删除
	 * @param id
	 */
	public void delete(@Param("id")int id, @Param("updatedUserId") int updatedUserId);
	
	/**
	 * 查询单个数据字典
	 * @param id
	 * @return
	 */
	public WorkOrderDeviceParts findOne(@Param("id")int id);
	
	/**
	 * 获取数据列表
	 * @param moduleId
	 * @return
	 */
	public List<WorkOrderDeviceParts> findDevicePartsListByModuleId(@Param("moduleId") int moduleId);
	
	/**
	 * 获取所有数据列表
	 * @return
	 */
	public List<WorkOrderDeviceParts> findAllDevicePartsList();
	
	/**
	 * 获取对象列表
	 * @param moduleId
	 * @return
	 */
	public List<WorkOrderDevicePartsDto> findDeviceModuleDtoListByModuleId();
	
}