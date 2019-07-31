package com.xhg.ops.system.service;

import java.util.List;
import java.util.Map;

import com.xhg.ops.system.entity.OpsSystemMenuDO;
import com.xhg.ops.system.entity.OpsSystemRoleMenuDO;
import com.xhg.ops.system.vo.rolemeun.OpsSystemRoleMenuRespVo;

/**
 * 
 * @ClassName: OpsSystemRoleMenuService
 * @Description: TODO
 * @author liun
 * @date 2018-07-12
 */
public interface OpsSystemRoleMenuService {

	Integer insert(OpsSystemRoleMenuDO opsSystemRoleMenuDO);

	void update(OpsSystemRoleMenuDO opsSystemRoleMenuDO);

	void updateBySelective(OpsSystemRoleMenuDO opsSystemRoleMenuDO);

	void delete(Integer id);

	OpsSystemRoleMenuDO queryById(Integer id);

	/**
	 * 根据角色查询菜单
	 * 
	 * @param roleId
	 * @return
	 */
	List<OpsSystemRoleMenuDO> queryByRoleId(Integer roleId);

	Integer queryAllCount(Map<String, Object> params);

	List<OpsSystemRoleMenuDO> queryAllList(Map<String, Object> params);

	void deleteBatch(List<Integer> ids);

	void insertByBatch(List<OpsSystemRoleMenuDO> opsSystemRoleMenuDoList);

	/**
	 * 查询所有角色对应的菜单放到缓存里面
	 * 
	 * @return
	 */
	void queryByRoleMenuList();

	/**
	 * 查询菜单树
	 * 
	 * @param userId
	 * @return
	 */
	List<OpsSystemRoleMenuRespVo> getUserPrevilege(Integer userId);
	/**
	 * 查询一二级菜单树
	 * 
	 * @param userId
	 * @return
	 */
	List<OpsSystemRoleMenuRespVo> getUserOneTwoPrevilege(Integer userId);
	/**
	 * 查询二及以下级菜单树
	 * 
	 * @param userId
	 * @return
	 */
	Map<String, Map<String, Boolean>> getUserTwoFollowingPrevilege(Integer userId);

	/**
	 * 递归组装菜单树
	 * 
	 * @param parent
	 *            父节点
	 * @param menus
	 *            所有的菜单
	 * @param results
	 *            封装后结果
	 * @param menuIds
	 *            拥有的角色id
	 */
	void menuRecurition(final OpsSystemRoleMenuRespVo parent, List<OpsSystemMenuDO> menus,
			List<OpsSystemRoleMenuRespVo> results, List<Integer> menuIds);
	
	

}
