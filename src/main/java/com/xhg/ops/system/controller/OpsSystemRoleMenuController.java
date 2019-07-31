package com.xhg.ops.system.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhg.core.web.vo.RequestBean;
import com.xhg.core.web.vo.ResponseBean;
import com.xhg.core.web.vo.RsBody;
import com.xhg.core.web.vo.RsHead;
import com.xhg.ops.enums.Status;
import com.xhg.ops.system.model.SystemUser;
import com.xhg.ops.system.service.LoginService;
import com.xhg.ops.system.service.OpsSystemRoleMenuService;
import com.xhg.ops.system.vo.rolemeun.OpsSystemRoleMenuRespVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import redis.clients.jedis.JedisCluster;

/**
 * 系统角色
 * 
 * @ClassName: OpsSystemRoleController
 * @Description: TODO
 * @author lilun
 * @date 2018-07-11
 */
@Controller
@RequestMapping("/ops/system/rolemenu")
@Api(value = "ops菜单权限接口", description = "ops菜单权限接口-李沦")
public class OpsSystemRoleMenuController {

	private final static Logger logger = LoggerFactory.getLogger(OpsSystemRoleMenuController.class);

	@Autowired
	private JedisCluster jedisCluster;
	@Autowired
	private OpsSystemRoleMenuService roleMenuService;
	@Autowired
	private LoginService loginService;

	@RequestMapping(value = "/querylist", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation("查询菜单权限-李沦")
	@ResponseBody
	public ResponseBean<List<OpsSystemRoleMenuRespVo>> queryList(@RequestBody RequestBean requestBean) {

		RsHead rh = new RsHead();
		RsBody rsBody = new RsBody();
		ResponseBean responseBean = new ResponseBean<>(rh, rsBody);
		try {

			SystemUser user = loginService.redisUser(requestBean.getRequestHead());
			rsBody.setCode(Status.SUCCESS.getName());
			rsBody.setMessage(Status.SUCCESS.getValue());
			rsBody.setData(roleMenuService.getUserPrevilege(user.getId()));
			return responseBean;
		} catch (Exception e) {
			 logger.error("查询菜单权限-李沦OpsSystemRoleMenuController.queryList异常:" + e.getMessage(), e);
			rsBody.setCode(Status.FAILD.getName());
			rsBody.setMessage(Status.FAILD.getValue());
			responseBean = new ResponseBean<>(rh, rsBody); // new ResponseBean(rh, rsBody, Status.FAILD);
			return responseBean;
		}
	}

}
