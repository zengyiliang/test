package com.xhg.ops.workorders.vo;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(value = "web工单工作台列表")
@Data
public class WebWorkOrderPlatformListVo extends WorkOrderBaseVo {

    @ApiModelProperty(value = "创建时间 yyyy.MM.dd hh:mm")
    private Date createDate;
    
    @ApiModelProperty(value = "创建人")
    private String createUserName;
    
    @ApiModelProperty(value = "工单类型")
    private String type;
    
    @ApiModelProperty(value = "工单状态")
    private String status;
    
    @ApiModelProperty(value = 
    		"工单状态标识 1待初审 2待分配 3待签单 4待出发 5待签到 6待处理 7待审批物料申请 8待审核物料申请 9待发货 10待查收 11待审核 12待复核 "
    		+ "13已初审 14已分配 15已签单 16已出发 17已签到 18已处理 19已申请更换物料 20已审批物料申请 21已审核物料申请 22已发货 23已查收 24已审核 25已复核 26已拒绝 27已撤回 28已转单 29已创建 30已关闭")
    private String statusCode;
    
	@ApiModelProperty(value = "操作标识列表 1工单初审 1001关闭工单 2工单分配 2001拒绝工单 3签单 3001申请撤单 4出发 4001申请转单 5签到 6处理工单 6001申请更换物料 7物料信息审批 8物料信息审核 9物料发货 10物料查收 11工单信息审核 12工单信息复核")
	private List<String> operationCode;
	
/*    @ApiModelProperty(value = "操作 1工单初审 2关闭工单 3工单分配 4拒绝工单 5签单 6申请撤单 7出发 8申请转单 9签到 10处理完成 11申请更换物料 12物料信息审批 13物料信息审核 14物料发货 15物料查收 16工单信息审核 17工单信息复核")
    private List<WebWorkOrderOperationVo> operation;*/

}
