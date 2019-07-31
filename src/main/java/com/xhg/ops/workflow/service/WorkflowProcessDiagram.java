package com.xhg.ops.workflow.service;

import java.io.InputStream;

/**
 * 流程图方面的接口
 */
public interface WorkflowProcessDiagram {
    // 显示流程图
    String showDiagram(String processInstanceId);

    /**
     * 根据流程实例获取输入图的输入流
     * @param processInstanceId
     * @return
     */
    InputStream getDiagram(String processInstanceId);

    /**
     * 根据流程实例获取输入图的输入流
     *  跟踪流程执行情况用红色框在流程图上标识路线跟节点
     * @param processInstanceId
     * @return
     */
    InputStream getDiagram2(String processInstanceId);
}
