package com.xhg.ops.workorders.service;

import java.util.List;
import java.util.Map;

import com.xhg.core.web.vo.RsBody;
import com.xhg.ops.workorders.dto.WebWorkOrderCityOpsManagerSearchDTO;
import com.xhg.ops.workorders.dto.WebWorkOrderCityOpsSearchDTO;
import com.xhg.ops.workorders.dto.WebWorkOrderCreateDTO;
import com.xhg.ops.workorders.dto.WebWorkOrderDealMaterialDTO;
import com.xhg.ops.workorders.dto.WebWorkOrderDealRecordDTO;
import com.xhg.ops.workorders.dto.WebWorkOrderDeviceAddressQueryDTO;
import com.xhg.ops.workorders.dto.WebWorkOrderFlowAssignConfirmDTO;
import com.xhg.ops.workorders.dto.WebWorkOrderFlowAssignRejectDTO;
import com.xhg.ops.workorders.dto.WebWorkOrderFlowDepartRejectDTO;
import com.xhg.ops.workorders.dto.WebWorkOrderFlowFirstReviewCloseDTO;
import com.xhg.ops.workorders.dto.WebWorkOrderFlowFirstReviewPassDTO;
import com.xhg.ops.workorders.dto.WebWorkOrderFlowSignRejectDTO;
import com.xhg.ops.workorders.dto.WorkOrderBaseDTO;
import com.xhg.ops.workorders.vo.WebWorkOrderAssignOpsVo;
import com.xhg.ops.workorders.vo.WebWorkOrderAssignPageVo;
import com.xhg.ops.workorders.vo.WebWorkOrderCreatePageVo;
import com.xhg.ops.workorders.vo.WebWorkOrderDealMaterialPageVo;
import com.xhg.ops.workorders.vo.WebWorkOrderDealRecordPageVo;
import com.xhg.ops.workorders.vo.WebWorkOrderDeviceAddressVo;
import com.xhg.ops.workorders.vo.WebWorkOrderFirstReviewOpsManagerVo;
import com.xhg.ops.workorders.vo.WebWorkOrderFirstReviewPageVo;

/**
 * web工单流程接口
 * @author 区涛
 * @date 2018年7月17日
 */
public interface WebWorkOrderFlowService {
	
	/**
	 * 新建工单页面
	 * @return
	 * @throws Exception
	 */
	WebWorkOrderCreatePageVo createOrderPage() throws Exception;

	/**
	 * 初审页面
	 * @param dto
	 * @return
	 * @throws Exception 
	 */
	WebWorkOrderFirstReviewPageVo firstReviewPage(WorkOrderBaseDTO dto) throws Exception;

	/**
	 * 初审通过
	 * @param dto
	 * @return
	 */
	RsBody<Map<String, Object>> firstReviewPass(WebWorkOrderFlowFirstReviewPassDTO dto);

	/**
	 * 初审-关闭工单
	 * @param dto
	 * @return
	 */
	RsBody<Map<String, Object>> firstReviewClose(WebWorkOrderFlowFirstReviewCloseDTO dto);

	/**
	 * 工单分配页面
	 * @param dto
	 * @return
	 * @throws Exception 
	 */
	WebWorkOrderAssignPageVo assignPage(WorkOrderBaseDTO dto) throws Exception;

	/**
	 * 确认分配工单
	 * @param dto
	 * @return
	 */
	RsBody<Map<String, Object>> assignConfirm(WebWorkOrderFlowAssignConfirmDTO dto);

	/**
	 * 工单分配-拒绝工单
	 * @param dto
	 * @return
	 */
	RsBody<Map<String, Object>> assignReject(WebWorkOrderFlowAssignRejectDTO dto);

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
	RsBody<Map<String, Object>> signReject(WebWorkOrderFlowSignRejectDTO dto);

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
	RsBody<Map<String, Object>> departReject(WebWorkOrderFlowDepartRejectDTO dto);

	/**
	 * 工单处理页面
	 * @return
	 * @throws Exception 
	 */
	WebWorkOrderDealRecordPageVo dealRecordPage() throws Exception;

	/**
	 * 处理工单
	 * @param dto
	 * @return
	 */
	RsBody<Map<String, Object>> dealRecordSubmit(WebWorkOrderDealRecordDTO dto);

	/**
	 * 物料申请页面
	 * @return
	 */
	WebWorkOrderDealMaterialPageVo dealMaterialPage();

	/**
	 * 物料申请提交
	 * @param dto
	 * @return
	 */
	RsBody<Map<String, Object>> dealMaterialSubmit(WebWorkOrderDealMaterialDTO dto);

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

	/**
	 * 查询设备地址
	 * @param dto
	 * @return
	 */
	@Deprecated
	WebWorkOrderDeviceAddressVo queryDeviceAddress(WebWorkOrderDeviceAddressQueryDTO dto);

	/**
	 * 新建工单
	 * @param dto
	 * @return
	 */
	RsBody<Map<String, Object>> createOrderSubmit(WebWorkOrderCreateDTO dto);

	/**
	 * 根据名字模糊搜索运维经理
	 * @param dto
	 * @return
	 * @throws Exception 
	 */
	List<WebWorkOrderFirstReviewOpsManagerVo> firstReviewSearchCityOpsManager(WebWorkOrderCityOpsManagerSearchDTO dto) throws Exception;

	/**
	 * 根据名字模糊搜索运维专员
	 * @param dto
	 * @return
	 * @throws Exception 
	 */
	List<WebWorkOrderAssignOpsVo> assignSearchOps(WebWorkOrderCityOpsSearchDTO dto) throws Exception;

	
}
