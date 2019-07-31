package com.xhg.ops.workflow.api.impl;

import com.xhg.core.web.vo.ResponseBean;
import com.xhg.core.web.vo.RsBody;
import com.xhg.ops.workflow.api.WorkflowQueryService;
import com.xhg.ops.workflow.dto.WorkflowQueryReq;
import com.xhg.ops.workflow.dto.WorkflowQueryResp;
import com.xhg.ops.workflow.model.AuditRecord;
import com.xhg.ops.workflow.model.PageBean;
import com.xhg.ops.workflow.model.ProcessProgress;
import com.xhg.ops.workflow.model.WorkflowProcessStaticInfo;
import com.xhg.ops.workflow.service.WorkflowCommonService;
import com.xhg.ops.workflow.service.WorkflowInnerQueryService;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("all")
@Service
public class WorkflowQueryServiceImpl implements WorkflowQueryService {
    protected static final Logger logger = LoggerFactory.getLogger(WorkflowQueryServiceImpl.class);

    @Autowired
    private WorkflowInnerQueryService workflowInnerQueryService;

    @Autowired
    WorkflowCommonService workflowCommonService;

    @Override
    public ResponseBean<List<AuditRecord>> queryAuditRecord(WorkflowQueryReq workflowQueryReq) {
        logger.debug("queryAuditRecord workflowQueryReq:"+workflowQueryReq);
        List<AuditRecord> list = this.workflowInnerQueryService.queryAuditRecord(workflowQueryReq);
        ResponseBean responseBean = new ResponseBean();
        RsBody<List<AuditRecord>> responseBody = new RsBody<List<AuditRecord>>(list);
        responseBean.setResponseBody(responseBody);
        return responseBean;
    }

    @Override
    public ResponseBean<PageBean<WorkflowQueryResp>> queryProcessUndo(WorkflowQueryReq workflowQueryReq) {
        logger.debug("queryProcessUndo workflowQueryReq:"+workflowQueryReq);
        ResponseBean responseBean = new ResponseBean();
        PageBean<WorkflowQueryResp> responseBody = new PageBean<WorkflowQueryResp>();
        List<WorkflowQueryResp> respList =new ArrayList<>();

        PageBean<Task> pageBean =  this.workflowInnerQueryService.queryProcessUndoByCandidateUser(workflowQueryReq);
        if( null != pageBean && pageBean.getTotalCount() >0){
            List<Task> list = pageBean.getList();
            list.forEach(p->{
                WorkflowQueryResp resp=new WorkflowQueryResp();
                resp.setTaskDoc(p.getDescription());
                resp.setTaskId(p.getId());
                resp.setUserId(p.getAssignee());
                resp.setCreateTime(p.getCreateTime());
                resp.setName(p.getName());
                resp.setProcessInstanceId(p.getProcessInstanceId());
                respList.add(resp);
            });
            responseBody.setList(respList);
            responseBody.setTotalCount(pageBean.getTotalCount());
            responseBody.setPageNo(workflowQueryReq.getPageNo());
            responseBody.setPageSize(workflowQueryReq.getPageSize());

        }else{
            responseBody.setList(respList);
            responseBody.setTotalCount(0L);
            responseBody.setPageNo(workflowQueryReq.getPageNo());
            responseBody.setPageSize(workflowQueryReq.getPageSize());
        }

        RsBody<PageBean<WorkflowQueryResp>> rsBody=new RsBody<PageBean<WorkflowQueryResp>>(responseBody);
        responseBean.setResponseBody(rsBody);
        return responseBean;



    }

    @Override
    public ResponseBean<PageBean<WorkflowQueryResp>> queryProcessing(WorkflowQueryReq workflowQueryReq) {
        logger.debug("queryProcessing workflowQueryReq:"+workflowQueryReq);
        ResponseBean responseBean = new ResponseBean();
        PageBean<WorkflowQueryResp> responseBody = new PageBean<WorkflowQueryResp>();
        List<WorkflowQueryResp> respList =new ArrayList<>();

        PageBean<HistoricProcessInstance> pageBean =  this.workflowInnerQueryService.queryProcessing(workflowQueryReq);
        if( null != pageBean && pageBean.getTotalCount() >0){
            List<HistoricProcessInstance> list = pageBean.getList();
            list.forEach(p->{
                WorkflowQueryResp resp=new WorkflowQueryResp();
                resp.setProcessInstanceId(p.getId());
                resp.setBusinessKey(p.getBusinessKey());
                resp.setStartUserId(p.getStartUserId());
                resp.setCreateTime(p.getStartTime());
                resp.setEndTime(p.getEndTime());
                resp.setDueDate(p.getDurationInMillis());
                resp.setName(p.getProcessDefinitionName());
                resp.setProcessDefineKey(p.getProcessDefinitionKey());
                respList.add(resp);
            });
            responseBody.setList(respList);
            responseBody.setTotalCount(pageBean.getTotalCount());
            responseBody.setPageNo(workflowQueryReq.getPageNo());
            responseBody.setPageSize(workflowQueryReq.getPageSize());

        }else{
            responseBody.setList(respList);
            responseBody.setTotalCount(0L);
            responseBody.setPageNo(workflowQueryReq.getPageNo());
            responseBody.setPageSize(workflowQueryReq.getPageSize());
        }

        RsBody<PageBean<WorkflowQueryResp>> rsBody=new RsBody<PageBean<WorkflowQueryResp>>(responseBody);
        responseBean.setResponseBody(rsBody);
        return responseBean;
    }


