package com.xhg.ops.workorders.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xhg.ops.common.ComConstant;
import com.xhg.ops.workorders.dao.WorkOrderAttachmentDao;
import com.xhg.ops.workorders.model.WorkOrderAttachment;
import com.xhg.ops.workorders.service.WorkOrderAttachmentService;

/**
 * 工单附件逻辑接口实现
 * @author 刘涛
 * @date 2018年7月12日
 */
@Service("workOrderAttachmentService")
public class WorkOrderAttachmentServiceImpl implements WorkOrderAttachmentService {

	@Autowired
	private WorkOrderAttachmentDao workOrderAttachmentDao;
	
	@Override
	public void insert(List<String> urlList, int business_id, int business_type, int user_id) {
		List<WorkOrderAttachment> list = new ArrayList<>();
		urlList.forEach(url -> {
			WorkOrderAttachment attachment = new WorkOrderAttachment();
			attachment.setBuesinessId(business_id);
			attachment.setBusinessType(business_type);
			attachment.setUrl(url);
			attachment.setCreatedTime(new Date());
			attachment.setUpdatedTime(new Date());
			attachment.setCreatedUserId(user_id);
			attachment.setUpdatedUserId(user_id);
			attachment.setEnable(ComConstant.ENABLE_YES);
			list.add(attachment);
		});
		workOrderAttachmentDao.insertBatchAttachment(list);
	}

	@Override
	public List<String> queryAttachmentList(int business_id, int business_type) {
		return workOrderAttachmentDao.queryAttachmentList(business_id, business_type);
	}

	/**
	 * 更新附件信息，将之前的信息删除，然后新增记录
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(List<String> urlList, int business_id, int business_type, int user_id) {
		workOrderAttachmentDao.deletebBatchAttachment(business_id, business_type, user_id);
		this.insert(urlList, business_id, business_type, user_id);
	}

}
