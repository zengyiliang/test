package com.xhg.ops.system.vo.dept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel(value = "部门职位集合")
@Data
public class SystemDeptPositionVO {

    @ApiModelProperty(value = "key")
    private String key;

    @ApiModelProperty(value = "value")
    private String value;

    @ApiModelProperty(value = "节点")
    private String note;

    @ApiModelProperty(value = "子集合")
    private List<SystemDeptPositionVO> list;
}
