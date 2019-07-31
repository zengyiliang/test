package com.xhg.ops.system.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "用户id获取对象")
@Data
public class SystemUserIdDTO {

    @ApiModelProperty(value = "id")
    private Integer id=0;
}
