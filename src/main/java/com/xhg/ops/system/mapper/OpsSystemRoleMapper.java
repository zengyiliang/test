package com.xhg.ops.system.mapper;

import java.util.List;
import java.util.Map;

import com.xhg.ops.system.entity.OpsSystemRoleDO;

/**
 * 
 * @ClassName: OpsSystemRoleMapper
 * @Description: TODO
 * @author lilun
 * @date 2018-07-12
 */
public interface OpsSystemRoleMapper {
	
	Integer insert(OpsSystemRoleDO opsSystemRole);
	
	void update(OpsSystemRoleDO opsSystemRole);
	
	void updateBySelective(OpsSystemRoleDO opsSystemRole);

	void delete(Integer id);
	
	OpsSystemRoleDO queryById(Integer id);
	
	Integer queryAllCount(Map<String,Object> params);
	
	List<OpsSystemRoleDO> queryAllList(Map<String,Object> params);

	void deleteBatch(List<Integer> ids);

	void insertByBatch(List<OpsSystemRoleDO> opsSystemRoleDoList);
}