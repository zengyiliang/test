package com.xhg.ops.workorders.vo;

import java.util.List;

import com.xhg.ops.workorders.dto.WorkOrderPullDownDTO;
import com.xhg.ops.workorders.enums.WorkOrderDataSource;
import com.xhg.ops.workorders.enums.WorkOrderType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(value = "web工单新建工单页面")
@Data
public class WebWorkOrderCreatePageVo {

    @ApiModelProperty(value = "工单来源 ")
    private List<WorkOrderPullDownDTO<WorkOrderDataSource>> source;

    @ApiModelProperty(value = "工单类型")
    private List<WorkOrderPullDownDTO<WorkOrderType>> type;

}
