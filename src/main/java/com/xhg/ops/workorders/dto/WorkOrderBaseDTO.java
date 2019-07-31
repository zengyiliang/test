package com.xhg.ops.workorders.dto;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "工单基础传参")
@Data
public class WorkOrderBaseDTO {

    @ApiModelProperty(value = "工单唯一ID", required=true)
    @NotBlank(message = "orderId不能为空")
    private String orderId;
    
    @ApiModelProperty(value = "工单编号", required=true)
    @NotBlank(message = "orderNo不能为空")
    private String orderNo;
    
}
