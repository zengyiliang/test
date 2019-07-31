package com.xhg.ops.workorders.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.xhg.ops.workorders.dto.WorkOrderDeviceModuleDto;
import com.xhg.ops.workorders.model.WorkOrderDeviceModule;

/**
 * 工单设备模块数据操作
 * @author 刘涛
 * @date 2018年7月21日
 */
@Repository
public interface WorkOrderDeviceModuleDao {
	
	/**
	 * 新增
	 * @param module
	 */
	public void insert(WorkOrderDeviceModule module);
	
	/**
	 * 更新数据
	 * @param module
	 */
	public void update(WorkOrderDeviceModule module);
	
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
	public WorkOrderDeviceModule findOne(@Param("id")int id);
	
	/**
	 * 获取数据列表
	 * @return
	 */
	public List<WorkOrderDeviceModule> findDeviceModuleList();
	
	/**
	 * 获取对象列表
	 * @return
	 */
	public List<WorkOrderDeviceModuleDto> findDeviceModuleDtoList();
	
}