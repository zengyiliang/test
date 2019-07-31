package com.xhg.ops.workflow.service;

import com.xhg.core.web.vo.ResponseBean;
import com.xhg.ops.OpsApplication;
import com.xhg.ops.workflow.api.WorkflowProcessService;
import com.xhg.ops.workflow.dto.WorkflowRequestEntity;
import com.xhg.ops.workflow.dto.WorkflowResponseEntity;
import com.xhg.ops.workflow.enums.ClientSourceEnum;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OpsApplication.class)
public class WorkflowProcessServiceTest {

    @Autowired
    private WorkflowProcessService workflowProcessService;
    @Autowired
    private TaskService taskService;


    //private String userId;
    //private List<String> roleList;

   // private WorkflowRequestEntity workflowRequestEntity;
    private String processDefineKey = "xhgVacationProcess";
    private String clientSource = ClientSourceEnum.CLIENTSOURCE_WORKORDERS.getCode();
    //52505
    //57501
    //67501
    //130005
    private String processInstanceId= "0c3bad64-9334-11e8-85b7-54e1adcdbcf6";
    private String taskId = "0c8bf051-9334-11e8-85b7-54e1adcdbcf6";
    // taskId=47510




    @Test
    public void teststartProcessInstance(){
        String userId = "13";
        List<String> roleList = new ArrayList<>();
        roleList.add("employee");


        WorkflowRequestEntity workflowRequestEntity = new WorkflowRequestEntity();
        //workflowRequestEntity.setClientSource(this.clientSource);

        workflowRequestEntity.setProcessDefineKey(this.processDefineKey);
        workflowRequestEntity.setUserId(userId);
        workflowRequestEntity.setRoleCodeList(roleList);

        workflowRequestEntity.setBusinessKey("xhg_tableName:id");


        workflowRequestEntity.setClientSource(ClientSourceEnum.CLIENTSOURCE_WORKORDERS.getCode());


        Map<String, Object> paramMapLocal = new HashMap<>();
        //paramMap.put("arg",vacation);
        paramMapLocal.put("days",1);
        paramMapLocal.put("reason","请假1天20180720d");

        workflowRequestEntity.setParamMapLocal(paramMapLocal);


        Map<String, Object> paramMap = new HashMap<>();
        //paramMap.put("arg",vacation);
        paramMap.put("days",1);
        //paramMap.put("reason","请假1天20180720d");

        workflowRequestEntity.setParamMap(paramMap);

        workflowRequestEntity.setMessage("请假备注");

        System.out.println("\n==========workflowRequestEntity:"+workflowRequestEntity);

        ResponseBean<WorkflowResponseEntity> responseBean =  workflowProcessService.startProcessInstance(workflowRequestEntity);
        System.out.println("\n==========responseBean:"+responseBean);

        //根据返回的内容，设置 processInstanceId

    }

    @Test
    public void testGetCurrentTask(){
        String processInstanceId = this.processInstanceId;

        Task currentTask = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        System.out.println("\n==========currentTask:"+currentTask.getId()+","+currentTask.getName());
        //根据返回的内容，设置 taskId
    }


    @Test
    public void testclaimAndCompleteTask(){

        //根据具体的流程，选择不同的用户
        String userId = "11";
        List<String> roleList = new ArrayList<>();
        roleList.add("director");


        WorkflowRequestEntity workflowRequestEntity = new WorkflowRequestEntity();
        workflowRequestEntity.setClientSource(this.clientSource);

        workflowRequestEntity.setProcessDefineKey(this.processDefineKey);
        workflowRequestEntity.setTaskId(taskId);

        workflowRequestEntity.setUserId(userId);
        workflowRequestEntity.setRoleCodeList(roleList);


        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("result","1");

        workflowRequestEntity.setParamMapLocal(paramMap);


        workflowRequestEntity.setProcessInstanceId(this.processInstanceId);

        workflowRequestEntity.setMessage("msg:同意申请");

        ResponseBean<WorkflowResponseEntity> responseBean =  workflowProcessService.claimAndCompleteTask(workflowRequestEntity);
        System.out.println("\n========== responseBean:"+responseBean);


    }


    @Test
    public void testclaimTask(){

        String userId = "11";
        List<String> roleList = new ArrayList<>();
        roleList.add("director");

        WorkflowRequestEntity workflowRequestEntity = new WorkflowRequestEntity();
        workflowRequestEntity.setClientSource(this.clientSource);

        workflowRequestEntity.setProcessDefineKey(this.processDefineKey);
        workflowRequestEntity.setUserId(userId);
        workflowRequestEntity.setRoleCodeList(roleList);


        workflowRequestEntity.setProcessInstanceId(this.processInstanceId);


        ResponseBean<WorkflowResponseEntity> responseBean =  workflowProcessService.claimTask(workflowRequestEntity);
        System.out.println("\n==========responseBean:"+responseBean);


    }


    @Test
    public void testCompleteTask(){


        String userId = "11";
        List<String> roleList = new ArrayList<>();
        roleList.add("director");


        WorkflowRequestEntity workflowRequestEntity = new WorkflowRequestEntity();
        workflowRequestEntity.setClientSource(this.clientSource);

        workflowRequestEntity.setProcessDefineKey(this.processDefineKey);
        workflowRequestEntity.setUserId(userId);
        workflowRequestEntity.setRoleCodeList(roleList);


        workflowRequestEntity.setProcessInstanceId(this.processInstanceId);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("result","result:同意");

        workflowRequestEntity.setParamMap(paramMap);

        workflowRequestEntity.setMessage("msg:同意申请");


        ResponseBean<WorkflowResponseEntity> responseBean =  workflowProcessService.completeTask(workflowRequestEntity);
        System.out.println("\n==========responseBean:"+responseBean);


    }

    class Vacation implements Serializable {

        // 天数
        private int days;

        // 原因
        private String reason;

        public int getDays() {
            return days;
        }

        public void setDays(int days) {
            this.days = days;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        @Override
        public String toString() {
            return "Vacation{" +
                    "days=" + days +
                    ", reason='" + reason + '\'' +
                    '}';
        }
    }
}
