package com.xhg.ops.system.dao;


import com.xhg.ops.system.model.SystemAreaZone;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemAreaZoneDao {

    int deleteByPrimaryKey(Integer id);

    int insert(SystemAreaZone record);

    int insertSelective(SystemAreaZone record);

    SystemAreaZone selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SystemAreaZone record);

    int updateByPrimaryKey(SystemAreaZone record);

    List<SystemAreaZone> queryAllList(SystemAreaZone recorde);

    int queryAllCount(SystemAreaZone recorde);
}
