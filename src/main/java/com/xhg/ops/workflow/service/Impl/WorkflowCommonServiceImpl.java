package com.xhg.ops.workflow.service.Impl;

import com.xhg.ops.workflow.model.WorkflowProcessStaticInfo;
import com.xhg.ops.workflow.service.WorkflowCommonService;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.*;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class WorkflowCommonServiceImpl implements WorkflowCommonService {

    protected static final Logger logger = LoggerFactory.getLogger(WorkflowCommonServiceImpl.class);

    @Resource
    RepositoryService repositoryService;

    @Resource
    RuntimeService runtimeService;

    @Resource
    TaskService taskService;

    @Override
    public List<ProcessDefinition> listProcessDefinition(String processDefinitionKey) {
        logger.info("\n------WorkflowCommonServiceImpl processDefinitionKey:{}",processDefinitionKey);
        /**
         select distinct RES.* from ACT_RE_PROCDEF RES
         WHERE RES.KEY_ ='xhgVacationProcess'
         order by RES.ID_ asc

         */
        //由于会部署多次或者是同一个流程版本会升级，因此，通过processDefinitionKey查询时会返回多条ProcessDefinition
        List<ProcessDefinition> list =repositoryService
                    .createProcessDefinitionQuery()
                    .processDefinitionKey(processDefinitionKey)
                    .orderByProcessDefinitionVersion().desc()
                    .list();

        logger.info("\n------WorkflowCommonServiceImpl list:{}",list);

        return list;
    }

    @Override
    public ProcessDefinition getProcessDefinition(String processDefinitionId) {
        logger.info("\n------getProcessDefinition processDefinitionId:{}",processDefinitionId);
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        return processDefinition;
    }

    @Override
    public BpmnModel getBpmnModelByProcessDefinition(ProcessDefinition processDefinition) {
        logger.info("\n------processDefinition:{}",processDefinition);
        BpmnModel bpmnModel = null;

        //获取流程资源的名称
        String sourceName = processDefinition.getResourceName();

        //获取流程资源
        //select * from ACT_GE_BYTEARRAY where DEPLOYMENT_ID_ = ? AND NAME_ = ?
        InputStream inputStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(),sourceName);
        //创建转换对象
        BpmnXMLConverter converter = new BpmnXMLConverter();
        //读取xml文件
        XMLInputFactory factory = XMLInputFactory.newInstance();

        try{
            XMLStreamReader reader = factory.createXMLStreamReader(inputStream);
            //将xml文件转换成BpmnModel
            bpmnModel = converter.convertToBpmnModel((XMLStreamReader) reader);
        }catch (Exception e){
            e.printStackTrace();
        }
        return bpmnModel;
    }

    @Override
    public Task getCurrentTask(String processInstanceId) {
        // 查询当前任务
        Task currentTask = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        return currentTask;
    }

    @Override
    public List<WorkflowProcessStaticInfo> parseBpmnModelForApplyInfo(BpmnModel bpmnModel) {
        List<WorkflowProcessStaticInfo> list = new ArrayList<>();

        String  starteventId = "";

        List<org.activiti.bpmn.model.Process> processList = bpmnModel.getProcesses();

        boolean isFindStartEventId = false;

        //先找出 starteventId
        if(processList != null && processList.size() > 0) {
            for (org.activiti.bpmn.model.Process process : processList) {
                if (process != null) {
                    Collection<FlowElement> flowElementCollection = process.getFlowElements();
                    if (flowElementCollection != null && flowElementCollection.size() > 0) {

                        for (FlowElement flowElement : flowElementCollection) {
                            if (flowElement instanceof StartEvent) {
                                starteventId = flowElement.getId();
                                isFindStartEventId = true;
                                break;
                            }
                        }
                    }
                }//if
                if(!isFindStartEventId){
                    break;
                }
            }
        }



        //再根据入线信息找到 startEvent关联的节点信息
        if(processList != null && processList.size() > 0){
            for(org.activiti.bpmn.model.Process process : processList){
                if(process != null){
                    Collection<FlowElement> flowElementCollection = process.getFlowElements();
                    if(flowElementCollection != null && flowElementCollection.size() > 0){

                        for(FlowElement flowElement : flowElementCollection){

                            boolean isFind = false;

                            if(flowElement instanceof UserTask){
                                UserTask userTask = (UserTask)flowElement;

                                //获取入线信息
                                List<SequenceFlow> incomingFlows = userTask.getIncomingFlows();

                                if(!isFind && !CollectionUtils.isEmpty(incomingFlows)) {
                                    for (SequenceFlow sequenceFlow : incomingFlows) {
                                        if(sequenceFlow.getSourceRef().equals(starteventId)) {
                                            isFind = true;
                                            break;
                                        }
                                    }
                                }

                                if(isFind) {
                                    List<String> candidateUsers = userTask.getCandidateUsers();
                                    List<String> candidateGroups = userTask.getCandidateGroups();
                                    String assignee = userTask.getAssignee();
                                    String document = userTask.getDocumentation();
                                    WorkflowProcessStaticInfo workflowProcessStaticInfo = new WorkflowProcessStaticInfo();
                                    workflowProcessStaticInfo.setAssignee(assignee);
                                    workflowProcessStaticInfo.setDocument(document);
                                    workflowProcessStaticInfo.setCandidateUsers(candidateUsers);
                                    workflowProcessStaticInfo.setCandidateGroups(candidateGroups);
                                    workflowProcessStaticInfo.setActId(userTask.getId());
                                    workflowProcessStaticInfo.setActName(userTask.getName());

                                    list.add(workflowProcessStaticInfo);

                                }

                            }
                        }
                    }
                }
            }
        }
        return list;
    }


}