    @Override
    public ResponseBean<PageBean<WorkflowQueryResp>> queryProcessed(WorkflowQueryReq workflowQueryReq) {
        logger.debug("queryProcessed workflowQueryReq:"+workflowQueryReq);
        ResponseBean responseBean = new ResponseBean();
        PageBean<WorkflowQueryResp> responseBody = new PageBean<WorkflowQueryResp>();
        List<WorkflowQueryResp> respList =new ArrayList<>();

        PageBean<HistoricProcessInstance> pageBean =  this.workflowInnerQueryService.queryProcessed(workflowQueryReq);
        if( null != pageBean && pageBean.getTotalCount() >0){
            List<HistoricProcessInstance> list = pageBean.getList();
            list.forEach(p->{
                WorkflowQueryResp resp=new WorkflowQueryResp();
                resp.setProcessInstanceId(p.getId());
                resp.setBusinessKey(p.getBusinessKey());
                resp.setStartUserId(p.getStartUserId());
                resp.setCreateTime(p.getStartTime());
                resp.setEndTime(p.getEndTime());
                resp.setDueDate(p.getDurationInMillis());
                resp.setName(p.getProcessDefinitionName());
                resp.setProcessDefineKey(p.getProcessDefinitionKey());
                respList.add(resp);
            });
            responseBody.setList(respList);
            responseBody.setTotalCount(pageBean.getTotalCount());
            responseBody.setPageNo(workflowQueryReq.getPageNo());
            responseBody.setPageSize(workflowQueryReq.getPageSize());

        }else{
            responseBody.setList(respList);
            responseBody.setTotalCount(0L);
            responseBody.setPageNo(workflowQueryReq.getPageNo());
            responseBody.setPageSize(workflowQueryReq.getPageSize());
        }

        RsBody<PageBean<WorkflowQueryResp>> rsBody=new RsBody<PageBean<WorkflowQueryResp>>(responseBody);
        responseBean.setResponseBody(rsBody);
        return responseBean;
    }

    @Override
    public ResponseBean<Long> statProcessUndo(WorkflowQueryReq workflowQueryReq) {
        logger.debug("statProcessUndo workflowQueryReq:"+workflowQueryReq);
        Long count = this.workflowInnerQueryService.statProcessUndo(workflowQueryReq);
        ResponseBean responseBean = new ResponseBean();
        RsBody<Long> responseBody = new RsBody<Long>(count);
        responseBean.setResponseBody(responseBody);
        return responseBean;
    }

    @Override
    public ResponseBean<Long> statProcessing(WorkflowQueryReq workflowQueryReq) {
        logger.debug("statProcessing workflowQueryReq:"+workflowQueryReq);
        Long count = this.workflowInnerQueryService.statProcessing(workflowQueryReq);
        ResponseBean responseBean = new ResponseBean();
        RsBody<Long> responseBody = new RsBody<Long>(count);
        responseBean.setResponseBody(responseBody);
        return responseBean;
    }

    @Override
    public ResponseBean<Long> statProcessed(WorkflowQueryReq workflowQueryReq) {
        Long count = this.workflowInnerQueryService.statProcessed(workflowQueryReq);
        ResponseBean responseBean = new ResponseBean();
        RsBody<Long> responseBody = new RsBody<Long>(count);
        responseBean.setResponseBody(responseBody);
        return responseBean;
    }
    
    @Override
	public ResponseBean<Set<User>> getTaskCandidate(WorkflowQueryReq workflowQueryReq) {
		Set<User> userSet = workflowInnerQueryService.getTaskCandidate(workflowQueryReq);
		ResponseBean responseBean = new ResponseBean();
		RsBody<Set<User>> responseBody = new RsBody<Set<User>>(userSet);
		responseBean.setResponseBody(responseBody);
		return responseBean;
	}

