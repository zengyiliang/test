package com.xhg.ops.workorders.controller;

import java.util.List;
import java.util.Map;

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
import com.xhg.ops.workorders.dto.WorkOrderBaseDTO;
import com.xhg.ops.workorders.dto.WebWorkOrderCityOpsManagerSearchDTO;
import com.xhg.ops.workorders.dto.WebWorkOrderCityOpsSearchDTO;
import com.xhg.ops.workorders.dto.WebWorkOrderCreateDTO;
import com.xhg.ops.workorders.dto.WebWorkOrderDealMaterialDTO;
import com.xhg.ops.workorders.dto.WebWorkOrderDealRecordDTO;
import com.xhg.ops.workorders.dto.WebWorkOrderDeviceAddressQueryDTO;
import com.xhg.ops.workorders.dto.WebWorkOrderFlowAssignConfirmDTO;
import com.xhg.ops.workorders.dto.WebWorkOrderFlowAssignRejectDTO;
import com.xhg.ops.workorders.dto.WebWorkOrderFlowDepartRejectDTO;
import com.xhg.ops.workorders.dto.WebWorkOrderFlowFirstReviewCloseDTO;
import com.xhg.ops.workorders.dto.WebWorkOrderFlowFirstReviewPassDTO;
import com.xhg.ops.workorders.dto.WebWorkOrderFlowSignRejectDTO;
import com.xhg.ops.workorders.service.WebWorkOrderFlowService;
import com.xhg.ops.workorders.vo.WebWorkOrderAssignOpsVo;
import com.xhg.ops.workorders.vo.WebWorkOrderAssignPageVo;
import com.xhg.ops.workorders.vo.WebWorkOrderCreatePageVo;
import com.xhg.ops.workorders.vo.WebWorkOrderDealMaterialPageVo;
import com.xhg.ops.workorders.vo.WebWorkOrderDealRecordPageVo;
import com.xhg.ops.workorders.vo.WebWorkOrderDeviceAddressVo;
import com.xhg.ops.workorders.vo.WebWorkOrderFirstReviewOpsManagerVo;
import com.xhg.ops.workorders.vo.WebWorkOrderFirstReviewPageVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/ops/web/workFlow")
@Api(value = "web后台工单流程操作接口",description = "web后台工单流程操作接口-区涛")
public class WebWorkOrderFlowController {

    @Autowired
    private WebWorkOrderFlowService webWorkOrderFlowService;

    @ApiOperation(value = "新建工单-新建工单页面")
    @PostMapping(value = "/createOrder/page")
    @ResponseBody
    public ResponseBean<WebWorkOrderCreatePageVo> createOrderPage(@RequestBody RequestBean<Map<String, Object>> requestBean) throws Exception {
    	Map<String, Object> dto = requestBean.getRequestBody().getData();
    	WebWorkOrderCreatePageVo vo = webWorkOrderFlowService.createOrderPage();
        RsBody<WebWorkOrderCreatePageVo> rsBody = new RsBody<>();
		rsBody.setData(vo);
		ResponseBean<WebWorkOrderCreatePageVo> responseBean = new ResponseBean<>();
        responseBean.setResponseBody(rsBody);
        responseBean.setResult(Status.SUCCESS);
        return responseBean;
    }
    
/*    @ApiOperation(value = "新建工单-根据机器编码获取设备地址")
    @PostMapping(value = "/createOrder/queryDeviceAddress")
    @ResponseBody
    public ResponseBean<WebWorkOrderDeviceAddressVo> queryDeviceAddress(@RequestBody RequestBean<WebWorkOrderDeviceAddressQueryDTO> requestBean) {
    	WebWorkOrderDeviceAddressQueryDTO dto = requestBean.getRequestBody().getData();
    	WebWorkOrderDeviceAddressVo vo = webWorkOrderFlowService.queryDeviceAddress(dto);
        RsBody<WebWorkOrderDeviceAddressVo> rsBody = new RsBody<>();
		rsBody.setData(vo);
		ResponseBean<WebWorkOrderDeviceAddressVo> responseBean = new ResponseBean<>();
        responseBean.setResponseBody(rsBody);
        responseBean.setResult(Status.SUCCESS);
        return responseBean;
    }*/
    
