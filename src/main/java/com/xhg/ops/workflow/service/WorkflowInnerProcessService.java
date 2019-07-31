package com.xhg.ops.workflow.service;

import com.xhg.core.web.vo.ResponseBean;
import com.xhg.ops.workflow.dto.WorkflowRequestEntity;
import com.xhg.ops.workflow.dto.WorkflowResponseEntity;

/**
 * 流程处理
 */
public interface WorkflowInnerProcessService {
    /**
     * 启动工作流
     * @param workflowRequestEntity
     * @return
     */
    WorkflowResponseEntity startProcessInstance(WorkflowRequestEntity workflowRequestEntity);

    /**
     * 领取任务并且处理处理
     * @param workflowRequestEntity
     * @return
     */
  WorkflowResponseEntity claimAndCompleteTask(WorkflowRequestEntity workflowRequestEntity);


    /**
     * 领取任务  可能是自己领取，也可能是上级指定
     * @param workflowRequestEntity
     * @return
     */
   WorkflowResponseEntity claimTask(WorkflowRequestEntity workflowRequestEntity);


    /**
     * 完成任务（不包含领取）
     * @param workflowRequestEntity
     * @return
     */
    WorkflowResponseEntity completeTask(WorkflowRequestEntity workflowRequestEntity);


}
