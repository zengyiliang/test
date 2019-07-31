package com.xhg.ops.workorders.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.xhg.ops.workorders.model.WorkOrderAttachment;

/**
 * 工单附件数据操作
 * @author 刘涛
 * @date 2018年7月12日
 */
@Repository
public interface WorkOrderAttachmentDao {

	/**
	 * 批量插入
	 * @param list
	 */
	void insertBatchAttachment(List<WorkOrderAttachment> list);
	
	/**
	 * 查询业务附件信息
	 * @param business_id
	 * @param business_type
	 * @return
	 */
	List<String> queryAttachmentList(@Param("business_id")int business_id, @Param("business_type")int business_type);
	
	/**
	 * 删除附件信息
	 * @param business_id
	 * @param business_type
	 * @param user_id
	 * @return
	 */
	int deletebBatchAttachment(@Param("business_id")int business_id, @Param("business_type")int business_type, @Param("user_id")int user_id);
	
}
