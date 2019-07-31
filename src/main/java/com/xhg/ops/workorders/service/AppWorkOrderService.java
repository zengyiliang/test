package com.xhg.ops.workorders.service;

import java.util.List;
import java.util.Map;

import com.xhg.core.web.vo.PagerResult;
import com.xhg.core.web.vo.RsBody;
import com.xhg.ops.workorders.dto.WorkOrderBaseDTO;
import com.xhg.ops.workorders.dto.AppWorkOrderMessageQueryDTO;
import com.xhg.ops.workorders.dto.AppWorkOrderMessageUpdateDTO;
import com.xhg.ops.workorders.dto.AppWorkOrderQueryDTO;
import com.xhg.ops.workorders.vo.AppWorkOrderDealMaterialVo;
import com.xhg.ops.workorders.vo.AppWorkOrderDealRecordVo;
import com.xhg.ops.workorders.vo.AppWorkOrderDetailVo;
import com.xhg.ops.workorders.vo.AppWorkOrderMessageVo;
import com.xhg.ops.workorders.vo.AppWorkOrderProgressVo;
import com.xhg.ops.workorders.vo.AppWorkOrderVo;

/**
 * app工单接口
 * @author 区涛
 * @date 2018年7月16日
 */
public interface AppWorkOrderService {
	
	/**
	 * 查询工单详情
	 * @param dto
	 * @return
	 */
	AppWorkOrderDetailVo detail(WorkOrderBaseDTO dto);

	/**
	 * 查询工单进度
	 * @param dto
	 * @return
	 */
	List<AppWorkOrderProgressVo> progress(WorkOrderBaseDTO dto);

	/**
	 * 查询工单
	 * @param dto
	 * @return
	 */
	PagerResult<AppWorkOrderVo> listOrder(AppWorkOrderQueryDTO dto);

	/**
	 * 查询工单处理信息
	 * @param dto
	 * @return
	 */
	AppWorkOrderDealRecordVo dealRecord(WorkOrderBaseDTO dto);

	/**
	 * 查询物料信息
	 * @param dto
	 * @return
	 */
	AppWorkOrderDealMaterialVo materialApplyInfo(WorkOrderBaseDTO dto);

	/**
	 * 查询工单消息
	 * @param dto
	 * @return
	 */
	PagerResult<AppWorkOrderMessageVo> message(AppWorkOrderMessageQueryDTO dto);

	/**
	 * @param dto
	 * 已读消息
	 * @return
	 */
	RsBody<Map<String, Object>> readMessage(AppWorkOrderMessageUpdateDTO dto);

	/**
	 * 查询物料信息列表
	 * @param dto
	 * @return
	 */
	List<AppWorkOrderDealMaterialVo> materialApplyInfoList(WorkOrderBaseDTO dto);
	
}
