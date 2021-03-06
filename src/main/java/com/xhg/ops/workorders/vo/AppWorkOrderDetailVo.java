package com.xhg.ops.workorders.vo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(value = "app工单详情")
@Data
public class AppWorkOrderDetailVo extends WorkOrderBaseVo {

    @ApiModelProperty(value = "工单类型")
    private String type;

    @ApiModelProperty(value = "工单来源")
    private String source;

    @ApiModelProperty(value = "创建时间 yyyy.MM.dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy.MM.dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "创建用户名称")
    private String createUserName;

    @ApiModelProperty(value = "工单描述")
    private String description;
    
    @ApiModelProperty(value = "设备ID")
    private String deviceId;
    
    @ApiModelProperty(value = "站点编码")
    private String siteCode;
    
    @ApiModelProperty(value = "设备地址")
    private String deviceAddress;
    
    @ApiModelProperty(value = "图片附件url地址列表")
    private List<String> attachmentUrls;
    
    @ApiModelProperty(value = "联系方式")
    private String contactInfo;
    
    @ApiModelProperty(value = "设备坐标经度")
    private String deviceLongitude;
    
    @ApiModelProperty(value = "设备坐标纬度")
    private String deviceLatitude;
    
    @ApiModelProperty(value = "是否显示物料信息 0不显示 1显示")
    private String showMaterialApplyInfo;
    
    @ApiModelProperty(value = "是否显示处理信息 0不显示 1显示")
    private String showOrderRecord;
    
    @ApiModelProperty(value = "是否显示操作按钮 0不显示 1显示")
    private String showOperationButton;
    
    @ApiModelProperty(value = "工单操作按钮")
    private AppWorkOrderOperationVo operation;

}
