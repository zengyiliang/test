package com.xhg.ops.workorders.vo;

import java.util.List;

import com.xhg.ops.workorders.util.datadict.DataDict;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(value = "app工单处理-申请更换物料页面")
@Data
public class AppWorkOrderDealMaterialPageVo {

    @ApiModelProperty(value = "故障模块")
    private List<DataDict> failModule;
    
    @ApiModelProperty(value = "更换配件")
    private List<DataDict> changeParts;

}
