package com.xhg.ops.system.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xhg.core.web.vo.RequestBean;
import com.xhg.core.web.vo.ResponseBean;
import com.xhg.core.web.vo.RsBody;
import com.xhg.core.web.vo.RsHead;
import com.xhg.ops.enums.NoJurisdictionUrlEnum;
import com.xhg.ops.enums.NoNeedLoginUrlEnum;
import com.xhg.ops.enums.Status;
import com.xhg.ops.system.entity.SystemRoleDO;
import com.xhg.ops.system.model.SystemUser;
import com.xhg.ops.system.service.OpsSystemRoleMenuService;
import com.xhg.ops.system.service.OpsSystemUserRoleService;
import com.xhg.ops.utils.RedisKey;

import redis.clients.jedis.JedisCluster;

@Order(2)
@WebFilter(filterName = "httpServletRequestWrapperFilter", urlPatterns = "/*")
@Component
public class HttpServletRequestWrapperFilter implements Filter {

	private Logger logger = LoggerFactory.getLogger(HttpServletRequestWrapperFilter.class);
	public static final int TOKEN_EXPIRE = 60 * 30;
	@Autowired
	private JedisCluster jedisCluster;
	@Autowired
	private OpsSystemRoleMenuService opsSystemRoleMenuService;
	@Autowired
	private OpsSystemUserRoleService opsSystemUserRoleService;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// 预留
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {

		if (!(request instanceof HttpServletRequest)) {
			filterChain.doFilter(request, response);
			return;
		}

		// 过滤器拦截地址
		String path = ((HttpServletRequest) request).getServletPath();

		// 不需要拦截的url
		if (NoNeedLoginUrlEnum.isUnNeedLogin(path) || path.indexOf("ops") == -1) {
			filterChain.doFilter(request, response);
			return;
		}

		// 处理post请求
		BodyReaderHttpServletRequestWrapper requestWrapper = null;
		requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) request);
		String s = requestWrapper.getBody();
		if (StringUtils.isBlank(s)) {
			filterChain.doFilter(request, response);
			return;
		}
		// 获取请求对象
		RequestBean<Object> requestBean = JSON.parseObject(s, RequestBean.class);
		if (requestBean == null || requestBean.getRequestHead() == null) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = requestBean.getRequestHead().getToken();
		String devicedId = requestBean.getRequestHead().getDeviceId();

		SystemUser user = new SystemUser();
		
		if (StringUtils.isNotBlank(token) && StringUtils.isNotEmpty(devicedId)
				&& requestBean.getRequestHead().getChannel().equals("pc")) {

			String redisJsom = jedisCluster.get(RedisKey.LOGIN_ID_PRE_PC + token);
			user = JSONObject.parseObject(redisJsom, SystemUser.class);// 将建json对象转换为Person对象
			if (user != null) {
				jedisCluster.setex(RedisKey.LOGIN_ID_PRE_PC + token, TOKEN_EXPIRE, redisJsom);
			}

		} else {
			String redisJsom = jedisCluster.get(RedisKey.LOGIN_ID_PRE_APP + devicedId + ":" + token);
			user = JSONObject.parseObject(redisJsom, SystemUser.class);// 将建json对象转换为Person对象
		}

		// 验证token有效性
		if (StringUtils.isEmpty(token) || StringUtils.isBlank(token) || user == null) {
			// 禁止访问
			this.loginForbidden((HttpServletResponse) response, Status.LOGIN.getName(), Status.LOGIN.getValue());
			return;
		}
		if (NoJurisdictionUrlEnum.isUnNeedLogin(path) == false) {
			String userRole = jedisCluster.get(RedisKey.USER_ROLE);
			Map<String, String> map = JSONObject.parseObject(userRole, Map.class);// 将建json对象转换为Person对象
			String json = map.get(String.valueOf(user.getId()));
		
			if (userRole == null || userRole.isEmpty()||json==null||json.isEmpty()) {
				opsSystemUserRoleService.queryByUserRoleList();
				userRole = jedisCluster.get(RedisKey.USER_ROLE);
			}
			Map<String, String> userRoleMap = JSONObject.parseObject(userRole, Map.class);// 将建json对象转换为Person对象
			String userRoleJson = userRoleMap.get(String.valueOf(user.getId()));
			List<SystemRoleDO> list = JSONObject.parseArray(userRoleJson, SystemRoleDO.class);
			RoleContext.setRole(list);
			if (requestBean.getRequestHead().getChannel().equals("pc")) {
				String roleMeun = jedisCluster.get(RedisKey.ROLE_MEUN);
				if (roleMeun == null || roleMeun.isEmpty()) {

					opsSystemRoleMenuService.queryByRoleMenuList();
					roleMeun = jedisCluster.get(RedisKey.ROLE_MEUN);
				}
				if (list != null && list.size() > 0) {
					boolean isRole = true;
					for (SystemRoleDO systemRoleDO : list) {
						String userPath = systemRoleDO.getRoleId() + path;
						if (roleMeun.indexOf(userPath) != -1) {
							isRole = false;
						}
					}
					if (isRole) {
						// // 没有访问权限
						// this.loginForbidden((HttpServletResponse) response,
						// Status.MANAGERUSER_UNAUTHORIZED.getName(),
						// Status.MANAGERUSER_UNAUTHORIZED.getValue());
						// return;
					}

				} else {
					// // 没有访问权限
					// this.loginForbidden((HttpServletResponse) response,
					// Status.MANAGERUSER_UNAUTHORIZED.getName(),
					// Status.MANAGERUSER_UNAUTHORIZED.getValue());
					// return;
					// 没有分配角色
				}
			}
			
		}
		
		UserContext.setUser(user);
		// String redisJsom = jedisCluster.get(RedisKey.ROLE_MEUN);
		// if (redisJsom.indexOf("12")==-1) {
		// // 没有访问权限
		// this.loginForbidden((HttpServletResponse) response,
		// Status.MANAGERUSER_UNAUTHORIZED.getName(),
		// Status.MANAGERUSER_UNAUTHORIZED.getValue());
		// return;
		// }
		filterChain.doFilter(requestWrapper, response);
		return;

	}

	// 用户未登录出路
	private void loginForbidden(HttpServletResponse response, String code, String message) {

		RsHead rsHead = new RsHead();
		RsBody<Object> rsBody = new RsBody<>();
		rsBody.setCode(code);
		rsBody.setMessage(message);
		ResponseBean<Object> responseBean = new ResponseBean<Object>(rsHead, rsBody);

		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			ObjectMapper mapper = new ObjectMapper();
			out.append(mapper.writeValueAsString(responseBean));

		} catch (IOException e) {
			logger.info("----返回异常----");
			e.printStackTrace();

		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	@Override
	public void destroy() {
		// 预留
	}
}
