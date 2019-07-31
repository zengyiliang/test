package com.xhg.ops.workflow.service;

import com.xhg.ops.OpsApplication;
import com.xhg.ops.workflow.dto.WorkflowQueryReq;
import com.xhg.ops.workflow.enums.ProcessEnum;
import com.xhg.ops.workflow.enums.ProcessStatusEnum;
import com.xhg.ops.workflow.model.AuditRecord;
import com.xhg.ops.workflow.model.PageBean;
import com.xhg.ops.workflow.model.ProcessProgress;
import com.xhg.ops.workflow.model.WorkflowProcessStaticInfo;
import com.xhg.ops.workflow.vo.WorkflowTaskVO;
import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OpsApplication.class)
public class WorkflowInnerQueryServiceTest {
    @Autowired
    WorkflowInnerQueryService workflowInnerQueryService;

    @Autowired
    HistoryService historyService;

    @Autowired
    TaskService taskService;

    private String processDefineKey = "xhgVacationProcess";

    @Test
    public void testQueryProcessStatus(){
        //45005
        //67501
        //-1
        String processInstanceId =  "-1";

        ProcessStatusEnum pse = this.workflowInnerQueryService.queryProcessStatus(processInstanceId);
        System.out.println("\n--- pse:"+pse);
    }

    @Test
    public void testqueryAuditRecord(){
        WorkflowQueryReq workflowQueryReq = new WorkflowQueryReq();
        //"67501"
        //45005
        workflowQueryReq.setProcessInstanceId("67501");

        List<AuditRecord> list = this.workflowInnerQueryService.queryAuditRecord(workflowQueryReq);

        System.out.println("\n--- list:"+list);
    }


    @Test
    public void testqueryProcessUndo(){
        //准备一个测试数据，开始申请，找到下一步执行人的信息和流程实例id

        WorkflowQueryReq workflowQueryReq = new WorkflowQueryReq();
        workflowQueryReq.setUserId("11");

        List<String> roleCodeList = new ArrayList<>();
        roleCodeList.add("manager");

        workflowQueryReq.setPageNo(2);
        workflowQueryReq.setPageSize(2);

        workflowQueryReq.setRoleCodeList(roleCodeList);

        //对比加与不加的查询效果
        workflowQueryReq.setBusinessKey("xhg_tableName:id");

        this.workflowInnerQueryService.queryProcessUndo(workflowQueryReq);

       // System.out.println("\n--- list:"+list);
    }


    @Test
    public void queryProcessUndoByCandidateGroup(){

        WorkflowQueryReq workflowQueryReq = new WorkflowQueryReq();
        //workflowQueryReq.setUserId("11");

        List<String> roleCodeList = new ArrayList<>();
        roleCodeList.add("manager");

        workflowQueryReq.setRoleCodeList(roleCodeList);
       // workflowQueryReq.setProcessInstanceId("170005");

        this.workflowInnerQueryService.queryProcessUndoByCandidateGroup(workflowQueryReq);

    }



    @Test
    public void queryProcessUndoByCandidateUser(){

        WorkflowQueryReq workflowQueryReq = new WorkflowQueryReq();
        workflowQueryReq.setUserId("11");


        this.workflowInnerQueryService.queryProcessUndoByCandidateUser(workflowQueryReq);

    }

    @Test
    public void queryProcessing(){

        WorkflowQueryReq workflowQueryReq = new WorkflowQueryReq();
        //11
        //24
        workflowQueryReq.setUserId("11");
        workflowQueryReq.setProcessDefineKey(processDefineKey);

        this.workflowInnerQueryService.queryProcessing(workflowQueryReq);
    }

    @Test
    public void queryProcessing2(){

        WorkflowQueryReq workflowQueryReq = new WorkflowQueryReq();
        //11
        //24
        workflowQueryReq.setUserId("26");
        workflowQueryReq.setProcessDefineKey("WORKORDERPROCESS");

        PageBean<HistoricProcessInstance> pb = this.workflowInnerQueryService.queryProcessing(workflowQueryReq);
        System.out.println("pb:"+pb);
        System.out.println("pb:"+pb.getTotalCount());
        System.out.println("pb:"+pb.getList());
    }

    @Test
    public void  queryProcessed(){
        WorkflowQueryReq workflowQueryReq = new WorkflowQueryReq();
        workflowQueryReq.setUserId("11");
        workflowQueryReq.setProcessDefineKey(processDefineKey);

        this.workflowInnerQueryService.queryProcessed(workflowQueryReq);
    }

