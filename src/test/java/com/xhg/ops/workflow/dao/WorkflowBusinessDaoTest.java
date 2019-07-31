package com.xhg.ops.workflow.dao;

import com.xhg.ops.OpsApplication;
import com.xhg.ops.workflow.dto.WorkOrderReqParam;
import com.xhg.ops.workflow.dto.WorkflowQueryReq;
import com.xhg.ops.workflow.dto.WorkflowQueryResp;
import com.xhg.ops.workorders.model.WorkOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OpsApplication.class)
public class WorkflowBusinessDaoTest {
    @Autowired
    WorkflowBusinessDao workflowBusinessDao;

    @Test
    public void countWorkOrderProcessed(){
    	Long count = this.workflowBusinessDao.countWorkOrderProcessed(this.genWorkOrderProcessedParam());
        System.out.println("\n---count:"+count);
    }

    @Test
    public void listWorkOrderProcessed(){
        List<WorkOrder> list = this.workflowBusinessDao.listWorkOrderProcessed(this.genWorkOrderProcessedParam());
        System.out.println("\n---list:"+list);
    }

    @Test
    public void countWorkOrderProcessing(){
    	Long count = this.workflowBusinessDao.countWorkOrderProcessing(this.genWorkOrderProcessingParam()).longValue();
        System.out.println("\n---count:"+count);
    }

    @Test
    public void listWorkOrderProcessing(){
        List<WorkflowQueryResp> list = this.workflowBusinessDao.listWorkOrderProcessing(this.genWorkOrderProcessingParam());
        System.out.println("\n---list:"+list);
    }


    @Test
    public void countWorkOrderProcessUndo(){
        Long count = this.workflowBusinessDao.countWorkOrderProcessUndo(this.genWorkOrderProcessUndoParam()).longValue();
        System.out.println("\n---count:"+count);
    }

    @Test
    public void listWorkOrderProcessUndo(){
        List<WorkOrder> list = this.workflowBusinessDao.listWorkOrderProcessUndo(this.genWorkOrderProcessUndoParam());
        System.out.println("\n---list:"+list);
    }


    private WorkOrderReqParam genWorkOrderProcessedParam(){
        WorkOrderReqParam workOrderReqParam = new WorkOrderReqParam();
        workOrderReqParam.setCreatedUserId(1);
//        workOrderReqParam.setOrderNo("XHG2018071300003");
        workOrderReqParam.setOrderType(1);
        workOrderReqParam.setProcessDefineKey("WORKORDERPROCESS");

        workOrderReqParam.setPageNo(2);
        workOrderReqParam.setPageSize(10);



        workOrderReqParam.setUserId("hubowei");

        return workOrderReqParam;
    }


    private WorkflowQueryReq genWorkOrderProcessingParam(){
    	WorkflowQueryReq workflowQueryReq = new WorkflowQueryReq();
        workflowQueryReq.setUserId("24");
//    	workflowQueryReq.setOrderNo("1");
//        workOrderReqParam.setOrderType(1);
        workflowQueryReq.setProcessDefineKey("WORKORDERPROCESS");
//
//        workOrderReqParam.setPageNo(1);
//        workOrderReqParam.setPageSize(10);
//
//        workOrderReqParam.setUserId("hubowei");




        return workflowQueryReq;
    }

    private WorkOrderReqParam genWorkOrderProcessUndoParam(){
        WorkOrderReqParam workOrderReqParam = new WorkOrderReqParam();
        workOrderReqParam.setCreatedUserId(1);
        workOrderReqParam.setOrderNo("XHG2018071300003");
        workOrderReqParam.setOrderType(1);
        workOrderReqParam.setProcessDefineKey("WORKORDERPROCESS");

        workOrderReqParam.setPageNo(2);
        workOrderReqParam.setPageSize(10);

        workOrderReqParam.setUserId("20");
        List<String> roleIdList=new ArrayList<>();
        roleIdList.add("a");
        roleIdList.add("b");
        workOrderReqParam.setRoleIdList(roleIdList);

        return workOrderReqParam;
    }

}
