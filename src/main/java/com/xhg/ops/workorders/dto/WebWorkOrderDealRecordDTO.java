package com.xhg.ops.workorders.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.xhg.ops.workorders.enums.WorkOrderFailType;
import com.xhg.ops.workorders.enums.WorkOrderType;
import com.xhg.ops.workorders.util.datadict.DataDict;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(value = "web工单处理-信息记录")
@Data
public class WebWorkOrderDealRecordDTO extends WorkOrderBaseDTO {
    
    @ApiModelProperty(value = "实际现象", required=true)
    @NotBlank(message = "actualSituation不能为空")
    private String actualSituation;
    
    @ApiModelProperty(value = "原因分析", required=true)
    @NotBlank(message = "analysis不能为空")
    private String analysis;
    
    @ApiModelProperty(value = "解决办法", required=true)
    @NotBlank(message = "solution不能为空")
    private String solution;
    
    @ApiModelProperty(value = "图片url地址")
    private String url;
    
    @ApiModelProperty(value = "工单类型 1故障维修 ", required=true)
    @NotNull(message = "type不能为空")
    private WorkOrderPullDownDTO<WorkOrderType> type;
    
    @ApiModelProperty(value = "故障模块", required=true)
    @NotNull(message = "failModule不能为空")
    private DataDict failModule;
    
    @ApiModelProperty(value = "更换配件", required=true)
    @NotNull(message = "changeParts不能为空")
    private DataDict changeParts;

    @ApiModelProperty(value = "故障类别 1软件 2硬件 3结构 4网络 5其他", required=true)
    @NotNull(message = "failType不能为空")
    private WorkOrderPullDownDTO<WorkOrderFailType> failType;

}
