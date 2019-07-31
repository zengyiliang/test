package com.xhg.ops.system.controller;


import com.xhg.core.web.vo.RequestBean;
import com.xhg.core.web.vo.ResponseBean;
import com.xhg.core.web.vo.RsBody;
import com.xhg.core.web.vo.RsHead;
import com.xhg.ops.system.model.SystemPosition;
import com.xhg.ops.system.service.SystemPositonService;
import com.xhg.ops.system.vo.SystemPostionVO;
import com.xhg.ops.system.vo.user.SystemUserIdDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/ops/system/position")
@Api(value = "ops系统职位接口",description = "职位管理-曾庆财")
public class SystemPositionController {

    @Autowired
    private SystemPositonService systemPositonService;


    @ApiOperation(value = "职位列表")
    @RequestMapping(value = "/position-list", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean<List<SystemPostionVO>> querySystemPostionList(@RequestBody RequestBean requestBean)
            throws Exception{
        List<SystemPostionVO> result = new ArrayList<>();
        RsHead rh = new RsHead();
        List<SystemPosition> list = systemPositonService.queryAllList(null);
        BeanToVOBean(list,result);
        RsBody<List<SystemPostionVO>> rsBody = new RsBody<>();
        rsBody.setData(result);
        rsBody.setCode("1");
        rsBody.setMessage("查询职位表成功");
        ResponseBean<List<SystemPostionVO>> responseBean = new ResponseBean<List<SystemPostionVO>>(rh,rsBody);
        return responseBean;

    }


    @ApiOperation(value = "获取等级职位列表")
    @RequestMapping(value = "/position-load", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean<List<Integer>> testquerySystemPostionList(@RequestBody RequestBean<SystemUserIdDTO> requestBean)
            throws Exception{
        RsHead rh = new RsHead();
        Integer userId = requestBean.getRequestBody().getData().getId();
        List<Integer> result =  systemPositonService. queryAllUserIdbyParams(userId);
        RsBody<List<Integer>> rsBody = new RsBody<>();
        rsBody.setData(result);
        rsBody.setCode("1");
        rsBody.setMessage("test");
        ResponseBean<List<Integer>> responseBean = new ResponseBean<>(rh,rsBody);
        return responseBean;

    }

    private void BeanToVOBean(List<SystemPosition> systemPositions,List<SystemPostionVO> systemPostionVOS){
        if(systemPositions!=null&&systemPositions.size()>0){
            systemPositions.forEach(position->{
                SystemPostionVO vo = new SystemPostionVO();
                vo.setPositionId(position.getId().toString());
                vo.setPositionName(position.getName());
                systemPostionVOS.add(vo);
            });
        }
    }
}
