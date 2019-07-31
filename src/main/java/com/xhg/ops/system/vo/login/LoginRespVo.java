package com.xhg.ops.system.vo.login;

import java.util.List;
import java.util.Map;

import com.xhg.ops.system.vo.rolemeun.OpsSystemRoleMenuRespVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("登陆返回信息")
public class LoginRespVo {


    @ApiModelProperty(value = "用户token")
    private String token;

    @ApiModelProperty(value = "用户名称")
    private String name;
    @ApiModelProperty(value = "用户id")
    private String id;

    @ApiModelProperty(value = "用户电话")
    private String phone;
    @ApiModelProperty(value="所有菜单")
    private List<OpsSystemRoleMenuRespVo> menuRespVos;
    @ApiModelProperty(value="一二级菜单")
    private List<OpsSystemRoleMenuRespVo> menus;
    @ApiModelProperty(value="前端按钮权限")
   private Map<String, Map<String, Boolean>> permission;
   
   

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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

	public List<OpsSystemRoleMenuRespVo> getMenuRespVos() {
		return menuRespVos;
	}

	public void setMenuRespVos(List<OpsSystemRoleMenuRespVo> menuRespVos) {
		this.menuRespVos = menuRespVos;
	}

	public List<OpsSystemRoleMenuRespVo> getMenus() {
		return menus;
	}

	public void setMenus(List<OpsSystemRoleMenuRespVo> menus) {
		this.menus = menus;
	}

	public Map<String, Map<String, Boolean>> getPermission() {
		return permission;
	}

	public void setPermission(Map<String, Map<String, Boolean>> permission) {
		this.permission = permission;
	}

   
}
