package com.xhg.ops.workorders.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(value = "web工单分配页面")
@Data
public class WebWorkOrderAssignPageVo {

    @ApiModelProperty(value = "运维人员用户列表")
    private List<WebWorkOrderAssignOpsVo> cityOpsList;
}
