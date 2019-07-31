package com.xhg.ops.workflow.api.impl;

import com.xhg.ops.workflow.api.WorkflowDeploymentService;
import com.xhg.ops.workflow.service.WorkflowInnerDeploymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkflowDeploymentServiceImpl implements WorkflowDeploymentService {
    @Autowired
    WorkflowInnerDeploymentService workflowInnerDeploymentService;

    @Override
    public void deployByZipInputStream(String zipFilePath) {
        workflowInnerDeploymentService.deployByZipInputStream(zipFilePath);
    }

    @Override
    public void deployByClasspath(String bpmnPath, List<String> bpmnFileNameList) {
        workflowInnerDeploymentService.deployByClasspath(bpmnPath,bpmnFileNameList);
    }

    @Override
    public void deployByInputStream(List<String> bpmnPathList) {
        workflowInnerDeploymentService.deployByInputStream(bpmnPathList);
    }
}
