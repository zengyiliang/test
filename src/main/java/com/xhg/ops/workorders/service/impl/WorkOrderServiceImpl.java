package com.xhg.ops.workorders.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xhg.core.web.exception.BusinessException;
import com.xhg.core.web.vo.PagerResult;
import com.xhg.ops.utils.MapUtils;
import com.xhg.ops.workorders.dao.WorkOrderDao;
import com.xhg.ops.workorders.dao.WorkOrderMaterielApplyDao;
import com.xhg.ops.workorders.dao.WorkOrderResultDao;
import com.xhg.ops.workorders.dto.WorkOrderFindConditionDto;
import com.xhg.ops.workorders.dto.WorkOrderFindResultDto;
import com.xhg.ops.workorders.enums.WorkOrderErrStatus;
import com.xhg.ops.workorders.model.WorkOrder;
import com.xhg.ops.workorders.model.WorkOrderResult;
import com.xhg.ops.workorders.service.WorkOrderAttachmentService;
import com.xhg.ops.workorders.service.WorkOrderService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("workOrderService")
public class WorkOrderServiceImpl implements WorkOrderService {

	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private WorkOrderDao workOrderDao;

	@Autowired
	private WorkOrderMaterielApplyDao workOrderMaterielApplyDao;

	@Autowired
	private WorkOrderResultDao workOrderResultDao;

	@Autowired
	private WorkOrderAttachmentService workOrderAttachmentService;


	/**
	 * 查询工单详情
	 *
	 * @param id
	 * @return
	 */
	@Override
	public WorkOrder queryWorkOrderDetail(int orderId) {
		WorkOrder workOrder = workOrderDao.queryOne(orderId);
		if (workOrder == null) {
			throw new BusinessException(WorkOrderErrStatus.ERROR.getCode(), "工单不存在");
		}
		// 查询附件信息
		List<String> attachments = workOrderAttachmentService.queryAttachmentList(orderId,
				WorkOrderAttachmentService.BusinessType.BUSINESS_TYPE_WORK_ORDER);
		workOrder.setAttachments(attachments);
		// 查询物料信息
		workOrder.setMaterielList(workOrderMaterielApplyDao.queryWorkOrderMaterielList(orderId));

		return workOrder;
	}

	/**
	 * 查询所有工单信息
	 */
	@Override
	public PagerResult<WorkOrderFindResultDto> queryAllWorkOrderListPage(int pageNum, int pageSize, WorkOrderFindConditionDto condition) {
		PageHelper.startPage(pageNum, pageSize);
		Map<String, Object> params = new HashMap<>();
		try {
			params = MapUtils.beanToMap(condition);
		} catch (Exception e) {
			logger.error("查询条件转换失败", e);
		}
		List<WorkOrderFindResultDto> list = workOrderDao.queryWorkOrderList(params);
		return new PagerResult<>(new PageInfo<>(list));
	}

	/**
	 * 查询工单处理结果信息
	 */
	@Override
	public WorkOrderResult queryWorkOrderResult(int orderId) {
		WorkOrderResult workOrderResult = workOrderResultDao.queryOne(orderId);
		if(workOrderResult == null) {
			throw new BusinessException(WorkOrderErrStatus.ERROR.getCode(), "工单结果信息不存在");
		}
		List<String> attachments = workOrderAttachmentService.queryAttachmentList(workOrderResult.getId(),
				WorkOrderAttachmentService.BusinessType.BUSINESS_TYPE_WORK_ORDER_RESULT);
		workOrderResult.setAttachments(attachments);
		return workOrderResult;
	}

	@Override
	public int countWorkOrderResult(int orderId) {
		int count = workOrderResultDao.countByOrderId(orderId);
		return count;
	}

	@Override
	public int countWorkOrderMaterielApply(int orderId) {
		int count = workOrderMaterielApplyDao.countByOrderId(orderId);
		return count;
	}

}
