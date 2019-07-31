package com.xhg.ops.system.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xhg.ops.system.dao.OpsSystemMenuDao;
import com.xhg.ops.system.dao.OpsSystemRoleDao;
import com.xhg.ops.system.dao.OpsSystemRoleMenuDao;
import com.xhg.ops.system.entity.OpsSystemRoleDO;
import com.xhg.ops.system.entity.OpsSystemRoleMenuDO;
import com.xhg.ops.system.filter.UserContext;
import com.xhg.ops.system.service.OpsSystemMenuService;
import com.xhg.ops.system.service.OpsSystemRoleMenuService;
import com.xhg.ops.system.service.OpsSystemRoleService;
import com.xhg.ops.system.vo.rolemeun.OpsSystemRoleMenuRespVo;
import com.xhg.ops.system.vo.rolemeun.SystemRoleQueryVO;

/**
 * 
 * @ClassName: OpsSystemRoleServiceImpl
 * @Description: TODO
 * @author lilun
 * @date 2018-07-12
 */
@Service
public class OpsSystemRoleServiceImpl implements OpsSystemRoleService {

	private final static Logger logger = LoggerFactory.getLogger(OpsSystemRoleServiceImpl.class);

	@Autowired
	private OpsSystemRoleDao opsSystemRoleDao;
	@Autowired
	private OpsSystemRoleMenuDao roleMenuDao;
	@Autowired
	private OpsSystemRoleMenuDao opsSystemRoleMenuDao;
	@Autowired
	private OpsSystemMenuDao systemMenuDao;
	@Autowired
	private OpsSystemRoleMenuService opsSystemMenuService;

	@Override
	public Integer insert(OpsSystemRoleDO opsSystemRoleDO) {
		try {
			return opsSystemRoleDao.insert(opsSystemRoleDO);
		} catch (Exception e) {
			logger.error("OpsSystemRoleServiceImpl_insert", e);
			throw e;
		}
	}

	@Override
	public void update(OpsSystemRoleDO opsSystemRoleDO) {
		try {
			opsSystemRoleDao.update(opsSystemRoleDO);
		} catch (Exception e) {
			logger.error("OpsSystemRoleServiceImpl_update", e);
			throw e;
		}
	}

	@Override
	public void updateBySelective(OpsSystemRoleDO opsSystemRoleDO) {
		try {
			opsSystemRoleDao.updateBySelective(opsSystemRoleDO);
		} catch (Exception e) {
			logger.error("OpsSystemRoleServiceImpl_updateBySelective", e);
			throw e;
		}
	}

	@Override
	public void delete(Integer id) {
		try {
			opsSystemRoleDao.delete(id);
		} catch (Exception e) {
			logger.error("OpsSystemRoleServiceImpl_delete", e);
			throw e;
		}
	}

	@Override
	public OpsSystemRoleDO queryById(Integer id) {
		return opsSystemRoleDao.queryById(id);
	}

	@Override
	public Integer queryAllCount(Map<String, Object> params) {
		return opsSystemRoleDao.queryAllCount(params);
	}

	@Override
	public List<OpsSystemRoleDO> queryAllList(Map<String, Object> params) {
		return opsSystemRoleDao.queryAllList(params);
	}

	@Override
	public void deleteBatch(List<Integer> ids) {
		try {
			opsSystemRoleDao.deleteBatch(ids);
		} catch (Exception e) {
			logger.error("OpsSystemRoleServiceImpl_deleteBatch", e);
			throw e;
		}
	}

	@Override
	public void insertByBatch(List<OpsSystemRoleDO> opsSystemRoleDoList) {
		try {
			opsSystemRoleDao.insertByBatch(opsSystemRoleDoList);
		} catch (Exception e) {
			logger.error("OpsSystemRoleServiceImpl_insertByBatch", e);
			throw e;
		}
	}

	/**
	 * XHG+五位编号（从1开始，不够前补0）
	 * 
	 * 
	 * @param equipmentNo
	 *            最新编号
	 * @return
	 */
	public static String getNewEquipmentNo(String equipmentNo) {
		String newEquipmentNo = "00001";
		String equipmentType = equipmentNo.substring(0, 3);
		equipmentNo = equipmentNo.substring(3, equipmentNo.length());
		if (equipmentNo != null && !equipmentNo.isEmpty()) {
			int newEquipment = Integer.parseInt(equipmentNo) + 1;
			newEquipmentNo = String.format(equipmentType + "%05d", newEquipment);
		}

		return newEquipmentNo;
	}

