package com.xhg.ops.system.service.impl;

import com.xhg.ops.system.dao.SystemDeptDao;
import com.xhg.ops.system.model.SystemDept;
import com.xhg.ops.system.model.SystemPosition;
import com.xhg.ops.system.service.SystemDeptService;
import com.xhg.ops.system.service.SystemPositonService;
import com.xhg.ops.system.vo.dept.SystemDeptPositionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SystemDeptServiceImpl implements SystemDeptService {

    @Autowired
    private SystemDeptDao systemDeptDao;

    @Autowired
    private SystemPositonService systemPositonService;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return systemDeptDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SystemDept record) {
        return systemDeptDao.insert(record);
    }

    @Override
    public int insertSelective(SystemDept record) {
        return systemDeptDao.insertSelective(record);
    }

    @Override
    public SystemDept selectByPrimaryKey(Integer id) {
        return systemDeptDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SystemDept record) {
        return systemDeptDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SystemDept record) {
        return systemDeptDao.updateByPrimaryKey(record);
    }

    @Override
    public List<SystemDept> queryAllList(SystemDept systemDept) {
        return systemDeptDao.queryAllList(systemDept);
    }

    @Override
    public int queryAllCount(SystemDept systemDept) {
        return systemDeptDao.queryAllCount(systemDept);
    }

    @Override
    public List<SystemDeptPositionVO> queryDeptPositions() {
        List<SystemDeptPositionVO> systemDeptPositionVOList = new ArrayList<>();
        SystemDept systemDept = new SystemDept();
        List<SystemDept> systemDeptList = systemDeptDao.queryAllList(systemDept);
        systemDeptList.stream().forEach(dept->{
            SystemDeptPositionVO vo = new SystemDeptPositionVO();
            vo.setKey(dept.getName());
            vo.setValue(dept.getId().toString());
            vo.setNote("0");
            SystemPosition systemPosition = new SystemPosition();
            systemPosition.setDeptId(dept.getId());
            List<SystemPosition> systemPositions = systemPositonService.queryAllList(systemPosition);
            if(systemPositions!=null)
                systemPositions.stream().filter(s->s!=null).collect(Collectors.toList());
            if(systemPositions.size()>0){
                List<SystemDeptPositionVO> list = new ArrayList<>();
                systemPositions.stream().forEach(postion->{
                    SystemDeptPositionVO vo0 = new SystemDeptPositionVO();
                    vo0.setKey(postion.getName());
                    vo0.setValue(postion.getId().toString());
                    vo0.setNote("1");
                    list.add(vo0);
                });
                vo.setList(list);
            }
            systemDeptPositionVOList.add(vo);
        });
        return systemDeptPositionVOList;
    }
}
