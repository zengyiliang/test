package com.xhg.ops.workflow.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhg.core.web.vo.ResponseBean;
import com.xhg.core.web.vo.RsBody;
import com.xhg.ops.workflow.api.WorkflowProcessService;
import com.xhg.ops.workflow.dto.WorkflowRequestEntity;
import com.xhg.ops.workflow.dto.WorkflowResponseEntity;
import com.xhg.ops.workflow.service.WorkflowInnerProcessService;

import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("all")
@Service
public class WorkflowProcessServiceImpl implements WorkflowProcessService {
    protected static final Logger logger = LoggerFactory.getLogger(WorkflowProcessServiceImpl.class);

    @Autowired
    private WorkflowInnerProcessService workflowInnerProcessService;

	@Override
    public ResponseBean<WorkflowResponseEntity> startProcessInstance(WorkflowRequestEntity workflowRequestEntity) {
        logger.info("startProcessInstance workflowRequestEntity:"+workflowRequestEntity);
        WorkflowResponseEntity workflowResponseEntity = this.workflowInnerProcessService.startProcessInstance(workflowRequestEntity);

        return this.genResponseBean(workflowResponseEntity);
    }

    @Override
    public ResponseBean<WorkflowResponseEntity> claimAndCompleteTask(WorkflowRequestEntity workflowRequestEntity) {
        logger.info("claimAndCompleteTask workflowRequestEntity:"+workflowRequestEntity);
        WorkflowResponseEntity workflowResponseEntity = workflowInnerProcessService.claimAndCompleteTask(workflowRequestEntity);
        return this.genResponseBean(workflowResponseEntity);
    }

    @Override
    public ResponseBean<WorkflowResponseEntity> claimTask(WorkflowRequestEntity workflowRequestEntity) {
        logger.info("claimTask workflowRequestEntity:"+workflowRequestEntity);
        WorkflowResponseEntity workflowResponseEntity = workflowInnerProcessService.claimTask(workflowRequestEntity);
        return this.genResponseBean(workflowResponseEntity);
    }

    @Override
    public ResponseBean<WorkflowResponseEntity> completeTask(WorkflowRequestEntity workflowRequestEntity) {
        logger.info("completeTask workflowRequestEntity:"+workflowRequestEntity);
        WorkflowResponseEntity workflowResponseEntity = workflowInnerProcessService.completeTask(workflowRequestEntity);
        return this.genResponseBean(workflowResponseEntity);


    }

    public ResponseBean<WorkflowResponseEntity> test(WorkflowRequestEntity workflowRequestEntity){
        logger.debug("\n-----------WorkflowProcessServiceImpl workflowRequestEntity :"+workflowRequestEntity);
        WorkflowResponseEntity workflowResponseEntity = new WorkflowResponseEntity();
        ResponseBean responseBean = new ResponseBean();
        RsBody<WorkflowResponseEntity> responseBody = new RsBody<WorkflowResponseEntity>(workflowResponseEntity);
        responseBean.setResponseBody(responseBody);

        logger.debug("\n-----------WorkflowProcessServiceImpl responseBean :"+responseBean);

        return responseBean;
    }

    private ResponseBean genResponseBean(WorkflowResponseEntity workflowResponseEntity){
        logger.debug("\n-----------genResponseBean workflowResponseEntity :"+workflowResponseEntity);
        ResponseBean responseBean = new ResponseBean();
        RsBody<WorkflowResponseEntity> responseBody = new RsBody<WorkflowResponseEntity>(workflowResponseEntity);
        responseBean.setResponseBody(responseBody);

        logger.debug("\n-----------WorkflowProcessServiceImpl genResponseBean responseBean:"+responseBean);

        return responseBean;
    }


}
