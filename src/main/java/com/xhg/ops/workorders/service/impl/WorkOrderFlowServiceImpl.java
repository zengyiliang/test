package com.xhg.ops.workorders.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xhg.core.util.LocationUtils;
import com.xhg.core.web.exception.BusinessException;
import com.xhg.core.web.vo.PagerResult;
import com.xhg.ops.common.ComConstant;
import com.xhg.ops.workflow.api.ActivitiService;
import com.xhg.ops.workflow.vo.WorkflowProcessInstanceVO;
import com.xhg.ops.workflow.vo.WorkflowTaskVO;
import com.xhg.ops.workorders.dao.WorkOrderDao;
import com.xhg.ops.workorders.dao.WorkOrderMaterielApplyDao;
import com.xhg.ops.workorders.dao.WorkOrderResultDao;
import com.xhg.ops.workorders.dto.WorkOrderFindResultDto;
import com.xhg.ops.workorders.dto.WorkOrderUserDTO;
import com.xhg.ops.workorders.enums.WorkOrderErrStatus;
import com.xhg.ops.workorders.enums.WorkOrderStatus;
import com.xhg.ops.workorders.enums.WorkOrderStatusCondition;
import com.xhg.ops.workorders.enums.WorkOrderType;
import com.xhg.ops.workorders.model.WorkOrder;
import com.xhg.ops.workorders.model.WorkOrderMaterielApply;
import com.xhg.ops.workorders.model.WorkOrderResult;
import com.xhg.ops.workorders.service.WorkOrderAttachmentService;
import com.xhg.ops.workorders.service.WorkOrderFlowService;
import com.xhg.ops.workorders.service.WorkOrderLogService;
import com.xhg.ops.workorders.service.WorkOrderMessageService;

import redis.clients.jedis.JedisCluster;

/**
 * 工单流程逻辑处理接口实现
 * 
 * @author 刘涛
 * @date 2018年7月12日
 */
@Service("workOrderFlowService")
public class WorkOrderFlowServiceImpl implements WorkOrderFlowService {

	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private WorkOrderDao workOrderDao;

	@Autowired
	private WorkOrderMaterielApplyDao workOrderMaterielApplyDao;

	@Autowired
	private WorkOrderResultDao workOrderResultDao;

	@Autowired
	private WorkOrderAttachmentService workOrderAttachmentService;

	@Autowired
	private WorkOrderLogService workOrderLogService;
	
	@Autowired
	private WorkOrderMessageService workOrderMessageService;
	
	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private JedisCluster jedisCluster;
	