    @Test
    public void  statProcessUndo(){
        WorkflowQueryReq workflowQueryReq = new WorkflowQueryReq();
        workflowQueryReq.setUserId("11");

        List<String> roleCodeList = new ArrayList<>();
        roleCodeList.add("manager");

        workflowQueryReq.setPageNo(2);
        workflowQueryReq.setPageSize(2);

        workflowQueryReq.setRoleCodeList(roleCodeList);

        //对比加与不加的查询效果
        workflowQueryReq.setBusinessKey("xhg_tableName:id");

       Long count = this.workflowInnerQueryService.statProcessUndo(workflowQueryReq);

       System.out.println("count:"+count);

    }


    @Test
    public void statProcessing(){

        WorkflowQueryReq workflowQueryReq = new WorkflowQueryReq();
        workflowQueryReq.setUserId("11");
        workflowQueryReq.setProcessDefineKey(processDefineKey);

        this.workflowInnerQueryService.statProcessing(workflowQueryReq);
    }

    @Test
    public void statProcessing2(){

        WorkflowQueryReq workflowQueryReq = new WorkflowQueryReq();
        workflowQueryReq.setUserId("26");
        workflowQueryReq.setProcessDefineKey("WORKORDERPROCESS");

        Long count = this.workflowInnerQueryService.statProcessing(workflowQueryReq);
        System.out.println(count);
    }

    @Test
    public void  statProcessed(){
        WorkflowQueryReq workflowQueryReq = new WorkflowQueryReq();
        workflowQueryReq.setUserId("11");
        workflowQueryReq.setProcessDefineKey(processDefineKey);

        this.workflowInnerQueryService.statProcessed(workflowQueryReq);
    }

    @Test
    public void testQueryProcessHistory(){
        //
        // String instanceId="742532";
        String instanceId="67501";

        List<WorkflowTaskVO> voList=new ArrayList<>();
        List<HistoricTaskInstance> taskList= historyService.createHistoricTaskInstanceQuery().processInstanceId(instanceId).orderByTaskCreateTime().desc().list();
        System.out.println("taskList:"+taskList);
        List<Comment> commentList=taskService.getProcessInstanceComments(instanceId);
        System.out.println("commentList:"+commentList);

        taskList.forEach(p->{
            WorkflowTaskVO vo=new WorkflowTaskVO();
            commentList.forEach(c->{
                System.out.println(p.getId()+"_________"+c.getTaskId());

                if(p.getId().equals(c.getTaskId())){
                    vo.setComment(c.getFullMessage());
                    System.out.println("p.getProcessVariables():"+p.getProcessVariables());
                    System.out.println("p.getTaskLocalVariables():"+p.getTaskLocalVariables());
                }
            });
            vo.setId(p.getId());
            voList.add(vo);
        });
        System.out.println("\n==========");
        voList.forEach(p->{
            System.out.println(p.getId()+"__"+p.getComment());
        });

        System.out.println("\n==========");
        System.out.println("\n==========voList:"+voList);

    }



    @Test
    public void queryProcessing2222(){

        WorkflowQueryReq workflowQueryReq = new WorkflowQueryReq();
        //11
        //24
        workflowQueryReq.setUserId("24");
        workflowQueryReq.setProcessDefineKey("WORKORDERPROCESS");

        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery()
                .taskAssignee("24").finished()
                .list();

        System.out.println("\n==========list:"+list);
    }

    @Test
    public void queryProcessProgressStatic(){
        WorkflowQueryReq workflowQueryReq = new WorkflowQueryReq();
        workflowQueryReq.setProcessDefineKey(ProcessEnum.MARKETLOCATION_PICKPLACEPROCESS.getProcessKey());

        List<ProcessProgress> list = this.workflowInnerQueryService.queryProcessProgressStatic(workflowQueryReq);

        System.out.println("\n==========list:"+list);

    }

    @Test
    public void queryWorkflowProcessStaticInfo(){
        WorkflowQueryReq workflowQueryReq = new WorkflowQueryReq();

        //流程还没有结束
        workflowQueryReq.setProcessInstanceId("c6102007-8f19-11e8-a5f9-54e1adcdbcf6");
        workflowQueryReq.setProcessDefineKey("xhgVacationProcess");

            // 流程已经结束
        /**
          workflowQueryReq.setProcessInstanceId("0ac8826a-907b-11e8-ba33-94c6912b8bbb");
          workflowQueryReq.setProcessDefineKey("pickplaceProcess");
        */
        List<WorkflowProcessStaticInfo> list = this.workflowInnerQueryService.queryWorkflowProcessStaticInfo(workflowQueryReq);

        System.out.println("\n==========list:"+list);

    }

    @Test
    public void getCurrentTaskStaticInfo(){
        WorkflowQueryReq workflowQueryReq = new WorkflowQueryReq();
        workflowQueryReq.setProcessInstanceId("c6102007-8f19-11e8-a5f9-54e1adcdbcf6");

        WorkflowProcessStaticInfo item = this.workflowInnerQueryService.getCurrentTaskStaticInfo(workflowQueryReq);

        System.out.println("\n==========item:"+item);
    }







}
