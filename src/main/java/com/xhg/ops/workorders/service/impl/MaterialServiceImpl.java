package com.xhg.ops.workorders.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhg.core.web.vo.PagerResult;
import com.xhg.core.web.vo.RsBody;
import com.xhg.core.web.vo.Status;
import com.xhg.ops.system.filter.UserContext;
import com.xhg.ops.system.model.SystemUser;
import com.xhg.ops.workorders.dto.MaterialModuleAddDTO;
import com.xhg.ops.workorders.dto.MaterialModuleDeleteDTO;
import com.xhg.ops.workorders.dto.MaterialModuleEditDTO;
import com.xhg.ops.workorders.dto.MaterialPartsAddDTO;
import com.xhg.ops.workorders.dto.MaterialPartsDeleteDTO;
import com.xhg.ops.workorders.dto.MaterialPartsEditDTO;
import com.xhg.ops.workorders.dto.MaterialPartsQueryDTO;
import com.xhg.ops.workorders.dto.WorkOrderDeviceModuleDto;
import com.xhg.ops.workorders.dto.WorkOrderDevicePartsDto;
import com.xhg.ops.workorders.model.WorkOrderDeviceModule;
import com.xhg.ops.workorders.model.WorkOrderDeviceParts;
import com.xhg.ops.workorders.service.MaterialService;
import com.xhg.ops.workorders.service.WorkOrderDeviceModuleService;
import com.xhg.ops.workorders.service.WorkOrderDevicePartsService;
import com.xhg.ops.workorders.util.datadict.DataDict;
import com.xhg.ops.workorders.util.datadict.DataDictUtil;

@Service("materialService")
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    private WorkOrderDeviceModuleService workOrderDeviceModuleService;
    
    @Autowired
    private WorkOrderDevicePartsService workOrderDevicePartsService;

	@Override
	public RsBody<Map<String, Object>> addModule(MaterialModuleAddDTO dto) {
		RsBody<Map<String,Object>> rsBody = new RsBody<>();
		WorkOrderDeviceModule module = new WorkOrderDeviceModule();
		module.setName(dto.getName());
		SystemUser user = UserContext.getUser();
		if(user!=null) {
			workOrderDeviceModuleService.insert(module, user.getId());
		}
		
		rsBody.setCode(Status.Bu_SUCCESS.getName());
		rsBody.setMessage(Status.Bu_SUCCESS.getValue());
		return rsBody;
	}

	@Override
	public RsBody<Map<String, Object>> editModule(MaterialModuleEditDTO dto) {
		RsBody<Map<String,Object>> rsBody = new RsBody<>();
		WorkOrderDeviceModule module = new WorkOrderDeviceModule();
		module.setName(dto.getName());
		module.setId(Integer.valueOf(dto.getId()));
		SystemUser user = UserContext.getUser();
		if(user!=null) {
			workOrderDeviceModuleService.update(module, user.getId());
		}
		
		rsBody.setCode(Status.Bu_SUCCESS.getName());
		rsBody.setMessage(Status.Bu_SUCCESS.getValue());
		return rsBody;
	}

	@Override
	public RsBody<Map<String, Object>> deleteModule(MaterialModuleDeleteDTO dto) {
		RsBody<Map<String,Object>> rsBody = new RsBody<>();
		int id = Integer.valueOf(dto.getId());
		SystemUser user = UserContext.getUser();
		if(user!=null) {
			workOrderDeviceModuleService.delete(id, user.getId());
		}
		
		rsBody.setCode(Status.Bu_SUCCESS.getName());
		rsBody.setMessage(Status.Bu_SUCCESS.getValue());
		return rsBody;
	}

	@Override
	public List<WorkOrderDeviceModuleDto> listModules() {
		List<WorkOrderDeviceModuleDto> list = workOrderDeviceModuleService.findDeviceModuleDtoList();
		return list;
	}
	
	@Override
	public List<DataDict> listPartsByModule(DataDict dto) {
		List<WorkOrderDeviceParts> parts = workOrderDevicePartsService.findDevicePartsList(Integer.valueOf(dto.getCode()));
		List<DataDict> dataDict = DataDictUtil.covertDataDict(parts);
		return dataDict;
	}

	@Override
	public RsBody<Map<String, Object>> addParts(MaterialPartsAddDTO dto) {
		RsBody<Map<String,Object>> rsBody = new RsBody<>();
		WorkOrderDeviceParts parts = new WorkOrderDeviceParts();
		parts.setName(dto.getName());
		parts.setModuleId(Integer.valueOf(dto.getModuleId()));
		SystemUser user = UserContext.getUser();
		if(user!=null) {
			workOrderDevicePartsService.insert(parts, user.getId());
		}
		
		rsBody.setCode(Status.Bu_SUCCESS.getName());
		rsBody.setMessage(Status.Bu_SUCCESS.getValue());
		return rsBody;
	}

	@Override
	public RsBody<Map<String, Object>> editParts(MaterialPartsEditDTO dto) {
		RsBody<Map<String,Object>> rsBody = new RsBody<>();
		WorkOrderDeviceParts parts = new WorkOrderDeviceParts();
		parts.setName(dto.getName());
		parts.setId(Integer.valueOf(dto.getId()));
		parts.setModuleId(Integer.valueOf(dto.getModuleId()));
		SystemUser user = UserContext.getUser();
		if(user!=null) {
			workOrderDevicePartsService.update(parts, user.getId());
		}
		
		rsBody.setCode(Status.Bu_SUCCESS.getName());
		rsBody.setMessage(Status.Bu_SUCCESS.getValue());
		return rsBody;
	}

	@Override
	public RsBody<Map<String, Object>> deleteParts(MaterialPartsDeleteDTO dto) {
		RsBody<Map<String,Object>> rsBody = new RsBody<>();
		int id = Integer.valueOf(dto.getId());
		SystemUser user = UserContext.getUser();
		if(user!=null) {
			workOrderDevicePartsService.delete(id, user.getId());
		}
		
		rsBody.setCode(Status.Bu_SUCCESS.getName());
		rsBody.setMessage(Status.Bu_SUCCESS.getValue());
		return rsBody;
	}

	@Override
	public PagerResult<WorkOrderDevicePartsDto> queryPageParts(MaterialPartsQueryDTO dto) {
		PagerResult<WorkOrderDevicePartsDto> pageParts = workOrderDevicePartsService.findDevicePartsDtoListPage(dto.getCurrentPage(), dto.getPageSize());
		return pageParts;
	}
	
    
}
