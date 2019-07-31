package com.xhg.ops.workorders.dto;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "已读消息传参")
@Data
public class AppWorkOrderMessageUpdateDTO {

    @ApiModelProperty(value = "消息ID", required=true)
    @NotBlank(message = "messageId不能为空")
    private String messageId;
    
}
