package com.xhg.ops.workorders.vo;

import java.util.List;

import com.xhg.ops.workorders.dto.WorkOrderPullDownDTO;
import com.xhg.ops.workorders.enums.WorkOrderFailType;
import com.xhg.ops.workorders.enums.WorkOrderType;
import com.xhg.ops.workorders.util.datadict.DataDict;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(value = "web工单处理-信息记录页面")
@Data
public class WebWorkOrderDealRecordPageVo {

    @ApiModelProperty(value = "工单类型 1故障维修 ")
    private List<WorkOrderPullDownDTO<WorkOrderType>> type;

    @ApiModelProperty(value = "故障模块")
    private List<DataDict> failModule;
    
    @ApiModelProperty(value = "更换配件")
    private List<DataDict> changeParts;

    @ApiModelProperty(value = "故障类别 1软件 2硬件 3结构 4网络 5其他")
    private List<WorkOrderPullDownDTO<WorkOrderFailType>> failType;

}
