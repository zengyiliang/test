package com.xhg.ops.workflow.dao;

import com.xhg.ops.workflow.dto.WorkflowQueryReq;
import com.xhg.ops.workflow.dto.WorkflowQueryResp;
import org.activiti.engine.history.HistoricActivityInstance;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 工作流中自定义查询，有些查询需要直接使用sql进行查询
 *
 * 此处的查询不涉及到工作流以外的表
 *
 */

@Repository
public interface WorkflowCustomQueryDao {
    /**
     * 查询 待处理 任务
     * @param workflowQueryReq
     * @return
     *  返回的内容中包含businessKey
     */
    Long countProcessUndoByCandidateUserIncludeBK(WorkflowQueryReq workflowQueryReq);

    List<WorkflowQueryResp> listProcessUndoByCandidateUserIncludeBK(WorkflowQueryReq workflowQueryReq);


    /**
     * 查询用户处理过的活动（大部分是任务）
     * @param workflowQueryReq
     * @return
     */
    Long countProcessedForUser(WorkflowQueryReq workflowQueryReq);

    List<WorkflowQueryResp> queryProcessedForUser(WorkflowQueryReq workflowQueryReq);




}
