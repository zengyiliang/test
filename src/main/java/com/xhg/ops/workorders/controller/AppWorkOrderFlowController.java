package com.xhg.ops.workorders.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhg.core.web.vo.RequestBean;
import com.xhg.core.web.vo.ResponseBean;
import com.xhg.core.web.vo.RsBody;
import com.xhg.core.web.vo.Status;
import com.xhg.ops.workorders.dto.AppWorkOrderDealMaterialDTO;
import com.xhg.ops.workorders.dto.AppWorkOrderDealRecordDTO;
import com.xhg.ops.workorders.dto.AppWorkOrderFlowAssignConfirmDTO;
import com.xhg.ops.workorders.dto.AppWorkOrderFlowAssignRejectDTO;
import com.xhg.ops.workorders.dto.AppWorkOrderFlowDepartRejectDTO;
import com.xhg.ops.workorders.dto.AppWorkOrderFlowFirstReviewCloseDTO;
import com.xhg.ops.workorders.dto.AppWorkOrderFlowFirstReviewPassDTO;
import com.xhg.ops.workorders.dto.AppWorkOrderFlowSignInDTO;
import com.xhg.ops.workorders.dto.AppWorkOrderFlowSignRejectDTO;
import com.xhg.ops.workorders.dto.WorkOrderBaseDTO;
import com.xhg.ops.workorders.service.AppWorkOrderFlowService;
import com.xhg.ops.workorders.vo.AppWorkOrderAssignPageVo;
import com.xhg.ops.workorders.vo.AppWorkOrderDealMaterialPageVo;
import com.xhg.ops.workorders.vo.AppWorkOrderDealRecordPageVo;
import com.xhg.ops.workorders.vo.AppWorkOrderFirstReviewPageVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/ops/app/workFlow")
@Api(value = "app端工单流程操作接口",description = "app端工单流程操作接口-区涛")
public class AppWorkOrderFlowController {

    @Autowired
    private AppWorkOrderFlowService appWorkOrderFlowService;

    @ApiOperation(value = "工单初审-初审页面")
    @PostMapping(value = "/firstReview/page")
    @ResponseBody
    public ResponseBean<AppWorkOrderFirstReviewPageVo> firstReviewPage(@Valid @RequestBody RequestBean<WorkOrderBaseDTO> requestBean) throws Exception {
    	WorkOrderBaseDTO dto = requestBean.getRequestBody().getData();
    	AppWorkOrderFirstReviewPageVo vo = appWorkOrderFlowService.firstReviewPage(dto);
        RsBody<AppWorkOrderFirstReviewPageVo> rsBody = new RsBody<>();
		rsBody.setData(vo);
		ResponseBean<AppWorkOrderFirstReviewPageVo> responseBean = new ResponseBean<>();
        responseBean.setResponseBody(rsBody);
        responseBean.setResult(Status.SUCCESS);
        return responseBean;
    }
    
