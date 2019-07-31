package com.xhg.ops.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "职位对象")
@Data
public class SystemPostionVO {

    @ApiModelProperty(value = "职位名称")
    private String positionName;

    @ApiModelProperty(value = "职位编码")
    private String positionId;
}
