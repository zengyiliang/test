package com.xhg.ops.workorders.service;

import java.util.List;

import com.xhg.core.web.vo.PagerResult;
import com.xhg.ops.workorders.dto.WebWorkOrderPlatformQueryDTO;
import com.xhg.ops.workorders.dto.WebWorkOrderQueryDTO;
import com.xhg.ops.workorders.dto.WorkOrderBaseDTO;
import com.xhg.ops.workorders.vo.WebWorkOrderDealMaterialVo;
import com.xhg.ops.workorders.vo.WebWorkOrderDealRecordVo;
import com.xhg.ops.workorders.vo.WebWorkOrderDetailShowVo;
import com.xhg.ops.workorders.vo.WebWorkOrderDetailVo;
import com.xhg.ops.workorders.vo.WebWorkOrderPlatformListVo;
import com.xhg.ops.workorders.vo.WebWorkOrderPlatformStaticsVo;
import com.xhg.ops.workorders.vo.WebWorkOrderProgressVo;
import com.xhg.ops.workorders.vo.WebWorkOrderVo;

/**
 * web工单接口
 * @author 区涛
 * @date 2018年7月16日
 */
public interface WebWorkOrderService {
	
	/**
	 * 查询工单详情基本信息
	 * @param dto
	 * @return
	 */
	WebWorkOrderDetailVo baseInfo(WorkOrderBaseDTO dto);

	/**
	 * 查询工单进度
	 * @param dto
	 * @return
	 */
	List<WebWorkOrderProgressVo> progress(WorkOrderBaseDTO dto);

	/**
	 * 查询工单
	 * @param dto
	 * @return
	 */
	PagerResult<WebWorkOrderVo> listOrder(WebWorkOrderQueryDTO dto);

	/**
	 * 查询工单处理信息
	 * @param dto
	 * @return
	 */
	WebWorkOrderDealRecordVo dealRecord(WorkOrderBaseDTO dto);

	/**
	 * 查询物料信息
	 * @param dto
	 * @return
	 */
	WebWorkOrderDealMaterialVo materialApplyInfo(WorkOrderBaseDTO dto);

	/**
	 * 工单详情展示控制
	 * @param dto
	 * @return
	 */
	WebWorkOrderDetailShowVo detail(WorkOrderBaseDTO dto);

	/**
	 * 工作台工单列表查询
	 * @param dto
	 * @return
	 */
	PagerResult<WebWorkOrderPlatformListVo> listPlatformOrderList(WebWorkOrderPlatformQueryDTO dto);

	/**
	 * 查询物料申请列表
	 * @param dto
	 * @return
	 */
	List<WebWorkOrderDealMaterialVo> materialApplyInfoList(WorkOrderBaseDTO dto);

	/**
	 * 工作台统计
	 * @param dto
	 * @return
	 */
	WebWorkOrderPlatformStaticsVo platformStatics();
	
}
