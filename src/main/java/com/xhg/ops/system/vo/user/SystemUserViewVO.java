package com.xhg.ops.system.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@ApiModel(value = "用户查看详情对象")
@Data
public class SystemUserViewVO {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "工号")
    private String userNo;

    @ApiModelProperty(value = "所属部门")
    private String deptName;

    @ApiModelProperty(value = "所属分区")
    private String areaZone;

    @ApiModelProperty(value = "用户角色")
    private String roleName;

    @ApiModelProperty(value = "操作日志集合")
    private List<SystemUserOperateVO> userOperateList;


}
