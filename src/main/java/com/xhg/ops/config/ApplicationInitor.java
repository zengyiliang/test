package com.xhg.ops.config;

import com.xhg.ops.common.ComConstant;
import com.xhg.ops.common.CommonArea;
import com.xhg.ops.system.dao.CyclingAreaDao;
import com.xhg.ops.system.model.CyclingAreaDO;
import com.xhg.ops.utils.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.util.List;


/**
 * 初始化
 * 全国地区数据进入缓存中
 */
@Component
@Order(value=1)
public class ApplicationInitor implements ApplicationRunner{

    private static final Logger logger = LoggerFactory.getLogger(ApplicationInitor.class);

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private CyclingAreaDao cyclingAreaDao;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        logger.info("=====服务启动中，初始化数据=======");

//        logger.info("=====服务启动中，初始化全国地区数据开始====");
//        byte[] bytes = jedisCluster.get(SerializeUtil.serialize(ComConstant.AREA_ALL_LIST));
//        if (null == bytes) {
//            List<CyclingAreaDO> areaDOS = cyclingAreaDao.queryAllList(null);
//            if(!areaDOS.isEmpty()) {
//                jedisCluster.set(SerializeUtil.serialize(ComConstant.AREA_ALL_LIST),SerializeUtil.serialize(areaDOS));
//                CommonArea.initAreaList(areaDOS);
//            }
//        }else{
//            List<CyclingAreaDO> areaDOS = (List<CyclingAreaDO>)SerializeUtil.deserialize(bytes);
//            CommonArea.initAreaList(areaDOS);
//        }
//        logger.info("=====服务启动中，初始化全国地区数据结束====");

    }
}
