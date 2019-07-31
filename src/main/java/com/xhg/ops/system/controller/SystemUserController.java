package com.xhg.ops.system.controller;

import java.util.HashMap;
import java.util.Map;

import com.xhg.core.web.vo.*;
import com.xhg.ops.system.vo.user.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhg.ops.common.ComConstant;
import com.xhg.ops.system.dto.SystemUserAddDTO;
import com.xhg.ops.system.dto.SystemUserQueryDTO;
import com.xhg.ops.system.model.SystemUser;
import com.xhg.ops.system.service.LoginService;
import com.xhg.ops.system.service.SystemUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/ops/system/user")
@Api(value = "ops系统web用户接口",description = "web用户模块管理-曾庆财")
public class SystemUserController {

    private Logger logger = LoggerFactory.getLogger(SystemUserController.class);

    @Autowired
    private SystemUserService systemUserService;

    @Autowired
    private LoginService loginService;


    @ApiOperation(value = "用户列表分页")
    @RequestMapping(value = "/user-list", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean<PagerResult<SystemUserQueryVO>> querySystemUserList(@RequestBody RequestBean<SystemUserQueryDTO> requestBean)
            throws Exception{

        RsHead rh = new RsHead();
        SystemUserQueryDTO dto = requestBean.getRequestBody().getData();
        PagerResult<SystemUserQueryVO> result = systemUserService.queryListPage(dto);
        RsBody<PagerResult<SystemUserQueryVO>> rsBody = new RsBody<>();
        rsBody.setData(result);
        ResponseBean<PagerResult<SystemUserQueryVO>> responseBean = new ResponseBean<>(rh,rsBody);
        return responseBean;

    }

    @ApiOperation(value = "添加用户")
    @RequestMapping(value = "/user-add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean<Map<String,Object>> saveSystemUser(@RequestBody RequestBean<SystemUserAddDTO> requestBean)
            throws Exception{

        RsBody<Map<String, Object>> rsBody = new RsBody<>();
        RsHead rh = new RsHead();
        SystemUserAddDTO dto = requestBean.getRequestBody().getData();
        //工号，手机号的长度校验
        SystemUser checkUser = new SystemUser();
        checkUser.setUserNo(dto.getUserNo());
        checkUser.setPhone(dto.getPhone());
        Map<String,Object> checkMap =  checkSystemUser(checkUser);
        if(checkMap == null) {
            String phone = dto.getPhone();
            if (!"".equals(phone) && phone.matches(ComConstant.PHONE_FORMAT)) {
                SystemUser systemUser = getSystemUser(requestBean.getRequestHead());
                if (systemUser != null)
                    dto.setId(systemUser.getId());
                Map<String, Object> map = systemUserService.saveSystemUser(dto);
                if (map.get("message") != null) {
                    rsBody.setCode("-1");
                    rsBody.setMessage(map.get("message").toString());
                } else
                    rsBody.setData(map);
            } else {
                rsBody.setCode("-1");
                rsBody.setMessage("请输入正确的手机号码！");
            }
        }else{
            rsBody.setMessage(checkMap.get("message").toString());
            rsBody.setCode("-1");
        }
        ResponseBean<Map<String,Object>> responseBean = new ResponseBean<Map<String,Object>>(rh,rsBody);
        return responseBean;

    }


    @ApiOperation(value = "加载用户信息")
    @RequestMapping(value = "/user-load", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean<SystemUserEditVO> loadSystemUserById(@RequestBody RequestBean<SystemUserIdDTO> requestBean)
            throws Exception{

        RsHead rh = new RsHead();
        Integer id = requestBean.getRequestBody().getData().getId();
        SystemUserEditVO systemUserEditVO = systemUserService.loadSystemUserById(id);
        RsBody<SystemUserEditVO> rsBody = new RsBody<>();
        rsBody.setData(systemUserEditVO);
        ResponseBean<SystemUserEditVO> responseBean = new ResponseBean<SystemUserEditVO>(rh,rsBody);
        return responseBean;

    }

    @ApiOperation(value = "修改用户")
    @RequestMapping(value = "/user-update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean<Map<String,Object>> updateSystemUser(@RequestBody RequestBean<SystemUserEditVO> requestBean)
            throws Exception{

        RsBody<Map<String, Object>> rsBody = new RsBody<>();
        RsHead rh = new RsHead();
        SystemUserEditVO systemUserEditVO = requestBean.getRequestBody().getData();
        //工号，手机号的长度校验
        SystemUser checkUser = new SystemUser();
        checkUser.setUserNo(systemUserEditVO.getUserNo());
        checkUser.setPhone(systemUserEditVO.getPhone());
        Map<String,Object> checkMap =  checkSystemUser(checkUser);
        if(checkMap == null) {
            String phone = systemUserEditVO.getPhone();
            if(!"".equals(phone) && phone.matches(ComConstant.PHONE_FORMAT)) {
                Integer currentUserId = null;
                SystemUser systemUser = getSystemUser(requestBean.getRequestHead());
                if(systemUser!=null)
                    currentUserId = systemUser.getId();
                Map<String,Object> map = systemUserService.updateSystemUser(systemUserEditVO,currentUserId);
                if(map.get("message")!=null){
                    rsBody.setCode("-1");
                    rsBody.setMessage(map.get("message").toString());
                }else {
                    rsBody.setCode("1");
                    rsBody.setMessage("修改用户成功！");
                }
            }else {
                rsBody.setCode("-1");
                rsBody.setMessage("请输入正确的手机号码！");
            }
        }else{
            rsBody.setMessage(checkMap.get("message").toString());
            rsBody.setCode("-1");
        }

        ResponseBean<Map<String,Object>> responseBean = new ResponseBean<Map<String,Object>>(rh,rsBody);
        return responseBean;

    }

    @ApiOperation(value = "重置用户密码",notes = "参数 id：14")
    @RequestMapping(value = "/user-reset", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean<Map<String,Object>> reSetSystemUserPassword(@RequestBody RequestBean<SystemUserIdDTO> requestBean)
            throws Exception{

        RsHead rh = new RsHead();
        Integer id = requestBean.getRequestBody().getData().getId();
        Integer currentUserId = null;
        SystemUser systemUser = getSystemUser(requestBean.getRequestHead());
        if(systemUser!=null)
            currentUserId = systemUser.getId();
        Map<String,Object> map = systemUserService.updateResetSystemUserPassword(id,currentUserId);
        RsBody<Map<String,Object>> rsBody = new RsBody<>();
        rsBody.setData(map);
        ResponseBean<Map<String,Object>> responseBean = new ResponseBean<Map<String,Object>>(rh,rsBody);
        return responseBean;

    }

    @ApiOperation(value = "查看用户详情",notes = "参数 id：14")
    @RequestMapping(value = "/user-view", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean<SystemUserViewVO> viewSystemUserById(@RequestBody RequestBean<SystemUserIdDTO> requestBean)
            throws Exception{

        RsHead rh = new RsHead();
        Integer id = requestBean.getRequestBody().getData().getId();
        SystemUserViewVO systemUserViewVO = systemUserService.viewSystemUserById(id);
        RsBody<SystemUserViewVO> rsBody = new RsBody<>();
        rsBody.setData(systemUserViewVO);
        ResponseBean<SystemUserViewVO> responseBean = new ResponseBean<SystemUserViewVO>(rh,rsBody);
        return responseBean;

    }

    @ApiOperation(value = "删除用户",notes = "参数 id：14")
    @RequestMapping(value = "/user-delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean deleteSystemUser(@RequestBody RequestBean<SystemUserIdDTO> requestBean)
            throws Exception{

        RsHead rh = new RsHead();
        Integer id = requestBean.getRequestBody().getData().getId();
        Map<String, Object> result = systemUserService.deleteSystemUser(id);
        RsBody<Map<String,Object>> rsBody = new RsBody<>();
        rsBody.setMessage(result.get("message").toString());
        rsBody.setCode(result.get("code").toString());
        ResponseBean<Map<String,Object>> responseBean = new ResponseBean<Map<String,Object>>(rh,rsBody);
        return responseBean;

    }

    @ApiOperation(value = "加载申请人信息-布点系统")
    @RequestMapping(value = "/applicant-view", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean<SystemApplicantVO> loadApplicationUser(@RequestBody RequestBean requestBean)
            throws Exception{
        RsBody<SystemApplicantVO> rsBody = new RsBody<>();
        RsHead rh = new RsHead();
        SystemUser systemUser = getSystemUser(requestBean.getRequestHead());
        if(systemUser==null){
            rsBody.setMessage("用户加载失败！");
            rsBody.setCode("-1");
        }else {
            SystemApplicantVO systemApplicantVO = systemUserService.loadSystemApplicantVO(systemUser);
            rsBody.setData(systemApplicantVO);
        }
        ResponseBean<SystemApplicantVO> responseBean = new ResponseBean<>(rh,rsBody);
        return responseBean;

    }

    /**
     * 手机、工号格式校验
     * @param systemUser
     * @return
     */
    private Map<String,Object> checkSystemUser(SystemUser systemUser){
        Map<String,Object> map = new HashMap<>();
        if(!systemUser.getPhone().matches(ComConstant.PHONE_FORMAT_NUM)){
            map.put("message","请输入正确格式的手机号");
            return map;
        }
        if(systemUser.getPhone().length()>11){
            map.put("message","请输入长度限制11个字符手机号");
            return map;
        }
        if(systemUser.getUserNo().length()>20) {
            map.put("message", "请输入长度限制20个字符工号");
            return map;
        }
        return null;
    }


    /**
     * 获取当前用户
     * @param rqHead
     * @return
     */
    private SystemUser getSystemUser(RqHead rqHead){
        logger.info("===============获取当前web端用户====================");
        SystemUser systemUser = loginService.redisUser(rqHead);
        if(systemUser==null)
            return null;
        if(systemUser.getId()==null)
            return null;
        logger.info("===============当前web端用户："+systemUser.getName()+"==========");
        return systemUser;
    }



}
