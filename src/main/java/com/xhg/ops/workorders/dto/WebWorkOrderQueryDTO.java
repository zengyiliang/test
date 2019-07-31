package com.xhg.ops.workorders.dto;

import com.xhg.ops.workorders.enums.WorkOrderType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "web工单列表查询传参")
@Data
public class WebWorkOrderQueryDTO {

    @ApiModelProperty(value = "工单编号")
    private String orderNo;
    
    @ApiModelProperty(value = "工单类型 1故障维修 ")
    private WorkOrderPullDownDTO<WorkOrderType> type;
    
    @ApiModelProperty(value = "创建人")
    private String createUserName;
	
    @ApiModelProperty(value = "当前页码")
    private Integer currentPage=1;

    @ApiModelProperty(value = "每一页显示的数目")
    private Integer pageSize = 10;
}
