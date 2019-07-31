package com.xhg.ops.workflow.model;

import lombok.Data;
import lombok.ToString;
import org.activiti.bpmn.model.Process;

/**
 * 流程文件信息
 */
@Data
@ToString
public class ProcessInfo {
    private String id;
    private String name;
    private String documentation;
    private boolean executable;
}
