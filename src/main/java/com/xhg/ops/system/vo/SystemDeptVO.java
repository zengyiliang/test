package com.xhg.ops.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "部门对象")
@Data
public class SystemDeptVO {


    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "部门编码")
    private String deptId;

}
