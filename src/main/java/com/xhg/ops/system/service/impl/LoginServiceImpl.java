package com.xhg.ops.system.service.impl;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xhg.core.web.vo.RequestBean;
import com.xhg.core.web.vo.ResponseBean;
import com.xhg.core.web.vo.RqHead;
import com.xhg.core.web.vo.RsBody;
import com.xhg.core.web.vo.RsHead;
import com.xhg.ops.enums.Status;
import com.xhg.ops.system.dao.SystemUserDao;
import com.xhg.ops.system.model.SystemUser;
import com.xhg.ops.system.service.LoginService;
import com.xhg.ops.system.service.OpsSystemRoleMenuService;
import com.xhg.ops.system.vo.login.LoginReqVo;
import com.xhg.ops.system.vo.login.LoginRespVo;
import com.xhg.ops.system.vo.login.LogoutReqVo;
import com.xhg.ops.system.vo.login.LogoutRespVo;
import com.xhg.ops.system.vo.login.RefreshTokenRespVo;
import com.xhg.ops.utils.RedisKey;

import redis.clients.jedis.JedisCluster;

@Service
public class LoginServiceImpl implements LoginService {

	private Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

	public static final int TOKEN_EXPIRE = 60 * 30;

	public static final int REFRESH_TOKEN_EXPIRE = 60 * 60 * 24 * 90;

	@Autowired
	private SystemUserDao systemUserDao;

	@Autowired
	private JedisCluster jedisCluster;
	
	@Autowired
	private OpsSystemRoleMenuService opsSystemRoleMenuService;
	/**
	 * type 类型1为pc端2为app
	 */
	@Override
	public ResponseBean<LoginRespVo> login(RequestBean<LoginReqVo> requestBean, Integer type) {

		LoginReqVo loginReqVo = requestBean.getRequestBody().getData();
		String osType = requestBean.getRequestHead().getOstype();
		String devicedId = requestBean.getRequestHead().getDeviceId();

		SystemUser userDO = systemUserDao.selectByPhone(loginReqVo.getLoginName());

		ResponseBean<LoginRespVo> responseBean = null;
		RsHead rsHead = null;
		RsBody<LoginRespVo> rsBody = null;

		// 用户不存在
		if (userDO == null) {
			rsHead = new RsHead();
			rsBody = new RsBody<>();
			rsBody.setCode(Status.OPS_USER_NOT_EXAIST.getName());
			rsBody.setMessage(Status.OPS_USER_NOT_EXAIST.getValue());
			responseBean = new ResponseBean(rsHead, rsBody);

			// 添加登录记录

			return responseBean;
		}

		// 用户和密码不一致
		if (!loginReqVo.getPassword().equals(userDO.getPassword())) {
			rsHead = new RsHead();
			rsBody = new RsBody<>();
			rsBody.setCode(Status.OPS_USER_INFO_ERROR.getName());
			rsBody.setMessage(Status.OPS_USER_INFO_ERROR.getValue());
			responseBean = new ResponseBean(rsHead, rsBody);

			// 添加登录记录
			return responseBean;
		}
		Date d = new Date();
		// 添加用户登录信息

		SystemUser systemUser = systemUserDao.selectByPrimaryKey(userDO.getId());
		String token = this.getToken(systemUser.getPhone() + systemUser.getName());
		// 组装返回vo
		LoginRespVo loginRespVo = new LoginRespVo();
		loginRespVo.setPhone(userDO.getPhone());
		loginRespVo.setName(systemUser.getName());
		loginRespVo.setPhone(systemUser.getPhone());
		loginRespVo.setToken(token);
		loginRespVo.setId(String.valueOf(systemUser.getId()));
		if (type==1) {
//			loginRespVo.setMenuRespVos(opsSystemRoleMenuService.getUserPrevilege(userDO.getId()));
			loginRespVo.setMenus(opsSystemRoleMenuService.getUserOneTwoPrevilege(userDO.getId()));
			loginRespVo.setPermission(opsSystemRoleMenuService.getUserTwoFollowingPrevilege(userDO.getId()));
		}
		
		// 组装返回对象
		rsHead = new RsHead();
		rsBody = new RsBody<>();
		rsBody.setCode(Status.Bu_SUCCESS.getName());
		rsBody.setMessage(Status.Bu_SUCCESS.getValue());
		rsBody.setData(loginRespVo);
		responseBean = new ResponseBean(rsHead, rsBody);
		String jsonObject = JSON.toJSONString(systemUser);
		if (type == 2) {
			jedisCluster.setex(RedisKey.LOGIN_ID_PRE_APP + devicedId + ":" + token, REFRESH_TOKEN_EXPIRE,
					jsonObject.toString());
		} else {
			
			jedisCluster.setex(RedisKey.LOGIN_ID_PRE_PC + token, TOKEN_EXPIRE, jsonObject.toString());
		}
		opsSystemRoleMenuService.queryByRoleMenuList();
		return responseBean;
	}

