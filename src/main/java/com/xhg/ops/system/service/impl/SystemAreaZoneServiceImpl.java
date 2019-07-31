package com.xhg.ops.system.service.impl;

import com.xhg.ops.common.ComConstant;
import com.xhg.ops.system.dao.SystemAreaZoneDao;
import com.xhg.ops.system.model.CyclingAreaDO;
import com.xhg.ops.system.model.SystemAreaZone;
import com.xhg.ops.system.service.SystemAreaZoneService;
import com.xhg.ops.utils.SerializeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.util.List;

@Service
public class SystemAreaZoneServiceImpl implements SystemAreaZoneService {

    @Autowired
    private SystemAreaZoneDao systemAreaZoneDao;

    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return systemAreaZoneDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SystemAreaZone record) {
        return systemAreaZoneDao.insert(record);
    }

    @Override
    public int insertSelective(SystemAreaZone record) {
        return systemAreaZoneDao.insertSelective(record);
    }

    @Override
    public SystemAreaZone selectByPrimaryKey(Integer id) {
        return systemAreaZoneDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SystemAreaZone record) {
        return systemAreaZoneDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SystemAreaZone record) {
        return systemAreaZoneDao.updateByPrimaryKey(record);
    }


    @Override
    public List<SystemAreaZone> queryAllList(SystemAreaZone recorde) {
        List<SystemAreaZone> systemAreaZoneList = null;

        byte[] bytes = jedisCluster.get(SerializeUtil.serialize(ComConstant.BASE_ZONE_AREA));
        if (null != bytes) {
            systemAreaZoneList = (List<SystemAreaZone>)SerializeUtil.deserialize(bytes);
        } else {
            systemAreaZoneList = systemAreaZoneDao.queryAllList(recorde);
            if(!systemAreaZoneList.isEmpty()) {
                jedisCluster.setex(SerializeUtil.serialize(ComConstant.BASE_ZONE_AREA),12*60*60,SerializeUtil.serialize(systemAreaZoneList));
            }
        }
        return systemAreaZoneList;
    }

    @Override
    public int queryAllCount(SystemAreaZone recorde) {
        return systemAreaZoneDao.queryAllCount(recorde);
    }
}
