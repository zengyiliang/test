package com.xhg.ops.common;

/**
 * 公共常量类
 */
public class ComConstant {
	
	public static final int ENABLE_YES = 1;
	public static final int ENABLE_NO = 0;

	/** 手机格式 */
	public static final String PHONE_FORMAT="^1(3|4|5|6|7|8|9)\\d{9}$";

	/**  7~12位数字、字母的密码格式 */
	public static final String PASSWORD_FORMAT="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{7,12}$";

    /**  7~12位数字或者字母的密码格式 */
    public static final String PASSWORD_FORMAT2="^[A-Za-z0-9]{7,12}$";

    /**  7~12位数字或者字母的密码格式 */
    public static final String PHONE_FORMAT_NUM="^[0-9]{1,11}$";

    /**  地区常量 */
	public static final String AREA_ALL_LIST ="ALL_AREA_LIST";

    /**  全国分区常量 */
	public static final String BASE_ZONE_AREA = "BASE_ZONE_AREA";
	
    /**
     * 按钮禁用
     */
    public static final String ISFORBIDDEN_Y = "1";
    /**
     * 按钮不禁用
     */
    public static final String ISFORBIDDEN_N = "0";
    /**
     * 前端显示
     */
    public static final String SHOW_Y = "1";
    /**
     * 前端不显示
     */
    public static final String SHOW_N = "0";
    
    /**
     * 工单主状态 1待处理
     */
    public static final String WORKORDER_MAIN_STATE_TODO = "1";
    /**
     * 工单主状态 2进行中
     */
    public static final String WORKORDER_MAIN_STATE_INPROGRESS = "2";
    /**
     * 工单主状态 3已结束
     */
    public static final String WORKORDER_MAIN_STATE_OVER = "3";
    
    /**
     * 全国分区编码
     */
    public static final String AREACODE_COUNTRY = "000";
    
    
    
}
