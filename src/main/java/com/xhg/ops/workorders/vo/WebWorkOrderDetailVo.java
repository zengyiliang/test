package com.xhg.ops.workorders.vo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(value = "web工单详情基本信息")
@Data
public class WebWorkOrderDetailVo extends WorkOrderBaseVo{
    
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
    
    @ApiModelProperty(value = "工单紧急程度")
    private String level;
    
    @ApiModelProperty(value = "工单状态")
    private String status;

}
