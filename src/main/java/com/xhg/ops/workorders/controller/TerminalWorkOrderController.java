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

import com.xhg.core.web.vo.RequestBean;
import com.xhg.core.web.vo.ResponseBean;
import com.xhg.core.web.vo.RsBody;
import com.xhg.core.web.vo.Status;
import com.xhg.ops.workorders.dto.TerminalWorkOrderSubmitDTO;
import com.xhg.ops.workorders.service.TerminalWorkOrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/ops/terminal")
@Api(value = "ops系统提供给终端的接口",description = "ops系统提供给终端的接口-区涛")
public class TerminalWorkOrderController {

    @Autowired
    private TerminalWorkOrderService terminalWorkOrderService;

    @ApiOperation(value = "批量上报故障工单")
    @PostMapping(value = "/batchSubmitSiteFault")
    @ResponseBody
    public ResponseBean<Map<String, Object>> batchSubmitSiteFault(@Valid @RequestBody RequestBean<List<TerminalWorkOrderSubmitDTO>> requestBean) {
    	List<TerminalWorkOrderSubmitDTO> dtoList = requestBean.getRequestBody().getData();
		RsBody<Map<String, Object>> rsBody = terminalWorkOrderService.batchSubmitSiteFault(dtoList);
		ResponseBean<Map<String, Object>> responseBean = new ResponseBean<>();
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
    
    @ApiOperation(value = "单个上报故障工单")
    @PostMapping(value = "/submitSiteFault")
    @ResponseBody
    public ResponseBean<Map<String, Object>> submitSiteFault(@Valid @RequestBody RequestBean<TerminalWorkOrderSubmitDTO> requestBean) {
    	TerminalWorkOrderSubmitDTO dto = requestBean.getRequestBody().getData();
		RsBody<Map<String, Object>> rsBody = terminalWorkOrderService.submitSiteFault(dto);
		ResponseBean<Map<String, Object>> responseBean = new ResponseBean<>();
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
}
