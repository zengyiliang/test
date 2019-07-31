package com.xhg.ops.system.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
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
import com.xhg.ops.system.entity.OpsSystemRoleDO;
import com.xhg.ops.system.entity.OpsSystemRoleMenuDO;
import com.xhg.ops.system.service.OpsSystemRoleService;
import com.xhg.ops.system.vo.rolemeun.OpsSystemRoleMenuRespVo;
import com.xhg.ops.system.vo.rolemeun.OpsSystemRoleReqVo;
import com.xhg.ops.system.vo.rolemeun.OpsSystemRoleRespVo;
import com.xhg.ops.system.vo.rolemeun.OpsSystemRoleUpdateReqVo;
import com.xhg.ops.system.vo.rolemeun.SystemRoleQueryReqVo;
import com.xhg.ops.system.vo.rolemeun.SystemRoleQueryVO;

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
@RequestMapping("/ops/system/role")
@Api(value = "ops角色管理接口", description = "ops角色管理接口-李沦")
public class OpsSystemRoleController {

	private final static Logger logger = LoggerFactory.getLogger(OpsSystemRoleController.class);

	@Autowired
	private OpsSystemRoleService opsSystemRoleService;

	@Autowired
	private JedisCluster jedisCluster;
	@Autowired
	private OpsSystemRoleService roleService;

