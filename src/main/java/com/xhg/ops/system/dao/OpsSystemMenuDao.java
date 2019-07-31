package com.xhg.ops.system.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xhg.ops.system.entity.OpsSystemMenuDO;
import com.xhg.ops.system.mapper.OpsSystemMenuMapper;

/**
 * 
 * @ClassName: OpsSystemMenuDao
 * @Description: TODO
 * @author lilun
 * @date 2018-07-12
 */
@Repository
public interface OpsSystemMenuDao extends OpsSystemMenuMapper {
	/**
	 * 根据路径查询是否有菜单权限
	 * @param url
	 * @return
	 */
	OpsSystemMenuDO queryByUrl(String url);
	/**
	 * 查询菜单树
	 * @param params
	 * @return
	 */
	List<OpsSystemMenuDO> queryAllListMenus(Map<String,Object> params);
	/**
	 * 查询一二级菜单树
	 * @param params
	 * @return
	 */
	List<OpsSystemMenuDO> queryAllListTwoMenus(Map<String,Object> params);
	/**
	 * 查询菜单列表
	 * @param params
	 * @return
	 */
	List<OpsSystemMenuDO> queryMenuList(Map<String,Object> params);
	/**
	 * 查询一二级菜单列表 
	 * @return
	 */
	List<OpsSystemMenuDO> queryAllSuperiorMenus();
	/**
	 * 查询二级及以下菜单树
	 * @param params
	 * @return
	 */
	List<OpsSystemMenuDO> queryUserTwoFollowingPrevilege();
}

