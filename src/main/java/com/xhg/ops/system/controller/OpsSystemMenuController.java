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
import com.xhg.ops.system.entity.OpsSystemMenuDO;
import com.xhg.ops.system.filter.UserContext;
import com.xhg.ops.system.model.SystemUser;
import com.xhg.ops.system.service.OpsSystemMenuService;
import com.xhg.ops.system.service.OpsSystemRoleMenuService;
import com.xhg.ops.system.vo.rolemeun.OpsSystemMenuDeleteReqVo;
import com.xhg.ops.system.vo.rolemeun.OpsSystemMenuReqVo;
import com.xhg.ops.system.vo.rolemeun.OpsSystemMenuRespVo;
import com.xhg.ops.system.vo.rolemeun.OpsSystemMenuUpdateReqVo;
import com.xhg.ops.system.vo.rolemeun.SystemParentMenuReqVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 系统菜单权限
 * 
 * @ClassName: OpsSystemMenuController
 * @Description: TODO
 * @author lilun
 * @date 2018-07-11
 */
@Controller
@RequestMapping("/ops/system/menu")
@Api(value = "ops权限管理接口", description = "ops权限管理接口-李沦")
public class OpsSystemMenuController {

	private final static Logger logger = LoggerFactory.getLogger(OpsSystemMenuController.class);

	@Autowired
	OpsSystemMenuService opsSystemMenuService;
	@Autowired
	private OpsSystemRoleMenuService roleMenuService;