	@RequestMapping(value = "/insert", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation("创建角色-李沦")
	@ResponseBody
	public ResponseBean insert(@RequestBody RequestBean<OpsSystemRoleReqVo> requestBean) {
		OpsSystemRoleReqVo reqVo = requestBean.getRequestBody().getData();

		RsHead rh = new RsHead();
		RsBody rsBody = new RsBody();
		ResponseBean responseBean = new ResponseBean<>(rh, rsBody);
		try {
			OpsSystemRoleDO roleDO = new OpsSystemRoleDO();
			BeanUtils.copyProperties(roleDO, reqVo);
			List<OpsSystemRoleMenuDO> menuDOs = new ArrayList<>();
			if (reqVo.getRoleMenu() != null) {
				for (int i = 0; i < reqVo.getRoleMenu().size(); i++) {
					OpsSystemRoleMenuDO menuDO = new OpsSystemRoleMenuDO();
					menuDO.setMenuId(reqVo.getRoleMenu().get(i));
					menuDOs.add(menuDO);

				}
			}

			roleService.insertRoleMenu(roleDO, menuDOs);
			rsBody.setCode(Status.Bu_SUCCESS.getName());
			rsBody.setMessage(Status.Bu_SUCCESS.getValue());

			return responseBean;
		} catch (Exception e) {
			 logger.error("创建角色OpsSystemRoleController.insert异常:" + e.getMessage(), e);
			rsBody.setCode(Status.FAILD.getName());
			rsBody.setMessage(Status.FAILD.getValue());
			responseBean = new ResponseBean<>(rh, rsBody); // new ResponseBean(rh, rsBody, Status.FAILD);
			return responseBean;
		}
	}
	@RequestMapping(value = "/querymenu", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation("新增角色的菜单列表-李沦")
	@ResponseBody
	public ResponseBean<List<OpsSystemRoleMenuRespVo>> queryMenu(
			@RequestBody RequestBean requestBean) {

		RsHead rh = new RsHead();
		RsBody<List<OpsSystemRoleMenuRespVo>> rsBody = new RsBody<List<OpsSystemRoleMenuRespVo>>();
		ResponseBean<List<OpsSystemRoleMenuRespVo>> responseBean = new ResponseBean<>(rh, rsBody);
		try {
			List<OpsSystemRoleMenuRespVo> vos = roleService.queryMenu();
			rsBody.setCode(Status.Bu_SUCCESS.getName());
			rsBody.setMessage(Status.Bu_SUCCESS.getValue());
			rsBody.setData(vos);
			return responseBean;
		} catch (Exception e) {
			 logger.error("编辑查询角色的菜单列表OpsSystemRoleController.queryRole异常:" + e.getMessage(), e);
			rsBody.setCode(Status.FAILD.getName());
			rsBody.setMessage(Status.FAILD.getValue());
			responseBean = new ResponseBean<>(rh, rsBody); // new ResponseBean(rh, rsBody, Status.FAILD);
			return responseBean;
		}
	}
	@RequestMapping(value = "/querylist", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation("查询角色列表-李沦")
	@ResponseBody
	public ResponseBean<List<OpsSystemRoleRespVo>> queryList(@RequestBody RequestBean<?> requestBean) {

		RsHead rh = new RsHead();
		RsBody<List<OpsSystemRoleRespVo>> rsBody = new RsBody<>();
		ResponseBean<List<OpsSystemRoleRespVo>> responseBean = new ResponseBean<>(rh, rsBody);
		try {
			SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			List<OpsSystemRoleDO> list = roleService.queryRoleList(null);
			List<OpsSystemRoleRespVo> respVos = new ArrayList<>();
			for (OpsSystemRoleDO opsSystemRoleDO : list) {
				OpsSystemRoleRespVo respVo = new OpsSystemRoleRespVo();
				BeanUtils.copyProperties(respVo, opsSystemRoleDO);
				respVo.setCreatedTime(format0.format(opsSystemRoleDO.getCreatedTime()));
				respVos.add(respVo);
			}
			rsBody.setCode(Status.Bu_SUCCESS.getName());
			rsBody.setMessage(Status.Bu_SUCCESS.getValue());
			rsBody.setData(respVos);
			return responseBean;
		} catch (Exception e) {
			logger.error("查询角色列表OpsSystemRoleController.queryList异常:" + e.getMessage(), e);
			rsBody.setCode(Status.FAILD.getName());
			rsBody.setMessage(Status.FAILD.getValue());
			responseBean = new ResponseBean<>(rh, rsBody); // new ResponseBean(rh, rsBody, Status.FAILD);
			return responseBean;
		}
	}

	@RequestMapping(value = "/queryrole", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation("编辑查询角色的菜单列表-李沦")
	@ResponseBody
	public ResponseBean<List<OpsSystemRoleMenuRespVo>> queryRole(
			@RequestBody RequestBean<SystemRoleQueryReqVo> requestBean) {
		SystemRoleQueryReqVo reqVo = requestBean.getRequestBody().getData();

		RsHead rh = new RsHead();
		RsBody<List<OpsSystemRoleMenuRespVo>> rsBody = new RsBody<List<OpsSystemRoleMenuRespVo>>();
		ResponseBean<List<OpsSystemRoleMenuRespVo>> responseBean = new ResponseBean<>(rh, rsBody);
		try {
			OpsSystemRoleDO roleDO = new OpsSystemRoleDO();
			BeanUtils.copyProperties(roleDO, reqVo);
			List<OpsSystemRoleMenuRespVo> vos = roleService.queryRoleIdMenu(reqVo.getRoleId());
			rsBody.setCode(Status.Bu_SUCCESS.getName());
			rsBody.setMessage(Status.Bu_SUCCESS.getValue());
			rsBody.setData(vos);
			return responseBean;
		} catch (Exception e) {
			 logger.error("编辑查询角色的菜单列表OpsSystemRoleController.queryRole异常:" + e.getMessage(), e);
			rsBody.setCode(Status.FAILD.getName());
			rsBody.setMessage(Status.FAILD.getValue());
			responseBean = new ResponseBean<>(rh, rsBody); // new ResponseBean(rh, rsBody, Status.FAILD);
			return responseBean;
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation("修改角色-李沦")
	@ResponseBody
	public ResponseBean update(@RequestBody RequestBean<OpsSystemRoleUpdateReqVo> requestBean) {
		OpsSystemRoleUpdateReqVo reqVo = requestBean.getRequestBody().getData();

		RsHead rh = new RsHead();
		RsBody<List<OpsSystemRoleMenuRespVo>> rsBody = new RsBody<List<OpsSystemRoleMenuRespVo>>();
		ResponseBean<List<OpsSystemRoleMenuRespVo>> responseBean = new ResponseBean<>(rh, rsBody);
		try {
			OpsSystemRoleDO roleDO = new OpsSystemRoleDO();
			BeanUtils.copyProperties(roleDO, reqVo);
			List<OpsSystemRoleMenuDO> menuDOs = new ArrayList<>();
			if (reqVo.getRoleMenu() != null) {
				for (int i = 0; i < reqVo.getRoleMenu().size(); i++) {
					OpsSystemRoleMenuDO menuDO = new OpsSystemRoleMenuDO();
					menuDO.setMenuId(reqVo.getRoleMenu().get(i));
					menuDOs.add(menuDO);

				}
			}
			roleService.updateRoleMenu(roleDO, menuDOs);
			rsBody.setCode(Status.Bu_SUCCESS.getName());
			rsBody.setMessage(Status.Bu_SUCCESS.getValue());

			return responseBean;
		} catch (Exception e) {
			 logger.error("修改角色OpsSystemRoleController.update异常:" + e.getMessage(), e);
			rsBody.setCode(Status.FAILD.getName());
			rsBody.setMessage(Status.FAILD.getValue());
			responseBean = new ResponseBean<>(rh, rsBody); // new ResponseBean(rh, rsBody, Status.FAILD);
			return responseBean;
		}
	}

	@ApiOperation(value = "角色列表加载-曾庆财")
	@RequestMapping(value = "/loadlist", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseBean<List<SystemRoleQueryVO>> querySystemRoleList(@RequestBody RequestBean requestBean)
			throws Exception {

		RsHead rh = new RsHead();
		List<SystemRoleQueryVO> result = opsSystemRoleService.querySystemRoleList(null);
		RsBody<List<SystemRoleQueryVO>> rsBody = new RsBody<>();
		rsBody.setData(result);
		ResponseBean<List<SystemRoleQueryVO>> responseBean = new ResponseBean<List<SystemRoleQueryVO>>(rh, rsBody);
		return responseBean;

	}
}
