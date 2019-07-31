package com.xhg.ops.workflow.service;

import com.xhg.ops.workflow.model.WorkflowProcessStaticInfo;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;

import java.util.List;

public interface WorkflowCommonService {
    /**
     * 根据流程key列出所有的流程定义
     * @param processDefinitionKey
     * @return
     */
    List<ProcessDefinition> listProcessDefinition(String processDefinitionKey);

    /**
     * 根据流程定义id获取流程定义信息
     * @param processDefinitionId
     * @return
     */
    ProcessDefinition getProcessDefinition(String processDefinitionId);

    /**
     * 根据流程定义id获取 BpmnModel
     * @param processDefinition
     * @return
     */
    BpmnModel getBpmnModelByProcessDefinition(ProcessDefinition processDefinition);


    /**
     * 根据流程实例id查询当前task信息
     *
     * @param processInstanceId
     * @return
     *  如果返回的task不为空，则表明流程正在运行中；
     *  如果为空，则：1.流程已经结束；2.流程根本不存在
     */
    Task getCurrentTask(String processInstanceId);


    /**
     * 解析流程文件的bpmnModel，获得第一个节点的信息，通常都是申请信息
     * 一般只会有一个开始申请节点
     * @param bpmnModel
     * @return
     */
    List<WorkflowProcessStaticInfo> parseBpmnModelForApplyInfo(BpmnModel bpmnModel);

}