    @ApiOperation(value = "工单初审-初审通过")
    @PostMapping(value = "/firstReview/pass")
    @ResponseBody
    public ResponseBean<Map<String, Object>> firstReviewPass(@Valid @RequestBody RequestBean<AppWorkOrderFlowFirstReviewPassDTO> requestBean) {
        AppWorkOrderFlowFirstReviewPassDTO dto = requestBean.getRequestBody().getData();
		RsBody<Map<String, Object>> rsBody = appWorkOrderFlowService.firstReviewPass(dto);
		ResponseBean<Map<String, Object>> responseBean = new ResponseBean<>();
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
    
    @ApiOperation(value = "工单初审-确认关闭工单")
    @PostMapping(value = "/firstReview/close")
    @ResponseBody
    public ResponseBean<Map<String, Object>> firstReviewClose(@Valid @RequestBody RequestBean<AppWorkOrderFlowFirstReviewCloseDTO> requestBean) {
        AppWorkOrderFlowFirstReviewCloseDTO dto = requestBean.getRequestBody().getData();
		RsBody<Map<String, Object>> rsBody = appWorkOrderFlowService.firstReviewClose(dto);
		ResponseBean<Map<String, Object>> responseBean = new ResponseBean<>();
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
    
    @ApiOperation(value = "工单分配-分配页面")
    @PostMapping(value = "/assign/page")
    @ResponseBody
    public ResponseBean<AppWorkOrderAssignPageVo> assignPage(@Valid @RequestBody RequestBean<WorkOrderBaseDTO> requestBean) throws Exception {
    	WorkOrderBaseDTO dto = requestBean.getRequestBody().getData();
    	AppWorkOrderAssignPageVo vo = appWorkOrderFlowService.assignPage(dto);
        RsBody<AppWorkOrderAssignPageVo> rsBody = new RsBody<>();
		rsBody.setData(vo);
		ResponseBean<AppWorkOrderAssignPageVo> responseBean = new ResponseBean<>();
        responseBean.setResponseBody(rsBody);
        responseBean.setResult(Status.SUCCESS);
        return responseBean;
    }
    
    @ApiOperation(value = "工单分配-确认分配工单")
    @PostMapping(value = "/assign/confirm")
    @ResponseBody
    public ResponseBean<Map<String, Object>> assignConfirm(@Valid @RequestBody RequestBean<AppWorkOrderFlowAssignConfirmDTO> requestBean) {
    	AppWorkOrderFlowAssignConfirmDTO dto = requestBean.getRequestBody().getData();
		RsBody<Map<String, Object>> rsBody = appWorkOrderFlowService.assignConfirm(dto);
		ResponseBean<Map<String, Object>> responseBean = new ResponseBean<>();
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
    
    @ApiOperation(value = "工单分配-确认拒绝工单")
    @PostMapping(value = "/assign/reject")
    @ResponseBody
    public ResponseBean<Map<String, Object>> assignReject(@Valid @RequestBody RequestBean<AppWorkOrderFlowAssignRejectDTO> requestBean) {
    	AppWorkOrderFlowAssignRejectDTO dto = requestBean.getRequestBody().getData();
		RsBody<Map<String, Object>> rsBody = appWorkOrderFlowService.assignReject(dto);
		ResponseBean<Map<String, Object>> responseBean = new ResponseBean<>();
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
    
    @ApiOperation(value = "签单-确认签单")
    @PostMapping(value = "/sign/confirm")
    @ResponseBody
    public ResponseBean<Map<String, Object>> signConfirm(@Valid @RequestBody RequestBean<WorkOrderBaseDTO> requestBean) {
    	WorkOrderBaseDTO dto = requestBean.getRequestBody().getData();
		RsBody<Map<String, Object>> rsBody = appWorkOrderFlowService.signConfirm(dto);
		ResponseBean<Map<String, Object>> responseBean = new ResponseBean<>();
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
    
    @ApiOperation(value = "签单-确认撤单")
    @PostMapping(value = "/sign/reject")
    @ResponseBody
    public ResponseBean<Map<String, Object>> signReject(@Valid @RequestBody RequestBean<AppWorkOrderFlowSignRejectDTO> requestBean) {
    	AppWorkOrderFlowSignRejectDTO dto = requestBean.getRequestBody().getData();
		RsBody<Map<String, Object>> rsBody = appWorkOrderFlowService.signReject(dto);
		ResponseBean<Map<String, Object>> responseBean = new ResponseBean<>();
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
    
    @ApiOperation(value = "出发-确认出发")
    @PostMapping(value = "/depart/confirm")
    @ResponseBody
    public ResponseBean<Map<String, Object>> departConfirm(@Valid @RequestBody RequestBean<WorkOrderBaseDTO> requestBean) {
    	WorkOrderBaseDTO dto = requestBean.getRequestBody().getData();
		RsBody<Map<String, Object>> rsBody = appWorkOrderFlowService.departConfirm(dto);
		ResponseBean<Map<String, Object>> responseBean = new ResponseBean<>();
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
    
    @ApiOperation(value = "出发-确认转单")
    @PostMapping(value = "/depart/reject")
    @ResponseBody
    public ResponseBean<Map<String, Object>> departReject(@Valid @RequestBody RequestBean<AppWorkOrderFlowDepartRejectDTO> requestBean) {
    	AppWorkOrderFlowDepartRejectDTO dto = requestBean.getRequestBody().getData();
		RsBody<Map<String, Object>> rsBody = appWorkOrderFlowService.departReject(dto);
		ResponseBean<Map<String, Object>> responseBean = new ResponseBean<>();
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
    
    @ApiOperation(value = "签到-确认签到")
    @PostMapping(value = "/signIn/confirm")
    @ResponseBody
    public ResponseBean<Map<String, Object>> signInConfirm(@Valid @RequestBody RequestBean<AppWorkOrderFlowSignInDTO> requestBean) {
    	AppWorkOrderFlowSignInDTO dto = requestBean.getRequestBody().getData();
		RsBody<Map<String, Object>> rsBody = appWorkOrderFlowService.signInConfirm(dto);
		ResponseBean<Map<String, Object>> responseBean = new ResponseBean<>();
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
	
    @ApiOperation(value = "工单处理-工单信息记录页面")
    @PostMapping(value = "/deal/recordPage")
    @ResponseBody
    public ResponseBean<AppWorkOrderDealRecordPageVo> dealRecordPage(@RequestBody RequestBean<Map<String, Object>> requestBean) throws Exception {
    	Map<String, Object> dto = requestBean.getRequestBody().getData();
    	AppWorkOrderDealRecordPageVo vo = appWorkOrderFlowService.dealRecordPage();
        RsBody<AppWorkOrderDealRecordPageVo> rsBody = new RsBody<>();
		rsBody.setData(vo);
		ResponseBean<AppWorkOrderDealRecordPageVo> responseBean = new ResponseBean<>();
        responseBean.setResponseBody(rsBody);
        responseBean.setResult(Status.SUCCESS);
        return responseBean;
    }
    
    @ApiOperation(value = "工单处理-确认提交工单信息")
    @PostMapping(value = "/deal/recordSubmit")
    @ResponseBody
    public ResponseBean<Map<String, Object>> dealRecordSubmit(@Valid @RequestBody RequestBean<AppWorkOrderDealRecordDTO> requestBean) {
    	AppWorkOrderDealRecordDTO dto = requestBean.getRequestBody().getData();
		RsBody<Map<String, Object>> rsBody = appWorkOrderFlowService.dealRecordSubmit(dto);
		ResponseBean<Map<String, Object>> responseBean = new ResponseBean<>();
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
    
    @ApiOperation(value = "工单处理-申请更换物料页面")
    @PostMapping(value = "/deal/materialPage")
    @ResponseBody
    public ResponseBean<AppWorkOrderDealMaterialPageVo> dealMaterialPage(@RequestBody RequestBean<Map<String, Object>> requestBean) {
    	Map<String, Object> dto = requestBean.getRequestBody().getData();
    	AppWorkOrderDealMaterialPageVo vo = appWorkOrderFlowService.dealMaterialPage();
        RsBody<AppWorkOrderDealMaterialPageVo> rsBody = new RsBody<>();
		rsBody.setData(vo);
		ResponseBean<AppWorkOrderDealMaterialPageVo> responseBean = new ResponseBean<>();
        responseBean.setResponseBody(rsBody);
        responseBean.setResult(Status.SUCCESS);
        return responseBean;
    }
    
    @ApiOperation(value = "工单处理-确认提交物料申请")
    @PostMapping(value = "/deal/materialSubmit")
    @ResponseBody
    public ResponseBean<Map<String, Object>> dealMaterialSubmit(@Valid @RequestBody RequestBean<AppWorkOrderDealMaterialDTO> requestBean) {
    	AppWorkOrderDealMaterialDTO dto = requestBean.getRequestBody().getData();
		RsBody<Map<String, Object>> rsBody = appWorkOrderFlowService.dealMaterialSubmit(dto);
		ResponseBean<Map<String, Object>> responseBean = new ResponseBean<>();
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
    
    @ApiOperation(value = "物料审批")
    @PostMapping(value = "/material/approve")
    @ResponseBody
    public ResponseBean<Map<String, Object>> materialApprove(@Valid @RequestBody RequestBean<WorkOrderBaseDTO> requestBean) {
    	WorkOrderBaseDTO dto = requestBean.getRequestBody().getData();
		RsBody<Map<String, Object>> rsBody = appWorkOrderFlowService.materialApprove(dto);
		ResponseBean<Map<String, Object>> responseBean = new ResponseBean<>();
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
    
    @ApiOperation(value = "物料审核")
    @PostMapping(value = "/material/review")
    @ResponseBody
    public ResponseBean<Map<String, Object>> materialReview(@Valid @RequestBody RequestBean<WorkOrderBaseDTO> requestBean) {
    	WorkOrderBaseDTO dto = requestBean.getRequestBody().getData();
		RsBody<Map<String, Object>> rsBody = appWorkOrderFlowService.materialReview(dto);
		ResponseBean<Map<String, Object>> responseBean = new ResponseBean<>();
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
    
    @ApiOperation(value = "物料发送")
    @PostMapping(value = "/material/deliver")
    @ResponseBody
    public ResponseBean<Map<String, Object>> materialDeliver(@Valid @RequestBody RequestBean<WorkOrderBaseDTO> requestBean) {
    	WorkOrderBaseDTO dto = requestBean.getRequestBody().getData();
		RsBody<Map<String, Object>> rsBody = appWorkOrderFlowService.materialDeliver(dto);
		ResponseBean<Map<String, Object>> responseBean = new ResponseBean<>();
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
    
    @ApiOperation(value = "物料查收")
    @PostMapping(value = "/material/receive")
    @ResponseBody
    public ResponseBean<Map<String, Object>> materialReceive(@Valid @RequestBody RequestBean<WorkOrderBaseDTO> requestBean) {
    	WorkOrderBaseDTO dto = requestBean.getRequestBody().getData();
		RsBody<Map<String, Object>> rsBody = appWorkOrderFlowService.materialReceive(dto);
		ResponseBean<Map<String, Object>> responseBean = new ResponseBean<>();
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
    
    @ApiOperation(value = "工单审核")
    @PostMapping(value = "/audit")
    @ResponseBody
    public ResponseBean<Map<String, Object>> audit(@Valid @RequestBody RequestBean<WorkOrderBaseDTO> requestBean) {
    	WorkOrderBaseDTO dto = requestBean.getRequestBody().getData();
		RsBody<Map<String, Object>> rsBody = appWorkOrderFlowService.audit(dto);
		ResponseBean<Map<String, Object>> responseBean = new ResponseBean<>();
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
    
    @ApiOperation(value = "工单复核")
    @PostMapping(value = "/reAudit")
    @ResponseBody
    public ResponseBean<Map<String, Object>> reAudit(@Valid @RequestBody RequestBean<WorkOrderBaseDTO> requestBean) {
    	WorkOrderBaseDTO dto = requestBean.getRequestBody().getData();
		RsBody<Map<String, Object>> rsBody = appWorkOrderFlowService.reAudit(dto);
		ResponseBean<Map<String, Object>> responseBean = new ResponseBean<>();
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
}
