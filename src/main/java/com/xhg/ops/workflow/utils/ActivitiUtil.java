package com.xhg.ops.workflow.utils;

import com.xhg.ops.workflow.model.ProcessInfo;
import com.xhg.ops.workflow.service.Impl.WorkflowInnerInnerQueryServiceImpl;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.history.HistoricVariableInstance;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;
import org.activiti.bpmn.model.Process;


/**
 * activiti中使用得到的工具方法
 */
public class ActivitiUtil {
    protected static final Logger logger = LoggerFactory.getLogger(ActivitiUtil.class);

    /**
     * 将历史参数列表设置到实体中去
     * @param entity 实体
     * @param varInstanceList 历史参数列表
     */
    public static <T> void setVars(T entity, List<HistoricVariableInstance> varInstanceList) {
        Class<?> tClass = entity.getClass();
        try {
            for (HistoricVariableInstance varInstance : varInstanceList) {
                Field field = tClass.getDeclaredField(varInstance.getVariableName());
                if (field == null) {
                    continue;
                }
                field.setAccessible(true);
                field.set(entity, varInstance.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取流程定义在resources中目录名，默认是 processes
     * @param workflowProcessLocation
     * @return
     */
    public static String getWorkflowProcessDir(String workflowProcessLocation){
        String dir = workflowProcessLocation.replace("classpath:/","");

        logger.info("\n============getWorkflowProcessDir getWorkflowProcessDir:"+workflowProcessLocation+",dir:"+dir);
        return dir;
    }

    /**
     * 获取流程定义在resources 的物理路径
     * @param workflowProcessLocation
     * @return
     */
    public static String getWorkflowProcessPath(String workflowProcessLocation){
        logger.info("\n============getWorkflowProcessPath workflowProcessLocation:"+workflowProcessLocation);

        if(StringUtils.isNotEmpty(workflowProcessLocation) && !workflowProcessLocation.startsWith("classpath:/")){
            return workflowProcessLocation;
        }

        String path = WorkflowInnerInnerQueryServiceImpl.class.getClassLoader()
                .getResource(ActivitiUtil.getWorkflowProcessDir(workflowProcessLocation)).getPath();
        logger.info("\n============getWorkflowProcessPath workflowProcessLocation:"+workflowProcessLocation+",path:"+path);

        return path;
    }


    /**
     * 从文件中解析BpmnModel
     * @param filePathAndName
     * @return
     */
    public static BpmnModel getBpmnModelFromFile(String filePathAndName) {
        BpmnModel bpmnModel = null;
        try{
            File file = new File(filePathAndName);
            InputStream inputStream = new FileInputStream(file);//实例化FileInputStream
            BpmnXMLConverter converter = new BpmnXMLConverter();
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader reader = factory.createXMLStreamReader(inputStream);//createXmlStreamReader
            //将xml文件转换成BpmnModel
            bpmnModel = converter.convertToBpmnModel(reader);
        }catch (Exception e){
            e.printStackTrace();
        }
         return bpmnModel;
    }


    /**
     * 从bpmnModel中解释ProcessInfo
     * @param bpmnModel
     * @return
     */
    public static ProcessInfo parseBpmnModelForProcessInfo(BpmnModel bpmnModel ) {
        ProcessInfo processInfo = new ProcessInfo();
        if(null == bpmnModel ){
            return processInfo;
        }
        List<org.activiti.bpmn.model.Process> processList = bpmnModel.getProcesses();
        if(CollectionUtils.isNotEmpty(processList)){
            Process process = processList.get(0);
            processInfo.setId(process.getId());
            processInfo.setName(process.getName());
            processInfo.setDocumentation(process.getDocumentation());
            processInfo.setExecutable(process.isExecutable());
        }
        return processInfo;
    }

}
