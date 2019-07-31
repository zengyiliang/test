package com.xhg.ops.workorders.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.xhg.ops.workorders.model.WorkOrderMessage;

/**
 * 工单消息数据操作
 * 
 * @author 刘涛
 * @date 2018年7月12日
 */
@Repository
public interface WorkOrderMessageDao {

	/**
	 * 查询接收人每组工单的最新消息
	 * 
	 * @param recevieId
	 *            接收人
	 * @param type
	 *            消息类型
	 * @return
	 */
	public List<WorkOrderMessage> queryNewestMessageList(@Param("receiveId") int receiveId, @Param("type") int type);

	/**
	 * 新增
	 * 
	 * @param list
	 */
	void insert(List<WorkOrderMessage> list);

	/**
	 * 更新消息
	 * 
	 * @param message
	 */
	int update(WorkOrderMessage message);

}
