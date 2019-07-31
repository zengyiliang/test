package com.xhg.ops.system.model;

import com.xhg.ops.common.BasePojo;

import java.io.Serializable;

public class SystemPosition extends BasePojo implements Serializable {

    private static final long serialVersionUID=1L;

    private String name;

    private Integer type;

    private Integer deptId;

    private Integer positionId;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }
}
