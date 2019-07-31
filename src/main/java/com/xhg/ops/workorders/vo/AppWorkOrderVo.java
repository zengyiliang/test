package com.xhg.ops.workorders.vo;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(value = "app工单查询")
@Data
public class AppWorkOrderVo extends WorkOrderBaseVo {

    @ApiModelProperty(value = "时间 yyyy.MM.dd hh:mm")
    private Date date;

    @ApiModelProperty(value = "工单描述")
    private String description;
    
    @ApiModelProperty(value = "工单紧急程度")
    private String level;
    
    @ApiModelProperty(value = "工单紧急程度 标签颜色 RGB, 例如：'255,182,193'")
    private String levelColor;
    
    @ApiModelProperty(value = "工单类型")
    private String type;

    @ApiModelProperty(value = "工单状态")
    private String status;
    
    @ApiModelProperty(value = 
    		"工单状态标识 1待初审 2待分配 3待签单 4待出发 5待签到 6待处理 7待审批物料申请 8待审核物料申请 9待发货 10待查收 11待审核 12待复核 "
    		+ "13已初审 14已分配 15已签单 16已出发 17已签到 18已处理 19已申请更换物料 20已审批物料申请 21已审核物料申请 22已发货 23已查收 24已审核 25已复核 26已拒绝 27已撤回 28已转单 29已创建 30已关闭")
    private String statusCode;

}
