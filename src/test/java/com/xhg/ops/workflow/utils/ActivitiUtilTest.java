package com.xhg.ops.workflow.utils;


import com.xhg.ops.OpsApplication;
import com.xhg.ops.workflow.model.ProcessInfo;
import org.activiti.bpmn.model.BpmnModel;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.Collection;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OpsApplication.class)
public class ActivitiUtilTest {
    @Value("${xhg.activiti.process-definitioin-path}")
    private String workflowProcessLocation; //流程定义的路径

    @Value("${xhg.activiti.use.tenant}")
    private boolean useTenant;

    @Test
    public void getWorkflowProcessDir(){
        System.out.println("workflowProcessLocation:"+workflowProcessLocation);
        String dir = ActivitiUtil.getWorkflowProcessDir(workflowProcessLocation);
        System.out.println("dir:"+dir);
    }

    @Test
    public void getWorkflowProcessPath(){
        System.out.println("workflowProcessLocation:"+workflowProcessLocation);
        String path = ActivitiUtil.getWorkflowProcessPath(workflowProcessLocation);
        System.out.println("path:"+path);
    }

    @Test
    public void file(){
        File myFile = new File("D:\\temp\\xhg-work-orders\\branches\\1.0.0\\src\\main\\resources\\processes");
        File[] dirfile = myFile.listFiles();
        for(File item:dirfile){
            System.out.println("item:"+item);
        }

        System.out.println("\n\n===========:");
        for(File item:dirfile){
            if(item.isFile() && item.getName().endsWith(".txt")){
                System.out.println("item:"+item);
            }

        }


    }


    @Test
    public void file2(){
        File myFile = new File("D:\\temp\\xhg-work-orders\\branches\\1.0.0\\src\\main\\resources\\processes");
        String[] extensions = new String[]{"txt"};
        Collection<File> fileCollection =  FileUtils.listFiles(myFile,extensions,true);


        for(File item:fileCollection){
            System.out.println("item:"+item+",getName:"+item.getName()+",getAbsolutePath:"+item.getAbsolutePath());
        }

    }

    @Test
    public void getBpmnModelFromFile(){
        String filePathAndName = "D:\\temp\\xhg-work-orders\\branches\\1.0.0\\src\\main\\resources\\processes\\marketlocation\\ContractFlow.bpmn";
        BpmnModel bpmnModel = ActivitiUtil.getBpmnModelFromFile(filePathAndName);
        System.out.println("bpmnModel:"+bpmnModel);
    }


    @Test
    public void parseBpmnModelForProcessInfo(){
        String filePathAndName = "D:\\temp\\xhg-work-orders\\branches\\1.0.0\\src\\main\\resources\\processes\\marketlocation\\ContractFlow.bpmn";
        BpmnModel bpmnModel = ActivitiUtil.getBpmnModelFromFile(filePathAndName);
        System.out.println("bpmnModel:"+bpmnModel);

        ProcessInfo processInfo =ActivitiUtil.parseBpmnModelForProcessInfo(bpmnModel);

        System.out.println("processInfo:"+processInfo);

    }

    @Test
    public void testVar(){
        System.out.println("\n\n deploying useTenant:"+useTenant);
    }

}