	private ExecutorService executor = Executors.newFixedThreadPool(5);
	
	
	/**
	 * 工单创建
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer createWorkOrder(WorkOrder workOrder, WorkOrderUserDTO user) {
		if (workOrder == null) {
			throw new BusinessException(WorkOrderErrStatus.ERROR.getCode(), WorkOrderErrStatus.ERROR.getMsg());
		}
		final Date now = new Date();
		workOrder.setStatus(WorkOrderStatus.CREATED.getCode());
		workOrder.setProcInstId("");
		workOrder.setProcTaskId("");
		workOrder.setProcUserId(user.getUserId());
		workOrder.setCreatedUser(user.getUserName());
		workOrder.setCreatedTime(now);
		workOrder.setUpdatedTime(now);
		workOrder.setCreatedUserId(user.getUserId());
		workOrder.setUpdatedUserId(user.getUserId());
		workOrder.setEnable(ComConstant.ENABLE_YES);
		workOrderDao.insert(workOrder);
		if (workOrder.getAttachments() != null && workOrder.getAttachments().size() > 0) {
			workOrderAttachmentService.insert(workOrder.getAttachments(), workOrder.getId(),
					WorkOrderAttachmentService.BusinessType.BUSINESS_TYPE_WORK_ORDER, user.getUserId());
		}
		// 新增日志
		workOrderLogService.addWorkOrderLog(workOrder.getId(), WorkOrderStatus.CREATED, "", user);
		logger.info("工单创建成功，工单ID：" + workOrder.getId());
		
		//启动流程
		String procInstanceId = activitiService.createWorkOrder(String.valueOf(user.getUserId()), String.valueOf(workOrder.getId())).getProcessInstanceId();
		// 查询最新的任务节点
		String procTaskId = activitiService.getCurrentTaskByProcessInstanceId(procInstanceId).getId();
		
		// 更新数据
		final String orderNo = this.getOrdeNo();
		Map<String, Object> params = new HashMap<>();
		params.put("id", workOrder.getId());
		params.put("updatedTime", now);
		params.put("orderNo", orderNo);
		params.put("procInstId", procInstanceId);
		params.put("procTaskId", procTaskId);
		workOrderDao.updateWorkOrder(params);
		workOrder.setOrderNo(orderNo);
		workOrder.setProcInstId(procInstanceId);
		workOrder.setProcTaskId(procTaskId);
		//发送与推送消息
		this.sendSiteMessage(workOrder, WorkOrderStatus.CREATED, user);
		this.pushMessage(workOrder, WorkOrderStatus.CREATED, procTaskId, user);
		return workOrder.getId();
	}
	
	/**
	 * 获取工单号,没有比对上次生成工单号的最大值进行递增
	 * @return
	 */
	private String getOrdeNo() {
		String orderNoPrefix = "XHG" + DateFormatUtils.format(new Date(), "yyyyMMdd");
		String redisKey = "WORKORDERNO:" + orderNoPrefix;
		if(!jedisCluster.exists(redisKey)) {
			String maxOrderNo = workOrderDao.selectOrderMaxOrderNo(orderNoPrefix);
			long count = StringUtils.isEmpty(maxOrderNo) ? 0 : Long.parseLong(maxOrderNo.substring(orderNoPrefix.length()));
			long result = jedisCluster.setnx(redisKey, String.valueOf(count));
			if(result > 0) {
				jedisCluster.expire(redisKey, 3600);	//缓存1小时，1小时重新从数据库拿到最新值
			}
		}
		long count = jedisCluster.incr(redisKey);
		return String.format(orderNoPrefix + "%05d", count);
	}
	
