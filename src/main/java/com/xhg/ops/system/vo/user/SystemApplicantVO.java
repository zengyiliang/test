package com.xhg.ops.system.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "布点系统--用户申请人信息对象")
public class SystemApplicantVO {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "申请人")
    private String name;

    @ApiModelProperty(value = "申请人联系电话")
    private String phone;

    @ApiModelProperty(value = "负责区域-省级code")
    private String province;

    @ApiModelProperty(value = "负责区域-省级name")
    private String provinceName;

    @ApiModelProperty(value = "负责区域-市级code")
    private String city;

    @ApiModelProperty(value = "负责区域-市级name")
    private String cityName;

    @ApiModelProperty(value = "负责区域-区级code")
    private String cityArea;

    @ApiModelProperty(value = "负责区域-区级name")
    private String cityAreaName;

    @ApiModelProperty(value = "申请人所属分公司")
    private String areaZone;

    @ApiModelProperty(value = "点位街道位置")
    private List<AreaCode> cityStreetList;

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityAreaName() {
        return cityAreaName;
    }

    public void setCityAreaName(String cityAreaName) {
        this.cityAreaName = cityAreaName;
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

    public String getAreaZone() {
        return areaZone;
    }

    public void setAreaZone(String areaZone) {
        this.areaZone = areaZone;
    }

    public List<AreaCode> getCityStreetList() {
        return cityStreetList;
    }

    public void setCityStreetList(List<AreaCode> cityStreetList) {
        this.cityStreetList = cityStreetList;
    }


    //区对象
    public class AreaCode{

        private String areaCode;

        private String areaName;

        public String getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }
    }


}
