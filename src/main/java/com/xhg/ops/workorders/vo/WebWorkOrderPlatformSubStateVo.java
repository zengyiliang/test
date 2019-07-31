package com.xhg.ops.workorders.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(value = "web工作台从状态")
@Data
public class WebWorkOrderPlatformSubStateVo {

	@ApiModelProperty(value = "工单从状态标识  1待初审 2待分配 3待签单 4待出发 5待签到 6待处理 7挂起 8待审核 9待复核")
    String subState;
    
    @ApiModelProperty(value = "数量")
    long num;
    
}
