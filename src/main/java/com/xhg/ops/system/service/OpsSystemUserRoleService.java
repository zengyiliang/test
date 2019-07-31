package com.xhg.ops.system.service;

import com.xhg.ops.system.model.OpsSystemUserRole;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface OpsSystemUserRoleService {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(OpsSystemUserRole record);

    OpsSystemUserRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OpsSystemUserRole record);

    int updateByPrimaryKey(OpsSystemUserRole record);

    int queryAllCount(OpsSystemUserRole record);

    List<OpsSystemUserRole> queryAllList(OpsSystemUserRole record);

    int deleteByUserId(Integer userId);
    /**
	 * 查询所有用户对应的角色放到缓存里面
	 * 
	 * @return
	 */
	void queryByUserRoleList();

    int updateRoleByUserId(OpsSystemUserRole opsSystemUserRole);
}
