package com.xhg.ops.workorders.vo;

import java.util.List;

import com.xhg.ops.workorders.dto.WorkOrderPullDownDTO;
import com.xhg.ops.workorders.enums.WorkOrderLevel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(value = "web工单初审页面")
@Data
public class WebWorkOrderFirstReviewPageVo {

    @ApiModelProperty(value = "紧急程度 1一般 2紧急 3加急")
    private List<WorkOrderPullDownDTO<WorkOrderLevel>> level;

    @ApiModelProperty(value = "城市运维经理列表")
    private List<WebWorkOrderFirstReviewOpsManagerVo> cityOpsManagerList;

}
