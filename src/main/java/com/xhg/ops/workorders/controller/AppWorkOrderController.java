package com.xhg.ops.workorders.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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
import com.xhg.ops.workorders.dto.AppWorkOrderMessageQueryDTO;
import com.xhg.ops.workorders.dto.AppWorkOrderMessageUpdateDTO;
import com.xhg.ops.workorders.dto.AppWorkOrderQueryDTO;
import com.xhg.ops.workorders.dto.WorkOrderBaseDTO;
import com.xhg.ops.workorders.service.AppWorkOrderService;
import com.xhg.ops.workorders.vo.AppWorkOrderDealMaterialVo;
import com.xhg.ops.workorders.vo.AppWorkOrderDealRecordVo;
import com.xhg.ops.workorders.vo.AppWorkOrderDetailVo;
import com.xhg.ops.workorders.vo.AppWorkOrderMessageVo;
import com.xhg.ops.workorders.vo.AppWorkOrderProgressVo;
import com.xhg.ops.workorders.vo.AppWorkOrderVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/ops/app/workOrder")
@Api(value = "app端工单接口",description = "app端工单接口-区涛")
public class AppWorkOrderController {

    @Autowired
    private AppWorkOrderService appWorkOrderService;

    @ApiOperation(value = "工单消息")
    @PostMapping(value = "/message")
    @ResponseBody
    public ResponseBean<PagerResult<AppWorkOrderMessageVo>> message(@RequestBody RequestBean<AppWorkOrderMessageQueryDTO> requestBean) {
    	AppWorkOrderMessageQueryDTO dto = requestBean.getRequestBody().getData();
        PagerResult<AppWorkOrderMessageVo> pageResult = appWorkOrderService.message(dto);
        RsBody<PagerResult<AppWorkOrderMessageVo>> rsBody = new RsBody<>();
		rsBody.setData(pageResult);
		ResponseBean<PagerResult<AppWorkOrderMessageVo>> responseBean = new ResponseBean<>();
        responseBean.setResponseBody(rsBody);
        responseBean.setResult(Status.SUCCESS);
        return responseBean;
    }
    
    @ApiOperation(value = "已读消息")
    @PostMapping(value = "/readMessage")
    @ResponseBody
    public ResponseBean<Map<String, Object>> readMessage(@Valid @RequestBody RequestBean<AppWorkOrderMessageUpdateDTO> requestBean) {
    	AppWorkOrderMessageUpdateDTO dto = requestBean.getRequestBody().getData();
		RsBody<Map<String, Object>> rsBody = appWorkOrderService.readMessage(dto);
		ResponseBean<Map<String, Object>> responseBean = new ResponseBean<>();
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
	
    @ApiOperation(value = "工单详情")
    @PostMapping(value = "/detail")
    @ResponseBody
    public ResponseBean<AppWorkOrderDetailVo> detail(@Valid @RequestBody RequestBean<WorkOrderBaseDTO> requestBean) {
    	WorkOrderBaseDTO dto = requestBean.getRequestBody().getData();
    	AppWorkOrderDetailVo vo = appWorkOrderService.detail(dto);
        RsBody<AppWorkOrderDetailVo> rsBody = new RsBody<>();
		rsBody.setData(vo);
		ResponseBean<AppWorkOrderDetailVo> responseBean = new ResponseBean<>();
        responseBean.setResponseBody(rsBody);
        responseBean.setResult(Status.SUCCESS);
        return responseBean;
    }
    
    @ApiOperation(value = "工单进度")
    @PostMapping(value = "/progress")
    @ResponseBody
    public ResponseBean<List<AppWorkOrderProgressVo>> progress(@Valid @RequestBody RequestBean<WorkOrderBaseDTO> requestBean) {
    	WorkOrderBaseDTO dto = requestBean.getRequestBody().getData();
    	List<AppWorkOrderProgressVo> voList = appWorkOrderService.progress(dto);
        RsBody<List<AppWorkOrderProgressVo>> rsBody = new RsBody<>();
		rsBody.setData(voList);
		ResponseBean<List<AppWorkOrderProgressVo>> responseBean = new ResponseBean<>();
        responseBean.setResponseBody(rsBody);
        responseBean.setResult(Status.SUCCESS);
        return responseBean;
    }

    @ApiOperation(value = "工单查询")
    @PostMapping(value = "/listOrder")
    @ResponseBody
    public ResponseBean<PagerResult<AppWorkOrderVo>> listOrder(@Valid @RequestBody RequestBean<AppWorkOrderQueryDTO> requestBean) {
    	AppWorkOrderQueryDTO dto = requestBean.getRequestBody().getData();
    	PagerResult<AppWorkOrderVo> pageResult = appWorkOrderService.listOrder(dto);
        RsBody<PagerResult<AppWorkOrderVo>> rsBody = new RsBody<>();
		rsBody.setData(pageResult);
		ResponseBean<PagerResult<AppWorkOrderVo>> responseBean = new ResponseBean<>();
        responseBean.setResponseBody(rsBody);
        responseBean.setResult(Status.SUCCESS);
        return responseBean;
    }
    
    @ApiOperation(value = "工单处理信息")
    @PostMapping(value = "/dealRecord")
    @ResponseBody
    public ResponseBean<AppWorkOrderDealRecordVo> dealRecord(@Valid @RequestBody RequestBean<WorkOrderBaseDTO> requestBean) {
    	WorkOrderBaseDTO dto = requestBean.getRequestBody().getData();
    	AppWorkOrderDealRecordVo vo = appWorkOrderService.dealRecord(dto);
        RsBody<AppWorkOrderDealRecordVo> rsBody = new RsBody<>();
		rsBody.setData(vo);
		ResponseBean<AppWorkOrderDealRecordVo> responseBean = new ResponseBean<>();
        responseBean.setResponseBody(rsBody);
        responseBean.setResult(Status.SUCCESS);
        return responseBean;
    }
    
    @ApiOperation(value = "物料申请信息")
    @PostMapping(value = "/materialApplyInfo")
    @ResponseBody
    public ResponseBean<List<AppWorkOrderDealMaterialVo>> materialApplyInfo(@Valid @RequestBody RequestBean<WorkOrderBaseDTO> requestBean) {
    	WorkOrderBaseDTO dto = requestBean.getRequestBody().getData();
    	List<AppWorkOrderDealMaterialVo> voList = appWorkOrderService.materialApplyInfoList(dto);
        RsBody<List<AppWorkOrderDealMaterialVo>> rsBody = new RsBody<>();
		rsBody.setData(voList);
		ResponseBean<List<AppWorkOrderDealMaterialVo>> responseBean = new ResponseBean<>();
        responseBean.setResponseBody(rsBody);
        responseBean.setResult(Status.SUCCESS);
        return responseBean;
    }
    
    

}
