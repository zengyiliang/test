package com.xhg.ops.workorders.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(value = "web新建工单传参")
@Data
public class WebWorkOrderCreateDTO {

    @ApiModelProperty(value = "工单类型 1故障维修", required=true)
    @NotNull(message = "type不能为空")
    private WorkOrderPullDownDTO type;
	
    @ApiModelProperty(value = "工单来源 1故障上报 2用户反馈 3市场反馈4运维反馈", required=true)
    @NotNull(message = "source不能为空")
    private WorkOrderPullDownDTO source;

    @ApiModelProperty(value = "工单描述")
    @NotBlank(message = "description不能为空")
    private String description;
    
    @ApiModelProperty(value = "设备编号/机器编码")
    @NotBlank(message = "deviceId不能为空")
    private String deviceId;
    
    @ApiModelProperty(value = "设备地址")
    @NotBlank(message = "deviceAddress不能为空")
    private String deviceAddress;
    
    @ApiModelProperty(value = "图片附件url地址列表")
    private List<String> attachmentUrls;
    
    @ApiModelProperty(value = "联系方式")
    @NotBlank(message = "contactInfo不能为空")
    private String contactInfo;
    
    @ApiModelProperty(value = "区编码")
    @NotBlank(message = "areaCode不能为空")
    private String areaCode;
    
    @ApiModelProperty(value = "站点编码")
    @NotBlank(message = "siteCode不能为空")
    private String siteCode;
    
    @ApiModelProperty(value = "坐标经度")
    @NotBlank(message = "longitude不能为空")
    private String longitude;
    
    @ApiModelProperty(value = "坐标纬度")
    @NotBlank(message = "latitude不能为空")
    private String latitude;

}
