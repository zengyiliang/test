package com.xhg.ops.workflow.dao;

import com.xhg.ops.workflow.dto.WorkOrderReqParam;
import com.xhg.ops.workflow.dto.WorkflowQueryReq;
import com.xhg.ops.workflow.dto.WorkflowQueryResp;
import com.xhg.ops.workorders.model.WorkOrder;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 与具体业务表关联的 工作流查询
 *
 * 这样做的好处：查询速度快
 *
 *
 * 缺点：1.不方便分库 2.和具体的工作流版本绑定在一起，不方便升级
 *
 */
@Repository
public interface WorkflowBusinessDao {

    /**
     * 统计工单 已结束
     * @param workOrderReqParam
     * @return
     */
	Long countWorkOrderProcessed(WorkOrderReqParam workOrderReqParam);

    /**
     * 查询 工单 已结束
     * @param workOrderReqParam
     * @return
     */
    List<WorkOrder> listWorkOrderProcessed(WorkOrderReqParam workOrderReqParam);

    /**
     * 统计工单 正在进行中
     * @param workflowQueryReq
     * processDefineKey
     * userId(可选)
     * orderNo(可选)
     * orderType(可选)
     * createdUserId(可选)
     * @return
     */
    Long countWorkOrderProcessing(WorkflowQueryReq workflowQueryReq);

    /**
     * 查询 工单 正在进行中
     * @param workflowQueryReq
     *  processDefineKey
     * userId(可选)
     * orderNo(可选)
     * orderType(可选)
     * createdUserId(可选)
     * @return
     */
    List<WorkflowQueryResp> listWorkOrderProcessing(WorkflowQueryReq workflowQueryReq);


    /**
     * 统计工单 待处理
     * @param workOrderReqParam
     * @return
     */
    Long countWorkOrderProcessUndo(WorkOrderReqParam workOrderReqParam);

    /**
     * 查询 工单 待处理
     * @param workOrderReqParam
     * @return
     */
    List<WorkOrder> listWorkOrderProcessUndo(WorkOrderReqParam workOrderReqParam);




}
