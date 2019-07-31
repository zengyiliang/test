package com.xhg.ops.system.service;


import com.xhg.ops.system.model.SystemDept;
import com.xhg.ops.system.vo.dept.SystemDeptPositionVO;

import java.util.List;

public interface SystemDeptService {

    int deleteByPrimaryKey(Integer id);

    int insert(SystemDept record);

    int insertSelective(SystemDept record);

    SystemDept selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SystemDept record);

    int updateByPrimaryKey(SystemDept record);

    List<SystemDept> queryAllList(SystemDept systemDept);

    int queryAllCount(SystemDept systemDept);

    List<SystemDeptPositionVO> queryDeptPositions();
}
