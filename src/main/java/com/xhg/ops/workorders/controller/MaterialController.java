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
import com.xhg.ops.workorders.dto.MaterialModuleAddDTO;
import com.xhg.ops.workorders.dto.MaterialModuleDeleteDTO;
import com.xhg.ops.workorders.dto.MaterialModuleEditDTO;
import com.xhg.ops.workorders.dto.MaterialPartsAddDTO;
import com.xhg.ops.workorders.dto.MaterialPartsDeleteDTO;
import com.xhg.ops.workorders.dto.MaterialPartsEditDTO;
import com.xhg.ops.workorders.dto.MaterialPartsQueryDTO;
import com.xhg.ops.workorders.dto.WorkOrderDeviceModuleDto;
import com.xhg.ops.workorders.dto.WorkOrderDevicePartsDto;
import com.xhg.ops.workorders.service.MaterialService;
import com.xhg.ops.workorders.util.datadict.DataDict;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/ops/material")
@Api(value = "物料接口",description = "物料接口-区涛")
public class MaterialController {

    @Autowired
    private MaterialService materialService;
	
    @ApiOperation(value = "新增模块")
    @PostMapping(value = "/addModule")
    @ResponseBody
    public ResponseBean<Map<String, Object>> addModule(@Valid @RequestBody RequestBean<MaterialModuleAddDTO> requestBean) {
    	MaterialModuleAddDTO dto = requestBean.getRequestBody().getData();
		RsBody<Map<String, Object>> rsBody = materialService.addModule(dto);
		ResponseBean<Map<String, Object>> responseBean = new ResponseBean<>();
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
    
    @ApiOperation(value = "编辑模块")
    @PostMapping(value = "/editModule")
    @ResponseBody
    public ResponseBean<Map<String, Object>> editModule(@Valid @RequestBody RequestBean<MaterialModuleEditDTO> requestBean) {
    	MaterialModuleEditDTO dto = requestBean.getRequestBody().getData();
		RsBody<Map<String, Object>> rsBody = materialService.editModule(dto);
		ResponseBean<Map<String, Object>> responseBean = new ResponseBean<>();
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
    
    @ApiOperation(value = "删除模块")
    @PostMapping(value = "/deleteModule")
    @ResponseBody
    public ResponseBean<Map<String, Object>> deleteModule(@Valid @RequestBody RequestBean<MaterialModuleDeleteDTO> requestBean) {
    	MaterialModuleDeleteDTO dto = requestBean.getRequestBody().getData();
		RsBody<Map<String, Object>> rsBody = materialService.deleteModule(dto);
		ResponseBean<Map<String, Object>> responseBean = new ResponseBean<>();
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
    
    @ApiOperation(value = "查询模块列表(不分页)")
    @PostMapping(value = "/listModules")
    @ResponseBody
    public ResponseBean<List<WorkOrderDeviceModuleDto>> listModules(@Valid @RequestBody RequestBean<Map<String, Object>> requestBean) {
		List<WorkOrderDeviceModuleDto> list = materialService.listModules();
		ResponseBean<List<WorkOrderDeviceModuleDto>> responseBean = new ResponseBean<>();
		RsBody<List<WorkOrderDeviceModuleDto>> rsBody = new RsBody<>();
		rsBody.setData(list);
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
    
    @ApiOperation(value = "模块联动查询配件")
    @PostMapping(value = "/listPartsByModule")
    @ResponseBody
    public ResponseBean<List<DataDict>> listPartsByModule(@Valid @RequestBody RequestBean<DataDict> requestBean) {
    	DataDict dto = requestBean.getRequestBody().getData();
		List<DataDict> list = materialService.listPartsByModule(dto);
		ResponseBean<List<DataDict>> responseBean = new ResponseBean<>();
		RsBody<List<DataDict>> rsBody = new RsBody<>();
		rsBody.setData(list);
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
    
    @ApiOperation(value = "新增配件")
    @PostMapping(value = "/addParts")
    @ResponseBody
    public ResponseBean<Map<String, Object>> addParts(@Valid @RequestBody RequestBean<MaterialPartsAddDTO> requestBean) {
    	MaterialPartsAddDTO dto = requestBean.getRequestBody().getData();
		RsBody<Map<String, Object>> rsBody = materialService.addParts(dto);
		ResponseBean<Map<String, Object>> responseBean = new ResponseBean<>();
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
    
    @ApiOperation(value = "编辑配件")
    @PostMapping(value = "/editParts")
    @ResponseBody
    public ResponseBean<Map<String, Object>> editParts(@Valid @RequestBody RequestBean<MaterialPartsEditDTO> requestBean) {
    	MaterialPartsEditDTO dto = requestBean.getRequestBody().getData();
		RsBody<Map<String, Object>> rsBody = materialService.editParts(dto);
		ResponseBean<Map<String, Object>> responseBean = new ResponseBean<>();
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
    
    @ApiOperation(value = "删除配件")
    @PostMapping(value = "/deleteParts")
    @ResponseBody
    public ResponseBean<Map<String, Object>> deleteParts(@Valid @RequestBody RequestBean<MaterialPartsDeleteDTO> requestBean) {
    	MaterialPartsDeleteDTO dto = requestBean.getRequestBody().getData();
		RsBody<Map<String, Object>> rsBody = materialService.deleteParts(dto);
		ResponseBean<Map<String, Object>> responseBean = new ResponseBean<>();
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }
    
    @ApiOperation(value = "分页查询配件列表")
    @PostMapping(value = "/queryPageParts")
    @ResponseBody
    public ResponseBean<PagerResult<WorkOrderDevicePartsDto>> queryPageParts(@Valid @RequestBody RequestBean<MaterialPartsQueryDTO> requestBean) {
    	MaterialPartsQueryDTO dto = requestBean.getRequestBody().getData();
		PagerResult<WorkOrderDevicePartsDto> pagerResult = materialService.queryPageParts(dto);
		ResponseBean<PagerResult<WorkOrderDevicePartsDto>> responseBean = new ResponseBean<>();
		RsBody<PagerResult<WorkOrderDevicePartsDto>> rsBody = new RsBody<>();
		rsBody.setData(pagerResult);
		responseBean.setResponseBody(rsBody);
		responseBean.setResult(Status.SUCCESS);
		return responseBean;
    }

}
