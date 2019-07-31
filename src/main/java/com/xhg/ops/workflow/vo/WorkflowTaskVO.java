package com.xhg.ops.workflow.vo;

import java.util.Date;

import lombok.Data;

@Data
public class WorkflowTaskVO {

	private String id; // 任务id
	private String name; // 任务名
	private String userId; // 处理人
	private String comment; // 审核意见
	private Date auditTime; // 审核时间
	private String taskDoc; // 流程节点描述，即userTask 中的documentation属性; 目前用来进行查询过滤（文本）
	private String taskState; // 流程节点描述，即userTask 中的documentation属性; 目前用来进行查询过滤(code)
	private String processInstanceId;// 该任务所属流程实例
	private Date createTime;//创建时间
	private Date endTime; // 完成时间

}
