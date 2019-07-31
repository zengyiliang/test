package com.xhg.ops.workorders.vo;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(value = "app工单进度")
@Data
public class AppWorkOrderProgressVo {

    @ApiModelProperty(value = "操作时间 yyyy.MM.dd hh:mm")
    private Date date;

    @ApiModelProperty(value = "职位名称")
    private String positionName;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "工单状态")
    private String status;
    
    @ApiModelProperty(value = "原因备注")
    private String remark;

}
