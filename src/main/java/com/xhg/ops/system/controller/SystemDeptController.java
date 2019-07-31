package com.xhg.ops.system.controller;

import com.xhg.core.web.vo.RequestBean;
import com.xhg.core.web.vo.ResponseBean;
import com.xhg.core.web.vo.RsBody;
import com.xhg.core.web.vo.RsHead;
import com.xhg.ops.system.model.SystemDept;
import com.xhg.ops.system.service.SystemDeptService;
import com.xhg.ops.system.vo.SystemDeptVO;
import com.xhg.ops.system.vo.dept.SystemDeptPositionVO;
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
@RequestMapping(value = "/ops/system/dept")
@Api(value = "ops系统部门接口",description = "部门管理-曾庆财")
public class SystemDeptController {

    @Autowired
    private SystemDeptService systemDeptService;

    @ApiOperation(value = "部门列表")
    @RequestMapping(value = "/dept-list", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean<List<SystemDeptVO>> querySystemUserList(@RequestBody RequestBean requestBean)
            throws Exception{
        List<SystemDeptVO> result = new ArrayList<>();
        RsHead rh = new RsHead();
        List<SystemDept> list = systemDeptService.queryAllList(null);
        BeanToVOBean(list,result);
        RsBody<List<SystemDeptVO>> rsBody = new RsBody<>();
        rsBody.setData(result);
        rsBody.setCode("1");
        rsBody.setMessage("查询部门列表表成功");
        ResponseBean<List<SystemDeptVO>> responseBean = new ResponseBean<List<SystemDeptVO>>(rh,rsBody);
        return responseBean;

    }

    @ApiOperation(value = "部门职位列表")
    @RequestMapping(value = "/dept-position-list", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean<List<SystemDeptPositionVO>> querySystemUserListTest(@RequestBody RequestBean requestBean)
            throws Exception{
        RsHead rh = new RsHead();
        List<SystemDeptPositionVO> list = systemDeptService.queryDeptPositions();

        RsBody<List<SystemDeptPositionVO>> rsBody = new RsBody<>();
        rsBody.setData(list);
        rsBody.setCode("1");
        rsBody.setMessage("部门职位列表成功");
        ResponseBean<List<SystemDeptPositionVO>> responseBean = new ResponseBean<>(rh,rsBody);
        return responseBean;

    }

    private void BeanToVOBean(List<SystemDept> systemDepts, List<SystemDeptVO> systemDeptVOS){

        if(systemDepts!=null&&systemDepts.size()>0){
            systemDepts.forEach(dept->{
                SystemDeptVO vo = new SystemDeptVO();
                vo.setDeptId(dept.getId().toString());
                vo.setDeptName(dept.getName());
                systemDeptVOS.add(vo);
            });
        }

    }
}