	/**
	 * 工单初审，总部运维主管进行审核
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void auditWorkOderOrder(int orderId, int level, int nextUserId, WorkOrderUserDTO user) {
		WorkOrder workOrder = this.findAndValidWorkOrder(orderId, WorkOrderStatus.AUDITED);
		final String procTaskId = workOrder.getProcTaskId();
		//处理流程
		activitiService.auditWorkOderOrder(procTaskId, String.valueOf(user.getUserId()), String.valueOf(nextUserId));
		
		WorkOrder updateWorkOrder = new WorkOrder();
		updateWorkOrder.setLevel(level);
		this.processSimpleWorkOrder(workOrder, updateWorkOrder, WorkOrderStatus.AUDITED, "", user);
	}

	/**
	 * 工单关闭，主管审核进行关闭
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void closeWorkOrder(int orderId, String reason, WorkOrderUserDTO user) {
		WorkOrder workOrder = this.findAndValidWorkOrder(orderId, WorkOrderStatus.CLOSED);
		final String taskId = workOrder.getProcTaskId();
		//处理流程
		activitiService.closeWorkOrder(taskId, String.valueOf(user.getUserId()), reason);
		this.processSimpleWorkOrder(workOrder, null, WorkOrderStatus.CLOSED, reason, user);
	}

	/**
	 * 工单分配，运维经理分配工单
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void assignWorkOrder(int orderId, int nextUserId, WorkOrderUserDTO user) {
		WorkOrder workOrder = this.findAndValidWorkOrder(orderId, WorkOrderStatus.ASSIGNED);
		final String taskId = workOrder.getProcTaskId();
		//处理流程
		activitiService.assignWorkOrder(taskId, String.valueOf(user.getUserId()),String.valueOf(nextUserId));
		this.processSimpleWorkOrder(workOrder, null, WorkOrderStatus.ASSIGNED, "", user);
		logger.info("工单：" + orderId + " 分配工单成功，下一步处理人为：" + nextUserId);
	}

	/**
	 * 工单拒绝，运维经理审核拒绝，重新流回上一级进行处理
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void rejectWorkOrder(int orderId, String reason, WorkOrderUserDTO user) {
		WorkOrder workOrder = this.findAndValidWorkOrder(orderId, WorkOrderStatus.REJECTED);
		final String taskId = workOrder.getProcTaskId();
		//处理流程
		activitiService.rejectWorkOrder(taskId, String.valueOf(user.getUserId()), reason);
		this.processSimpleWorkOrder(workOrder, null, WorkOrderStatus.REJECTED, reason, user);
	}

	/**
	 * 运维专员进行签单操作
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void agreeWorkOderOrder(int orderId, WorkOrderUserDTO user) {
		WorkOrder workOrder = this.findAndValidWorkOrder(orderId, WorkOrderStatus.AGREED);
		final String taskId = workOrder.getProcTaskId();
		//处理流程
		activitiService.agreeWorkOderOrder(taskId, String.valueOf(user.getUserId()));
		this.processSimpleWorkOrder(workOrder, null, WorkOrderStatus.AGREED, "", user);
	}

	/**
	 * 运维专员撤单操作
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void revokeWorkOderOrder(int orderId, String reason, WorkOrderUserDTO user) {
		WorkOrder workOrder = this.findAndValidWorkOrder(orderId, WorkOrderStatus.REVOKED);
		final String taskId = workOrder.getProcTaskId();
		//处理流程
		activitiService.revokeWorkOderOrder(taskId, String.valueOf(user.getUserId()), reason);
		this.processSimpleWorkOrder(workOrder, null, WorkOrderStatus.REVOKED, reason, user);

	}

	/**
	 * 运维专员进行出发操单操作
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void startWorkOrder(int orderId, WorkOrderUserDTO user) {
		WorkOrder workOrder = this.findAndValidWorkOrder(orderId, WorkOrderStatus.STARTED);
		final String taskId = workOrder.getProcTaskId();
		//处理流程
		activitiService.startWorkOrder(taskId, String.valueOf(user.getUserId()));
		this.processSimpleWorkOrder(workOrder, null, WorkOrderStatus.STARTED, "", user);
	}

	/**
	 * 运维专员进行转单操作
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void transferWorkOrder(int orderId, String reason, WorkOrderUserDTO user) {
		WorkOrder workOrder = this.findAndValidWorkOrder(orderId, WorkOrderStatus.TRANSFERED);
		final String taskId = workOrder.getProcTaskId();
		//处理流程
		activitiService.transferWorkOrder(taskId, String.valueOf(user.getUserId()), reason);
		this.processSimpleWorkOrder(workOrder, null, WorkOrderStatus.TRANSFERED, reason, user);
	}

	/**
	 * 运维专员进行签到操单操作
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void signWorkOrder(int orderId, double longitude, double latitude, WorkOrderUserDTO user) {
		WorkOrder workOrder = this.findAndValidWorkOrder(orderId, WorkOrderStatus.TRANSFERED);
		// 计算两点之前的距离，判断是否能进行签到操作
		double distance = LocationUtils.getDistance(latitude, longitude,
				Double.parseDouble(workOrder.getSiteLatitude()), Double.parseDouble(workOrder.getSiteLongitude()));
		if (distance > SIGN_DISTANCE) {
			throw new BusinessException(WorkOrderErrStatus.ERROR.getCode(), "不在签到范围内");
		}
		final String taskId = workOrder.getProcTaskId();
		//处理流程
		activitiService.signWorkOrder(taskId, String.valueOf(user.getUserId()));
		this.processSimpleWorkOrder(workOrder, null, WorkOrderStatus.SIGNED, "", user);
		logger.info("工单：" + orderId + " 签到成功，签到经纬度（" + longitude + ", " + latitude + "）");

	}

	/**
	 * 运维专员提交工单操作
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void processWorkOrder(int orderId, WorkOrderResult workOrderResult, WorkOrderUserDTO user) {
		WorkOrder workOrder = this.findAndValidWorkOrder(orderId, WorkOrderStatus.PROCESSED);
		final String taskId = workOrder.getProcTaskId();
		
		workOrderResult.setOrderId(orderId);
		workOrderResult.setCreatedUser(user.getUserName());
		workOrderResult.setCreatedTime(new Date());
		workOrderResult.setUpdatedTime(new Date());
		workOrderResult.setCreatedUserId(user.getUserId());
		workOrderResult.setUpdatedUserId(user.getUserId());
		workOrderResult.setEnable(ComConstant.ENABLE_YES);
		workOrderResultDao.insert(workOrderResult);
		if (workOrderResult.getAttachments() != null && workOrderResult.getAttachments().size() > 0) {
			workOrderAttachmentService.insert(workOrderResult.getAttachments(), workOrderResult.getId(),
					WorkOrderAttachmentService.BusinessType.BUSINESS_TYPE_WORK_ORDER_RESULT, user.getUserId());
		}
		
		//处理流程
		activitiService.processWorkOrder(taskId, String.valueOf(user.getUserId()));
		this.processSimpleWorkOrder(workOrder, null, WorkOrderStatus.PROCESSED, "", user);
		
		logger.info("工单：" + orderId + " 提交成功");
	}

	/**
	 * 运维专员提交物料申请
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void submitMaterielApply(int orderId, WorkOrderMaterielApply workOrderMaterielApply, WorkOrderUserDTO user) {
		WorkOrder workOrder = this.findAndValidWorkOrder(orderId, WorkOrderStatus.MATERIAL_APPLY);
		final String taskId = workOrder.getProcTaskId();
		
		workOrderMaterielApply.setOrderId(orderId);
		workOrderMaterielApply.setCreatedTime(new Date());
		workOrderMaterielApply.setUpdatedTime(new Date());
		workOrderMaterielApply.setCreatedUserId(user.getUserId());
		workOrderMaterielApply.setUpdatedUserId(user.getUserId());
		workOrderMaterielApply.setEnable(ComConstant.ENABLE_YES);
		workOrderMaterielApplyDao.insert(workOrderMaterielApply);
		
		//处理流程
		activitiService.submitMaterielApply(taskId, String.valueOf(user.getUserId()));
		this.processSimpleWorkOrder(workOrder, null, WorkOrderStatus.MATERIAL_APPLY, "", user);
		
		logger.info("工单：" + orderId + " 提交物料申请成功");
	}

	/**
	 * 运维经理 - 审批物料操作
	 */
	@Override
	public void approveMaterielApply(int orderId, WorkOrderUserDTO user) {
		WorkOrder workOrder = this.findAndValidWorkOrder(orderId, WorkOrderStatus.MATERIAL_APPROVED);
		final String taskId = workOrder.getProcTaskId();
		//处理流程
		activitiService.auditMaterielApply(taskId, String.valueOf(user.getUserId()));
		this.processSimpleWorkOrder(workOrder, null, WorkOrderStatus.MATERIAL_APPROVED, "", user);
	}

