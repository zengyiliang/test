package com.xhg.ops.workflow.dao;

import com.xhg.ops.OpsApplication;
import com.xhg.ops.workflow.dto.WorkflowQueryReq;
import com.xhg.ops.workflow.dto.WorkflowQueryResp;
import org.activiti.engine.history.HistoricActivityInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OpsApplication.class)
public class WorkflowCustomQueryDaoTest {

    @Autowired
    WorkflowCustomQueryDao workflowCustomQueryDao;


    @Test
    public void queryProcessedForUser(){
        WorkflowQueryReq workflowQueryReq = new WorkflowQueryReq();
        workflowQueryReq.setUserId("11");
        workflowQueryReq.setProcessDefineKey("xhgVacationProcess");

        List<WorkflowQueryResp> list = this.workflowCustomQueryDao.queryProcessedForUser(workflowQueryReq);

        System.out.println("\n\n\n==========list:"+list);


    }

    @Test
    public void countProcessedForUser(){
        WorkflowQueryReq workflowQueryReq = new WorkflowQueryReq();
        workflowQueryReq.setUserId("11");
        workflowQueryReq.setProcessDefineKey("xhgVacationProcess");

        long count = this.workflowCustomQueryDao.countProcessedForUser(workflowQueryReq);

        System.out.println("\n\n\n==========count:"+count);


    }

}
