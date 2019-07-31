package com.xhg.ops.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xhg.ops.system.dao.OpsSystemMenuDao;
import com.xhg.ops.system.dao.OpsSystemRoleMenuDao;
import com.xhg.ops.system.dao.OpsSystemUserRoleDao;
import com.xhg.ops.system.entity.OpsSystemMenuDO;
import com.xhg.ops.system.entity.OpsSystemRoleMenuDO;
import com.xhg.ops.system.model.OpsSystemUserRole;
import com.xhg.ops.system.service.OpsSystemRoleMenuService;
import com.xhg.ops.system.vo.rolemeun.OpsSystemRoleMenuRespVo;
import com.xhg.ops.utils.RedisKey;

import redis.clients.jedis.JedisCluster;

/**
 * 
 * @ClassName: OpsSystemRoleMenuServiceImpl
 * @Description: TODO
 * @author liun
 * @param <E>
 * @date 2018-07-12
 */
@Service
public class OpsSystemRoleMenuServiceImpl implements OpsSystemRoleMenuService {

	private final static Logger logger = LoggerFactory.getLogger(OpsSystemRoleMenuServiceImpl.class);
	public static final int REFRESH_TOKEN_EXPIRE = 60 * 60 * 24 * 90;
	@Autowired
	private JedisCluster jedisCluster;
	@Autowired
	private OpsSystemRoleMenuDao opsSystemRoleMenuDao;
	@Autowired
	private OpsSystemUserRoleDao userRoleDao;
	@Autowired
	private OpsSystemMenuDao systemMenuDao;

	@Override
	public Integer insert(OpsSystemRoleMenuDO opsSystemRoleMenuDO) {
		try {
			return opsSystemRoleMenuDao.insert(opsSystemRoleMenuDO);
		} catch (Exception e) {
			logger.error("OpsSystemRoleMenuServiceImpl_insert", e);
			throw e;
		}
	}

	@Override
	public void update(OpsSystemRoleMenuDO opsSystemRoleMenuDO) {
		try {
			opsSystemRoleMenuDao.update(opsSystemRoleMenuDO);
		} catch (Exception e) {
			logger.error("OpsSystemRoleMenuServiceImpl_update", e);
			throw e;
		}
	}

	@Override
	public void updateBySelective(OpsSystemRoleMenuDO opsSystemRoleMenuDO) {
		try {
			opsSystemRoleMenuDao.updateBySelective(opsSystemRoleMenuDO);
		} catch (Exception e) {
			logger.error("OpsSystemRoleMenuServiceImpl_updateBySelective", e);
			throw e;
		}
	}

	@Override
	public void delete(Integer id) {
		try {
			opsSystemRoleMenuDao.delete(id);
		} catch (Exception e) {
			logger.error("OpsSystemRoleMenuServiceImpl_delete", e);
			throw e;
		}
	}

	@Override
	public OpsSystemRoleMenuDO queryById(Integer id) {
		return opsSystemRoleMenuDao.queryById(id);
	}

	@Override
	public Integer queryAllCount(Map<String, Object> params) {
		return opsSystemRoleMenuDao.queryAllCount(params);
	}

	@Override
	public List<OpsSystemRoleMenuDO> queryAllList(Map<String, Object> params) {
		return opsSystemRoleMenuDao.queryAllList(params);
	}

	@Override
	public void deleteBatch(List<Integer> ids) {
		try {
			opsSystemRoleMenuDao.deleteBatch(ids);
		} catch (Exception e) {
			logger.error("OpsSystemRoleMenuServiceImpl_deleteBatch", e);
			throw e;
		}
	}

	@Override
	public void insertByBatch(List<OpsSystemRoleMenuDO> opsSystemRoleMenuDoList) {
		try {
			opsSystemRoleMenuDao.insertByBatch(opsSystemRoleMenuDoList);
		} catch (Exception e) {
			logger.error("OpsSystemRoleMenuServiceImpl_insertByBatch", e);
			throw e;
		}
	}

	@Override
	public void queryByRoleMenuList() {
		// TODO Auto-generated method stub
		List<Integer> roles = new ArrayList<>();
		List<OpsSystemRoleMenuDO> menuDOs = opsSystemRoleMenuDao.queryByRoleMenuList(roles);
		Map<String, OpsSystemRoleMenuDO> map = new HashMap<>();
		for (OpsSystemRoleMenuDO roleMenuDO : menuDOs) {
			map.put(roleMenuDO.getRoleId() + roleMenuDO.getUrl(), roleMenuDO);
		}
		String jsonObject = JSON.toJSONString(map);
		jedisCluster.setex(RedisKey.ROLE_MEUN, REFRESH_TOKEN_EXPIRE, jsonObject);
	}

	@Override
	public List<OpsSystemRoleMenuDO> queryByRoleId(Integer roleId) {
		// TODO Auto-generated method stub
		return opsSystemRoleMenuDao.queryByRoleId(roleId);
	}

