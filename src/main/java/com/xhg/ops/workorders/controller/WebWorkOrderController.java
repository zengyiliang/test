package com.xhg.ops.workorders.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhg.core.web.vo.PagerResult;
import com.xhg.core.web.vo.RequestBean;
import com.xhg.core.web.vo.ResponseBean;
import com.xhg.core.web.vo.RsBody;
import com.xhg.core.web.vo.Status;
import com.xhg.ops.workorders.dto.WebWorkOrderPlatformQueryDTO;
import com.xhg.ops.workorders.dto.WebWorkOrderQueryDTO;
import com.xhg.ops.workorders.dto.WorkOrderBaseDTO;
import com.xhg.ops.workorders.service.WebWorkOrderService;
import com.xhg.ops.workorders.vo.WebWorkOrderDealMaterialVo;
import com.xhg.ops.workorders.vo.WebWorkOrderDealRecordVo;
import com.xhg.ops.workorders.vo.WebWorkOrderDetailShowVo;
import com.xhg.ops.workorders.vo.WebWorkOrderDetailVo;
import com.xhg.ops.workorders.vo.WebWorkOrderPlatformListVo;
import com.xhg.ops.workorders.vo.WebWorkOrderPlatformStaticsVo;
import com.xhg.ops.workorders.vo.WebWorkOrderProgressVo;
import com.xhg.ops.workorders.vo.WebWorkOrderVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/ops/web/workOrder")
@Api(value = "web后台工单接口",description = "web后台工单接口-区涛")
public class WebWorkOrderController {

    @Autowired
    private WebWorkOrderService webWorkOrderService;
	
    @ApiOperation(value = "点击工单详情")
    @PostMapping(value = "/detail")
    @ResponseBody
    public ResponseBean<WebWorkOrderDetailShowVo> detail(@RequestBody RequestBean<WorkOrderBaseDTO> requestBean) {
    	WorkOrderBaseDTO dto = requestBean.getRequestBody().getData();
    	WebWorkOrderDetailShowVo vo = webWorkOrderService.detail(dto);
        RsBody<WebWorkOrderDetailShowVo> rsBody = new RsBody<>();
		rsBody.setData(vo);
		ResponseBean<WebWorkOrderDetailShowVo> responseBean = new ResponseBean<>();
        responseBean.setResponseBody(rsBody);
        responseBean.setResult(Status.SUCCESS);
        return responseBean;
    }
    
    @ApiOperation(value = "工单详情-基本信息")
    @PostMapping(value = "/baseInfo")
    @ResponseBody
    public ResponseBean<WebWorkOrderDetailVo> baseInfo(@RequestBody RequestBean<WorkOrderBaseDTO> requestBean) {
    	WorkOrderBaseDTO dto = requestBean.getRequestBody().getData();
    	WebWorkOrderDetailVo vo = webWorkOrderService.baseInfo(dto);
        RsBody<WebWorkOrderDetailVo> rsBody = new RsBody<>();
		rsBody.setData(vo);
		ResponseBean<WebWorkOrderDetailVo> responseBean = new ResponseBean<>();
        responseBean.setResponseBody(rsBody);
        responseBean.setResult(Status.SUCCESS);
        return responseBean;
    }
    
    @ApiOperation(value = "工单详情-工单进度")
    @PostMapping(value = "/progress")
    @ResponseBody
    public ResponseBean<List<WebWorkOrderProgressVo>> progress(@RequestBody RequestBean<WorkOrderBaseDTO> requestBean) {
    	WorkOrderBaseDTO dto = requestBean.getRequestBody().getData();
    	List<WebWorkOrderProgressVo> voList = webWorkOrderService.progress(dto);
        RsBody<List<WebWorkOrderProgressVo>> rsBody = new RsBody<>();
		rsBody.setData(voList);
		ResponseBean<List<WebWorkOrderProgressVo>> responseBean = new ResponseBean<>();
        responseBean.setResponseBody(rsBody);
        responseBean.setResult(Status.SUCCESS);
        return responseBean;
    }
    
