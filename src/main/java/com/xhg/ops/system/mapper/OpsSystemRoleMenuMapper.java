package com.xhg.ops.system.mapper;

import java.util.List;
import java.util.Map;

import com.xhg.ops.system.entity.OpsSystemRoleMenuDO;

/**
 * 
 * @ClassName: OpsSystemRoleMenuMapper
 * @Description: TODO
 * @author liun
 * @date 2018-07-12
 */
public interface OpsSystemRoleMenuMapper {
	
	Integer insert(OpsSystemRoleMenuDO opsSystemRoleMenu);
	
	void update(OpsSystemRoleMenuDO opsSystemRoleMenu);
	
	void updateBySelective(OpsSystemRoleMenuDO opsSystemRoleMenu);

	void delete(Integer id);
	
	OpsSystemRoleMenuDO queryById(Integer id);
	
	Integer queryAllCount(Map<String,Object> params);
	
	List<OpsSystemRoleMenuDO> queryAllList(Map<String,Object> params);

	void deleteBatch(List<Integer> ids);

	void insertByBatch(List<OpsSystemRoleMenuDO> opsSystemRoleMenuDoList);
}