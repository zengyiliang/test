package com.xhg.ops.workorders.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "app工单操作按钮")
@Data
public class AppWorkOrderOperationVo {

    @ApiModelProperty(value = "操作标识 1工单初审 2关闭工单 3工单分配 4拒绝工单 5签单 6申请撤单 7出发 8申请转单 9签到 10处理完成 11申请更换物料 12物料信息审批 13物料信息审核 14物料发货 15物料查收 16工单信息审核 17工单信息复核")
    private String code;
    
    @ApiModelProperty(value = "操作名称 ")
    private String name;
    
    @ApiModelProperty(value = "是否禁用 0不禁用 1禁用")
    private String isForbidden;
    
}