	/**
	 * 用户登出接口 type 类型1为pc端2为app
	 * 
	 * @param requestBean
	 * @return
	 */
	@Override
	public ResponseBean<LogoutRespVo> logout(RequestBean<LogoutReqVo> requestBean, Integer type) {

		String token = requestBean.getRequestHead().getToken();
		String deviceId = requestBean.getRequestHead().getDeviceId();

		String userId ="";
		if (type == 2) {
			userId = jedisCluster.get(RedisKey.LOGIN_ID_PRE_APP + deviceId + ":" + token);
		} else {
			userId = jedisCluster.get(RedisKey.LOGIN_ID_PRE_PC + token);
		}

		if (StringUtils.isEmpty(token) || StringUtils.isEmpty(userId)) {
			RsHead rsHead = new RsHead();
			RsBody<LogoutRespVo> rsBody = new RsBody<>();
			rsBody.setCode(Status.Bu_SUCCESS.getName());
			rsBody.setMessage(Status.Bu_SUCCESS.getValue());

			return new ResponseBean(rsHead, rsBody);
		}

		if (type == 2) {
			jedisCluster.del(RedisKey.LOGIN_ID_PRE_APP + deviceId + ":" + token);
		} else {
			jedisCluster.del(RedisKey.LOGIN_ID_PRE_PC + token);
		}
		RsHead rsHead = new RsHead();
		RsBody<LogoutRespVo> rsBody = new RsBody<>();
		rsBody.setCode(Status.Bu_SUCCESS.getName());
		rsBody.setMessage(Status.Bu_SUCCESS.getValue());

		return new ResponseBean(rsHead, rsBody);
	}

	/**
	 * 获取token
	 *
	 * @param
	 * @return
	 */
	private String getToken(String refreshToken) {
		return DigestUtils.md5Hex("@@K8&^%^" + refreshToken + new Date().getTime()).toUpperCase();
	}

	@Override
	public SystemUser redisUser(RqHead rqHead) {
		// TODO Auto-generated method stub
		SystemUser jb = new SystemUser();
		if (rqHead.getChannel().equals("pc")) {
			String json = jedisCluster.get(RedisKey.LOGIN_ID_PRE_PC  + rqHead.getToken());
			jb =  JSONObject.parseObject(json, SystemUser.class);// 将建json对象转换为Person对象

		} else {
			String json = jedisCluster.get(RedisKey.LOGIN_ID_PRE_APP + rqHead.getDeviceId() + ":"
					+ rqHead.getToken());
			jb =  JSONObject.parseObject(json, SystemUser.class);// 将建json对象转换为Person对象

		}
		if (jb==null) {
			jb=new SystemUser();
		}

		return jb;
	}

	/**
     * 刷新token
     *
     * @param token
     * @return
     */
    @Override
    public ResponseBean<RefreshTokenRespVo> refreshToken(String primaryToken, String deviceId) {

    	SystemUser jb = new SystemUser();
			String json = jedisCluster.get(RedisKey.LOGIN_ID_PRE_APP + deviceId + ":"+primaryToken);
			jb =  JSONObject.parseObject(json, SystemUser.class);// 将建json对象转换为Person对象


        // 判断refreshToken 是否有效
        if (jb==null || StringUtils.isEmpty(jb.getPhone())) {

            RsHead rsHead = new RsHead();
            RsBody<RefreshTokenRespVo> rsBody = new RsBody<>();
            rsBody.setCode(Status.BU_FAILD.getName());
            rsBody.setMessage(Status.BU_FAILD.getValue());

            return new ResponseBean(rsHead, rsBody);
        }

        SystemUser systemUser =systemUserDao.selectByPhone(jb.getPhone());

        String tokens = this.getToken(systemUser.getPhone() + systemUser.getName());
    	String jsonObject = JSON.toJSONString(systemUser);
			jedisCluster.setex(RedisKey.LOGIN_ID_PRE_APP + deviceId + ":" + tokens, REFRESH_TOKEN_EXPIRE,
					jsonObject.toString());
			//更新原token时间
			jedisCluster.setex(RedisKey.LOGIN_ID_PRE_APP + deviceId + ":" + primaryToken, TOKEN_EXPIRE,
					jsonObject.toString());
			RefreshTokenRespVo vo=new RefreshTokenRespVo();
			vo.setDeviceId(deviceId);
			vo.setToken(tokens);
        // 组装返回对象
        RsHead rsHead = new RsHead();
        RsBody<RefreshTokenRespVo> rsBody = new RsBody<>();
        rsBody.setCode(Status.Bu_SUCCESS.getName());
        rsBody.setMessage(Status.Bu_SUCCESS.getValue());
        rsBody.setData(vo);

        return new ResponseBean(rsHead, rsBody);
    }
}
