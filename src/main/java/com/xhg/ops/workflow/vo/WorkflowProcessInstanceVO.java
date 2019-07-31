package com.xhg.ops.workflow.vo;

import java.util.Date;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * 流程实例相关数据
 * 
 * @Name:
 * @Description:
 * @Copyright: Copyright (c) 2018
 * @Author hubowei
 * @Create 2018年7月17日 上午10:44:53
 * @Version 1.0.0
 */
@Data
@ToString
@Slf4j
public class WorkflowProcessInstanceVO {

	// 流程实例ID
	private String processInstanceId;

	// taskId
	private String taskId;

	// 流程实例名
	private String name;

	// 流程实例名
	private String description;

	// 流程实例定义key
	private String processDefinitionKey;

	// 启动该流程实例设置的业务Key
	private String businessKey;

	// 流程实例开始时间
	private Date startTime;
	
	// 流程实例完成时间
	private Date endTime;

	// 启动流程实例人
	private String startUserId;

}
