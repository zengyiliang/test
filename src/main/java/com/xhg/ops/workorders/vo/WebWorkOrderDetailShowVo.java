package com.xhg.ops.workorders.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(value = "web工单详情展示控制")
@Data
public class WebWorkOrderDetailShowVo extends WorkOrderBaseVo {

    @ApiModelProperty(value = "工单详情页-是否显示工单基本信息 0不显示 1显示")
    private String showBaseInfo;
    
    @ApiModelProperty(value = "工单详情页-是否显示物料申请信息 0不显示 1显示")
    private String showMaterialApplyInfo;
    
    @ApiModelProperty(value = "工单详情页-是否显示工单信息记录 0不显示 1显示")
    private String showOrderRecord;
    
    @ApiModelProperty(value = "工单详情页-是否显示流程进度 0不显示 1显示")
    private String showProgress;
    
    @ApiModelProperty(value = "工单状态")
    private String status;
    
    @ApiModelProperty(value = 
    		"工单状态标识 1待初审 2待分配 3待签单 4待出发 5待签到 6待处理 7待审批物料申请 8待审核物料申请 9待发货 10待查收 11待审核 12待复核 "
    		+ "13已初审 14已分配 15已签单 16已出发 17已签到 18已处理 19已申请更换物料 20已审批物料申请 21已审核物料申请 22已发货 23已查收 24已审核 25已复核 26已拒绝 27已撤回 28已转单 29已创建 30已关闭")
    private String statusCode;

}
