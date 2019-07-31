package com.xhg.ops.utils;

import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosAlert;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

import java.util.List;
import java.util.Map;

/**
 * 极光推送工具类
 * @author ilan
 *
 */
public class SystemMessagePushUtil {

	/**
	 *
	 * @param registrationId
	 * @param notification_alert
	 * @param notification_title
	 * @param params
	 * @return
	 */
	public static PushPayload pushMessageByRegistrationIdAndParams(String registrationId, String notification_alert,
			String notification_title, Map<String,String> params,String iosContext) {

		boolean iosContextFlag = iosContext.equalsIgnoreCase("1")?true:false;

		IosAlert alert = IosAlert.newBuilder()
	            .setTitleAndBody(notification_title, "", notification_alert)
	            .setActionLocKey("PLAY")
	            .build();
		return PushPayload.newBuilder()
				// 指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
				.setPlatform(Platform.all())
				// 指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
				.setAudience(Audience.registrationId(registrationId))
				// jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
				.setOptions(Options.newBuilder().setApnsProduction(iosContextFlag).build())
				.setNotification(Notification.newBuilder()
						// 指定当前推送的android通知
						.addPlatformNotification(AndroidNotification.newBuilder().setAlert(notification_alert) // 设置通知内容（必填）
								.setTitle(notification_title) // 设置通知标题（可选）
								// 此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
								.addExtras(params).build())

						// 指定当前推送的iOS通知
						.addPlatformNotification(IosNotification.newBuilder()
								// 传一个IosAlert对象，指定apns title、title、subtitle等
								.setAlert(alert)
								// 直接传alert
								// 此项是指定此推送的badge（应用角标）自动加1
								.incrBadge(1)
								.setSound("sound.caf")
								// 此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
								.addExtras(params)
								// 此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
								// 取消此注释，消息推送时ios将无法在锁屏情况接收
								// .setContentAvailable(true)
								.build())
						.build())
				.build();
	}

