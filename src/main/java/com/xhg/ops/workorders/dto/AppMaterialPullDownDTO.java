package com.xhg.ops.workorders.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "app物料下拉框基础类")
@Data
public class AppMaterialPullDownDTO {

    @ApiModelProperty(value = "物料编号")
    private String materialNo;
    
    @ApiModelProperty(value = "物料名称")
    private String materialName;
    
}
