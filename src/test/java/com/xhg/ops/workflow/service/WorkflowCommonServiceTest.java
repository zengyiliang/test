package com.xhg.ops.workflow.service;

import com.xhg.ops.OpsApplication;
import com.xhg.ops.workflow.model.WorkflowProcessStaticInfo;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OpsApplication.class)
public class WorkflowCommonServiceTest {
    @Autowired
    WorkflowCommonService workflowCommonService;

    @Autowired
    RepositoryService repositoryService;

    private String processDefineKey = "xhgVacationProcess";

    @Test
    public void listProcessDefinition(){
        workflowCommonService.listProcessDefinition(processDefineKey);
    }

    @Test
    public void getProcessDefinition(){
        String processDefinitionId = "xhgVacationProcess:1:52504";
        ProcessDefinition pd = workflowCommonService.getProcessDefinition(processDefinitionId);
        System.out.println("pd:"+pd);
    }

    @Test
    public void getBpmnModelByProcessDefinition(){
        String processDefinitionId = "xhgVacationProcess:97:367507";
        ProcessDefinition processDefinition = workflowCommonService.getProcessDefinition(processDefinitionId);
        BpmnModel bpmnModel = workflowCommonService.getBpmnModelByProcessDefinition(processDefinition);
        System.out.println("\n---bpmnModel:"+bpmnModel);

    }


    @Test
    public void getCurrentTask(){
        //57501
        //67501

        //170005 正在处理中
        //-1
        String processInstanceId = "170005";
        Task task = this.workflowCommonService.getCurrentTask(processInstanceId);
        System.out.println("task:"+task);

        System.out.println("task getTaskDefinitionKey:"+task.getTaskDefinitionKey());
        //System.out.println("task:"+ FastJsonUtils.toJSONString(task));


    }

    @Test
    public void parseBpmnModelForApplyInfo(){

        String processDefinitionId = "xhgVacationProcess:97:367507";
        ProcessDefinition processDefinition = workflowCommonService.getProcessDefinition(processDefinitionId);
        BpmnModel bpmnModel = workflowCommonService.getBpmnModelByProcessDefinition(processDefinition);
        System.out.println("\n---bpmnModel:"+bpmnModel);

        List<WorkflowProcessStaticInfo> list = this.workflowCommonService.parseBpmnModelForApplyInfo(bpmnModel);

        System.out.println("\n---list:"+list);

    }

    @Test
    public void getInputStreamByProcessDefId() throws IOException {

        String processDefinitionId = "xhgVacationProcess:759:5b8526b5-9311-11e8-867b-000c29383d84";
        ProcessDefinition processDefinition = workflowCommonService.getProcessDefinition(processDefinitionId);
        System.out.println("\n---processDefinition:"+processDefinition);

        String resourceName = processDefinition.getResourceName();
        String diagramResourceName = processDefinition.getDiagramResourceName();

        System.out.println("\n---resourceName:"+resourceName+",diagramResourceName:"+diagramResourceName);

        InputStream inputStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(),
                resourceName);

        File f = new File("d:/temp/xxx.pbmn");
        FileUtils.copyInputStreamToFile(inputStream, f);



    }


}