	@RequestMapping(value = "/insert", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation("创建权限-李沦")
	@ResponseBody
	public ResponseBean insert(@RequestBody RequestBean<OpsSystemMenuReqVo> requestBean) {

		OpsSystemMenuReqVo vo = requestBean.getRequestBody().getData();
		RsHead rh = new RsHead();
		RsBody rsBody = new RsBody();
		ResponseBean responseBean = new ResponseBean<>(rh, rsBody);
		try {
			OpsSystemMenuDO menuDO = new OpsSystemMenuDO();
			BeanUtils.copyProperties(menuDO, vo);
			int id=opsSystemMenuService.insert(menuDO);
			if (id==-1) {
				rsBody.setCode(Status.OPS_MENU_CODE.getName());
				rsBody.setMessage(Status.OPS_MENU_CODE.getValue());
			}else {
				
			
			rsBody.setCode(Status.Bu_SUCCESS.getName());
			rsBody.setMessage(Status.Bu_SUCCESS.getValue());
			}
			return responseBean;
		} catch (Exception e) {
			 logger.error("创建权限-李沦OpsSystemMenuController.insert异常:" + e.getMessage(), e);
			rsBody.setCode(Status.FAILD.getName());
			rsBody.setMessage(Status.FAILD.getValue());
			responseBean = new ResponseBean<>(rh, rsBody); // new ResponseBean(rh, rsBody, Status.FAILD);
			return responseBean;
		}
	}

	@RequestMapping(value = "/querylist", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation("查询权限列表-李沦")
	@ResponseBody
	public ResponseBean<List<OpsSystemMenuRespVo>> queryList(@RequestBody RequestBean requestBean) {
		RsHead rh = new RsHead();
		RsBody rsBody = new RsBody();
		ResponseBean responseBean = new ResponseBean<>(rh, rsBody);
		try {
			SystemUser user= UserContext.getUser();
			List<OpsSystemMenuDO> list = opsSystemMenuService.queryMenuList(null);
			List<OpsSystemMenuRespVo> listVos = new ArrayList<>();
			for (OpsSystemMenuDO opsSystemMenuDO : list) {
				OpsSystemMenuRespVo vo = new OpsSystemMenuRespVo();
				BeanUtils.copyProperties(vo, opsSystemMenuDO);
				listVos.add(vo);
			}
			rsBody.setCode(Status.Bu_SUCCESS.getName());
			rsBody.setMessage(Status.Bu_SUCCESS.getValue());
			rsBody.setData(listVos);
			return responseBean;
		} catch (Exception e) {
			 logger.error("查询权限OpsSystemMenuController.queryList异常:" + e.getMessage(), e);
			rsBody.setCode(Status.FAILD.getName());
			rsBody.setMessage(Status.FAILD.getValue());
			responseBean = new ResponseBean<>(rh, rsBody); // new ResponseBean(rh, rsBody, Status.FAILD);
			return responseBean;
		}
	}
	@RequestMapping(value = "/querysuperiorlist", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation("新增修改的上级菜单列表-李沦")
	@ResponseBody
	public ResponseBean<List<OpsSystemMenuRespVo>> querySuperiorList(@RequestBody RequestBean requestBean) {
		RsHead rh = new RsHead();
		RsBody rsBody = new RsBody();
		ResponseBean responseBean = new ResponseBean<>(rh, rsBody);
		try {
			SystemUser user= UserContext.getUser();
			List<OpsSystemMenuDO> list = opsSystemMenuService.queryAllSuperiorMenus();
			List<OpsSystemMenuRespVo> listVos = new ArrayList<>();
			for (OpsSystemMenuDO opsSystemMenuDO : list) {
				OpsSystemMenuRespVo vo = new OpsSystemMenuRespVo();
				BeanUtils.copyProperties(vo, opsSystemMenuDO);
				listVos.add(vo);
			}
			rsBody.setCode(Status.Bu_SUCCESS.getName());
			rsBody.setMessage(Status.Bu_SUCCESS.getValue());
			rsBody.setData(listVos);
			return responseBean;
		} catch (Exception e) {
			 logger.error("新增修改的上级菜单列表OpsSystemMenuController.querySuperiorList异常:" + e.getMessage(), e);
			rsBody.setCode(Status.FAILD.getName());
			rsBody.setMessage(Status.FAILD.getValue());
			responseBean = new ResponseBean<>(rh, rsBody); // new ResponseBean(rh, rsBody, Status.FAILD);
			return responseBean;
		}
	}
	@RequestMapping(value = "/queryparentlist", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation("编辑菜单-李沦")
	@ResponseBody
	public ResponseBean<OpsSystemMenuUpdateReqVo> queryParentList(@RequestBody RequestBean<SystemParentMenuReqVo> requestBean) {

		RsHead rh = new RsHead();
		RsBody rsBody = new RsBody();
		ResponseBean responseBean = new ResponseBean<>(rh, rsBody);
		try {
			OpsSystemMenuDO menuDO = opsSystemMenuService.queryById(requestBean.getRequestBody().getData().getId());
				OpsSystemMenuUpdateReqVo vo = new OpsSystemMenuUpdateReqVo();
				if (menuDO!=null) {
					BeanUtils.copyProperties(vo, menuDO);
					rsBody.setData(vo);
				}
				
			rsBody.setCode(Status.Bu_SUCCESS.getName());
			rsBody.setMessage(Status.Bu_SUCCESS.getValue());
			return responseBean;
		} catch (Exception e) {
			logger.error("编辑菜单OpsSystemMenuController.queryParentList异常:" + e.getMessage(), e);
			rsBody.setCode(Status.FAILD.getName()); 
			rsBody.setMessage(Status.FAILD.getValue());
			responseBean = new ResponseBean<>(rh, rsBody); // new ResponseBean(rh, rsBody, Status.FAILD);
			return responseBean;
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation("修改权限列表-李沦")
	@ResponseBody
	public ResponseBean update(@RequestBody RequestBean<OpsSystemMenuUpdateReqVo> requestBean) {
		OpsSystemMenuUpdateReqVo vo = requestBean.getRequestBody().getData();
		RsHead rh = new RsHead();
		RsBody rsBody = new RsBody();
		ResponseBean responseBean = new ResponseBean<>(rh, rsBody);
		try {
			OpsSystemMenuDO menuDO = new OpsSystemMenuDO();
			BeanUtils.copyProperties(menuDO, vo);
			opsSystemMenuService.updateBySelective(menuDO);
			rsBody.setCode(Status.Bu_SUCCESS.getName());
			rsBody.setMessage(Status.Bu_SUCCESS.getValue());
			//权限缓存修改
			 roleMenuService.queryByRoleMenuList();
			return responseBean;
		} catch (Exception e) {
			 logger.error("修改权限列表OpsSystemMenuController.update异常:" + e.getMessage(), e);
			rsBody.setCode(Status.FAILD.getName());
			rsBody.setMessage(Status.FAILD.getValue());
			responseBean = new ResponseBean<>(rh, rsBody); // new ResponseBean(rh, rsBody, Status.FAILD);
			return responseBean;
		}
	}
	@RequestMapping(value = "/delect", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation("删除权限-李沦")
	@ResponseBody
	public ResponseBean delete(@RequestBody RequestBean<OpsSystemMenuDeleteReqVo> requestBean) {
		OpsSystemMenuDeleteReqVo vo = requestBean.getRequestBody().getData();
		RsHead rh = new RsHead();
		RsBody rsBody = new RsBody();
		ResponseBean responseBean = new ResponseBean<>(rh, rsBody);
		try {
			OpsSystemMenuDO menuDO = new OpsSystemMenuDO();
			BeanUtils.copyProperties(menuDO, vo);
			opsSystemMenuService.delete(vo.getId());;
			rsBody.setCode(Status.Bu_SUCCESS.getName());
			rsBody.setMessage(Status.Bu_SUCCESS.getValue());
			//权限缓存修改
			 roleMenuService.queryByRoleMenuList();
			return responseBean;
		} catch (Exception e) {
			 logger.error("删除权限OpsSystemMenuController.delect异常:" + e.getMessage(), e);
			rsBody.setCode(Status.FAILD.getName());
			rsBody.setMessage(Status.FAILD.getValue());
			responseBean = new ResponseBean<>(rh, rsBody); // new ResponseBean(rh, rsBody, Status.FAILD);
			return responseBean;
		}
	}
}
