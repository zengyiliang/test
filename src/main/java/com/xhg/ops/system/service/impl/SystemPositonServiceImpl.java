package com.xhg.ops.system.service.impl;

import com.xhg.ops.system.dao.SystemPostionDao;
import com.xhg.ops.system.model.SystemPosition;
import com.xhg.ops.system.service.SystemPositonService;
import com.xhg.ops.system.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SystemPositonServiceImpl implements SystemPositonService {

    @Autowired
    private SystemPostionDao systemPostionDao;

    @Autowired
    private SystemUserService systemUserService;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return systemPostionDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SystemPosition record) {
        return systemPostionDao.insert(record);
    }

    @Override
    public int insertSelective(SystemPosition record) {
        return systemPostionDao.insertSelective(record);
    }

    @Override
    public SystemPosition selectByPrimaryKey(Integer id) {
        return systemPostionDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SystemPosition record) {
        return systemPostionDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SystemPosition record) {
        return systemPostionDao.updateByPrimaryKey(record);
    }

    @Override
    public List<SystemPosition> queryAllList(SystemPosition systemPosition) {
        return systemPostionDao.queryAllList(systemPosition);
    }

    @Override
    public int queryAllCount(SystemPosition systemPosition) {
        return systemPostionDao.queryAllCount(systemPosition);
    }

    @Override
    public List<SystemPosition> queryAllPositions(Integer id) {

        List<SystemPosition> result = new ArrayList<>();

        SystemPosition param = new SystemPosition();
        param.setId(id);
        //SELECT id,NAME,dept_id,position_id FROM t_ops_bu_system_position WHERE 1=1 AND id = ?
        List<SystemPosition> list = systemPostionDao.queryAllList(param);

        if(list!=null&&list.size()>0){
            SystemPosition systemPosition = list.get(0);
            result.add(systemPosition);//

            Map<String,Object> params = new HashMap<>();
            params.put("deptId",systemPosition.getDeptId());
            List<Integer> positionIds = new ArrayList<>();
            positionIds.add(systemPosition.getId());
            params.put("positionIds",positionIds);
            //SELECT id,NAME,dept_id,position_id FROM t_ops_bu_system_position WHERE 1=1 AND dept_id=? AND position_id IN(?)
            List<SystemPosition> systemPositionList = systemPostionDao.queryAllPositions(params);
            if(systemPositionList !=null&&systemPositionList.size()>0){
                result.addAll(systemPositionList);
                List<SystemPosition> temp =  getSystemPostions(systemPositionList);
                if(temp!=null&&temp.size()>0){
                    result.addAll(temp);
                }
            }
            return result;
        }

        return result;
    }

    @Override
    public List<Integer> queryAllUserIdbyParams(Integer userId) {
        List<Integer> positionIds = new ArrayList<>();
        List<SystemPosition> systemPositionList = queryAllPositions(userId);
        if(systemPositionList!=null&&systemPositionList.size()>0){
            systemPositionList.stream().forEach(s->{
                positionIds.add(s.getId());
            });

            return systemUserService.queryAllUserIdbyParams(positionIds);
        }
        return null;
    }


    private List<SystemPosition> getSystemPostions(List<SystemPosition> systemPositionList){
        List<SystemPosition> result = null;

        List<Integer> pIds = new ArrayList<>();
        Map<String,Object> params = new HashMap<>();
        params.put("deptId",systemPositionList.get(0).getDeptId());
        systemPositionList.stream().forEach(p->{
            pIds.add(p.getId());
        });
        params.put("positionIds",pIds);
        List<SystemPosition> systemPositionList1 = systemPostionDao.queryAllPositions(params);
        if(systemPositionList1!=null&&systemPositionList1.size()>0){
            result = new ArrayList<>();
            result.addAll(systemPositionList1);
            List<SystemPosition> systemPositionList2 = getSystemPostions(systemPositionList1);
            if(systemPositionList2!=null && systemPositionList2.size()>0){
                result.addAll(systemPositionList2);
            }
        }

        return result;
    }

    private List<SystemPosition> getSystemPostions22(List<SystemPosition> systemPositionList){
        List<SystemPosition> result = null;

        List<Integer> pIds = new ArrayList<>();
        Map<String,Object> params = new HashMap<>();
        params.put("deptId",systemPositionList.get(0).getDeptId());
        systemPositionList.stream().forEach(p->{
            pIds.add(p.getId());
        });
        params.put("positionIds",pIds);
        List<SystemPosition> systemPositionList1 = systemPostionDao.queryAllPositions(params);
        if(systemPositionList1!=null&&systemPositionList1.size()>0){
            result = new ArrayList<>();
            result.addAll(systemPositionList1);
            List<SystemPosition> systemPositionList2 = getSystemPostions(systemPositionList1);
            if(systemPositionList2!=null && systemPositionList2.size()>0){
                result.addAll(systemPositionList2);
            }else{
                return result;
            }
        }else{
            return result;
        }
        return result;
    }
}
