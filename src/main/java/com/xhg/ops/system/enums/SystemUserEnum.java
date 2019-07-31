package com.xhg.ops.system.enums;


public enum SystemUserEnum {


    //1:创建账号、2:重置密码、3:修改密码:4:编辑信息
    CREATE_USER(1,"创建账号"),
    RESET_USER_PASSWORD(2,"重置密码"),
    UPDATE_USER_PASSWORD(3,"修改密码"),
    EDIT_USER(4,"编辑信息");

    SystemUserEnum(Integer code, String value){
        this.code = code;
        this.value = value;
    }

    private Integer code;

    private String value;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
