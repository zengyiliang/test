package com.xhg.ops.workorders.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.xhg.ops.workorders.util.datadict.DataDict;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(value = "web工单处理-物料申请信息")
@Data
public class WebWorkOrderDealMaterialDTO extends WorkOrderBaseDTO {

    @ApiModelProperty(value = "故障模块", required=true)
    @NotNull(message = "failModule不能为空")
    private DataDict failModule;
    
    @ApiModelProperty(value = "更换配件", required=true)
    @NotNull(message = "changeParts不能为空")
    private DataDict changeParts;
    
    @ApiModelProperty(value = "省编码", required=true)
    @NotBlank(message = "provinceCode不能为空")
    private String provinceCode;
    
    @ApiModelProperty(value = "市编码", required=true)
    @NotBlank(message = "cityCode不能为空")
    private String cityCode;
    
    @ApiModelProperty(value = "区编码", required=true)
    @NotBlank(message = "districtCode不能为空")
    private String districtCode;
    
    @ApiModelProperty(value = "详细补充地址", required=true)
    @NotBlank(message = "address不能为空")
    private String address;
    
    @ApiModelProperty(value = "联系人姓名", required=true)
    @NotBlank(message = "contactPersonName不能为空")
    private String contactPersonName;
    
    @ApiModelProperty(value = "联系人电话", required=true)
    @NotBlank(message = "contactPersonPhone不能为空")
    private String contactPersonPhone;
    
}
