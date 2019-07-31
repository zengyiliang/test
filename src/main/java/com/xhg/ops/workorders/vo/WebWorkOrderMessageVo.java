package com.xhg.ops.workorders.vo;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(value = "web工单消息")
@Data
public class WebWorkOrderMessageVo {

    @ApiModelProperty(value = "工单编号")
    private String orderNo;
    
    @ApiModelProperty(value = "消息日期 yyyy.MM.dd hh:mm")
    private Date date;

    @ApiModelProperty(value = "职位名称")
    private String positionName;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "工单状态")
    private String status;

}
