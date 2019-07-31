package com.xhg.ops.workflow.service;

import com.xhg.ops.OpsApplication;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.InputStream;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OpsApplication.class)
public class WorkflowProcessDiagramTest {
    @Resource
    WorkflowProcessDiagram workflowProcessDiagram;

    @Test
    public void WorkflowProcessDiagram() throws Exception{
        String processInstanceId = "45005";
        InputStream in =  this.workflowProcessDiagram.getDiagram(processInstanceId);
        File f = new File("d:/temp/test.png");
        FileUtils.copyInputStreamToFile(in, f);
    }

    @Test
    public void WorkflowProcessDiagram2() throws Exception{
        String processInstanceId = "45005";
        InputStream in =  this.workflowProcessDiagram.getDiagram2(processInstanceId);
        File f = new File("d:/temp/test2.png");
        FileUtils.copyInputStreamToFile(in, f);
    }
}
