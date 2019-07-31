package com.xhg.ops.system.service;

import com.xhg.core.web.vo.RequestBean;
import com.xhg.core.web.vo.ResponseBean;
import com.xhg.core.web.vo.RqHead;
import com.xhg.ops.system.model.SystemUser;
import com.xhg.ops.system.vo.login.LoginReqVo;
import com.xhg.ops.system.vo.login.LoginRespVo;
import com.xhg.ops.system.vo.login.LogoutReqVo;
import com.xhg.ops.system.vo.login.LogoutRespVo;
import com.xhg.ops.system.vo.login.RefreshTokenRespVo;

public interface LoginService {

    public ResponseBean<LoginRespVo> login(RequestBean<LoginReqVo> requestBean, Integer type);

    public ResponseBean<LogoutRespVo> logout(RequestBean<LogoutReqVo> requestBean, Integer type);
    
    public SystemUser redisUser(RqHead rqHead );
    /**
     * 刷新token
     *
     * @param token
     * @return
     */
    public ResponseBean<RefreshTokenRespVo> refreshToken(String token, String deviceId) ;

}
