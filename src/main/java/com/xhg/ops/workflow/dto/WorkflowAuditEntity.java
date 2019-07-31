package com.xhg.ops.workflow.dto;

import lombok.Data;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;


/**
 * 审核意见
 */
@Data
@ToString
public class WorkflowAuditEntity {
    private int auditResult = AUDITRESULT_AGREE; //审核结果
    private String auditComment; //审核意见

    public static int AUDITRESULT_AGREE = 1; //同意
    public static int AUDITRESULT_DISAGREE = 2; //拒绝


    /**
     * 返回审核参数
     * @return
     */
    public Map<String,Object> getAuditMap(){
        Map<String,Object> map = new HashMap<>();
        map.put("auditResult",this.getAuditResult());
        map.put("auditComment",this.getAuditComment());
        return map;
    }


}
