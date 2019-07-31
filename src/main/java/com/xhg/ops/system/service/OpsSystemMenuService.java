package com.xhg.ops.system.service;

import java.util.List;
import java.util.Map;

import com.xhg.ops.system.entity.OpsSystemMenuDO;


/**
 * 
 * @ClassName: OpsSystemMenuService
 * @Description: TODO
 * @author lilun
 * @date 2018-07-12
 */
public interface OpsSystemMenuService {

	Integer insert(OpsSystemMenuDO opsSystemMenuDO);
	
	void update(OpsSystemMenuDO opsSystemMenuDO);
	
	void updateBySelective(OpsSystemMenuDO opsSystemMenuDO);

	void delete(Integer id);
	
	OpsSystemMenuDO queryById(Integer id);
	
	Integer queryAllCount(Map<String,Object> params);
	
	List<OpsSystemMenuDO> queryAllList(Map<String,Object> params);
	
	void deleteBatch(List<Integer> ids);

	void insertByBatch(List<OpsSystemMenuDO> opsSystemMenuDoList);
	/**
	 * 根据路径查询是否有菜单权限
	 * @param url
	 * @return
	 */
	OpsSystemMenuDO queryByUrl(String url);
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
	 * 查询一二级菜单树
	 * @param params
	 * @return
	 */
	List<OpsSystemMenuDO> queryAllListTwoMenus(Map<String,Object> params);
	/**
	 * 查询二级及以下菜单树
	 * @param params
	 * @return
	 */
	List<OpsSystemMenuDO> queryUserTwoFollowingPrevilege();
}