	public List<OpsSystemRoleMenuRespVo> getUserPrevilege(Integer userId) {
		OpsSystemUserRole record = new OpsSystemUserRole();
		record.setUserId(userId);
		List<OpsSystemUserRole> roles = userRoleDao.queryAllList(record);

		// 不是超级管理员，返回该用户对应的所有角色对应的所有菜单,组装成菜单树返回
		List<Integer> roleIds = roles.stream().map(OpsSystemUserRole::getRoleId).collect(Collectors.toList());
		List<OpsSystemRoleMenuDO> roleMenus = opsSystemRoleMenuDao.queryByRoleMenuList(roleIds);

		List<OpsSystemRoleMenuRespVo> menus = new ArrayList<>();
		if (!roleMenus.isEmpty()) {
			List<Integer> ids = roleMenus.stream().map(OpsSystemRoleMenuDO::getMenuId).collect(Collectors.toList());
			menuRecurition(null, systemMenuDao.queryAllListMenus(new HashMap<String, Object>()), menus, ids);
			return menus;
		} else {
			return menus;
		}
	}

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
	 *            拥有角色的菜单id
	 */
	public void menuRecurition(final OpsSystemRoleMenuRespVo parent, List<OpsSystemMenuDO> menus,
			List<OpsSystemRoleMenuRespVo> results, List<Integer> menuIds) {
		if (!menus.isEmpty()) {
			for (OpsSystemMenuDO item : menus) {
				OpsSystemRoleMenuRespVo vo = new OpsSystemRoleMenuRespVo();
				vo.setName(item.getName());
				vo.setCode(item.getCode());
				vo.setId(String.valueOf(item.getId()));
				vo.setUrl(item.getUrl());
				vo.setParentId(String.valueOf(item.getParentId()));
				vo.setIcon(item.getIcon());
				if (menuIds.contains(item.getId())) {
					vo.setShow(true);
				} else {
					vo.setShow(false);
				}
				if (0 == item.getParentId()) {
					// vo.setParentVo(null);
					results.add(vo);
				} else if (0 != item.getParentId() && null != parent) {
					parent.getRespVos().add(vo);
					// MenuVO mVo = parent.deepClone();
					// vo.setParentVo(mVo);
					// 如果有子菜单可见设置父菜单为可见
					if (vo.getShow()) {
						// setShow(vo);
						parent.setShow(true);
						if (!"0".equals(parent.getParentId())) {
							results.stream().filter(t -> {
								if (t.getId().equals(String.valueOf(parent.getParentId()))) {
									t.setShow(true);
									return true;
								}
								return false;
							}).findFirst();
						}
					}
				}

				if (null != item.getMenus() && !item.getMenus().isEmpty()) {
					List<OpsSystemRoleMenuRespVo> subMenus = new ArrayList<>();
					vo.setRespVos(subMenus);
					menuRecurition(vo, item.getMenus(), results, menuIds);
				}
			}
		}
	}

	@Override
	public List<OpsSystemRoleMenuRespVo> getUserOneTwoPrevilege(Integer userId) {
		// TODO Auto-generated method stub
		OpsSystemUserRole record = new OpsSystemUserRole();
		record.setUserId(userId);
		List<OpsSystemUserRole> roles = userRoleDao.queryAllList(record);

		// 不是超级管理员，返回该用户对应的所有角色对应的所有菜单,组装成菜单树返回
		List<Integer> roleIds = roles.stream().map(OpsSystemUserRole::getRoleId).collect(Collectors.toList());
		List<OpsSystemRoleMenuDO> roleMenus = opsSystemRoleMenuDao.queryByRoleMenuList(roleIds);

		List<OpsSystemRoleMenuRespVo> menus = new ArrayList<>();
		if (!roleMenus.isEmpty()) {
			List<Integer> ids = roleMenus.stream().map(OpsSystemRoleMenuDO::getMenuId).collect(Collectors.toList());
			menuRecurition(null, systemMenuDao.queryAllListTwoMenus(new HashMap<String, Object>()), menus, ids);
			return menus;
		} else {
			return menus;
		}
	}

	@Override
	public Map<String, Map<String, Boolean>> getUserTwoFollowingPrevilege(Integer userId) {
		// TODO Auto-generated method stub
		OpsSystemUserRole record = new OpsSystemUserRole();
		record.setUserId(userId);
		List<OpsSystemUserRole> roles = userRoleDao.queryAllList(record);

		// 不是超级管理员，返回该用户对应的所有角色对应的所有菜单,组装成菜单树返回
		List<Integer> roleIds = roles.stream().map(OpsSystemUserRole::getRoleId).collect(Collectors.toList());
		List<OpsSystemRoleMenuDO> roleMenus = opsSystemRoleMenuDao.queryByRoleMenuList(roleIds);

		Map<String, Map<String, Boolean>> menus = new HashMap<>();
		if (!roleMenus.isEmpty()) {
			List<Integer> ids = roleMenus.stream().map(OpsSystemRoleMenuDO::getMenuId).collect(Collectors.toList());
			menuRecurition(null,systemMenuDao.queryUserTwoFollowingPrevilege(), menus, ids);
			return menus;
		} else {
			return menus;
		}
	}

	/**
	 * 递归组装按钮菜单
	 * 
	 * @param menus
	 *            所有的菜单
	 * @param results
	 *            封装后结果
	 * @param menuIds
	 *            拥有角色的菜单id
	 */
	public void menuRecurition(final OpsSystemMenuDO parent,List<OpsSystemMenuDO> menus, Map<String, Map<String, Boolean>> results,
			List<Integer> menuIds) {
		Map<String, Boolean> mapButton = new HashMap<>();
		if (!menus.isEmpty()) {
			for (OpsSystemMenuDO item : menus) {


					
					if (null != item.getMenus() && item.getMenus().size()>0) {

						menuRecurition(item,item.getMenus(), results, menuIds);
					}else {
						if (parent!=null) {
							if (menuIds.contains(item.getId())) {
								mapButton.put(item.getCode(), true);
							} else {
								mapButton.put(item.getCode(), false);
							}
						}
						
					}
				
				
			}
			if (parent!=null) {
				results.put(parent.getCode(), mapButton);
			}
			
		}
	}
}
