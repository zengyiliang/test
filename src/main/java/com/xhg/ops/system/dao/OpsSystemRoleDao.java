package com.xhg.ops.system.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xhg.ops.system.entity.OpsSystemRoleDO;
import com.xhg.ops.system.mapper.OpsSystemRoleMapper;

/**
 * 
 * @ClassName: OpsSystemRoleDao
 * @Description: TODO
 * @author lilun
 * @date 2018-07-12
 */
@Repository
public interface OpsSystemRoleDao extends OpsSystemRoleMapper {
	
	/**
	 * 查询角色编码是否存在
	 * @param roleCode
	 * @return
	 */
	OpsSystemRoleDO queryByRoleCode(String roleCode);
	/**
	 * 查询最大的角色编码
	 * @return
	 */
	String queryMaxRoleCode();
	/**
	 * 查询角色列表及创建人
	 * @param params
	 * @return
	 */
	List<OpsSystemRoleDO> queryRoleList(Map<String,Object> params);
	
}

