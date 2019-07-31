package com.xhg.ops.workorders.service;

import java.util.Map;

import com.xhg.core.web.vo.RsBody;
import com.xhg.ops.workorders.dto.WorkOrderBaseDTO;
import com.xhg.ops.workorders.dto.AppWorkOrderDealMaterialDTO;
import com.xhg.ops.workorders.dto.AppWorkOrderDealRecordDTO;
import com.xhg.ops.workorders.dto.AppWorkOrderFlowAssignConfirmDTO;
import com.xhg.ops.workorders.dto.AppWorkOrderFlowAssignRejectDTO;
import com.xhg.ops.workorders.dto.AppWorkOrderFlowDepartRejectDTO;
import com.xhg.ops.workorders.dto.AppWorkOrderFlowFirstReviewCloseDTO;
import com.xhg.ops.workorders.dto.AppWorkOrderFlowFirstReviewPassDTO;
import com.xhg.ops.workorders.dto.AppWorkOrderFlowSignInDTO;
import com.xhg.ops.workorders.dto.AppWorkOrderFlowSignRejectDTO;
import com.xhg.ops.workorders.vo.AppWorkOrderAssignPageVo;
import com.xhg.ops.workorders.vo.AppWorkOrderDealMaterialPageVo;
import com.xhg.ops.workorders.vo.AppWorkOrderDealRecordPageVo;
import com.xhg.ops.workorders.vo.AppWorkOrderFirstReviewPageVo;

/**
 * app工单流程接口
 * @author 区涛
 * @date 2018年7月17日
 */
public interface AppWorkOrderFlowService {

	/**
	 * 初审页面
	 * @param dto
	 * @return
	 * @throws Exception 
	 */
	AppWorkOrderFirstReviewPageVo firstReviewPage(WorkOrderBaseDTO dto) throws Exception;

	/**
	 * 初审通过
	 * @param dto
	 * @return
	 */
	RsBody<Map<String, Object>> firstReviewPass(AppWorkOrderFlowFirstReviewPassDTO dto);

	/**
	 * 初审-关闭工单
	 * @param dto
	 * @return
	 */
	RsBody<Map<String, Object>> firstReviewClose(AppWorkOrderFlowFirstReviewCloseDTO dto);

	/**
	 * 工单分配页面
	 * @param dto
	 * @return
	 * @throws Exception 
	 */
	AppWorkOrderAssignPageVo assignPage(WorkOrderBaseDTO dto) throws Exception;

	/**
	 * 确认分配工单
	 * @param dto
	 * @return
	 */
	RsBody<Map<String, Object>> assignConfirm(AppWorkOrderFlowAssignConfirmDTO dto);

	/**
	 * 工单分配-拒绝工单
	 * @param dto
	 * @return
	 */
	RsBody<Map<String, Object>> assignReject(AppWorkOrderFlowAssignRejectDTO dto);

	/**
	 * 签单
	 * @param dto
	 * @return
	 */
	RsBody<Map<String, Object>> signConfirm(WorkOrderBaseDTO dto);

	/**
	 * 撤单
	 * @param dto
	 * @return
	 */
	RsBody<Map<String, Object>> signReject(AppWorkOrderFlowSignRejectDTO dto);

	/**
	 * 出发
	 * @param dto
	 * @return
	 */
	RsBody<Map<String, Object>> departConfirm(WorkOrderBaseDTO dto);

	/**
	 * 转单
	 * @param dto
	 * @return
	 */
	RsBody<Map<String, Object>> departReject(AppWorkOrderFlowDepartRejectDTO dto);

	/**
	 * 签到
	 * @param dto
	 * @return
	 */
	RsBody<Map<String, Object>> signInConfirm(AppWorkOrderFlowSignInDTO dto);

	/**
	 * 工单处理页面
	 * @return
	 * @throws Exception 
	 */
	AppWorkOrderDealRecordPageVo dealRecordPage() throws Exception;

	/**
	 * 处理工单
	 * @param dto
	 * @return
	 */
	RsBody<Map<String, Object>> dealRecordSubmit(AppWorkOrderDealRecordDTO dto);

	/**
	 * 物料申请页面
	 * @return
	 */
	AppWorkOrderDealMaterialPageVo dealMaterialPage();

	/**
	 * 物料申请提交
	 * @param dto
	 * @return
	 */
	RsBody<Map<String, Object>> dealMaterialSubmit(AppWorkOrderDealMaterialDTO dto);

	/**
	 * 物料审批
	 * @param dto
	 * @return
	 */
	RsBody<Map<String, Object>> materialApprove(WorkOrderBaseDTO dto);

	/**
	 * 物料审核
	 * @param dto
	 * @return
	 */
	RsBody<Map<String, Object>> materialReview(WorkOrderBaseDTO dto);

	/**
	 * 发货
	 * @param dto
	 * @return
	 */
	RsBody<Map<String, Object>> materialDeliver(WorkOrderBaseDTO dto);

	/**
	 * 工单信息审核
	 * @param dto
	 * @return
	 */
	RsBody<Map<String, Object>> audit(WorkOrderBaseDTO dto);

	/**
	 * 工单复核，关闭工单
	 * @param dto
	 * @return
	 */
	RsBody<Map<String, Object>> reAudit(WorkOrderBaseDTO dto);

	/**
	 * 物料查收
	 * @param dto
	 * @return
	 */
	RsBody<Map<String, Object>> materialReceive(WorkOrderBaseDTO dto); 
	
	
}
