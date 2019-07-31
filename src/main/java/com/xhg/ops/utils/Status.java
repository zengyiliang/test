package com.xhg.ops.utils;

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
     * 该银行卡不支持绑定
     */
    BANKS_DO_NOT_SUPPORT_BINDING("11005", "该银行卡不支持绑定"),
    /**
     * 成功
     */
    Bu_SUCCESS("1", "成功"),
    /**
     * 交易处理中，请稍后查询
     */
    HANDLE_IN("11000", "交易处理中，请稍后查询"),
    /**
     * 绑卡中
     */
    TIE_CARD("11001", "绑卡中"),
    /**
     * 验证码错误
     */
    VERIFICATION_CODE("11002", "验证码错误"),
    /**
     * 绑卡失败
     */
    TIE_CARD_FAIL("11003", "绑卡失败"),
    /**
     * 只支持借记卡
     */
    NO_DEBIT_CARD("11004", "只支持借记卡，请更换银行卡"),
    /**
     * 该卡号已经绑卡，请先进行解绑
     */
    ALREADY_BOUND_CARD("11005", "该卡号已经绑卡，请先进行解绑"),
    /**
     * 不支持的银行
     */
    NO_SUPPORT("11006", "不支持该银行储蓄卡绑定，请更换其他银行卡"),
    /**
     * 失败
     */
    BU_FAILD("-1", "失败"),
    /**
     * 充值失败
     */
    BU_RECHARGE_FAILURE("-1001", "充值失败"),
    /**
     * 您的充值申请已提交成功
     */
    ORDER_SUBMISSION("11007", "您的充值申请已提交成功"),
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
    MANAGERUSER_UNAUTHORIZED("-3", "没有权限"),
    /**
     * 请求过于频繁，请稍后再试
     */
    FREQUENT("-3", "请求过于频繁，请稍后再试"),
    /**
     * app强更
     */
    APP_MUST_UPDATE("-4", "app强制更新"),
    /**
     * 终端测试账号不允许充值
     */
    NOT_ALLOW_RECHARGE("-5", "终端测试账号不允许充值"),

    /**
     * 用户不存在
     */
    MERCHANT_USER_NOT_EXAIST("1001", "该账号不存在"),

    /**
     * 用户名或密码错误
     */
    MERCHANT_USER_INFO_ERROR("1002", "用户名或密码错误"),

    /**
     * 手机号码错误
     */
    MERCHANT_PHONE_ERROR("1003", "手机号码格式有误，请检查后重试"),

    /**
     * 短信验证码发送失败
     */
    MERCHANT_MESSAGE_SEND_ERROR("1004", "短信验证码发送失败"),


    /**
     * 验证码不正确
     */
    MERCHANT_VALIDATE_CODE_ERROR("1005", "验证码错误，请重新输入"),

    /**
     * 验证码发送太频繁，请稍后再试
     */
    MERCHANT_VALIDATE_FREQUENTLY("1006", "验证码发送太频繁，请稍后再试"),

    /**
     * 验证码发送次数太多,今日禁止
     */
    MERCHANT_VALIDATE_FORBIDDEN("1007", "当日获取验证码已超过10次，请明天再试。"),
    /**
     * 用户合同过期
     */
    MERCHANT_USER_CONTRACT_DATE("1008", "用户合同过期"),

    /**
     * 回收机不允许空值
     */
    NOT_ALLOW_ENPTYSITEBOXLIST("1009", "回收机列表不允许空值");

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
