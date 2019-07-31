package com.xhg.ops.workflow.api;

import com.xhg.core.web.vo.ResponseBean;
import com.xhg.ops.workflow.dto.WorkflowRequestEntity;
import com.xhg.ops.workflow.dto.WorkflowResponseEntity;

/**
 * 流程处理
 */
public interface WorkflowProcessService {
    /**
     * 启动工作流
     * @param workflowRequestEntity
     *  processDefineKey
     *  userId
     *  businessKey
     *  paramMap
     * @return
     */
    ResponseBean<WorkflowResponseEntity> startProcessInstance(WorkflowRequestEntity workflowRequestEntity);

    /**
     * 领取任务并且处理处理
     * @param workflowRequestEntity
     *  taskId
     *  userId
     *  paramMap(可选)
     *  message(可选)
     *
     *
     * @return
     */
    ResponseBean<WorkflowResponseEntity> claimAndCompleteTask(WorkflowRequestEntity workflowRequestEntity);


    /**
     * 领取任务  可能是自己领取，也可能是上级指定
     *  claimAndCompleteTask = claimTask+completeTask
     * @param workflowRequestEntity
     *  userId
     *  processInstanceId
     *
     * @return
     */
    ResponseBean<WorkflowResponseEntity> claimTask(WorkflowRequestEntity workflowRequestEntity);


    /**
     * 注意：任务必须是领取了（claimTask方法）才能够调用这个方法（这样才能够保证有某个人处理了任务）
     * 要不然后面的查询会有问题
     *
     * 完成任务（不包含领取）
     * 和 claimTask结合使用
     * @param workflowRequestEntity
     *
     *  processInstanceId
     *  message
     *  paramMap
     * @return
     */
    ResponseBean<WorkflowResponseEntity> completeTask(WorkflowRequestEntity workflowRequestEntity);


}
