package com.xhg.ops.system.service.impl;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;
import com.xhg.ops.system.service.OpsSystemMessagePushService;
import com.xhg.ops.utils.SystemCommonProperties;
import com.xhg.ops.utils.SystemMessagePushUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OpsSystemMessagePushServiceImpl implements OpsSystemMessagePushService {

    private static Logger logger = LoggerFactory.getLogger(OpsSystemMessagePushServiceImpl.class);

    @Autowired
    private SystemCommonProperties systemCommonProperties;

    @Override
    public int sendToRegistrationIdWithParams(String registrationId, String notification_alert, String notification_title, String type, Map<String, String> buss) {
        int result = -1;

        try {
            JPushClient jPushClient = getJPushClientByType(type);
            PushPayload pushPayload= SystemMessagePushUtil.pushMessageByRegistrationIdAndParams(registrationId,
                    notification_alert, notification_title, buss,systemCommonProperties.getIosContextFlag());
            PushResult pushResult=jPushClient.sendPush(pushPayload);  //发送推送对象
            if(pushResult.getResponseCode() == 200) {  //状态码等于200 为成功
                result=200;
            }
            logger.info("#根据app注册id推送消息,消息推送结果：{}",result);
        } catch (APIConnectionException e) {
            logger.info("#根据app注册id推送消息,消息推送失败：{}",e.getMessage());
        } catch (APIRequestException e) {
            logger.info("#根据app注册id推送消息,消息推送失败：{}",e.getMessage());
        }

        return result;
    }

    @Override
    public int sendToAliasWithParams(List<String> alias, String notification_alert, String notification_title, String type, Map<String, String> buss) {
        int result =-1;
        try {
            JPushClient jPushClient = getJPushClientByType(type);
            PushPayload pushPayload= SystemMessagePushUtil.pushObjectWithAliasAndParams(alias, notification_alert, notification_title,
                    buss,systemCommonProperties.getIosContextFlag());
            PushResult pushResult=jPushClient.sendPush(pushPayload);  //发送推送对象
            if(pushResult.getResponseCode() == 200) {  //状态码等于200 为成功
                result=200;
            }
            logger.info("#根据app别名推送消息,消息推送结果：{}",result);
        } catch (APIConnectionException e) {
            logger.info("#根据app别名推送消息,连接失败,消息推送失败：{}",e.getMessage());
        } catch (APIRequestException e) {
            logger.info("#根据app别名推送消息,请求失败,消息推送失败：{}",e.getMessage());
        }
        return result;
    }

    @Override
    public int sendToTagsWithAndParams(List<String> tags, String notification_alert, String notification_title, String type, Map<String, String> buss) {
        int result =-1;
        try {
            JPushClient jPushClient = getJPushClientByType(type);
            PushPayload pushPayload= SystemMessagePushUtil.buildPushObjectWithTagsAndParam(tags, notification_alert, notification_title,
                    buss,systemCommonProperties.getIosContextFlag());
            PushResult pushResult=jPushClient.sendPush(pushPayload);  //发送推送对象
            if(pushResult.getResponseCode() == 200) {  //状态码等于200 为成功
                result=200;
            }
            logger.info("#根据app标签推送消息,消息推送结果：{}",result);
        } catch (APIConnectionException e) {
            logger.info("#根据app标签推送消息,连接失败,消息推送失败：{}",e.getMessage());
        } catch (APIRequestException e) {
            logger.info("#根据app标签推送消息,请求失败,消息推送失败：{}",e.getMessage());
        }
        return result;
    }

    @Override
    public int sendToTagsWithOrParams(List<String> tags, String notification_alert, String notification_title, String type, Map<String, String> buss) {
        int result =-1;
        try {
            JPushClient jPushClient = getJPushClientByType(type);
            PushPayload pushPayload= SystemMessagePushUtil.buildPushObjectWithTagsIsOrParam(tags, notification_alert, notification_title,
                    buss,systemCommonProperties.getIosContextFlag());
            PushResult pushResult=jPushClient.sendPush(pushPayload);  //发送推送对象
            if(pushResult.getResponseCode() == 200) {  //状态码等于200 为成功
                result=200;
            }
            logger.info("#根据app标签推送消息,消息推送结果：{}",result);
        } catch (APIConnectionException e) {
            logger.info("#根据app标签推送消息,连接失败,消息推送失败：{}",e.getMessage());
        } catch (APIRequestException e) {
            logger.info("#根据app标签推送消息,请求失败,消息推送失败：{}",e.getMessage());
        }
        return result;
    }

    @Override
    public int sendToAllWithParams(String notification_alert, String notification_title, String type, Map<String, String> buss) {
        int result = -1;
        try {
            JPushClient jPushClient = getJPushClientByType(type);
            PushPayload pushPayload= SystemMessagePushUtil.buildPushObjectWithAllAndParams( notification_alert,  notification_title,
                    buss,systemCommonProperties.getIosContextFlag());
            PushResult pushResult=jPushClient.sendPush(pushPayload);  //发送推送对象
            if(pushResult.getResponseCode() == 200) {  //状态码等于200 为成功
                result=200;
            }
            logger.info("#根据app标签推送消息,消息推送结果：{}",result);
        } catch (APIConnectionException e) {
            logger.info("#根据app标签推送消息,连接失败,消息推送失败：{}",e.getMessage());
        } catch (APIRequestException e) {
            logger.info("#根据app标签推送消息,请求失败,消息推送失败：{}",e.getMessage());
        }
        return result;
    }

    @Override
    public int sendToAllIosWithParams(String notification_alert, String notification_title, String type, Map<String, String> buss) {
        int result =-1;
        try {
            JPushClient jPushClient = getJPushClientByType(type);
            PushPayload pushPayload= SystemMessagePushUtil.buildPushObjectWithIosByParams( notification_alert,  notification_title,
                    buss,systemCommonProperties.getIosContextFlag());
            PushResult pushResult=jPushClient.sendPush(pushPayload);  //发送推送对象
            if(pushResult.getResponseCode() == 200) {  //状态码等于200 为成功
                result=200;
            }
            logger.info("#根据app标签推送消息,消息推送结果：{}",result);
        } catch (APIConnectionException e) {
            logger.info("#根据app标签推送消息,连接失败,消息推送失败：{}",e.getMessage());
        } catch (APIRequestException e) {
            logger.info("#根据app标签推送消息,请求失败,消息推送失败：{}",e.getMessage());
        }
        return result;
    }

    @Override
    public int sendToAllAndroidWithParams(String notification_alert, String notification_title, String type, Map<String, String> buss) {
        int result = -1;
        try {
            JPushClient jPushClient = getJPushClientByType(type);
            PushPayload pushPayload= SystemMessagePushUtil.buildPushObjectWithAndroidByParams( notification_alert,  notification_title, buss);
            PushResult pushResult=jPushClient.sendPush(pushPayload);  //发送推送对象
            if(pushResult.getResponseCode() == 200) {  //状态码等于200 为成功
                result=200;
            }
            logger.info("#根据app标签推送消息,消息推送结果：{}",result);
        } catch (APIConnectionException e) {
            logger.info("#根据app标签推送消息,连接失败,消息推送失败：{}",e.getMessage());
        } catch (APIRequestException e) {
            logger.info("#根据app标签推送消息,请求失败,消息推送失败：{}",e.getMessage());
        }
        return result;
    }

    //获取对应的key
    private JPushClient getJPushClientByType(String type){
        JPushClient jPushClient = null;
        if("Ops".equals(type)){
            jPushClient = new JPushClient(systemCommonProperties.getMasterSecretOps(), systemCommonProperties.getAppkeyOps());
        }else{
            jPushClient = new JPushClient(systemCommonProperties.getMasterSecretTest(), systemCommonProperties.getAppkeyTest());
        }
        return jPushClient;
    }
}
