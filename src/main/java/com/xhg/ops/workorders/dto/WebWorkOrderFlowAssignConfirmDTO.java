package com.xhg.ops.workorders.dto;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "web工单分配确认传参")
@Data
public class WebWorkOrderFlowAssignConfirmDTO extends WorkOrderBaseDTO {
    
    @ApiModelProperty(value = "运维人员用户id")
    @NotBlank(message = "cityOpsUserId不能为空")
    private String cityOpsUserId;
    
}
