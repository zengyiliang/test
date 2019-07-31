package com.xhg.ops.workorders.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(value = "工单基础数据")
@Data
public class WorkOrderBaseVo {

    @ApiModelProperty(value = "工单唯一ID")
    private String orderId;
    
    @ApiModelProperty(value = "工单编号")
    private String orderNo;

}
