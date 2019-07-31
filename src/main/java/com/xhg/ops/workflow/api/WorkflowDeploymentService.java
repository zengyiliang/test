package com.xhg.ops.workflow.api;


import java.util.List;

/**
 * 流程部署
 */
public interface WorkflowDeploymentService {
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
}
