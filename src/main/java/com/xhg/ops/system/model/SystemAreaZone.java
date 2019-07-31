package com.xhg.ops.system.model;

import com.xhg.ops.common.BasePojo;

import java.io.Serializable;
import java.util.Date;

public class SystemAreaZone implements Serializable {

    private static final long serialVersionUID=1L;

    private String name;

    private String code;


    private Integer id;

    private Integer createdUserId;

    private Integer updatedUserId;

    private Date createdTime;

    private Date updatedTime;

    private Integer enable;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(Integer createdUserId) {
        this.createdUserId = createdUserId;
    }

    public Integer getUpdatedUserId() {
        return updatedUserId;
    }

    public void setUpdatedUserId(Integer updatedUserId) {
        this.updatedUserId = updatedUserId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
