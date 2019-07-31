package com.xhg.ops.workflow.dto;

import java.util.Date;

import com.xhg.core.base.BaseDto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class WorkflowTaskQuery extends BaseDto{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;        //任务id
	private String name;      //任务名
	private String userId;   //处理人
    private String comment;   //审核意见
    private Date auditTime;   //审核时间
    private String taskDoc;  //流程节点描述，即userTask 中的documentation属性; 目前用来进行查询过滤
    private String processInstanceId;//该任务所属流程实例
    private Date endTime;   //完成时间
    
    private Integer currentPage=1;

    private Integer pageSize = 10;
}
