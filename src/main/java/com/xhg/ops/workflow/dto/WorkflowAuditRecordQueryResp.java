package com.xhg.ops.workflow.dto;

import com.xhg.ops.workflow.model.AuditRecord;
import lombok.Data;
import lombok.ToString;

/**
 * 审核记录，流程进度
 */
@Data
@ToString
public class WorkflowAuditRecordQueryResp extends AuditRecord{
    private String processInstanceId; // 流程实例id
}
