package com.xhg.ops.workorders.service;

import java.util.List;
import java.util.Map;

import com.xhg.core.web.vo.PagerResult;
import com.xhg.core.web.vo.RsBody;
import com.xhg.ops.workorders.dto.MaterialModuleAddDTO;
import com.xhg.ops.workorders.dto.MaterialModuleDeleteDTO;
import com.xhg.ops.workorders.dto.MaterialModuleEditDTO;
import com.xhg.ops.workorders.dto.MaterialPartsAddDTO;
import com.xhg.ops.workorders.dto.MaterialPartsDeleteDTO;
import com.xhg.ops.workorders.dto.MaterialPartsEditDTO;
import com.xhg.ops.workorders.dto.MaterialPartsQueryDTO;
import com.xhg.ops.workorders.dto.WorkOrderDeviceModuleDto;
import com.xhg.ops.workorders.dto.WorkOrderDevicePartsDto;
import com.xhg.ops.workorders.util.datadict.DataDict;

/**
 * 物料接口
 * @author 区涛
 * @date 2018年7月23日
 */
public interface MaterialService {
	
	/**
	 * 新增模块
	 * @param dto
	 * @return
	 */
	RsBody<Map<String, Object>> addModule(MaterialModuleAddDTO dto);
	
	/**
	 * 编辑模块
	 * @param dto
	 * @return
	 */
	RsBody<Map<String, Object>> editModule(MaterialModuleEditDTO dto);
	
	/**
	 * 删除模块
	 * @param dto
	 * @return
	 */
	RsBody<Map<String, Object>> deleteModule(MaterialModuleDeleteDTO dto);
	
	/**
	 * 查询模块
	 * @return
	 */
	List<WorkOrderDeviceModuleDto> listModules();
	
	/**
	 * 
	 * @param moduleId
	 * @return
	 */
	//List<WorkOrderDeviceModuleDto> listPartsByModule(String moduleId);
	
	/**
	 * 新增配件
	 * @param dto
	 * @return
	 */
	RsBody<Map<String, Object>> addParts(MaterialPartsAddDTO dto);
	
	/**
	 * 编辑配件
	 * @param dto
	 * @return
	 */
	RsBody<Map<String, Object>> editParts(MaterialPartsEditDTO dto);
	
	/**
	 * 删除配件
	 * @param dto
	 * @return
	 */
	RsBody<Map<String, Object>> deleteParts(MaterialPartsDeleteDTO dto);
	
	/**
	 * 分页查询配件
	 * @param dto
	 * @return
	 */
	PagerResult<WorkOrderDevicePartsDto> queryPageParts(MaterialPartsQueryDTO dto);

	/**
	 * 根据模块查询配件
	 * @param dto
	 * @return
	 */
	List<DataDict> listPartsByModule(DataDict dto);
}