	@Override
	public ResponseBean<Task> getCurrentTaskByProcessInstanceId(WorkflowQueryReq workflowQueryReq) {
		Task task = workflowInnerQueryService.getCurrentTaskByProcessInstanceId(workflowQueryReq);
		ResponseBean responseBean = new ResponseBean();
		RsBody<Task> responseBody = new RsBody<Task>(task);
		responseBean.setResponseBody(responseBody);
		return responseBean;
	}

	@Override
	public ResponseBean<Boolean> queryProcessUndoContainTaskId(WorkflowQueryReq workflowQueryReq) {
		Boolean flag = workflowInnerQueryService.queryProcessUndoContainTaskId(workflowQueryReq);
		ResponseBean responseBean = new ResponseBean();
		RsBody<Boolean> responseBody = new RsBody<Boolean>(flag);
		responseBean.setResponseBody(responseBody);
		return responseBean;
	}

    @Override
    public List<ProcessProgress> queryProcessProgress(WorkflowQueryReq workflowQueryReq) {
        return workflowInnerQueryService.queryProcessProgress(workflowQueryReq);
    }

    @Override
    public List<ProcessProgress> queryProcessProgressMinInfo(WorkflowQueryReq workflowQueryReq) {
        return workflowInnerQueryService.queryProcessProgressMinInfo(workflowQueryReq);
    }

    @Override
    public List<ProcessProgress> queryProcessProgressStatic(WorkflowQueryReq workflowQueryReq) {
        return this.workflowInnerQueryService.queryProcessProgressStatic(workflowQueryReq);
    }

    @Override
    public ResponseBean<PageBean<WorkflowQueryResp>> queryProcessingByDB(WorkflowQueryReq workflowQueryReq) {
        //
        PageBean<WorkflowQueryResp> pb = this.workflowInnerQueryService.queryProcessingByDB(workflowQueryReq);

        ResponseBean responseBean = new ResponseBean();

        RsBody<PageBean<WorkflowQueryResp>> rsBody=new RsBody<PageBean<WorkflowQueryResp>>(pb);
        responseBean.setResponseBody(rsBody);
        return responseBean;


    }

	@Override
	public ResponseBean<Long> statProcessingByDB(WorkflowQueryReq workflowQueryReq) {
	    logger.debug("statProcessingByDB workflowQueryReq:"+workflowQueryReq);
        Long count = this.workflowInnerQueryService.statProcessingByDB(workflowQueryReq);
        ResponseBean responseBean = new ResponseBean();
        RsBody<Long> responseBody = new RsBody<Long>(count);
        responseBean.setResponseBody(responseBody);
        return responseBean;
	}

    @Override
    public PageBean<WorkflowQueryResp> queryProcessUndoByCandidateUserIncludeBK(WorkflowQueryReq workflowQueryReq) {
        return this.workflowInnerQueryService.queryProcessUndoByCandidateUserIncludeBK(workflowQueryReq);
    }

    @Override
    public WorkflowProcessStaticInfo getCurrentTaskStaticInfo(WorkflowQueryReq workflowQueryReq) {
        return this.workflowInnerQueryService.getCurrentTaskStaticInfo(workflowQueryReq);
    }

    @Override
    public List<WorkflowProcessStaticInfo> queryWorkflowProcessStaticInfo(WorkflowQueryReq workflowQueryReq) {
        return this.workflowInnerQueryService.queryWorkflowProcessStaticInfo(workflowQueryReq);
    }

    @Override
    public ResponseBean<WorkflowQueryResp> queryProcessedForUser(WorkflowQueryReq workflowQueryReq) {
        PageBean<WorkflowQueryResp> pb = this.workflowInnerQueryService.queryProcessedForUser(workflowQueryReq);

        ResponseBean responseBean = new ResponseBean();

        RsBody<PageBean<WorkflowQueryResp>> rsBody=new RsBody<PageBean<WorkflowQueryResp>>(pb);
        responseBean.setResponseBody(rsBody);
        return responseBean;
    }

    @Override
    public List<WorkflowProcessStaticInfo> parseBpmnModelForApplyInfo(WorkflowQueryReq workflowQueryReq) {
        ProcessDefinition processDefinition = this.workflowCommonService.listProcessDefinition(workflowQueryReq.getProcessDefineKey()).get(0);
        logger.info("parseBpmnModelForApplyInfo processDefinition:"+processDefinition);
        BpmnModel bpmnModel = this.workflowCommonService.getBpmnModelByProcessDefinition(processDefinition);
        logger.info("parseBpmnModelForApplyInfo bpmnModel:"+bpmnModel);

        List<WorkflowProcessStaticInfo> list = this.workflowCommonService.parseBpmnModelForApplyInfo(bpmnModel);
        logger.info("parseBpmnModelForApplyInfo list:"+list);
        return list;
    }


}
