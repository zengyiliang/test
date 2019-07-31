package com.xhg.ops.workflow.model;

import lombok.Data;
import lombok.ToString;
import org.activiti.engine.history.HistoricVariableInstance;

import java.util.Date;
import java.util.List;

/**
 * 审核记录
 */
@Data
@ToString
public class AuditRecord {
    private String processInstanceId;

    private String applyUser; //申请用户
    private Date applyTime;    //申请时间

	private String taskId;  
    private String taskName;    //节点名称
    private String taskDoc;

    private String auditor;
    private String result;
    private String comment;    //审核意见
    private Date auditTime;

    private List<HistoricVariableInstance> varInstanceList; //流程变量

}
