package com.xhg.ops.system.controller;

import com.xhg.core.web.vo.*;
import com.xhg.ops.common.ComConstant;
import com.xhg.ops.common.ComService;
import com.xhg.ops.system.model.SystemUser;
import com.xhg.ops.system.service.LoginService;
import com.xhg.ops.system.service.SystemUserService;
import com.xhg.ops.system.vo.image.ImageVO;
import com.xhg.ops.system.vo.user.SystemAppUserViewVO;
import com.xhg.ops.system.vo.user.SystemUserLoadVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Controller
@RequestMapping(value = "/ops/system/user")
@Api(value = "ops系统app用户接口",description = "app用户模块管理-曾庆财")
public class SystemUserAppController {

    private Logger logger = LoggerFactory.getLogger(SystemUserController.class);
    @Autowired
    private SystemUserService systemUserService;

    @Autowired
    private ComService comService;

    @Autowired
    private LoginService loginService;

    @ApiOperation(value = "app端加载用户基本信息")
    @RequestMapping(value = "/user-app-info", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean<SystemAppUserViewVO> appQuerySystemUser(@RequestBody RequestBean requestBean)
            throws Exception{
        SystemUser systemUser = new SystemUser();
        SystemUser temp = getSystemUser(requestBean.getRequestHead());
        if(temp==null){
            RsHead rh = new RsHead();
            RsBody<SystemAppUserViewVO> rsBody = new RsBody<>();
            rsBody.setData(null);
            rsBody.setMessage("获取用户信息失败!");
            rsBody.setCode("-1");
            ResponseBean<SystemAppUserViewVO> responseBean = new ResponseBean<SystemAppUserViewVO>(rh,rsBody);
            return responseBean;
        }
        RsHead rh = new RsHead();
        systemUser.setId(temp.getId());
        SystemAppUserViewVO result = systemUserService.querySystemAppUserViewVO(systemUser);
        RsBody<SystemAppUserViewVO> rsBody = new RsBody<>();
        rsBody.setData(result);
        ResponseBean<SystemAppUserViewVO> responseBean = new ResponseBean<SystemAppUserViewVO>(rh,rsBody);
        return responseBean;

    }

    @ApiOperation(value = "app端修改用户密码")
    @RequestMapping(value = "/user-app-update-password", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean<Map<String,Object>> appModifySystemUserPassword(@RequestBody RequestBean<SystemUserLoadVO> requestBean)
            throws Exception{

        String oldPassword = requestBean.getRequestBody().getData().getOldPassword();
        String newPassword = requestBean.getRequestBody().getData().getPassword();
        RsBody<Map<String,Object>> rsBody = new RsBody<>();
        if(oldPassword==null||"".equals(oldPassword)||newPassword==null||"".equals(newPassword)) {
            rsBody.setCode("-1");
            rsBody.setMessage("密码不能为空值！");
        }else{
            if (validatePassword(newPassword)) {
                SystemUser tmp =  getSystemUser(requestBean.getRequestHead());
                if(tmp == null){
                    rsBody.setCode("-1");
                    rsBody.setMessage("获取用户信息失败!");
                }else {
                    SystemUser systemUser = new SystemUser();
                    systemUser.setId(tmp.getId());
                    systemUser.setPassword(requestBean.getRequestBody().getData().getPassword());
                    Map<String,Object> result = systemUserService.updateModifySystemUserPassword(systemUser, oldPassword);
                    rsBody.setCode("1".equals(result.get("code").toString())?"1":"-1");
                    rsBody.setMessage("1".equals(result.get("code").toString())?"密码修改成功!":"旧密码输入错误!");
                }
            } else {
                rsBody.setCode("-1");
                rsBody.setMessage("请输入正确格式的密码！");
            }
        }
        RsHead rh = new RsHead();
        ResponseBean<Map<String,Object>> responseBean = new ResponseBean<Map<String,Object>>(rh,rsBody);
        return responseBean;

    }



    @RequestMapping(value = "/user-app-upload-img",method = RequestMethod.POST)
    @ApiOperation(value = "图片上传接口")
    @ResponseBody
    public ResponseBean<ImageVO> uploadImg(@RequestParam("editormd-image-file") MultipartFile multipartFile) throws Exception{

        ResponseBean<ImageVO> responseBean=new ResponseBean<>();
        RsBody<ImageVO> rsBody=comService.uploadImg(multipartFile);
        responseBean.setResponseBody(rsBody);
        return responseBean;
    }

    /**
     * 校验输入密码的正确格式
     * @param password
     * @return
     */
    private boolean validatePassword(String password){
        //TODO
        return password.matches(ComConstant.PASSWORD_FORMAT2);
    }

    private SystemUser getSystemUser(RqHead rqHead){
        logger.info("==========获取当前app用户==========");
        SystemUser systemUser = loginService.redisUser(rqHead);
        if(systemUser==null)
            return null;
        if(systemUser.getId()==null)
            return null;
        logger.info("==========当前app用户："+systemUser.getName()+"==========");
        return systemUser;
    }


}
