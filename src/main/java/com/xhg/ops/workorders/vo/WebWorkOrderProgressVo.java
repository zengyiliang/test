package com.xhg.ops.workorders.vo;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(value = "web工单进度")
@Data
public class WebWorkOrderProgressVo {

    @ApiModelProperty(value = "操作时间 yyyy.MM.dd hh:mm")
    private Date date;

    @ApiModelProperty(value = "职位名称")
    private String positionName;

    @ApiModelProperty(value = "操作人")
    private String userName;

    @ApiModelProperty(value = "执行结果")
    private String status;
    
    @ApiModelProperty(value = "操作")
    private String operation;
    
    @ApiModelProperty(value = "备注")
    private String remark;

}
