package com.xhg.ops.workorders.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(value = "web物料申请信息")
@Data
public class WebWorkOrderDealMaterialVo extends WorkOrderBaseVo {

    @ApiModelProperty(value = "模块名称")
    private String moduleName;
    
    @ApiModelProperty(value = "物料名称")
    private String materialName;
    
    @ApiModelProperty(value = "收货地址")
    private String address;
    
    @ApiModelProperty(value = "联系人姓名")
    private String contactPersonName;
    
    @ApiModelProperty(value = "联系人电话")
    private String contactPersonPhone;
    
}
