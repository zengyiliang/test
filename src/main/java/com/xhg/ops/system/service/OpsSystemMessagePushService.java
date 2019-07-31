package com.xhg.ops.system.service;

import java.util.List;
import java.util.Map;

public interface OpsSystemMessagePushService {

	/**
	 *
	 * @param registrationId
	 * @param notification_alert
	 * @param notification_title
	 * @param type
	 * @param buss
	 * @return
	 */
	public int sendToRegistrationIdWithParams(String registrationId, String notification_alert, String notification_title, String type, Map<String, String> buss);

	/**
	 * @param alias
	 * @param notification_alert
	 * @param notification_title
	 * @param buss
	 * @return
	 */
	public int sendToAliasWithParams(List<String> alias, String notification_alert, String notification_title, String type, Map<String, String> buss);

	/**
	 * 通过标签推送，带上参数
	 * @param tags
	 * @param notification_alert
	 * @param notification_title
	 * @param type
	 * @param buss
	 * @return
	 */
	public int sendToTagsWithAndParams(List<String> tags, String notification_alert, String notification_title, String type, Map<String, String> buss);

	/**
	 * 通过标签推送，带上参数
	 * @param tags
	 * @param notification_alert
	 * @param notification_title
	 * @param type
	 * @param buss
	 * @return
	 */
	public int sendToTagsWithOrParams(List<String> tags, String notification_alert, String notification_title, String type, Map<String, String> buss);

	/**
	 * 推送给所有用户，外带额外的参数
	 * @param notification_alert
	 * @param notification_title
	 * @param type
	 * @param buss
	 * @return
	 */
	public int sendToAllWithParams(String notification_alert, String notification_title, String type, Map<String, String> buss);

	/**
	 * 推送给所有ios用户
	 * @param notification_alert  推送内容(必填)
	 * @param notification_title  推送标题(可选)将代替app名称
	 * @param buss  此字段为透传字段，不会显示在通知栏。
	 *     用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
	 * @return  0推送失败   1推送成功
	 */
	public int sendToAllIosWithParams(String notification_alert, String notification_title, String type, Map<String, String> buss);

	/**
	 * 推送给所有安卓用户
	 * @param notification_alert  推送内容(必填)
	 * @param notification_title  推送标题(可选)将代替app名称
	 * @param buss  此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
	 * @return  0推送失败   1推送成功
	 */
	public int sendToAllAndroidWithParams(String notification_alert, String notification_title, String type, Map<String, String> buss);
}
