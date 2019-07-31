package com.xhg.ops.workflow.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.Map;

/**
 * 流程进度
 */
@Data
@ToString
public class ProcessProgress {
    private String processInstanceId;
    private String actId;   //流程文件中的actId
    private String actName; //流程文件中的actName，原型中的操作

    private String taskId;  //流程实例中的任务id，一般是actType为userTask时才不为空

    private String actType; //类型，例如：startEvent,endEvent,userTask,exclusiveGateway

    private String assIgnee;    //任务处理人，原型中的操作人姓名，和职位需要根据这个 assIgnee返回

    private Date operateTime;    //操作时间
    private String comment;    //审核意见，原型中的 备注

    private Map<String,Object> taskParam;   //全局变量；任务参数，即用户填写的相关参数，例如：请假中的请假天数，请假原因；审核中的 同意，拒绝

    private Map<String,Object> taskParamLocal; //局部变量
    //执行结果，正常来说是成功的，例如：请假申请，提交时没有出现异常一般是成功的；
    // 审核结果需要从taskParam中解释，例如：result，这个要看具体的业务


}
