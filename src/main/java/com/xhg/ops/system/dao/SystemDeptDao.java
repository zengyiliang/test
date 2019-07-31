package com.xhg.ops.system.dao;

import com.xhg.ops.system.model.SystemDept;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemDeptDao {

    int deleteByPrimaryKey(Integer id);

    int insert(SystemDept record);

    int insertSelective(SystemDept record);

    SystemDept selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SystemDept record);

    int updateByPrimaryKey(SystemDept record);

    List<SystemDept> queryAllList(SystemDept systemDept);

    int queryAllCount(SystemDept systemDept);
}
