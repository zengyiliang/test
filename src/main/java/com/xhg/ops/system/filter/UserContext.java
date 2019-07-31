package com.xhg.ops.system.filter;

import com.xhg.ops.system.model.SystemUser;

/**
 * 用户线程上下文
 * 
 * @author zhoujf
 *
 */
public class UserContext {

    /**
     * 用户Id
     */
    public final static ThreadLocal<SystemUser> USER_ID_THREAD_LOCAL = new ThreadLocal<>(); 
    
    
    public static SystemUser getUser() {
        return USER_ID_THREAD_LOCAL.get();
    }
    
    public static void setUser(SystemUser user) {
        USER_ID_THREAD_LOCAL.set(user);
    }
}
