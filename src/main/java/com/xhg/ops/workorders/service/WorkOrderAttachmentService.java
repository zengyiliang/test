package com.xhg.ops.workorders.service;

import java.util.List;

/**
 * 工单附件逻辑接口
 * 
 * @author 刘涛
 * @date 2018年7月12日
 */
public interface WorkOrderAttachmentService {

	public interface BusinessType {
		/** 工单附件 */
		public final static int BUSINESS_TYPE_WORK_ORDER = 1;

		/** 工单提交附件 **/
		public final static int BUSINESS_TYPE_WORK_ORDER_RESULT = 2;
	}

	/**
	 * 新增附件信息
	 * 
	 * @param urlList
	 *            地址列表
	 * @param business_id
	 *            业务编号
	 * @param business_type
	 *            业务类型
	 * @param user_id
	 *            用户编号
	 */
	void insert(List<String> urlList, int business_id, int business_type, int user_id);

	/**
	 * 查询业务附件列表
	 * 
	 * @param business_id
	 *            业务编号
	 * @param business_type
	 *            业务类型
	 * @return
	 */
	List<String> queryAttachmentList(int business_id, int business_type);

	/**
	 * 更新附件信息
	 * 
	 * @param urlList
	 *            地址列表
	 * @param business_id
	 *            业务编号
	 * @param bussiness_type
	 *            业务类型
	 * @param user_id
	 *            用户编号
	 */
	void update(List<String> urlList, int business_id, int business_type, int user_id);

}
