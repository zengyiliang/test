package com.xhg.ops.workflow.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.ToString;

/**
 * 工作流请求通用参数
 */
@Data
@ToString
public class WorkflowRequestEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private String userId; // 用户id，根据具体的情况，可能是 id,也可能是loginName（唯一）
	private List<String> roleCodeList; // 有些系统 一个用户可能存在多个角色，是role  code，eg:superManager
	private String processInstanceId; // 流程实例id
	private String taskId; // 流程任务id
	private String processDefineKey; // 流程定义key，例如：请假流程文件vacation.bpmn中的id属性(<process id="vacationProcess">)
	private String clientSource; // 客户来源,ops
	private String message; // 审核人填写的审核意见，申请人填写申请备注
	private String businessKey;	//业务主键，一般是业务表的id，格式可以是id，也可以是tablename_id
	private Map<String, Object> paramMap; // 全局变量,taskId为空

	private Map<String, Object> paramMapLocal; // 局部变量，会保存taskId
}
