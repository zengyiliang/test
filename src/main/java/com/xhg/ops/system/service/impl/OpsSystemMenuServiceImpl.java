package com.xhg.ops.system.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhg.ops.system.dao.OpsSystemMenuDao;
import com.xhg.ops.system.entity.OpsSystemMenuDO;
import com.xhg.ops.system.filter.UserContext;
import com.xhg.ops.system.service.OpsSystemMenuService;


/**
 * 
 * @ClassName: OpsSystemMenuServiceImpl
 * @Description: TODO
 * @author lilun
 * @date 2018-07-12
 */
@Service
public class  OpsSystemMenuServiceImpl  implements OpsSystemMenuService {

  	private final static Logger logger = LoggerFactory.getLogger(OpsSystemMenuServiceImpl.class);
	
	@Autowired
    private OpsSystemMenuDao opsSystemMenuDao;
    
	@Override
	public Integer insert(OpsSystemMenuDO opsSystemMenuDO) {
		try {
			opsSystemMenuDO.setUpdatedUserId(UserContext.getUser().getId());
			opsSystemMenuDO.setCreatedUserId(UserContext.getUser().getId());
			opsSystemMenuDO.setUpdatedTime(new Date());
			opsSystemMenuDO.setCreatedTime(new Date());
			Map<String,Object> params =new HashMap<>();
			params.put("code", opsSystemMenuDO.getCode());
			int count=opsSystemMenuDao.queryAllCount(params);
			if (count==0) {
				 opsSystemMenuDao.insert(opsSystemMenuDO);
			}else {
				return -1;
			}
			
			 
			 return 1;
		} catch(Exception e) {
        	logger.error("OpsSystemMenuServiceImpl_insert", e);
			throw e;
        }
	}
	
	@Override
	public void update(OpsSystemMenuDO opsSystemMenuDO)
	{
		try {
			opsSystemMenuDO.setUpdatedUserId(UserContext.getUser().getId());
			opsSystemMenuDO.setUpdatedTime(new Date());
			opsSystemMenuDao.update(opsSystemMenuDO);
		} catch(Exception e) {
        	logger.error("OpsSystemMenuServiceImpl_update", e);
			throw e;
        }
	}
	
	@Override
	public void updateBySelective(OpsSystemMenuDO opsSystemMenuDO) {
		try {
			opsSystemMenuDO.setUpdatedUserId(UserContext.getUser().getId());
			opsSystemMenuDO.setUpdatedTime(new Date());
			opsSystemMenuDao.updateBySelective(opsSystemMenuDO);
		} catch(Exception e) {
        	logger.error("OpsSystemMenuServiceImpl_updateBySelective", e);
			throw e;
        }
	}

	@Override
	public void delete(Integer id) {
		try {
			opsSystemMenuDao.delete(id);
		} catch(Exception e) {
        	logger.error("OpsSystemMenuServiceImpl_delete", e);
			throw e;
        }
	}
	
	@Override
	public OpsSystemMenuDO queryById(Integer id) {
		return opsSystemMenuDao.queryById(id);
	}
	
	@Override
	public Integer queryAllCount(Map<String,Object> params) {
		return opsSystemMenuDao.queryAllCount(params);
	}
	
	@Override
	public List<OpsSystemMenuDO> queryAllList(Map<String,Object> params) {
		return opsSystemMenuDao.queryAllList(params);
	}
	
	@Override
	public 	void deleteBatch(List<Integer> ids) {
		try {
			opsSystemMenuDao.deleteBatch(ids);
		} catch(Exception e) {
        	logger.error("OpsSystemMenuServiceImpl_deleteBatch", e);
			throw e;
        }
	}

	@Override
	public void insertByBatch(List<OpsSystemMenuDO> opsSystemMenuDoList) {
		try {
			opsSystemMenuDao.insertByBatch(opsSystemMenuDoList);
		} catch(Exception e) {
        	logger.error("OpsSystemMenuServiceImpl_insertByBatch", e);
			throw e;
        }
	}

	@Override
	public OpsSystemMenuDO queryByUrl(String url) {
		return opsSystemMenuDao.queryByUrl(url);
	}

	@Override
	public List<OpsSystemMenuDO> queryMenuList(Map<String, Object> params) {
		return opsSystemMenuDao.queryMenuList(params);
	}

	@Override
	public List<OpsSystemMenuDO> queryAllSuperiorMenus() {
		return opsSystemMenuDao.queryAllSuperiorMenus();
	}

	@Override
	public List<OpsSystemMenuDO> queryAllListTwoMenus(Map<String, Object> params) {
		return opsSystemMenuDao.queryAllListTwoMenus(params);
	}

	@Override
	public List<OpsSystemMenuDO> queryUserTwoFollowingPrevilege() {
		return opsSystemMenuDao.queryUserTwoFollowingPrevilege();
	}
}