    @ApiOperation(value = "新建工单-提交新建工单")
    @PostMapping(value = "/createOrder/submit")
    @ResponseBody
    public ResponseBean<Map<String, Object>> createOrderSubmit(@RequestBody RequestBean<WebWorkOrderCreateDTO> requestBean) {
    	WebWorkOrderCreateDTO dto = requestBean.getRequestBody().getData();
		RsBody<Map<String, Object>> rsBody = webWorkOrderFlowService.createOrderSubmit(dto);
		ResponseBean<Map<String, Object>> responseBean = new ResponseBean<>();
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
    
    @ApiOperation(value = "工单初审-初审页面")
    @PostMapping(value = "/firstReview/page")
    @ResponseBody
    public ResponseBean<WebWorkOrderFirstReviewPageVo> firstReviewPage(@RequestBody RequestBean<WorkOrderBaseDTO> requestBean) throws Exception {
    	WorkOrderBaseDTO dto = requestBean.getRequestBody().getData();
    	WebWorkOrderFirstReviewPageVo vo = webWorkOrderFlowService.firstReviewPage(dto);
        RsBody<WebWorkOrderFirstReviewPageVo> rsBody = new RsBody<>();
		rsBody.setData(vo);
		ResponseBean<WebWorkOrderFirstReviewPageVo> responseBean = new ResponseBean<>();
        responseBean.setResponseBody(rsBody);
        responseBean.setResult(Status.SUCCESS);
        return responseBean;
    }
    
    @ApiOperation(value = "工单初审-搜索城市运维经理")
    @PostMapping(value = "/firstReview/searchCityOpsManager")
    @ResponseBody
    public ResponseBean<List<WebWorkOrderFirstReviewOpsManagerVo>> firstReviewSearchCityOpsManager(@RequestBody RequestBean<WebWorkOrderCityOpsManagerSearchDTO> requestBean) throws Exception {
    	WebWorkOrderCityOpsManagerSearchDTO dto = requestBean.getRequestBody().getData();
    	List<WebWorkOrderFirstReviewOpsManagerVo> voList = webWorkOrderFlowService.firstReviewSearchCityOpsManager(dto);
        RsBody<List<WebWorkOrderFirstReviewOpsManagerVo>> rsBody = new RsBody<>();
		rsBody.setData(voList);
		ResponseBean<List<WebWorkOrderFirstReviewOpsManagerVo>> responseBean = new ResponseBean<>();
        responseBean.setResponseBody(rsBody);
        responseBean.setResult(Status.SUCCESS);
        return responseBean;
    }
    
    @ApiOperation(value = "工单初审-初审通过")
    @PostMapping(value = "/firstReview/pass")
    @ResponseBody
    public ResponseBean<Map<String, Object>> firstReviewPass(@RequestBody RequestBean<WebWorkOrderFlowFirstReviewPassDTO> requestBean) {
        WebWorkOrderFlowFirstReviewPassDTO dto = requestBean.getRequestBody().getData();
		RsBody<Map<String, Object>> rsBody = webWorkOrderFlowService.firstReviewPass(dto);
		ResponseBean<Map<String, Object>> responseBean = new ResponseBean<>();
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
    
    @ApiOperation(value = "工单初审-确认关闭工单")
    @PostMapping(value = "/firstReview/close")
    @ResponseBody
    public ResponseBean<Map<String, Object>> firstReviewClose(@RequestBody RequestBean<WebWorkOrderFlowFirstReviewCloseDTO> requestBean) {
        WebWorkOrderFlowFirstReviewCloseDTO dto = requestBean.getRequestBody().getData();
		RsBody<Map<String, Object>> rsBody = webWorkOrderFlowService.firstReviewClose(dto);
		ResponseBean<Map<String, Object>> responseBean = new ResponseBean<>();
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
    
    @ApiOperation(value = "工单分配-分配页面")
    @PostMapping(value = "/assign/page")
    @ResponseBody
    public ResponseBean<WebWorkOrderAssignPageVo> assignPage(@RequestBody RequestBean<WorkOrderBaseDTO> requestBean) throws Exception {
    	WorkOrderBaseDTO dto = requestBean.getRequestBody().getData();
    	WebWorkOrderAssignPageVo vo = webWorkOrderFlowService.assignPage(dto);
        RsBody<WebWorkOrderAssignPageVo> rsBody = new RsBody<>();
		rsBody.setData(vo);
		ResponseBean<WebWorkOrderAssignPageVo> responseBean = new ResponseBean<>();
        responseBean.setResponseBody(rsBody);
        responseBean.setResult(Status.SUCCESS);
        return responseBean;
    }
    
    @ApiOperation(value = "工单分配-搜索运维专员")
    @PostMapping(value = "/assign/searchOps")
    @ResponseBody
    public ResponseBean<List<WebWorkOrderAssignOpsVo>> assignSearchOps(@RequestBody RequestBean<WebWorkOrderCityOpsSearchDTO> requestBean) throws Exception {
    	WebWorkOrderCityOpsSearchDTO dto = requestBean.getRequestBody().getData();
    	List<WebWorkOrderAssignOpsVo> voList = webWorkOrderFlowService.assignSearchOps(dto);
        RsBody<List<WebWorkOrderAssignOpsVo>> rsBody = new RsBody<>();
		rsBody.setData(voList);
		ResponseBean<List<WebWorkOrderAssignOpsVo>> responseBean = new ResponseBean<>();
        responseBean.setResponseBody(rsBody);
        responseBean.setResult(Status.SUCCESS);
        return responseBean;
    }
    
    @ApiOperation(value = "工单分配-确认分配工单")
    @PostMapping(value = "/assign/confirm")
    @ResponseBody
    public ResponseBean<Map<String, Object>> assignConfirm(@RequestBody RequestBean<WebWorkOrderFlowAssignConfirmDTO> requestBean) {
    	WebWorkOrderFlowAssignConfirmDTO dto = requestBean.getRequestBody().getData();
		RsBody<Map<String, Object>> rsBody = webWorkOrderFlowService.assignConfirm(dto);
		ResponseBean<Map<String, Object>> responseBean = new ResponseBean<>();
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
    
    @ApiOperation(value = "工单分配-确认拒绝工单")
    @PostMapping(value = "/assign/reject")
    @ResponseBody
    public ResponseBean<Map<String, Object>> assignReject(@RequestBody RequestBean<WebWorkOrderFlowAssignRejectDTO> requestBean) {
    	WebWorkOrderFlowAssignRejectDTO dto = requestBean.getRequestBody().getData();
		RsBody<Map<String, Object>> rsBody = webWorkOrderFlowService.assignReject(dto);
		ResponseBean<Map<String, Object>> responseBean = new ResponseBean<>();
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
    
    @ApiOperation(value = "签单-确认签单")
    @PostMapping(value = "/sign/confirm")
    @ResponseBody
    public ResponseBean<Map<String, Object>> signConfirm(@RequestBody RequestBean<WorkOrderBaseDTO> requestBean) {
    	WorkOrderBaseDTO dto = requestBean.getRequestBody().getData();
		RsBody<Map<String, Object>> rsBody = webWorkOrderFlowService.signConfirm(dto);
		ResponseBean<Map<String, Object>> responseBean = new ResponseBean<>();
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
    
    @ApiOperation(value = "签单-确认撤单")
    @PostMapping(value = "/sign/reject")
    @ResponseBody
    public ResponseBean<Map<String, Object>> signReject(@RequestBody RequestBean<WebWorkOrderFlowSignRejectDTO> requestBean) {
    	WebWorkOrderFlowSignRejectDTO dto = requestBean.getRequestBody().getData();
		RsBody<Map<String, Object>> rsBody = webWorkOrderFlowService.signReject(dto);
		ResponseBean<Map<String, Object>> responseBean = new ResponseBean<>();
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
    
    @ApiOperation(value = "出发-确认出发")
    @PostMapping(value = "/depart/confirm")
    @ResponseBody
    public ResponseBean<Map<String, Object>> departConfirm(@RequestBody RequestBean<WorkOrderBaseDTO> requestBean) {
    	WorkOrderBaseDTO dto = requestBean.getRequestBody().getData();
		RsBody<Map<String, Object>> rsBody = webWorkOrderFlowService.departConfirm(dto);
		ResponseBean<Map<String, Object>> responseBean = new ResponseBean<>();
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
    
    @ApiOperation(value = "出发-确认转单")
    @PostMapping(value = "/depart/reject")
    @ResponseBody
    public ResponseBean<Map<String, Object>> departReject(@RequestBody RequestBean<WebWorkOrderFlowDepartRejectDTO> requestBean) {
    	WebWorkOrderFlowDepartRejectDTO dto = requestBean.getRequestBody().getData();
		RsBody<Map<String, Object>> rsBody = webWorkOrderFlowService.departReject(dto);
		ResponseBean<Map<String, Object>> responseBean = new ResponseBean<>();
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
	
    @ApiOperation(value = "工单处理-工单信息记录页面")
    @PostMapping(value = "/deal/recordPage")
    @ResponseBody
    public ResponseBean<WebWorkOrderDealRecordPageVo> dealRecordPage(@RequestBody RequestBean<Map<String, Object>> requestBean) throws Exception {
    	Map<String, Object> dto = requestBean.getRequestBody().getData();
    	WebWorkOrderDealRecordPageVo vo = webWorkOrderFlowService.dealRecordPage();
        RsBody<WebWorkOrderDealRecordPageVo> rsBody = new RsBody<>();
		rsBody.setData(vo);
		ResponseBean<WebWorkOrderDealRecordPageVo> responseBean = new ResponseBean<>();
        responseBean.setResponseBody(rsBody);
        responseBean.setResult(Status.SUCCESS);
        return responseBean;
    }
    
    @ApiOperation(value = "工单处理-确认提交工单信息")
    @PostMapping(value = "/deal/recordSubmit")
    @ResponseBody
    public ResponseBean<Map<String, Object>> dealRecordSubmit(@RequestBody RequestBean<WebWorkOrderDealRecordDTO> requestBean) {
    	WebWorkOrderDealRecordDTO dto = requestBean.getRequestBody().getData();
		RsBody<Map<String, Object>> rsBody = webWorkOrderFlowService.dealRecordSubmit(dto);
		ResponseBean<Map<String, Object>> responseBean = new ResponseBean<>();
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
    
    @ApiOperation(value = "工单处理-申请更换物料页面")
    @PostMapping(value = "/deal/materialPage")
    @ResponseBody
    public ResponseBean<WebWorkOrderDealMaterialPageVo> dealMaterialPage(@RequestBody RequestBean<Map<String, Object>> requestBean) {
    	Map<String, Object> dto = requestBean.getRequestBody().getData();
    	WebWorkOrderDealMaterialPageVo vo = webWorkOrderFlowService.dealMaterialPage();
        RsBody<WebWorkOrderDealMaterialPageVo> rsBody = new RsBody<>();
		rsBody.setData(vo);
		ResponseBean<WebWorkOrderDealMaterialPageVo> responseBean = new ResponseBean<>();
        responseBean.setResponseBody(rsBody);
        responseBean.setResult(Status.SUCCESS);
        return responseBean;
    }
    
    @ApiOperation(value = "工单处理-确认提交物料申请")
    @PostMapping(value = "/deal/materialSubmit")
    @ResponseBody
    public ResponseBean<Map<String, Object>> dealMaterialSubmit(@RequestBody RequestBean<WebWorkOrderDealMaterialDTO> requestBean) {
    	WebWorkOrderDealMaterialDTO dto = requestBean.getRequestBody().getData();
		RsBody<Map<String, Object>> rsBody = webWorkOrderFlowService.dealMaterialSubmit(dto);
		ResponseBean<Map<String, Object>> responseBean = new ResponseBean<>();
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
    
    @ApiOperation(value = "物料审批")
    @PostMapping(value = "/material/approve")
    @ResponseBody
    public ResponseBean<Map<String, Object>> materialWebrove(@RequestBody RequestBean<WorkOrderBaseDTO> requestBean) {
    	WorkOrderBaseDTO dto = requestBean.getRequestBody().getData();
		RsBody<Map<String, Object>> rsBody = webWorkOrderFlowService.materialApprove(dto);
		ResponseBean<Map<String, Object>> responseBean = new ResponseBean<>();
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
    
    @ApiOperation(value = "物料审核")
    @PostMapping(value = "/material/review")
    @ResponseBody
    public ResponseBean<Map<String, Object>> materialReview(@RequestBody RequestBean<WorkOrderBaseDTO> requestBean) {
    	WorkOrderBaseDTO dto = requestBean.getRequestBody().getData();
		RsBody<Map<String, Object>> rsBody = webWorkOrderFlowService.materialReview(dto);
		ResponseBean<Map<String, Object>> responseBean = new ResponseBean<>();
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
    
    @ApiOperation(value = "物料发送")
    @PostMapping(value = "/material/deliver")
    @ResponseBody
    public ResponseBean<Map<String, Object>> materialDeliver(@RequestBody RequestBean<WorkOrderBaseDTO> requestBean) {
    	WorkOrderBaseDTO dto = requestBean.getRequestBody().getData();
		RsBody<Map<String, Object>> rsBody = webWorkOrderFlowService.materialDeliver(dto);
		ResponseBean<Map<String, Object>> responseBean = new ResponseBean<>();
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
    
    @ApiOperation(value = "物料查收")
    @PostMapping(value = "/material/receive")
    @ResponseBody
    public ResponseBean<Map<String, Object>> materialReceive(@RequestBody RequestBean<WorkOrderBaseDTO> requestBean) {
    	WorkOrderBaseDTO dto = requestBean.getRequestBody().getData();
		RsBody<Map<String, Object>> rsBody = webWorkOrderFlowService.materialReceive(dto);
		ResponseBean<Map<String, Object>> responseBean = new ResponseBean<>();
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
    
    @ApiOperation(value = "工单审核")
    @PostMapping(value = "/audit")
    @ResponseBody
    public ResponseBean<Map<String, Object>> audit(@RequestBody RequestBean<WorkOrderBaseDTO> requestBean) {
    	WorkOrderBaseDTO dto = requestBean.getRequestBody().getData();
		RsBody<Map<String, Object>> rsBody = webWorkOrderFlowService.audit(dto);
		ResponseBean<Map<String, Object>> responseBean = new ResponseBean<>();
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
    
    @ApiOperation(value = "工单复核")
    @PostMapping(value = "/reAudit")
    @ResponseBody
    public ResponseBean<Map<String, Object>> reAudit(@RequestBody RequestBean<WorkOrderBaseDTO> requestBean) {
    	WorkOrderBaseDTO dto = requestBean.getRequestBody().getData();
		RsBody<Map<String, Object>> rsBody = webWorkOrderFlowService.reAudit(dto);
		ResponseBean<Map<String, Object>> responseBean = new ResponseBean<>();
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
}
