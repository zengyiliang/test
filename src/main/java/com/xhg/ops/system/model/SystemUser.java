package com.xhg.ops.system.model;

import java.io.Serializable;
import java.util.List;

import com.xhg.ops.common.BasePojo;
import com.xhg.ops.system.entity.OpsSystemMenuDO;
import com.xhg.ops.system.entity.OpsSystemRoleDO;
import com.xhg.ops.system.entity.OpsSystemRoleMenuDO;
import com.xhg.ops.system.service.OpsSystemRoleMenuService;
public class SystemUser  extends BasePojo implements Serializable {

    private static final long serialVersionUID=1L;

    private String userNo;

    private String phone;

    private String name;

    private String password;

    private Integer deptId;

    private Integer positionId;

    private Integer refAreaId;

    private String roleNames;

    private Integer userType;

    private String province;

    private String city;

    private String cityArea;

    private String cityStreet;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityArea() {
        return cityArea;
    }

    public void setCityArea(String cityArea) {
        this.cityArea = cityArea;
    }

    public String getCityStreet() {
        return cityStreet;
    }

    public void setCityStreet(String cityStreet) {
        this.cityStreet = cityStreet;
    }

    private List<OpsSystemRoleDO>  opsSystemRoleDOList;
    
    private List<OpsSystemRoleMenuDO> opsSystemRoleMenuDOs;

    public List<OpsSystemRoleDO> getOpsSystemRoleDOList() {
        return opsSystemRoleDOList;
    }

    public void setOpsSystemRoleDOList(List<OpsSystemRoleDO> opsSystemRoleDOList) {
        this.opsSystemRoleDOList = opsSystemRoleDOList;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo == null ? null : userNo.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public Integer getRefAreaId() {
        return refAreaId;
    }

    public void setRefAreaId(Integer refAreaId) {
        this.refAreaId = refAreaId;
    }

    public String getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames == null ? null : roleNames.trim();
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

	public List<OpsSystemRoleMenuDO> getOpsSystemRoleMenuDOs() {
		return opsSystemRoleMenuDOs;
	}

	public void setOpsSystemRoleMenuDOs(List<OpsSystemRoleMenuDO> opsSystemRoleMenuDOs) {
		this.opsSystemRoleMenuDOs = opsSystemRoleMenuDOs;
	}

	
    
}
