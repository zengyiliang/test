package com.xhg.ops.workorders.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.xhg.ops.workorders.enums.WorkOrderLevel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "app工单初审通过传参")
@Data
public class AppWorkOrderFlowFirstReviewPassDTO extends WorkOrderBaseDTO {
    
    @ApiModelProperty(value = "城市运维经理用户id", required=true)
    @NotBlank(message = "cityOpsManagerUserId不能为空")
    private String cityOpsManagerUserId;
    
    @ApiModelProperty(value = "紧急程度 1一般 2紧急 3加急", required=true)
    @NotNull(message = "level不能为空")
    private WorkOrderPullDownDTO<WorkOrderLevel> level;

}
