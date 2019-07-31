package com.xhg.ops.system.dao;

import com.xhg.ops.system.model.SystemUser;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SystemUserDao {

    int deleteByPrimaryKey(Integer id);

    int insert(SystemUser record);

    int insertSelective(SystemUser record);

    SystemUser selectByPrimaryKey(Integer id);

    List<String> selectCityStreet(Map<String,Object> params);

    SystemUser selectByPhone(String phone);

    int updateByPrimaryKeySelective(SystemUser record);

    int updateByPrimaryKey(SystemUser record);

    int queryAllCount(SystemUser record);

    List<SystemUser> queryAllList(SystemUser record);

    List<SystemUser> queryAllListWithoutId(SystemUser systemUser);

    int updateResetSystemUserPassword(Map<String, Object> params);

    List<SystemUser> loadSystemUserInfosByParams(Map<String,Object> parmas);

    List<Integer> queryAllUserIdbyParams(List<Integer> positionIds);
}
