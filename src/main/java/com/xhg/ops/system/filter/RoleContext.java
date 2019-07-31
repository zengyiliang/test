package com.xhg.ops.system.filter;

import java.util.List;

import com.xhg.ops.system.entity.SystemRoleDO;

/**
 * 用户线程上下文
 * 
 * @author lilun
 *
 */
public class RoleContext {

    /**
     * 用户Id
     */
    public final static ThreadLocal<List<SystemRoleDO>> ROLE_ID_THREAD_LOCAL = new ThreadLocal<>(); 
    
    
    public static List<SystemRoleDO> getRole() {
        return ROLE_ID_THREAD_LOCAL.get();
    }
    
    public static void setRole(List<SystemRoleDO> roleDOs) {
    	ROLE_ID_THREAD_LOCAL.set(roleDOs);
    }
}
