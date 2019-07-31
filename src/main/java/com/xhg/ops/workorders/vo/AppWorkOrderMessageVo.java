package com.xhg.ops.workorders.vo;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(value = "app工单消息")
@Data
public class AppWorkOrderMessageVo extends WorkOrderBaseVo{

    @ApiModelProperty(value = "消息Id")
    private String messageId;
    
    @ApiModelProperty(value = "消息日期 yyyy.MM.dd hh:mm")
    private Date date;

    @ApiModelProperty(value = "职位名称")
    private String positionName;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "工单状态")
    private String status;
    
    @ApiModelProperty(value = "消息状态 0未读 1已读")
    private String read;

}
