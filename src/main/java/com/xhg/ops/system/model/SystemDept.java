package com.xhg.ops.system.model;

import com.xhg.ops.common.BasePojo;

import java.io.Serializable;

public class SystemDept extends BasePojo implements Serializable {

    private static final long serialVersionUID=1L;

    private String name;

    private Integer level;

    private Integer deptId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }
}
