package com.xhg.ops.workorders.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(value = "app工单分配页面")
@Data
public class AppWorkOrderAssignPageVo {

    @ApiModelProperty(value = "运维人员列表")
    private List<AppWorkOrderAssignOpsVo> cityOpsList;

}
