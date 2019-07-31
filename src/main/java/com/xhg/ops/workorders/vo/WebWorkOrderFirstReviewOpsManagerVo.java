package com.xhg.ops.workorders.vo;

import com.xhg.ops.utils.SensitiveInfoUtils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@ApiModel(value = "web工单初审页面城市运维经理")
public class WebWorkOrderFirstReviewOpsManagerVo {

    @ApiModelProperty(value = "城市运维经理用户id")
    @Getter
    @Setter
    private String cityOpsManagerUserId;
    
    @ApiModelProperty(value = "城市运维经理名字")
    @Getter
    @Setter
    private String cityOpsManagerName;

    @ApiModelProperty(value = "电话")
    @Setter
    private String phone;

	public String getPhone() {
		return SensitiveInfoUtils.mobilePhone(phone);
	}

}
