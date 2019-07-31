package com.xhg.ops.workflow.service;

import com.xhg.core.web.vo.ResponseBean;
import com.xhg.ops.OpsApplication;
import com.xhg.ops.workflow.dto.WorkflowQueryReq;
import com.xhg.ops.workflow.dto.WorkflowQueryResp;
import com.xhg.ops.workflow.enums.ProcessEnum;
import com.xhg.ops.workflow.model.PageBean;

import com.xhg.ops.workflow.model.ProcessProgress;
import com.xhg.ops.workflow.model.WorkflowProcessStaticInfo;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.xhg.ops.workflow.api.WorkflowQueryService;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OpsApplication.class)
public class WorkflowQueryServiceTest {
    @Autowired
    private WorkflowQueryService workflowQueryService;

    private String processDefineKey = ProcessEnum.WORK_ORDER_PROCESS.getProcessKey();


    @Test
    public void queryAuditRecord(){
        WorkflowQueryReq workflowQueryReq = new WorkflowQueryReq();

        workflowQueryService.queryAuditRecord(workflowQueryReq);
    }


    @Test
    public void  queryProcessUndo(){
        WorkflowQueryReq workflowQueryReq = new WorkflowQueryReq();
        workflowQueryReq.setUserId("11");

        List<String> roleCodeList = new ArrayList<>();
        roleCodeList.add("manager");
        workflowQueryReq.setRoleCodeList(roleCodeList);


        workflowQueryReq.setProcessDefineKey(processDefineKey);

        this.workflowQueryService.queryProcessUndo(workflowQueryReq);
    }

    @Test
    public void queryProcessing(){

        WorkflowQueryReq workflowQueryReq = new WorkflowQueryReq();
        workflowQueryReq.setUserId("hubowei");
        workflowQueryReq.setPageSize(1);
        workflowQueryReq.setPageNo(20);
        workflowQueryReq.setProcessDefineKey(processDefineKey);

        ResponseBean<PageBean<WorkflowQueryResp>> responseBean=this.workflowQueryService.queryProcessing(workflowQueryReq);
        List<WorkflowQueryResp> list=responseBean.getResponseBody().getData().getList();
        list.forEach(p->{
        	System.out.println(p);
        });
    }

    @Test
    public void  queryProcessed(){
        WorkflowQueryReq workflowQueryReq = new WorkflowQueryReq();
        workflowQueryReq.setUserId("11");
        workflowQueryReq.setProcessDefineKey(processDefineKey);

        this.workflowQueryService.queryProcessed(workflowQueryReq);
    }


    @Test
    public void  queryProcessProgress(){
        WorkflowQueryReq workflowQueryReq = new WorkflowQueryReq();
        workflowQueryReq.setProcessInstanceId("0ca7b865-8e56-11e8-b094-54e1adcdbcf6");
        List<ProcessProgress> list = this.workflowQueryService.queryProcessProgress(workflowQueryReq);

        System.out.println("list:"+list);

        for(ProcessProgress pp : list){
            System.out.println(pp);
        }

        for(ProcessProgress pp : list){
            System.out.println(pp.getActName()+","+",userId:"+pp.getAssIgnee()+",相关参数："+pp.getTaskParamLocal()+",审批意见："+pp.getComment());
        }

    }


    @Test
    public void  queryProcessProgressMinInfo(){
        WorkflowQueryReq workflowQueryReq = new WorkflowQueryReq();
        //1557508
        //0ac8826a-907b-11e8-ba33-94c6912b8bbb
        workflowQueryReq.setProcessInstanceId("0ac8826a-907b-11e8-ba33-94c6912b8bbb");
        List<ProcessProgress> list = this.workflowQueryService.queryProcessProgressMinInfo(workflowQueryReq);

        System.out.println("list:"+list);

        for(ProcessProgress pp : list){
            System.out.println(pp);
        }

        for(ProcessProgress pp : list){
            System.out.println("actId:"+pp.getActId()+","+pp.getActName()+","+",userId:"+pp.getAssIgnee()+",相关参数："+pp.getTaskParam()+",审批意见："+pp.getComment()+","+pp.getOperateTime());
        }

    }

