package com.xhg.ops.workflow.dto;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * 查询返回参数
 */
@Data
@ToString
public class WorkflowQueryResp {
	private String userId; // 用户名
	private String processInstanceId; // 流程实例id
	private String taskId; // 流程任务id
	private String taskDoc;
	private Date endTime; // 完成时间
	private Date createTime;//创建时间
	private Long dueDate;//操作用时
    private String name;
 // 启动该流程实例设置的业务Key
 	private String businessKey;
 	private String startUserId;
 	private String processDefineKey;

	private Integer pageSize;
	private Integer pageNo;
	private Integer total;

}
