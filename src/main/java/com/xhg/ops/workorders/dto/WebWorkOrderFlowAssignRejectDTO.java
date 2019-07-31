package com.xhg.ops.workorders.dto;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "web工单分配拒绝传参")
@Data
public class WebWorkOrderFlowAssignRejectDTO extends WorkOrderBaseDTO {
    
    @ApiModelProperty(value = "原因")
    @NotBlank(message = "reason不能为空")
    private String reason;
    
}
