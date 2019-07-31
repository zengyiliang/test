package com.xhg.ops.system.dao;

import com.xhg.ops.system.entity.SystemUserDO;
import com.xhg.ops.system.model.OpsSystemUserRole;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpsSystemUserRoleDao {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(OpsSystemUserRole record);

    OpsSystemUserRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OpsSystemUserRole record);

    int updateByPrimaryKey(OpsSystemUserRole record);

    int queryAllCount(OpsSystemUserRole record);

    List<OpsSystemUserRole> queryAllList(OpsSystemUserRole record);

    int deleteByUserId(Integer userId);
    
    /**
     * 查询所有用户对应的角色缓存数据库
     * @return
     */
    List<SystemUserDO> selectByUserRoleList();

    int updateRoleByUserId(OpsSystemUserRole opsSystemUserRole);
}
