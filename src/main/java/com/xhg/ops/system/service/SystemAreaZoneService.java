package com.xhg.ops.system.service;


import com.xhg.ops.system.model.SystemAreaZone;

import java.util.List;

public interface SystemAreaZoneService {

    int deleteByPrimaryKey(Integer id);

    int insert(SystemAreaZone record);

    int insertSelective(SystemAreaZone record);

    SystemAreaZone selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SystemAreaZone record);

    int updateByPrimaryKey(SystemAreaZone record);

    List<SystemAreaZone> queryAllList(SystemAreaZone recorde);

    int queryAllCount(SystemAreaZone recorde);
}
