package com.xhg.ops.workorders.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(value = "app工单处理信息")
@Data
public class AppWorkOrderDealRecordVo extends WorkOrderBaseVo {

    @ApiModelProperty(value = "实际现象")
    private String actualSituation;
    
    @ApiModelProperty(value = "原因分析")
    private String analysis;
    
    @ApiModelProperty(value = "解决办法")
    private String solution;
    
    @ApiModelProperty(value = "图片url地址列表")
    private List<String> urls;
    
    @ApiModelProperty(value = "工单类型")
    private String type;

    @ApiModelProperty(value = "故障模块")
    private String failModule;
    
    @ApiModelProperty(value = "更换配件")
    private String changeParts;

    @ApiModelProperty(value = "故障类别")
    private String failType;

}
