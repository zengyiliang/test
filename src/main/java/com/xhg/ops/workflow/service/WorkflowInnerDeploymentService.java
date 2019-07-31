package com.xhg.ops.workflow.service;

import java.util.List;
import java.util.Map;

/**
 * 流程部署
 */
public interface WorkflowInnerDeploymentService {
    /**
     *  通过zip文件部署流程
     * @param zipFilePath
     */
    void deployByZipInputStream(String zipFilePath);

    /**
     * 通过classpath部署
     * @param bpmnPath
     * @param bpmnFileNameList
     */
   void deployByClasspath(String bpmnPath,List<String> bpmnFileNameList);


    /**
     * 通过输入流部署
     * @param bpmnPathList
     */
    void deployByInputStream(List<String> bpmnPathList);

    /**
     * 通过输入流部署
     * @param bpmnPathList
     * @param tenantId
     */
    void deployByInputStream(List<String> bpmnPathList,String tenantId);

}
