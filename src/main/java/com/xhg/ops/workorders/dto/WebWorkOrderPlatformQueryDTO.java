package com.xhg.ops.workorders.dto;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "web工单工作台列表查询传参")
@Data
public class WebWorkOrderPlatformQueryDTO {

    @ApiModelProperty(value = "工单主状态 1待处理  2进行中 3已结束", required=true)
    @NotBlank(message = "mainState不能为空")
    private String mainState;
    
    @ApiModelProperty(value = "工单从状态  1待初审 2待分配 3待签单 4待出发 5待签到 6待处理 7挂起 8待审核 9待复核")
    private String subState;
	
    @ApiModelProperty(value = "当前页码")
    private Integer currentPage=1;

    @ApiModelProperty(value = "每一页显示的数目")
    private Integer pageSize = 5;
}
