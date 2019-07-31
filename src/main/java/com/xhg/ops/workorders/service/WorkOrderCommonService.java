package com.xhg.ops.workorders.service;

import com.xhg.ops.system.dto.SystemUserLoadDTO;
import com.xhg.ops.workorders.dto.WorkOrderUserDTO;

/**
 * 工单业务公共服务接口
 * @author 区涛
 * @date 2018年7月21日
 */
public interface WorkOrderCommonService {
	
	/**
	 * 配置当前登录用户信息, 用于工单流程操作
	 * @return
	 */
	WorkOrderUserDTO assembleUserDTO();

	/**
	 * 判断是否显示物料信息
	 * @param orderId
	 * @return
	 */
	String getShowMaterialApplyInfo(int orderId);

	/**
	 * 判断是否显示工单处理信息
	 * @param orderId
	 * @return
	 */
	String getShowOrderRecord(int orderId);

	/**
	 * 组装DTO, 用于搜索运维经理，运维专员等角色用户
	 * @param countryCode 全国编码
	 * @param areaCode 市级编码
	 * @param name 名字
	 * @param roleCode 角色编码
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	SystemUserLoadDTO assembleSystemUserLoadDTO(String countryCode, String areaCode, String name, String roleCode,
			Integer currentPage, Integer pageSize);
}