	@Override
	public Integer insertRoleMenu(OpsSystemRoleDO opsSystemRoleDO, List<OpsSystemRoleMenuDO> menuDOs) {
		// TODO Auto-generated method stub
		String maxRoleCode = opsSystemRoleDao.queryMaxRoleCode();
		String roleCode = getNewEquipmentNo(maxRoleCode);
		opsSystemRoleDO.setRoleCode(roleCode);
		opsSystemRoleDO.setCreatedTime(new Date());
		opsSystemRoleDO.setUpdatedTime(new Date());
		opsSystemRoleDO.setCreatedUserId(UserContext.getUser().getId());
		opsSystemRoleDO.setUpdatedUserId(UserContext.getUser().getId());
		opsSystemRoleDO.setEnable(1);
		int roleId = opsSystemRoleDao.insert(opsSystemRoleDO);
		if (roleId > 0) {

			for (OpsSystemRoleMenuDO opsSystemRoleMenuDO : menuDOs) {
				opsSystemRoleMenuDO.setCreatedTime(new Date());
				opsSystemRoleMenuDO.setUpdatedTime(new Date());
				opsSystemRoleMenuDO.setCreatedUserId(UserContext.getUser().getId());
				opsSystemRoleMenuDO.setUpdatedUserId(UserContext.getUser().getId());
				opsSystemRoleMenuDO.setRoleId(opsSystemRoleDO.getId());
				opsSystemRoleMenuDO.setEnable(1);
				roleMenuDao.insert(opsSystemRoleMenuDO);
			}
		}
		// 查询所有角色对应的菜单放到缓存里面
		opsSystemMenuService.queryByRoleMenuList();
		return roleId;
	}

	@Override
	public List<SystemRoleQueryVO> querySystemRoleList(Map<String, Object> params) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<SystemRoleQueryVO> systemRoleQueryVOList = new ArrayList<>();

		List<OpsSystemRoleDO> opsSystemRoleDOList = opsSystemRoleDao.queryAllList(params);

		if (opsSystemRoleDOList != null && opsSystemRoleDOList.size() > 0) {
			opsSystemRoleDOList.forEach(role -> {
				SystemRoleQueryVO vo = new SystemRoleQueryVO();
				vo.setRoleId(role.getId().toString());
				vo.setCreatedTime(sdf.format(role.getCreatedTime()));
				vo.setRoleName(role.getName());
				// 创建人mock
				vo.setCreatedName("");
				systemRoleQueryVOList.add(vo);
			});
		}

		return systemRoleQueryVOList;
	}

	@Override
	public List<OpsSystemRoleMenuRespVo> queryRoleIdMenu(Integer roleId) {
		// TODO Auto-generated method stub

		// 不是超级管理员，返回该用户对应的所有角色对应的所有菜单,组装成菜单树返回
		List<Integer> roleIds = new ArrayList<>();
		roleIds.add(roleId);
		List<OpsSystemRoleMenuDO> roleMenus = opsSystemRoleMenuDao.queryByRoleMenuList(roleIds);

		List<OpsSystemRoleMenuRespVo> menus = new ArrayList<>();
		if (!roleMenus.isEmpty()) {
			List<Integer> ids = roleMenus.stream().map(OpsSystemRoleMenuDO::getMenuId).collect(Collectors.toList());
			opsSystemMenuService.menuRecurition(null, systemMenuDao.queryAllListMenus(new HashMap<String, Object>()),
					menus, ids);
			return menus;
		} else {
			return menus;
		}
	}

	@Transactional
	public Integer updateRoleMenu(OpsSystemRoleDO opsSystemRoleDO, List<OpsSystemRoleMenuDO> menuDOs) {
		// TODO Auto-generated method stub
		try {
			List<Integer> ids = menuDOs.stream().map(OpsSystemRoleMenuDO::getMenuId).collect(Collectors.toList());
			opsSystemRoleDao.updateBySelective(opsSystemRoleDO);
			opsSystemRoleMenuDao.updateByRoleMenu(ids, opsSystemRoleDO.getId());
			List<Integer> exisIds = opsSystemRoleMenuDao.queryByListExist(ids, opsSystemRoleDO.getId());
			ids.removeAll(exisIds);
			for (Integer integer : ids) {
				OpsSystemRoleMenuDO opsSystemRoleMenuDO = new OpsSystemRoleMenuDO();
				opsSystemRoleMenuDO.setCreatedTime(new Date());
				opsSystemRoleMenuDO.setUpdatedTime(new Date());
				opsSystemRoleMenuDO.setCreatedUserId(UserContext.getUser().getId());
				opsSystemRoleMenuDO.setUpdatedUserId(UserContext.getUser().getId());
				opsSystemRoleMenuDO.setRoleId(opsSystemRoleDO.getId());
				opsSystemRoleMenuDO.setMenuId(integer);
				opsSystemRoleMenuDO.setEnable(1);
				roleMenuDao.insert(opsSystemRoleMenuDO);

			}
			// 查询所有角色对应的菜单放到缓存里面
			opsSystemMenuService.queryByRoleMenuList();
			return 1;
		} catch (Exception e) {
			// TODO: handle exception
		}

		return -1;
	}

	@Override
	public List<OpsSystemRoleDO> queryRoleList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return opsSystemRoleDao.queryRoleList(params);
	}

	@Override
	public List<OpsSystemRoleMenuRespVo> queryMenu() {
		// TODO Auto-generated method stub
		List<Integer> ids = new ArrayList<>();

		List<OpsSystemRoleMenuRespVo> menus = new ArrayList<>();
		opsSystemMenuService.menuRecurition(null, systemMenuDao.queryAllListMenus(new HashMap<String, Object>()), menus,
				ids);
		return menus;
	}

}
