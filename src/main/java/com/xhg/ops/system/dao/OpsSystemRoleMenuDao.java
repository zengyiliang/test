package com.xhg.ops.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.xhg.ops.system.entity.OpsSystemRoleMenuDO;
import com.xhg.ops.system.mapper.OpsSystemRoleMenuMapper;

/**
 * 
 * @ClassName: OpsSystemRoleMenuDao
 * @Description: TODO
 * @author liun
 * @date 2018-07-12
 */
@Repository
public interface OpsSystemRoleMenuDao extends OpsSystemRoleMenuMapper {

	/**
	 * 查询所有角色对应的菜单 
	 * @return
	 */
	List<OpsSystemRoleMenuDO> queryByRoleMenuList(List<Integer> roleId);
	/**
	 * 根据角色查询菜单
	 * @param roleId
	 * @return
	 */
	List<OpsSystemRoleMenuDO> queryByRoleId(Integer roleId);
	/**
	 * 查询角色并且list中存在的数据 
	 * @return
	 */
	List<Integer> 	queryByListExist(@Param("list")List<Integer> list,@Param("roleId")Integer roleId);
	/**
	 * 修改list中不存在的菜单id为失效
	 * @param list
	 * @param roleId
	 * @return
	 */
	Integer updateByRoleMenu(@Param("list")List<Integer> list,@Param("roleId")Integer roleId);
}

