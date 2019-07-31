package com.xhg.ops.workorders;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xhg.core.web.vo.PagerResult;
import com.xhg.ops.OpsApplication;
import com.xhg.ops.workorders.dto.WorkOrderDeviceModuleDto;
import com.xhg.ops.workorders.dto.WorkOrderDevicePartsDto;
import com.xhg.ops.workorders.model.WorkOrderDeviceModule;
import com.xhg.ops.workorders.model.WorkOrderDeviceParts;
import com.xhg.ops.workorders.service.WorkOrderDeviceModuleService;
import com.xhg.ops.workorders.service.WorkOrderDevicePartsService;
import com.xhg.ops.workorders.util.datadict.DataDictUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OpsApplication.class)
public class WorkOrderDeviceTest {

	@Autowired
	private WorkOrderDeviceModuleService workOrderDeviceModuleService;
	
	@Autowired
	private WorkOrderDevicePartsService workOrderDevicePartsService;
	
//	@Test
	public void insertDevice() {
		WorkOrderDeviceModule module = new WorkOrderDeviceModule();
		module.setName("监控模块");
		workOrderDeviceModuleService.insert(module, 36);
		System.out.println("moduleId: " + module.getId());
	}
	
//	@Test
	public void findDeviceDtoListPage() {
		PagerResult<WorkOrderDeviceModuleDto> page = workOrderDeviceModuleService.findDeviceModuleDtoListPage(1, 10);
		System.out.println(page);
	}
	
//	@Test
	public void insertParts() {
		WorkOrderDeviceParts parts = new WorkOrderDeviceParts();
		parts.setName("大电子秤");
		parts.setModuleId(1);
		workOrderDevicePartsService.insert(parts, 36);
		System.out.println("partsId: " + parts.getId());
	}
	
//	@Test
	public void findPartsListPage() {
		PagerResult<WorkOrderDevicePartsDto> page = workOrderDevicePartsService.findDevicePartsDtoListPage(1, 10);
		System.out.println(page);
	}
	
//	@Test
	public void findPartList() {
		List<WorkOrderDeviceModule> list = workOrderDeviceModuleService.findDeviceModuleList();
		System.out.println(list);
		System.out.println(DataDictUtil.covertDataDict(list).get(0));
	}
	
//	@Test
	public void updateDeviceModule() {
		WorkOrderDeviceModule module = new WorkOrderDeviceModule();
		module.setId(3);
		module.setName("监控模块");
		workOrderDeviceModuleService.update(module, 36);
	}
	
	@Test
	public void updateDeviceParts() {
		WorkOrderDeviceParts parts = new WorkOrderDeviceParts();
		parts.setName("有刷直流马达（传送机构）");
		parts.setId(1);
		parts.setModuleId(1);
		workOrderDevicePartsService.update(parts, 36);
	}
}