	/**
	 * 物控 - 审核物料操作
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void auditMaterielApply(int orderId, WorkOrderUserDTO user) {
		WorkOrder workOrder = this.findAndValidWorkOrder(orderId, WorkOrderStatus.MATERIAL_AUDITED);
		final String taskId = workOrder.getProcTaskId();
		//处理流程
		activitiService.checkMateriel(taskId, String.valueOf(user.getUserId()));
		this.processSimpleWorkOrder(workOrder, null, WorkOrderStatus.MATERIAL_AUDITED, "", user);
	}

	/**
	 * 物控 - 审核物料操作
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deliveryMateriel(int orderId, WorkOrderUserDTO user) {
		WorkOrder workOrder = this.findAndValidWorkOrder(orderId, WorkOrderStatus.MATERIAL_DELIVERED);
		final String taskId = workOrder.getProcTaskId();
		//处理流程
		activitiService.deliveryMateriel(taskId, String.valueOf(user.getUserId()));
		this.processSimpleWorkOrder(workOrder, null, WorkOrderStatus.MATERIAL_DELIVERED, "", user);

	}

	/**
	 * 运维专员物料收货操作
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void recevieMateriel(int orderId, WorkOrderUserDTO user) {
		WorkOrder workOrder = this.findAndValidWorkOrder(orderId, WorkOrderStatus.MATERIAL_RECEIVED);
		final String taskId = workOrder.getProcTaskId();
		//处理流程
		activitiService.recevieMateriel(taskId, String.valueOf(user.getUserId()));
		this.processSimpleWorkOrder(workOrder, null, WorkOrderStatus.MATERIAL_RECEIVED, "", user);
	}

	/**
	 * 运维经理进行工单确认
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void confirmWorkOrder(int orderId, WorkOrderUserDTO user) {
		WorkOrder workOrder = this.findAndValidWorkOrder(orderId, WorkOrderStatus.CONFIRMED);
		final String taskId = workOrder.getProcTaskId();
		//处理流程
		activitiService.confirmWorkOrder(taskId, String.valueOf(user.getUserId()));
		this.processSimpleWorkOrder(workOrder, null, WorkOrderStatus.CONFIRMED, "", user);
	}

	/**
	 * 运维主管进行工单复核-流程结束
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void reviewWorkOrder(int orderId, WorkOrderUserDTO user) {
		WorkOrder workOrder = this.findAndValidWorkOrder(orderId, WorkOrderStatus.REVIEWED);
		final String taskId = workOrder.getProcTaskId();
		//处理流程
		activitiService.reviewWorkOrder(taskId, String.valueOf(user.getUserId()));
		this.processSimpleWorkOrder(workOrder, null, WorkOrderStatus.REVIEWED, "", user);
		
		logger.info("工单：" + orderId + " 复核成功，工单流程结束！");
	}

	/**
	 * 查找并验证工单信息
	 * @param orderId
	 * @param status
	 * @return
	 */
	private WorkOrder findAndValidWorkOrder(int orderId, WorkOrderStatus status) {
		WorkOrder workOrder = workOrderDao.queryOne(orderId);
		if(workOrder == null) {
			throw new BusinessException(WorkOrderErrStatus.ERROR.getCode(), "工单不存在");
		} else if(status.getCode() == workOrder.getStatus()) {
			throw new BusinessException(WorkOrderErrStatus.ERROR.getCode(), "工单已处理");
		}
		return workOrder;
	}
	
