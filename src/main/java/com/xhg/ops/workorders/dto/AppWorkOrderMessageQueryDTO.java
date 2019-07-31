package com.xhg.ops.workorders.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "app工单消息查询传参")
@Data
public class AppWorkOrderMessageQueryDTO {

    @ApiModelProperty(value = "当前页码")
    private Integer currentPage=1;

    @ApiModelProperty(value = "每一页显示的数目")
    private Integer pageSize = 10;
}
