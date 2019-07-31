package com.xhg.ops.utils;

public class RedisKey {

    //用户id前缀 后面跟token
    public static final String LOGIN_ID_PRE_APP = "USER:OPS:ID:APP";
    //用户id前缀 后面跟token
    public static final String LOGIN_ID_PRE_PC = "USER:OPS:ID:PC";
    /**
     * 缓存所有角色菜单
     */
    public static final  String ROLE_MEUN = "ops:orders:rolemeun";
    /**
     * 缓存所有用户角色
     */
    public static final  String USER_ROLE = "ops:orders:userrole";

}