	/**
	 *
	 * @param alias
	 * @param notification_alert
	 * @param notification_title
	 * @param params
	 * @return
	 */
	public static PushPayload pushObjectWithAliasAndParams(List<String> alias, String notification_alert,
			String notification_title, Map<String,String> params,String iosContext) {

		boolean iosContextFlag = iosContext.equalsIgnoreCase("1")?true:false;
		
		IosAlert alert = IosAlert.newBuilder()
	            .setTitleAndBody(notification_title, "", notification_alert)
	            .setActionLocKey("PLAY")
	            .build();
		
		return PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.alias(alias))
				.setOptions(Options.newBuilder().setApnsProduction(iosContextFlag).build())
				.setNotification(Notification.newBuilder()
						// 指定当前推送的android通知
						.addPlatformNotification(AndroidNotification.newBuilder().setAlert(notification_alert) // 设置通知内容（必填）
								.setTitle(notification_title) // 设置通知标题（可选）
								// 此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
								.addExtras(params).build())
						// 指定当前推送的iOS通知
						.addPlatformNotification(IosNotification.newBuilder().setAlert(alert)
								// 此项是指定此推送的badge（应用角标）自动加1
								.incrBadge(1)
								// 此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
								// 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
								.setSound("sound.caf")
								// 此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
								.addExtras(params)
								// 取消此注释，消息推送时ios将无法在锁屏情况接收
								.setContentAvailable(true).build())
						.build())
				.build();
	}

	/**
	 *
	 * @param notification_alert
	 * @param notification_title
	 * @param params
	 * @return
	 */
	public static PushPayload buildPushObjectWithAllAndParams(String notification_alert, String notification_title,
			Map<String,String> params,String iosContext) {

		boolean iosContextFlag = iosContext.equalsIgnoreCase("1")?true:false;
		
		IosAlert alert = IosAlert.newBuilder()
	            .setTitleAndBody(notification_title, "", notification_alert)
	            .setActionLocKey("PLAY")
	            .build();
		
		return PushPayload.newBuilder()
				// 指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
				.setPlatform(Platform.all())
				// 指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
				.setAudience(Audience.all())
				// jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
				.setOptions(Options.newBuilder().setApnsProduction(iosContextFlag).build())
				.setNotification(Notification.newBuilder()
						// 指定当前推送的android通知
						.addPlatformNotification(AndroidNotification.newBuilder().setAlert(notification_alert) // 设置通知内容（必填）
								.setTitle(notification_title) // 设置通知标题（可选）
								// 此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
								.addExtras(params).build())

						// 指定当前推送的iOS通知
						.addPlatformNotification(IosNotification.newBuilder()
								// 传一个IosAlert对象，指定apns title、title、subtitle等
								.setAlert(alert)
								// 直接传alert
								// 此项是指定此推送的badge（应用角标）自动加1
								.incrBadge(1)
								// 此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
								// 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
								.setSound("sound.caf")
								// 此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
								.addExtras(params)
								// 此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
								// 取消此注释，消息推送时ios将无法在锁屏情况接收
								// .setContentAvailable(true)
								.build())
						.build())
				.build();
	}

	/**
	 *
	 * @param tags
	 * @param notification_alert
	 * @param notification_title
	 * @param params
	 * @return
	 */
	public static PushPayload buildPushObjectWithTagsAndParam(List<String> tags,String notification_alert, String notification_title,
			Map<String,String> params,String iosContext) {

		boolean iosContextFlag = iosContext.equalsIgnoreCase("1")?true:false;
		
		IosAlert alert = IosAlert.newBuilder()
	            .setTitleAndBody(notification_title, "", notification_alert)
	            .setActionLocKey("PLAY")
	            .build();
		return PushPayload.newBuilder()
				// 指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
				.setPlatform(Platform.all())
				// 指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
				//.setAudience(Audience.tag(tags))//并集广州 深圳
				.setAudience(Audience.tag_and(tags))//交集广东 深圳
				// jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
				.setOptions(Options.newBuilder().setApnsProduction(iosContextFlag).build())
				.setNotification(Notification.newBuilder()
						// 指定当前推送的android通知
						.addPlatformNotification(AndroidNotification.newBuilder().setAlert(notification_alert) // 设置通知内容（必填）
								.setTitle(notification_title) // 设置通知标题（可选）
								// 此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
								.addExtras(params).build())

						// 指定当前推送的iOS通知
						.addPlatformNotification(IosNotification.newBuilder()
								// 传一个IosAlert对象，指定apns title、title、subtitle等
								.setAlert(alert)
								// 直接传alert
								// 此项是指定此推送的badge（应用角标）自动加1
								.incrBadge(1)
								// 此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
								// 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
								.setSound("sound.caf")
								// 此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
								.addExtras(params)
								// 此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
								// 取消此注释，消息推送时ios将无法在锁屏情况接收
								// .setContentAvailable(true)
								.build())
						.build())
				.build();
	}

	/**
	 *
	 * @param tags
	 * @param notification_alert
	 * @param notification_title
	 * @param params
	 * @return
	 */
	public static PushPayload buildPushObjectWithTagsIsOrParam(List<String> tags,String notification_alert, String notification_title,
															  Map<String,String> params,String iosContext) {

		boolean iosContextFlag = iosContext.equalsIgnoreCase("1")?true:false;

		IosAlert alert = IosAlert.newBuilder()
				.setTitleAndBody(notification_title, "", notification_alert)
				.setActionLocKey("PLAY")
				.build();
		return PushPayload.newBuilder()
				// 指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
				.setPlatform(Platform.all())
				// 指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
				.setAudience(Audience.tag(tags))//并集广州 深圳
				//.setAudience(Audience.tag_and(tags))//交集广东 深圳
				// jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
				.setOptions(Options.newBuilder().setApnsProduction(iosContextFlag).build())
				.setNotification(Notification.newBuilder()
						// 指定当前推送的android通知
						.addPlatformNotification(AndroidNotification.newBuilder().setAlert(notification_alert) // 设置通知内容（必填）
								.setTitle(notification_title) // 设置通知标题（可选）
								// 此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
								.addExtras(params).build())

						// 指定当前推送的iOS通知
						.addPlatformNotification(IosNotification.newBuilder()
								// 传一个IosAlert对象，指定apns title、title、subtitle等
								.setAlert(alert)
								// 直接传alert
								// 此项是指定此推送的badge（应用角标）自动加1
								.incrBadge(1)
								// 此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
								// 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
								.setSound("sound.caf")
								// 此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
								.addExtras(params)
								// 此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
								// 取消此注释，消息推送时ios将无法在锁屏情况接收
								// .setContentAvailable(true)
								.build())
						.build())
				.build();
	}

	/**
	 *
	 * @param notification_alert
	 * @param notification_title
	 * @param params
	 * @return
	 */
	public static PushPayload buildPushObjectWithIosByParams(String notification_alert, String notification_title,Map<String,String> params,String iosContext) {

		boolean iosContextFlag = iosContext.equalsIgnoreCase("1")?true:false;

		IosAlert alert = IosAlert.newBuilder()
	            .setTitleAndBody(notification_title, "", notification_alert)
	            .setActionLocKey("PLAY")
	            .build();
		
		return PushPayload.newBuilder()
				// 指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
				.setPlatform(Platform.ios())
				// 指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应用客户端调用接口获取到的registration id
				.setAudience(Audience.all())
				.setOptions(Options.newBuilder().setApnsProduction(iosContextFlag).build())
				// jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
				.setNotification(Notification.newBuilder()

						// 指定当前推送的iOS通知
						.addPlatformNotification(IosNotification.newBuilder()
								// 传一个IosAlert对象，指定apns title、title、subtitle等
								.setAlert(alert)
								// 直接传alert
								// 此项是指定此推送的badge（应用角标）自动加1
								.incrBadge(1)
								// 此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
								// 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
								.setSound("sound.caf")
								// 此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
								.addExtras(params)
								// 此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
								// 取消此注释，消息推送时ios将无法在锁屏情况接收
								// .setContentAvailable(true)
								.build())
						.build())
				.build();
	}

	/**
	 *
	 * @param notification_alert
	 * @param notification_title
	 * @param params
	 * @return
	 */
	public static PushPayload buildPushObjectWithAndroidByParams(String notification_alert, String notification_title,Map<String,String> params) {
		return PushPayload.newBuilder()
				// 指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
				.setPlatform(Platform.android())
				// 指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
				.setAudience(Audience.all())
				// jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
				.setNotification(Notification.newBuilder()
						// 指定当前推送的android通知
						.addPlatformNotification(AndroidNotification.newBuilder().setAlert(notification_alert) // 设置通知内容（必填）
								.setTitle(notification_title) // 设置通知标题（可选）
								// 此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
								.addExtras(params).build())
						.build())
				.build();
	}
}
