package com.xhg.ops.workorders.controller;

import com.xhg.core.web.vo.*;
import com.xhg.ops.workflow.api.WorkflowProcessService;
import com.xhg.ops.workflow.dto.WorkflowRequestEntity;
import com.xhg.ops.workflow.dto.WorkflowResponseEntity;
import com.xhg.ops.workorders.dto.AppWorkOrderMessageQueryDTO;
import com.xhg.ops.workorders.service.AppWorkOrderService;
import com.xhg.ops.workorders.vo.AppWorkOrderMessageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/ops/app/WorkflowTest")
@Api(value = "WorkflowTest",description = "WorkflowTest")
public class WorkflowTestController {
    @Autowired
    private WorkflowProcessService workflowProcessService;

    @ApiOperation(value = "工单消息")
    @PostMapping(value = "/test")
    @ResponseBody
    public ResponseBean<WorkflowResponseEntity> test() {
        System.out.println("\n----------WorkflowTestController");
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

        return responseBean;

    }
}
