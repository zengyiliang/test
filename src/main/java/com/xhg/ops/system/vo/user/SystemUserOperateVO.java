package com.xhg.ops.system.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "用户操作日志对象")
@Data
public class SystemUserOperateVO {

    @ApiModelProperty(value = "操作时间")
    private String operateTime;

    @ApiModelProperty(value = "操作内容")
    private String content;

    @ApiModelProperty(value = "操作人")
    private String operator;
}
