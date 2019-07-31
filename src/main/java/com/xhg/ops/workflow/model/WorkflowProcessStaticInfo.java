package com.xhg.ops.workflow.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 工作流的流程文件的静态信息
 */
@Data
@ToString
public class WorkflowProcessStaticInfo {
    private String actId;   //流程文件中的actId
    private String actName; //流程文件中的actName，原型中的操作
    private String assignee; //处理人
    private List<String> candidateUsers; //候选用户
    private List<String> candidateGroups; //候选角色
    private String document; //document，描述

}
