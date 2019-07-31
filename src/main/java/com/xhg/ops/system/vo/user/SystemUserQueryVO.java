package com.xhg.ops.system.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "用户分页查询对象")
public class SystemUserQueryVO {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "用户姓名")
    private String name;

    @ApiModelProperty(value = "电话号码")
    private String phone;

    @ApiModelProperty(value = "工号")
    private String userNo;

    @ApiModelProperty(value = "所属部门")
    private String deptName;

    @ApiModelProperty(value = "所属地区")
    private String areaZone;

    @ApiModelProperty(value = "用户角色")
    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getAreaZone() {
        return areaZone;
    }

    public void setAreaZone(String areaZone) {
        this.areaZone = areaZone;
    }
}
