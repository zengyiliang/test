package com.xhg.ops.system.entity;

import com.xhg.ops.common.BasePojo;

import java.io.Serializable;
import java.util.List;

public class SystemUserDO  implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer userId;

    private List<SystemRoleDO> roleDOs;

 

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

	public List<SystemRoleDO> getRoleDOs() {
		return roleDOs;
	}

	public void setRoleDOs(List<SystemRoleDO> roleDOs) {
		this.roleDOs = roleDOs;
	}
    
}
