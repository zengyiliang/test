package com.xhg.ops.system.service;

import java.util.List;
import java.util.Map;

import com.xhg.ops.system.entity.OpsSystemRoleDO;
import com.xhg.ops.system.entity.OpsSystemRoleMenuDO;
import com.xhg.ops.system.vo.rolemeun.OpsSystemRoleMenuRespVo;
import com.xhg.ops.system.vo.rolemeun.SystemRoleQueryRespVo;
import com.xhg.ops.system.vo.rolemeun.SystemRoleQueryVO;



/**
 * 
 * @ClassName: OpsSystemRoleService
 * @Description: TODO
 * @author lilun
 * @date 2018-07-12
 */
public interface OpsSystemRoleService {

	Integer insert(OpsSystemRoleDO opsSystemRoleDO);
	
	
	void update(OpsSystemRoleDO opsSystemRoleDO);
	
	void updateBySelective(OpsSystemRoleDO opsSystemRoleDO);

	void delete(Integer id);
	
	OpsSystemRoleDO queryById(Integer id);
	
	Integer queryAllCount(Map<String,Object> params);
	
	List<OpsSystemRoleDO> queryAllList(Map<String,Object> params);
	
	void deleteBatch(List<Integer> ids);

	void insertByBatch(List<OpsSystemRoleDO> opsSystemRoleDoList);
	
	List<OpsSystemRoleMenuRespVo> queryRoleIdMenu(Integer roleId);
	/**
	 * 新增角色及对应菜单
	 * @param opsSystemRoleDO
	 * @param menuDOs
	 * @return
	 */
	Integer insertRoleMenu(OpsSystemRoleDO opsSystemRoleDO,List<OpsSystemRoleMenuDO> menuDOs);
	/**
	 * 修改角色及对应菜单
	 * @param opsSystemRoleDO
	 * @param menuDOs
	 * @return
	 */
	Integer updateRoleMenu(OpsSystemRoleDO opsSystemRoleDO,List<OpsSystemRoleMenuDO> menuDOs);
	/**
	 * 获取角色列表
	 */
	List<SystemRoleQueryVO> querySystemRoleList(Map<String,Object> params)throws Exception;
	/**
	 * 查询角色列表及创建人
	 * @param params
	 * @return
	 */
	List<OpsSystemRoleDO> queryRoleList(Map<String,Object> params);
	/**
	 * 获取菜单
	 * @return
	 */
	 List<OpsSystemRoleMenuRespVo> queryMenu();
}
