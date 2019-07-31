package com.xhg.ops.system.mapper;

import java.util.List;
import java.util.Map;

import com.xhg.ops.system.entity.OpsSystemMenuDO;

/**
 * 
 * @ClassName: OpsSystemMenuMapper
 * @Description: TODO
 * @author lilun
 * @date 2018-07-12
 */
public interface OpsSystemMenuMapper {
	
	Integer insert(OpsSystemMenuDO opsSystemMenu);
	
	void update(OpsSystemMenuDO opsSystemMenu);
	
	void updateBySelective(OpsSystemMenuDO opsSystemMenu);

	void delete(Integer id);
	
	OpsSystemMenuDO queryById(Integer id);
	
	Integer queryAllCount(Map<String,Object> params);
	
	List<OpsSystemMenuDO> queryAllList(Map<String,Object> params);

	void deleteBatch(List<Integer> ids);

	void insertByBatch(List<OpsSystemMenuDO> opsSystemMenuDoList);
}