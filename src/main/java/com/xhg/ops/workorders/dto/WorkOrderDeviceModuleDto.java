package com.xhg.ops.workorders.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 工单设备模块对象
 * 
 * @author 刘涛
 * @date 2018年7月21日
 */
@ApiModel(value = "工单设备模块对象")
@Data
@ToString
public class WorkOrderDeviceModuleDto {

	@ApiModelProperty("编号")
	private Integer id;

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("备注")
	private String remark;

	@ApiModelProperty("创建时间")
	private String createdTime;
	
	@ApiModelProperty("创建人")
	private String createdUserName;

}
