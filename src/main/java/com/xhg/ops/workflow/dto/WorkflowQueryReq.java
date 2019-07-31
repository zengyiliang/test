package com.xhg.ops.workflow.dto;


import com.xhg.ops.workflow.model.PageBean;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 查询请求参数
 */
@Data
@ToString
public class WorkflowQueryReq extends PageBean {
    private String userId;  //用户名
    private List<String> roleCodeList; // 有些系统 一个用户可能存在多个角色，是role  code，eg:superManager
    private String processInstanceId; // 流程实例id
    private String businessKey; //业务主键
    private String taskId; // 流程任务id
    private String taskDoc;  //流程节点描述，即userTask 中的documentation属性; 目前用来进行查询过滤
    
    private String processDefineKey;//工作流Key
    

}
