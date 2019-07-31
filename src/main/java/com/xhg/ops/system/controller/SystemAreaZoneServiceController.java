package com.xhg.ops.system.controller;

import com.xhg.core.web.vo.*;
import com.xhg.ops.system.dto.SystemAreaDTO;
import com.xhg.ops.system.model.CyclingAreaDO;
import com.xhg.ops.system.model.SystemAreaZone;
import com.xhg.ops.system.service.SystemAreaDistrictService;
import com.xhg.ops.system.service.SystemAreaZoneService;
import com.xhg.ops.system.vo.areazone.SystemAreaListVO;
import com.xhg.ops.system.vo.areazone.SystemAreaZoneServiceVO;
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
@RequestMapping(value = "/ops/system/area")
@Api(value = "ops系统区域接口",description = "区域管理-曾庆财")
public class SystemAreaZoneServiceController {

    @Autowired
    private SystemAreaZoneService systemAreaZoneService;

    @Autowired
    private SystemAreaDistrictService systemAreaDistrictService;



    @ApiOperation(value = "区域列表",notes = "下拉框使用")
    @RequestMapping(value = "/area-list", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean<List<SystemAreaZoneServiceVO>> querySystemUserList(@RequestBody RequestBean requestBean)
            throws Exception{
        List<SystemAreaZoneServiceVO> result = new ArrayList<>();
        RsHead rh = new RsHead();
        List<SystemAreaZone> list = systemAreaZoneService.queryAllList(null);
        BeanToVOBean(list,result);
        RsBody<List<SystemAreaZoneServiceVO>> rsBody = new RsBody<>();
        rsBody.setData(result);
        rsBody.setCode("1");
        rsBody.setMessage("查询区域表成功");
        ResponseBean<List<SystemAreaZoneServiceVO>> responseBean = new ResponseBean<>(rh,rsBody);
        return responseBean;

    }

    @RequestMapping(value = "/query-area-info",method = RequestMethod.POST)
    @ApiOperation(value = "全国地区列表查询")
    @ResponseBody
    public ResponseBean<List<CyclingAreaDO>> queryAreaInfo(@RequestBody RequestBean requestBean){
        ResponseBean<List<CyclingAreaDO>> responseBean=new ResponseBean<>();
        RsBody<List<CyclingAreaDO>> rsBody=new RsBody<>();
        //rsBody.setData(systemAreaDistrictService.queryStaticsList());
        responseBean.setResponseBody(rsBody);
        responseBean.setResult(Status.SUCCESS);
        return responseBean;
    }


    @RequestMapping(value = "/query-province",method = RequestMethod.POST)
    @ApiOperation(value = "省级列表查询")
    @ResponseBody
    public ResponseBean<List<SystemAreaListVO>> queryProvinces(@RequestBody RequestBean requestBean){
        List<SystemAreaListVO> result = new ArrayList<>();

        ResponseBean<List<SystemAreaListVO>> responseBean=new ResponseBean<>();
        RsBody<List<SystemAreaListVO>> rsBody=new RsBody<>();
        CyclingAreaDO cyclingAreaDO = new CyclingAreaDO();
        cyclingAreaDO.setNodeLevel(0);
        List<CyclingAreaDO> list = systemAreaDistrictService.queryAllList(cyclingAreaDO);
        list.stream().forEach(area->{
            SystemAreaListVO systemAreaListVO = new SystemAreaListVO();
            systemAreaListVO.setAreaCode(area.getAreaCode());
            systemAreaListVO.setAreaName(area.getAreaName());
            result.add(systemAreaListVO);
        });
        rsBody.setData(result);
        responseBean.setResponseBody(rsBody);
        responseBean.setResult(Status.SUCCESS);
        return responseBean;
    }

    @RequestMapping(value = "/query-city",method = RequestMethod.POST)
    @ApiOperation(value = "市级列表查询")
    @ResponseBody
    public ResponseBean<List<SystemAreaListVO>> queryCitys(@RequestBody RequestBean<SystemAreaDTO> requestBean){
        SystemAreaDTO systemAreaDTO = requestBean.getRequestBody().getData();
        ResponseBean<List<SystemAreaListVO>> responseBean=new ResponseBean<>();
        RsBody<List<SystemAreaListVO>> rsBody=new RsBody<>();
        rsBody.setData(systemAreaDistrictService.queryAllByParentId(systemAreaDTO.getAreaCode()));
        responseBean.setResponseBody(rsBody);
        responseBean.setResult(Status.SUCCESS);
        return responseBean;
    }

    @RequestMapping(value = "/query-area",method = RequestMethod.POST)
    @ApiOperation(value = "区级列表查询")
    @ResponseBody
    public ResponseBean<List<SystemAreaListVO>> queryAreas(@RequestBody RequestBean<SystemAreaDTO> requestBean){
        SystemAreaDTO systemAreaDTO = requestBean.getRequestBody().getData();
        ResponseBean<List<SystemAreaListVO>> responseBean=new ResponseBean<>();
        RsBody<List<SystemAreaListVO>> rsBody=new RsBody<>();
        rsBody.setData(systemAreaDistrictService.queryAllByParentId(systemAreaDTO.getAreaCode()));
        responseBean.setResponseBody(rsBody);
        responseBean.setResult(Status.SUCCESS);
        return responseBean;
    }

    @RequestMapping(value = "/query-street",method = RequestMethod.POST)
    @ApiOperation(value = "街道列表查询")
    @ResponseBody
    public ResponseBean<List<SystemAreaListVO>> queryStreets(@RequestBody RequestBean<SystemAreaDTO> requestBean){
        SystemAreaDTO systemAreaDTO = requestBean.getRequestBody().getData();
        ResponseBean<List<SystemAreaListVO>> responseBean=new ResponseBean<>();
        RsBody<List<SystemAreaListVO>> rsBody=new RsBody<>();
        rsBody.setData(systemAreaDistrictService.queryAllByParentId(systemAreaDTO.getAreaCode()));
        responseBean.setResponseBody(rsBody);
        responseBean.setResult(Status.SUCCESS);
        return responseBean;
    }


    private void BeanToVOBean(List<SystemAreaZone> systemAreaZones,List<SystemAreaZoneServiceVO> systemAreaZoneServiceVOS){

        if(systemAreaZones!=null&&systemAreaZones.size()>0){
            systemAreaZones.stream().forEach(zone->{
                SystemAreaZoneServiceVO vo = new SystemAreaZoneServiceVO();
                vo.setAreaId(zone.getId().toString());
                vo.setAreaName(zone.getName());
                systemAreaZoneServiceVOS.add(vo);
            });
        }
    }

}
