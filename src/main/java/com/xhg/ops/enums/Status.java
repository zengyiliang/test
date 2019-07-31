package com.xhg.ops.enums;

/**
 * 定义返回状态
 */
public enum Status {

    /**
     * 成功
     */
    SUCCESS("1", "服务器连接成功"),
    /**
     * 后台用户登录成功
     */
    MANAGERUSER_LOGIN_SUCCESS("1", "后台用户登录成功"),
    /**
     * 失败
     */
    FAILD("-1", "服务器异常，请稍后再试"),

    /**
     * 成功
     */
    Bu_SUCCESS("1", "成功"),

    /**
     * 失败
     */
    BU_FAILD("-1", "失败"),
   
    /**
     * 系统错误
     */
    ERROR("500", "系统错误"),
    /**
     * 退出登录
     */
    LOGOUT("-1", "退出登录"),
    /**
     * 未登录
     */
    LOGIN("-2", "登录状态失效"),
    /**
     * 没有权限
     */
    OPS_UNAUTHORIZED("-3", "没有权限"),
 

    /**
     * 用户不存在
     */
   OPS_USER_NOT_EXAIST("1001", "该账号不存在"),

    /**
     * 用户名或密码错误
     */
    OPS_USER_INFO_ERROR("1002", "手机号或密码错误，请重试"),

    /**
     * 手机号码错误
     */
    OPS_PHONE_ERROR("1003", "手机号码格式有误，请检查后重试"),
    /**
     * 菜单编码已存在
     */
    OPS_MENU_CODE("1004", "菜单编码已存在"),
    
    ACTIVITI_ERROR_CODE("2001", "工作流处理异常"),

    ;

    /**
     * 名称
     */
    private final String name;

    /**
     * 值
     */
    private final String value;

    Status(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * 获取名称
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 获取值
     *
     * @return
     */
    public String getValue() {
        return value;
    }
}
