package com.xhg.ops.workflow.enums;

/**
 * 客户来源枚举类型
 */
public enum ClientSourceEnum {
    CLIENTSOURCE_WORKORDERS("workorders", "工单系统"),
    CLIENTSOURCE_MARKETLOCATION("marketlocation", "布点系统");

    /**
     * 值
     */
    private   String code;
    /**
     * 描述
     */
    private  String desc;


    ClientSourceEnum(String code,String desc) {
        this.code=code;
        this.desc = desc;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
