package com.xhg.ops.workflow.exception;

import com.xhg.core.web.vo.ResponseBean;
import com.xhg.ops.OpsApplication;
import com.xhg.ops.workflow.api.WorkflowProcessService;
import com.xhg.ops.workflow.dto.WorkflowRequestEntity;
import com.xhg.ops.workflow.dto.WorkflowResponseEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OpsApplication.class)
public class WorkflowExceptionTest {
    @Autowired
    private WorkflowProcessService workflowProcessService;

    @Test
    public void teststartProcessInstanceExcept(){
        String userId = "";
        List<String> roleList = null;
        //roleList.add("employee");


        WorkflowRequestEntity workflowRequestEntity = new WorkflowRequestEntity();
        workflowRequestEntity.setClientSource("");

        workflowRequestEntity.setProcessDefineKey("");
        workflowRequestEntity.setUserId(userId);
        workflowRequestEntity.setRoleCodeList(roleList);

        workflowRequestEntity.setBusinessKey("");

        Map<String, Object> paramMap = null;
        workflowRequestEntity.setParamMap(paramMap);

        ResponseBean<WorkflowResponseEntity> responseBean =  workflowProcessService.startProcessInstance(workflowRequestEntity);
        System.out.println("\n==========responseBean:"+responseBean);

        //根据返回的内容，设置 processInstanceId

    }

}
