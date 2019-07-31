package com.xhg.ops.workorders.vo;

import com.xhg.ops.utils.SensitiveInfoUtils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@ApiModel(value = "web工单分配运维专员信息")
@Data
public class WebWorkOrderAssignOpsVo {

    @ApiModelProperty(value = "运维人员用户id")
    @Getter
    @Setter
    private String cityOpsUserId;
    
    @ApiModelProperty(value = "运维人员名字")
    @Getter
    @Setter
    private String cityOpsName;

    @ApiModelProperty(value = "电话")
    @Setter
    private String phone;

	public String getPhone() {
		return SensitiveInfoUtils.mobilePhone(phone);
	}

}
