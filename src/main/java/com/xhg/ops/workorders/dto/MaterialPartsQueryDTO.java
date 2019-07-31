package com.xhg.ops.workorders.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "物料配件查询传参")
@Data
public class MaterialPartsQueryDTO {
    
	@ApiModelProperty(value = "一页显示数量")
	private int pageSize = 10;
	
    @ApiModelProperty(value = "当前页")
    private int currentPage = 1;
    
}
