package com.xhg.ops.system.service;


import com.xhg.ops.system.model.SystemPosition;

import java.util.List;
import java.util.Map;

public interface SystemPositonService {

    int deleteByPrimaryKey(Integer id);

    int insert(SystemPosition record);

    int insertSelective(SystemPosition record);

    SystemPosition selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SystemPosition record);

    int updateByPrimaryKey(SystemPosition record);

    List<SystemPosition> queryAllList(SystemPosition systemPosition);

    int queryAllCount(SystemPosition systemPosition);

    /**
     * 获取包括本身的下级所有信息的集合
     * @param id
     * @return
     */
    List<SystemPosition> queryAllPositions(Integer id);


    List<Integer> queryAllUserIdbyParams(Integer userId);
}
