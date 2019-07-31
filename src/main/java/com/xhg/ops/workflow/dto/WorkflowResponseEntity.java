package com.xhg.ops.workflow.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class WorkflowResponseEntity implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String processInstanceId; //流程实例id
    private String taskId;  //流程任务id



}
