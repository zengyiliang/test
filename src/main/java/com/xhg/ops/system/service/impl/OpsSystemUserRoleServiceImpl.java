package com.xhg.ops.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xhg.ops.system.dao.OpsSystemUserRoleDao;
import com.xhg.ops.system.entity.SystemRoleDO;
import com.xhg.ops.system.entity.SystemUserDO;
import com.xhg.ops.system.model.OpsSystemUserRole;
import com.xhg.ops.system.service.OpsSystemUserRoleService;
import com.xhg.ops.utils.RedisKey;

import redis.clients.jedis.JedisCluster;

@Service
public class OpsSystemUserRoleServiceImpl implements OpsSystemUserRoleService {
	public static final int REFRESH_EXPIRE = 60 * 60 * 24 * 90;
    @Autowired
    private OpsSystemUserRoleDao opsSystemUserRoleDao;
    @Autowired
    private JedisCluster jedisCluster;
    
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return opsSystemUserRoleDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(OpsSystemUserRole record) {
        return opsSystemUserRoleDao.insertSelective(record);
    }

    @Override
    public OpsSystemUserRole selectByPrimaryKey(Integer id) {
        return opsSystemUserRoleDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(OpsSystemUserRole record) {
        return opsSystemUserRoleDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OpsSystemUserRole record) {
        return opsSystemUserRoleDao.updateByPrimaryKey(record);
    }

    @Override
    public int queryAllCount(OpsSystemUserRole record) {
        return opsSystemUserRoleDao.queryAllCount(record);
    }

    @Override
    public List<OpsSystemUserRole> queryAllList(OpsSystemUserRole record) {
        return opsSystemUserRoleDao.queryAllList(record);
    }

    @Override
    public int deleteByUserId(Integer userId) {
        return opsSystemUserRoleDao.deleteByUserId(userId);
    }

	

	@Override
	public void queryByUserRoleList() {
		// TODO Auto-generated method stub
		OpsSystemUserRole record=new OpsSystemUserRole();
		
		 List<SystemUserDO> userRoles=opsSystemUserRoleDao.selectByUserRoleList();
		
		Map<String, String> map=new HashMap<>();
		for (SystemUserDO userRole : userRoles) {
			String jsonObject = JSON.toJSON(userRole.getRoleDOs()).toString();
			map.put(String.valueOf(userRole.getUserId()), jsonObject);
		}
		String jsonObject = JSON.toJSON(map).toString();
			jedisCluster.setex(RedisKey.USER_ROLE   , REFRESH_EXPIRE,
					jsonObject);
	}

    @Override
    public int updateRoleByUserId(OpsSystemUserRole opsSystemUserRole) {
        return opsSystemUserRoleDao.updateRoleByUserId(opsSystemUserRole);
    }

}