	/**
	 * 工单处理
	 * @param workOrder 原始工单信息
	 * @param updateWorkOrder 需更新工单信息
	 * @param status 需要更新的状态
	 * @param remark 原因备注
	 * @param user 操作用户
	 */
	private void processSimpleWorkOrder(final WorkOrder workOrder, WorkOrder updateWorkOrder, WorkOrderStatus status, String remark, WorkOrderUserDTO user) {
		// 查询最新的任务节点
		String procTaskId = activitiService.getCurrentTaskByProcessInstanceId(workOrder.getProcInstId()).getId();
		
		Map<String, Object> params = new HashMap<>();
		params.put("id", workOrder.getId());
		params.put("status", status.getCode());
		params.put("procTaskId", procTaskId);
		params.put("updatedTime", new Date());
		params.put("updatedUserId", user.getUserId());
		params.put("neStatus", status.getCode());
		if(updateWorkOrder != null) {
			params.put("level", updateWorkOrder.getLevel());
			params.put("remark", remark);
		}
		
		int flag = workOrderDao.updateWorkOrder(params);
		if (flag < 1) {
			throw new BusinessException(WorkOrderErrStatus.ERROR.getCode(), "请刷新页面后重新操作");
		}
		// 新增日志
		workOrderLogService.addWorkOrderLog(workOrder.getId(), status, remark, user);
		
		//发送站内消息
		this.sendSiteMessage(workOrder,status, user);
		
		//推送消息
		this.pushMessage(workOrder, status, procTaskId, user);
		
	}
	
