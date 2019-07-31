package com.xhg.ops.system.dao;

import com.xhg.ops.system.model.SystemPosition;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SystemPostionDao {


    int deleteByPrimaryKey(Integer id);

    int insert(SystemPosition record);

    int insertSelective(SystemPosition record);

    SystemPosition selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SystemPosition record);

    int updateByPrimaryKey(SystemPosition record);

    List<SystemPosition> queryAllList(SystemPosition systemPosition);

    List<SystemPosition> queryAllPositions(Map<String,Object> params);

    int queryAllCount(SystemPosition systemPosition);
}
