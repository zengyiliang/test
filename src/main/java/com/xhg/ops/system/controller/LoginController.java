package com.xhg.ops.system.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.xhg.core.web.vo.RequestBean;
import com.xhg.core.web.vo.ResponseBean;
import com.xhg.core.web.vo.RsBody;
import com.xhg.core.web.vo.RsHead;
import com.xhg.ops.enums.Status;
import com.xhg.ops.system.service.LoginService;
import com.xhg.ops.system.vo.login.LoginReqVo;
import com.xhg.ops.system.vo.login.LoginRespVo;
import com.xhg.ops.system.vo.login.LogoutRespVo;
import com.xhg.ops.system.vo.login.RefreshTokenReqVo;
import com.xhg.ops.system.vo.login.RefreshTokenRespVo;
import com.xhg.ops.utils.PhoneFormatCheckUtils;
import com.xhg.ops.utils.RedisKey;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import redis.clients.jedis.JedisCluster;

/**
 * @Name: 用户信息控制器
 * @Description: TODO
 * @Copyright: Copyright (c) 2018
 * @Author lilun
 * @Create Date 2018年7月11日
 * @Version 1.0.0
 */

@Controller
@RequestMapping("/ops/system/login")
@Api(value = "ops登录接口", description = "ops登录接口-李沦")
public class LoginController {
	Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private JedisCluster jedisCluster;
	@Autowired
	private LoginService loginService;

	/**
	 * 登录
	 *
	 * @param requestBean
	 * @return
	 */
	@RequestMapping(value = "/loginpc", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation("用户登录PC")
	@ResponseBody
	public ResponseBean<LoginRespVo> loginPC(@RequestBody RequestBean<LoginReqVo> requestBean) {
		LoginReqVo loginReqVo = requestBean.getRequestBody().getData();

		RsHead rh = new RsHead();
		RsBody<LoginRespVo> rsBody = new RsBody<LoginRespVo>();
		ResponseBean<LoginRespVo> responseBean = new ResponseBean<>(rh, rsBody);
		try {

			if (loginReqVo == null || StringUtils.isEmpty(loginReqVo.getLoginName())
					|| StringUtils.isEmpty(loginReqVo.getPassword())
					|| !PhoneFormatCheckUtils.isChinaPhoneLegal(loginReqVo.getLoginName())) {
				rsBody.setCode(Status.OPS_PHONE_ERROR.getName());
				rsBody.setMessage(Status.OPS_PHONE_ERROR.getValue());
				rsBody.setData(new LoginRespVo());
				return responseBean;
			}
			

			return loginService.login(requestBean, 1);
		} catch (Exception e) {
			 logger.error("用户登录PCLoginController.loginPC异常:" + e.getMessage(), e);
			rsBody.setData(new LoginRespVo());
			rsBody.setCode(Status.FAILD.getName());
			rsBody.setMessage(Status.FAILD.getValue());
			responseBean = new ResponseBean<>(rh, rsBody); // new ResponseBean(rh, rsBody, Status.FAILD);
			return responseBean;
		}
	}

	/**
	 * 登录
	 *
	 * @param requestBean
	 * @return
	 */
	@RequestMapping(value = "/loginapp", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "用户登录APP", httpMethod = "POST")
	@ResponseBody
	public ResponseBean<LoginRespVo> loginApp(@RequestBody RequestBean<LoginReqVo> requestBean) {
		LoginReqVo loginReqVo = requestBean.getRequestBody().getData();

		RsHead rh = new RsHead();
		RsBody<LoginRespVo> rsBody = new RsBody<LoginRespVo>();
		ResponseBean<LoginRespVo> responseBean = new ResponseBean<>(rh, rsBody);
		try {

			if (loginReqVo == null || StringUtils.isEmpty(loginReqVo.getLoginName())
					|| StringUtils.isEmpty(loginReqVo.getPassword())
					|| !PhoneFormatCheckUtils.isChinaPhoneLegal(loginReqVo.getLoginName())) {
				rsBody.setCode(Status.OPS_PHONE_ERROR.getName());
				rsBody.setMessage(Status.OPS_PHONE_ERROR.getValue());
				rsBody.setData(new LoginRespVo());
				return responseBean;
			}
			

			return loginService.login(requestBean, 2);
		} catch (Exception e) {
			 logger.error("用户登录APPLoginController.loginApp异常:" + e.getMessage(), e);
			rsBody.setData(new LoginRespVo());
			rsBody.setCode(Status.FAILD.getName());
			rsBody.setMessage(Status.FAILD.getValue());
			responseBean = new ResponseBean<>(rh, rsBody); // new ResponseBean(rh, rsBody, Status.FAILD);
			return responseBean;
		}
	}

	/**
	 * 登出
	 *
	 * @param requestBean
	 * @return
	 */
	@RequestMapping(value = "/logoutapp", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation("用户登出APP")
	@ResponseBody
	public ResponseBean logoutApp(@RequestBody RequestBean requestBean) {
		
		// 登出
		return  loginService.logout(requestBean, 2);
	}

	/**
	 * 登出
	 *
	 * @param requestBean
	 * @return
	 */
	@RequestMapping(value = "/logoutpc", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation("用户登出PC")
	@ResponseBody
	public ResponseBean logoutPC(@RequestBody RequestBean requestBean) {
		
		// 登出
		return  loginService.logout(requestBean, 1);
	}

	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation("刷新token")
	@ResponseBody
	public ResponseBean<RefreshTokenRespVo> refresh(@RequestBody RequestBean<RefreshTokenReqVo> requestBean) {

		String devicedId = requestBean.getRequestHead().getDeviceId();

		if (StringUtils.isEmpty(requestBean.getRequestHead().getToken())
				|| StringUtils.isEmpty(requestBean.getRequestHead().getDeviceId())) {
			RsHead rsHead = new RsHead();
			RsBody<RefreshTokenRespVo> rsBody = new RsBody<>();
			rsBody.setCode(Status.BU_FAILD.getName());
			rsBody.setMessage(Status.BU_FAILD.getValue());
			ResponseBean<RefreshTokenRespVo> responseBean = new ResponseBean<>(rsHead, rsBody);
			return responseBean;
		}

		// 刷新token
		return  loginService.refreshToken(requestBean.getRequestHead().getToken(),
					 devicedId);
	}

}