	/**
	 * 发送站内消息
	 * @param workOrder
	 * @param status
	 * @param user
	 */
	private void sendSiteMessage(final WorkOrder workOrder, final WorkOrderStatus status, final WorkOrderUserDTO user) {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					String content = String.format(" %s %s %s", user.getRoleName(), user.getUserName(), status.getDesc());
					Set<Integer> userIdSet = new HashSet<>(Arrays.asList(workOrder.getCreatedUserId()));
					workOrderMessageService.sendSiteMessage(workOrder.getId(), workOrder.getOrderNo(), content, userIdSet, user);
				} catch(Exception e) {
					logger.error("站内消息发送失败", e);
				}
			}
		});
	}
	
	/**
	 * 给下一步处理人推送消息，如果工单关闭或结束，则不推送消息
	 * @param workOrder
	 * @param status
	 * @param procTaskId
	 * @param user
	 */
	private void pushMessage(final WorkOrder workOrder, final WorkOrderStatus status, final String procTaskId, final WorkOrderUserDTO user) {
		if(!WorkOrderStatus.isFinished(status.getCode())) {
			executor.execute(new Runnable() {
				@Override
				public void run() {
					Set<Integer> userIdSet = activitiService.getTaskCandidate(procTaskId).stream().map(Integer::parseInt).collect(Collectors.toSet());
					if(userIdSet != null && userIdSet.size() > 0) {
						if(userIdSet.size() == 1 && userIdSet.contains(user.getUserId())) {
							// 同一节点处理，不推送信息
							return;
						}
						try {
							String content = String.format("您有一个新的#%s#工单#%s#需处理，点击查看", WorkOrderType.getNameByCode(workOrder.getOrderType()), workOrder.getOrderNo());
							workOrderMessageService.pushMessage(workOrder.getId(), "", content, userIdSet, user);
						} catch(Exception e) {
							logger.error("消息推送失败", e);
						}
					}
				}
			});
		}
	}
	
	/**
	 * 当前登录人是否能处理此节点订单
	 */
	@Override
	public boolean isCanProcess(String procTaskId, WorkOrderUserDTO user) {
		return activitiService.queryProcessUndoContainUserId(procTaskId, String.valueOf(user.getUserId()));
	}

	/***
	 * 查询待处理工单
	 */
	@Override
	public PagerResult<WorkOrderFindResultDto> findUnprocessWorkOrderListPage(int pageNum, int pageSize, WorkOrderStatusCondition condition, WorkOrderUserDTO user) {
		if(condition == null) {
			throw new BusinessException(WorkOrderErrStatus.ERROR.getCode(), "查询状态不能为空");
		}
		PagerResult<WorkflowTaskVO> flowPager = activitiService.queryProcessUndo(String.valueOf(user.getUserId()), condition.getCode(), pageSize, pageNum);
		Set<String> procInstIdList = flowPager.getList().stream().map(WorkflowTaskVO::getProcessInstanceId).collect(Collectors.toSet());
		List<WorkOrderFindResultDto> list = new ArrayList<>();
		if(procInstIdList.size() > 0) {
			Map<String, Object> params = new HashMap<>();
			params.put("procInstIdList", procInstIdList);
			list = workOrderDao.queryWorkOrderList(params);
		}
		
		PagerResult<WorkOrderFindResultDto> pager = new PagerResult<>();
		pager.setTotal(flowPager.getTotal());
		pager.setList(list);
		pager.setPageSize(pageSize);
		pager.setCurrentPage(pageNum);
		pager.setTotalPages((int)(pager.getTotal() + pager.getPageSize() - 1) / pager.getPageSize());
		return pager;
	}

	/**
	 * 查询进行中的订单
	 */
	@Override
	public PagerResult<WorkOrderFindResultDto> findProcessWorkOrderListPage(int pageNum, int pageSize, WorkOrderStatusCondition condition, WorkOrderUserDTO user) {
		if(condition == null) {
			throw new BusinessException(WorkOrderErrStatus.ERROR.getCode(), "查询状态不能为空");
		}
		PagerResult<WorkflowProcessInstanceVO> flowPager = activitiService.queryProcessing(String.valueOf(user.getUserId()), condition.getCode(), pageSize, pageNum);
		Set<String> procInstIdList = flowPager.getList().stream().map(WorkflowProcessInstanceVO::getProcessInstanceId).collect(Collectors.toSet());
		
		List<WorkOrderFindResultDto> list = new ArrayList<>();
		if(procInstIdList.size() > 0) {
			Map<String, Object> params = new HashMap<>();
			params.put("procInstIdList", procInstIdList);
			list = workOrderDao.queryWorkOrderList(params);
		}
		
		PagerResult<WorkOrderFindResultDto> pager = new PagerResult<>();
		pager.setTotal(flowPager.getTotal());
		pager.setList(list);
		pager.setPageSize(pageSize);
		pager.setCurrentPage(pageNum);
		pager.setTotalPages((int)(pager.getTotal() + pager.getPageSize() - 1) / pager.getPageSize());
		return pager;
	}


	/**
	 * 查询已结束工单
	 */
	@Override
	public PagerResult<WorkOrderFindResultDto> findFinishedWorkOrderListPage(int pageNum, int pageSize, WorkOrderUserDTO user) {
		PagerResult<WorkflowProcessInstanceVO> flowPager = activitiService.queryProcessed(String.valueOf(user.getUserId()), pageSize, pageNum);
		Set<String> procInstIdList = flowPager.getList().stream().map(WorkflowProcessInstanceVO::getProcessInstanceId).collect(Collectors.toSet());
		
		List<WorkOrderFindResultDto> list = new ArrayList<>();
		if(procInstIdList.size() > 0) {
			Map<String, Object> params = new HashMap<>();
			params.put("procInstIdList", procInstIdList);
			list = workOrderDao.queryWorkOrderList(params);
		}
		
		PagerResult<WorkOrderFindResultDto> pager = new PagerResult<>();
		pager.setTotal(flowPager.getTotal());
		pager.setList(list);
		pager.setPageSize(pageSize);
		pager.setCurrentPage(pageNum);
		pager.setTotalPages((int)(pager.getTotal() + pager.getPageSize() - 1) / pager.getPageSize());
		return pager;
	}

	/**
	 * 统计待处理工单数
	 * 根据不同角色，查询角色可处理的工单数
	 */
	@Override
	public Map<String, Long> statUnProcessCount(WorkOrderUserDTO user) {
		return  activitiService.statProcessUndoAllCount(String.valueOf(user.getUserId()));
	}

	/**
	 * 统计进行中工单
	 */
	@Override
	public long statProcessingCount(WorkOrderUserDTO user) {
		return activitiService.statProcessingCount(String.valueOf(user.getUserId()));
	}

	/**
	 * 统计已结束工单
	 */
	@Override
	public long statFinishedCount(WorkOrderUserDTO user) {
		return activitiService.statProcessedCount(String.valueOf(user.getUserId()));
	}

}