    @ApiOperation(value = "工作台-工单列表查询")
    @PostMapping(value = "/platformList")
    @ResponseBody
    public ResponseBean<PagerResult<WebWorkOrderPlatformListVo>> platformList(@RequestBody RequestBean<WebWorkOrderPlatformQueryDTO> requestBean) {
    	WebWorkOrderPlatformQueryDTO dto = requestBean.getRequestBody().getData();
    	PagerResult<WebWorkOrderPlatformListVo> pageResult = webWorkOrderService.listPlatformOrderList(dto);
        RsBody<PagerResult<WebWorkOrderPlatformListVo>> rsBody = new RsBody<>();
		rsBody.setData(pageResult);
		ResponseBean<PagerResult<WebWorkOrderPlatformListVo>> responseBean = new ResponseBean<>();
        responseBean.setResponseBody(rsBody);
        responseBean.setResult(Status.SUCCESS);
        return responseBean;
    }

    @ApiOperation(value = "工单查询")
    @PostMapping(value = "/listOrder")
    @ResponseBody
    public ResponseBean<PagerResult<WebWorkOrderVo>> listOrder(@RequestBody RequestBean<WebWorkOrderQueryDTO> requestBean) {
    	WebWorkOrderQueryDTO dto = requestBean.getRequestBody().getData();
    	PagerResult<WebWorkOrderVo> pageResult = webWorkOrderService.listOrder(dto);
        RsBody<PagerResult<WebWorkOrderVo>> rsBody = new RsBody<>();
		rsBody.setData(pageResult);
		ResponseBean<PagerResult<WebWorkOrderVo>> responseBean = new ResponseBean<>();
        responseBean.setResponseBody(rsBody);
        responseBean.setResult(Status.SUCCESS);
        return responseBean;
    }
    
    @ApiOperation(value = "工单详情-工单信息记录")
    @PostMapping(value = "/dealRecord")
    @ResponseBody
    public ResponseBean<WebWorkOrderDealRecordVo> dealRecord(@RequestBody RequestBean<WorkOrderBaseDTO> requestBean) {
    	WorkOrderBaseDTO dto = requestBean.getRequestBody().getData();
    	WebWorkOrderDealRecordVo vo = webWorkOrderService.dealRecord(dto);
        RsBody<WebWorkOrderDealRecordVo> rsBody = new RsBody<>();
		rsBody.setData(vo);
		ResponseBean<WebWorkOrderDealRecordVo> responseBean = new ResponseBean<>();
        responseBean.setResponseBody(rsBody);
        responseBean.setResult(Status.SUCCESS);
        return responseBean;
    }
    
    @ApiOperation(value = "工单详情-物料申请信息")
    @PostMapping(value = "/materialApplyInfo")
    @ResponseBody
    public ResponseBean<List<WebWorkOrderDealMaterialVo>> materialWeblyInfo(@RequestBody RequestBean<WorkOrderBaseDTO> requestBean) {
    	WorkOrderBaseDTO dto = requestBean.getRequestBody().getData();
    	List<WebWorkOrderDealMaterialVo> voList = webWorkOrderService.materialApplyInfoList(dto);
        RsBody<List<WebWorkOrderDealMaterialVo>> rsBody = new RsBody<>();
		rsBody.setData(voList);
		ResponseBean<List<WebWorkOrderDealMaterialVo>> responseBean = new ResponseBean<>();
        responseBean.setResponseBody(rsBody);
        responseBean.setResult(Status.SUCCESS);
        return responseBean;
    }
    
    @ApiOperation(value = "工作台统计")
    @PostMapping(value = "/platformStatics")
    @ResponseBody
    public ResponseBean<WebWorkOrderPlatformStaticsVo> platformStatics(@RequestBody RequestBean<Map<String, Object>> requestBean) {
    	WebWorkOrderPlatformStaticsVo vo = webWorkOrderService.platformStatics();
        RsBody<WebWorkOrderPlatformStaticsVo> rsBody = new RsBody<>();
		rsBody.setData(vo);
		ResponseBean<WebWorkOrderPlatformStaticsVo> responseBean = new ResponseBean<>();
        responseBean.setResponseBody(rsBody);
        responseBean.setResult(Status.SUCCESS);
        return responseBean;
    }

}
