package com.xhg.ops.workflow.service;

import com.xhg.ops.OpsApplication;
import com.xhg.ops.workflow.api.WorkflowDeploymentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OpsApplication.class)
public class WorkflowDeploymentServiceTest {
    @Autowired
    WorkflowDeploymentService workflowDeploymentService;

    @Autowired
    WorkflowInnerDeploymentService workflowInnerDeploymentService;

    @Value("${spring.activiti.process-definition-location-prefix}")
    private String workflowProcessLocation; //流程定义的路径


    @Test
    public void deployByZipInputStream(){
        //指定目录
        //String zipFilePath = "C:\\tmp\\bpmn.zip";
        //项目中的位置
        String zipFilePath = "src/main/resources/processes/bpmn.zip";

        System.out.println("workflowDeploymentService:"+workflowDeploymentService);
        workflowDeploymentService.deployByZipInputStream(zipFilePath);

    }

    //diagrams/HelloWorld.bpmn
    @Test
    public void deployByClasspath(){
        //项目中的位置
        //String bpmnPath = "processes";

        //从配置文件中获取bpmnpath
        String bpmnPath = workflowProcessLocation;
        System.out.println("bpmnPath:"+bpmnPath);
        bpmnPath = bpmnPath.replace("classpath:/","");
        System.out.println("bpmnPath:"+bpmnPath);


        List<String> fileNameList = new ArrayList<>();
        fileNameList.add("WorkOrderFlow_classpath.bpmn");
        fileNameList.add("WorkOrderFlow_classpath2.bpmn");

      // this.workflowInnerDeploymentService.deployByClasspath(bpmnPath,fileNameList);

    }

    @Test
    public void deployByInputStream(){
        List<String> bpmnPathList = new ArrayList<>();
        bpmnPathList.add("C:\\tmp\\bpmn\\WorkOrderFlow_classpath.bpmn");
        bpmnPathList.add("C:\\tmp\\bpmn\\WorkOrderFlow_classpath2.bpmn");

        this.workflowInnerDeploymentService.deployByInputStream(bpmnPathList);

    }


}