    @Test
    public void  queryProcessUndoByCandidateUserIncludeBK(){
        WorkflowQueryReq workflowQueryReq = new WorkflowQueryReq();
        workflowQueryReq.setProcessDefineKey("pickplaceProcess");
        workflowQueryReq.setUserId("50");

        List<String> roleCodeList = new ArrayList<>();
        roleCodeList.add("Branch Office Auditor");

        workflowQueryReq.setRoleCodeList(roleCodeList);


        PageBean<WorkflowQueryResp>  bp = this.workflowQueryService.queryProcessUndoByCandidateUserIncludeBK(workflowQueryReq);

        System.out.println("bp:"+bp);

    }


    @Test
    public void getCurrentTaskStaticInfo(){
        WorkflowQueryReq workflowQueryReq = new WorkflowQueryReq();
        //af6092ba-8f44-11e8-b2dd-94c6912b8bbb
        //c6102007-8f19-11e8-a5f9-54e1adcdbcf6
        workflowQueryReq.setProcessInstanceId("af6092ba-8f44-11e8-b2dd-94c6912b8bbb");

        WorkflowProcessStaticInfo item = this.workflowQueryService.getCurrentTaskStaticInfo(workflowQueryReq);

        System.out.println("\n==========item:"+item);
    }

    @Test
    public void queryProcessProgressStatic(){
        WorkflowQueryReq workflowQueryReq = new WorkflowQueryReq();
        workflowQueryReq.setProcessDefineKey(ProcessEnum.MARKETLOCATION_PICKPLACEPROCESS.getProcessKey());

        List<ProcessProgress> list = this.workflowQueryService.queryProcessProgressStatic(workflowQueryReq);

        System.out.println("\n==========list:"+list);


        //
        workflowQueryReq.setProcessDefineKey(ProcessEnum.MARKETLOCATION_INSTALLPROCESS.getProcessKey());

        list = this.workflowQueryService.queryProcessProgressStatic(workflowQueryReq);

        System.out.println("\n==========list:"+list);


        // 非常规合同
        workflowQueryReq.setProcessDefineKey(ProcessEnum.MARKETLOCATION_CONTRACTPROCESS_UNCONVENTIONAL.getProcessKey());

        list = this.workflowQueryService.queryProcessProgressStatic(workflowQueryReq);

        System.out.println("\n==========contractProcess_unconventional list:"+list);

        //常规合同
        workflowQueryReq.setProcessDefineKey(ProcessEnum.MARKETLOCATION_CONTRACTPROCESS_CONVENTIONAL.getProcessKey());

        list = this.workflowQueryService.queryProcessProgressStatic(workflowQueryReq);

        System.out.println("\n==========contractProcess_conventional list:"+list);

    }


    @Test
    public void queryProcessingByDB(){
        WorkflowQueryReq workflowQueryReq = new WorkflowQueryReq();
        workflowQueryReq.setProcessDefineKey("WORKORDERPROCESS");
        workflowQueryReq.setUserId("24");

        ResponseBean<PageBean<WorkflowQueryResp>> responseBean = this.workflowQueryService.queryProcessingByDB(workflowQueryReq);
        System.out.println("\n==========responseBean:"+responseBean);
    }

    @Test
    public void queryProcessedForUser(){
        WorkflowQueryReq workflowQueryReq = new WorkflowQueryReq();
        workflowQueryReq.setUserId("11");
        workflowQueryReq.setProcessDefineKey("xhgVacationProcess");

        ResponseBean<WorkflowQueryResp> responseBean = this.workflowQueryService.queryProcessedForUser(workflowQueryReq);

        System.out.println("\n==========responseBean:"+responseBean);

    }


    @Test
    public void parseBpmnModelForApplyInfo(){
        WorkflowQueryReq workflowQueryReq = new WorkflowQueryReq();
        //contractProcess
        //xhgVacationProcess
        //pickplaceProcess
        //pickplaceProcess
        workflowQueryReq.setProcessDefineKey("xhgVacationProcess");
        List<WorkflowProcessStaticInfo> list = this.workflowQueryService.parseBpmnModelForApplyInfo(workflowQueryReq);

        System.out.println("\n---list:"+list);

    }



}
