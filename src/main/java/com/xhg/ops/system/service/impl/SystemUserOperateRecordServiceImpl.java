package com.xhg.ops.system.service.impl;

import com.xhg.ops.system.dao.SystemUserOperateRecordDao;
import com.xhg.ops.system.model.SystemUserOperateRecord;
import com.xhg.ops.system.service.SystemUserOperateRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemUserOperateRecordServiceImpl implements SystemUserOperateRecordService {

    @Autowired
    private SystemUserOperateRecordDao systemUserOperateRecordDao;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return systemUserOperateRecordDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SystemUserOperateRecord record) {
        return systemUserOperateRecordDao.insert(record);
    }

    @Override
    public int insertSelective(SystemUserOperateRecord record) {
        return systemUserOperateRecordDao.insertSelective(record);
    }

    @Override
    public SystemUserOperateRecord selectByPrimaryKey(Integer id) {
        return systemUserOperateRecordDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SystemUserOperateRecord record) {
        return systemUserOperateRecordDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SystemUserOperateRecord record) {
        return systemUserOperateRecordDao.updateByPrimaryKey(record);
    }

    @Override
    public int queryAllCount(SystemUserOperateRecord record) {
        return systemUserOperateRecordDao.queryAllCount(record);
    }

    @Override
    public List<SystemUserOperateRecord> queryAllList(SystemUserOperateRecord record) {
        return systemUserOperateRecordDao.queryAllList(record);
    }

    @Override
    public List<SystemUserOperateRecord> selectByUserId(Integer userId) {
        return systemUserOperateRecordDao.selectByUserId(userId);
    }
}
