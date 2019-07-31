package com.xhg.ops.workflow.dto;

import com.xhg.ops.workflow.model.PageParam;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 工单系统查询参数
 */


@Data
@ToString
public class WorkOrderReqParam extends PageParam {
    private String processDefineKey; //流程定义key
    private String userId; //登陆用户的userId

    //登陆用户所在的角色id，前台传递过来
    // 因为登陆用户一般都有对应的角色；这样后面不需要每次访问都查询一次
    private List<String> roleIdList;

    private String orderNo;
    private Integer orderType;
    private Integer createdUserId;

}
