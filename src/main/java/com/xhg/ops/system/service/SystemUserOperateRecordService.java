package com.xhg.ops.system.service;

import com.xhg.ops.system.model.SystemUserOperateRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemUserOperateRecordService {

    int deleteByPrimaryKey(Integer id);

    int insert(SystemUserOperateRecord record);

    int insertSelective(SystemUserOperateRecord record);

    SystemUserOperateRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SystemUserOperateRecord record);

    int updateByPrimaryKey(SystemUserOperateRecord record);

    int queryAllCount(SystemUserOperateRecord record);

    List<SystemUserOperateRecord> queryAllList(SystemUserOperateRecord record);

    List<SystemUserOperateRecord> selectByUserId(Integer userId);
}
