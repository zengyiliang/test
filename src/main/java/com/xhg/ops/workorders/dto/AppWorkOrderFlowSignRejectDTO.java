package com.xhg.ops.workorders.dto;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "app签单撤单传参")
@Data
public class AppWorkOrderFlowSignRejectDTO extends WorkOrderBaseDTO {

    @ApiModelProperty(value = "原因", required=true)
    @NotBlank(message = "reason不能为空")
    private String reason;
    
}
