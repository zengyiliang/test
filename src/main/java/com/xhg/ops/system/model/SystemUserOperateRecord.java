package com.xhg.ops.system.model;

import com.xhg.ops.common.BasePojo;

import java.io.Serializable;

public class SystemUserOperateRecord  extends BasePojo implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer userId;

    private Integer operateType;

    private String remark;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
