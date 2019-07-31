package com.xhg.ops.workorders.enums;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 订单状态枚举映射
 * 
 * @author 区涛
 * @date 2018年7月16日
 */
public enum WorkOrderStatusMapping {

/*	操作标识 1工单初审 2关闭工单 3工单分配 4拒绝工单 5签单 6申请撤单 7出发 8申请转单 9签到 10处理完成 11申请更换物料 12物料信息审批 13物料信息审核 14物料发货 15物料查收 16工单信息审核 17工单信息复核
	
	工单状态标识 
	待处理 1待初审 2待分配 3待签单 4待出发 5待签到 6待处理 7待审批物料申请 8待审核物料申请 9待发货 10待查收 11待审核 12待复核
	进行中 13已初审 14已分配 15已签单 16已出发 17已签到 18已处理 19已申请更换物料 20已审批物料申请 21已审核物料申请 22已发货 23已查收 24已审核 25已复核 26已拒绝 27已撤回 28已转单 29已创建
	已结束   30已关闭
	
	工单主状态 1待处理  2进行中 3已结束 
	工单从状态  1待初审 2待分配 3待签单 4待出发 5待签到 6待处理 7挂起 8待审核 9待复核*/
	
	CREATED_TOAUDIT(WorkOrderStatus.CREATED.getCode(), "29", "已创建", "1", "待初审", "1", "待初审", "1", "初审", "创建工单"),
	AUDITED_TOASSIGN(WorkOrderStatus.AUDITED.getCode(), "13", "已初审", "2", "待分配", "2", "待分配","3", "工单分配", "工单初审"),
	CLOSED(WorkOrderStatus.CLOSED.getCode(), "30", "已关闭", "", "已关闭", "", "", "","", "工单初审"),
	ASSIGNED_TOAGREE(WorkOrderStatus.ASSIGNED.getCode(), "14", "已分配", "3", "待签单", "3", "待签单", "5", "签单", "工单分配"),
	REJECTED_TOAUDIT(WorkOrderStatus.REJECTED.getCode(), "26", "已拒绝", "1", "待初审", "1", "待初审","1", "初审", "签单"),
	AGREED_TOSTART(WorkOrderStatus.AGREED.getCode(), "15", "已签单", "4", "待出发", "4", "待出发", "7", "出发", "签单"),
	REVOKED_TOASSIGN(WorkOrderStatus.REVOKED.getCode(), "27", "已撤回", "2", "待分配", "2", "待分配", "3", "工单分配", "出发"),
	STARTED_TOSIGN(WorkOrderStatus.STARTED.getCode(), "16", "已出发", "5", "待签到", "5", "待签到", "9", "签到", "出发"),
	TRANSFERED_TOASSIGN(WorkOrderStatus.TRANSFERED.getCode(), "28", "已转单", "2", "待分配", "2", "待分配", "3", "工单分配", "签到"),
	SIGNED_TOPROCESS(WorkOrderStatus.SIGNED.getCode(), "17", "已签到", "6", "待处理", "6", "待处理", "10", "处理完成", "签到"),
	PROCESSED_TOREVIEW(WorkOrderStatus.PROCESSED.getCode(), "18", "已处理", "11", "待审核", "9", "待审核", "16", "工单信息审核", "工单处理"),
	MATERIAL_APPLY_TOAUDIT(WorkOrderStatus.MATERIAL_APPLY.getCode(), "19", "已申请更换物料", "7", "待审批物料申请", "7", "挂起","12", "物料信息审批", "工单处理"),
	MATERIAL_APPROVED_TOAUDIT(WorkOrderStatus.MATERIAL_APPROVED.getCode(), "20", "已审批物料申请", "8", "待审核物料申请", "7", "挂起", "13", "物料信息审核", "物料审批"),
	MATERIAL_AUDITED_TODELIVER(WorkOrderStatus.MATERIAL_AUDITED.getCode(), "21", "已审核物料申请", "9", "待发货", "7", "挂起", "14", "物料发货", "物料审核"),
	MATERIAL_DELIVERED_TORECEIVE(WorkOrderStatus.MATERIAL_DELIVERED.getCode(), "22", "已发货", "10", "待查收", "7", "挂起", "15", "物料查收", "物料发送"),
	MATERIAL_RECEIVED_TOSTART(WorkOrderStatus.MATERIAL_RECEIVED.getCode(), "23", "已查收", "4", "待出发", "7", "挂起", "7", "出发", "物料查收"),
	CONFIRMED_TOREVIEW(WorkOrderStatus.CONFIRMED.getCode(), "24", "已审核", "12", "待复核", "9", "待复核", "17", "工单信息复核", "工单审核"),
	REVIEWED(WorkOrderStatus.REVIEWED.getCode(), "25", "已复核", "", "已完成", "", "", "", "", "工单复核"),
	
	// TODO 少了MATERIAL_APPROVED 已审批物料申请
/*	CREATED(1, "已创建", "待初审"),
	AUDITED(2, "已初审", "待分配"),
	CLOSED(2001, "已关闭", "订单关闭"),
	ASSIGNED(3, "已分配", "待签单"),
	REJECTED(3001, "已拒绝", "待初审"),
	AGREED(4, "已签单", "待出发"),
	REVOKED(4001, "已撤回", "待分配"),
	STARTED(5, "已出发", "待签到"),
	TRANSFERED(5001, "已转单", "待分配"),
	SIGNED(6, "已签到", "待处理"),
	PROCESSED(7, "已处理", "待审核"),
	MATERIAL_APPLY(7001, "物料已申请", "待审核"),
	MATERIAL_AUDITED(7002, "物料已审核", "待发货"),
	MATERIAL_DELIVERED(7003, "物料已发货", "待查收"),
	MATERIAL_RECEIVED(7004, "物料已查收", "待出发"),
	CONFIRMED(8, "已审核", "待复核"),
	REVIEWED(9, "已复核", "订单结束"),*/
	;

	WorkOrderStatusMapping(int code, String descCode, String desc, String next_desc_code, String next_desc, String next_desc_code2, String next_desc2,
			String nextOperateCode, String nextOperateName, String operateName) {
		this.code = code;
		this.descCode = descCode;
		this.desc = desc;
		this.next_desc_code = next_desc_code;
		this.next_desc = next_desc;
		this.next_desc_code2 = next_desc_code2;
		this.next_desc2 = next_desc2;
		this.nextOperateCode = nextOperateCode;
		this.nextOperateName = nextOperateName;
		this.operateName = operateName;
	}

	/**
	 * 映射状态码
	 */
	private int code;
	
	/**
	 * 当前状态码
	 */
	private String descCode;
	
	/***
	 * 当前状态描述
	 */
	private String desc;

	/**
	 * 下一步状态码
	 */
	private String next_desc_code;
	
	/**
	 * 下一步状态描述
	 */
	private String next_desc;
	
	/**
	 * 下一步状态码2
	 */
	private String next_desc_code2;
	
	/**
	 * 下一步状态描述2
	 */
	private String next_desc2;
	
	/**
	 * 下一步操作标识
	 */
	private String nextOperateCode;
	
	/**
	 * 下一步操作名称
	 */
	private String nextOperateName;
	
	/**
	 * 当前操作名称
	 */
	private String operateName;
	

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public String getNext_desc_code() {
		return next_desc_code;
	}

	public String getNext_desc() {
		return next_desc;
	}

	public String getDescCode() {
		return descCode;
	}
	
	public String getNext_desc_code2() {
		return next_desc_code2;
	}

	public String getNext_desc2() {
		return next_desc2;
	}

	public String getNextOperateCode() {
		return nextOperateCode;
	}

	public String getNextOperateName() {
		return nextOperateName;
	}
	
    public String getOperateName() {
		return operateName;
	}

	public static WorkOrderStatusMapping getByCode(int code) {
        for (WorkOrderStatusMapping mapping : WorkOrderStatusMapping.values()) {
            if (code==mapping.getCode()) {
                return mapping;
            }
        }
        return null;
    }
    
    public static List<WorkOrderStatusMapping> getListByNextDescCode2(String next_desc_code2) {
    	List<WorkOrderStatusMapping> list = new ArrayList<WorkOrderStatusMapping>();
        for (WorkOrderStatusMapping mapping : WorkOrderStatusMapping.values()) {
            if (StringUtils.equals(next_desc_code2, mapping.getNext_desc_code2())) {
            	list.add(mapping);
            }
        }
        
        return list;
    }
    
    /**
     * 获取待处理列表
     * @return
     */
    public static List<WorkOrderStatusMapping> getToDoList() {
    	List<WorkOrderStatusMapping> toToList = new ArrayList<WorkOrderStatusMapping>();
		toToList.add(WorkOrderStatusMapping.CREATED_TOAUDIT);
		toToList.add(WorkOrderStatusMapping.AUDITED_TOASSIGN);
		toToList.add(WorkOrderStatusMapping.ASSIGNED_TOAGREE);
		toToList.add(WorkOrderStatusMapping.REJECTED_TOAUDIT);
		toToList.add(WorkOrderStatusMapping.AGREED_TOSTART);
		toToList.add(WorkOrderStatusMapping.REVOKED_TOASSIGN);
		toToList.add(WorkOrderStatusMapping.STARTED_TOSIGN);
		toToList.add(WorkOrderStatusMapping.TRANSFERED_TOASSIGN);
		toToList.add(WorkOrderStatusMapping.SIGNED_TOPROCESS);
		toToList.add(WorkOrderStatusMapping.PROCESSED_TOREVIEW);
		toToList.add(WorkOrderStatusMapping.MATERIAL_APPLY_TOAUDIT);
		toToList.add(WorkOrderStatusMapping.MATERIAL_AUDITED_TODELIVER);
		toToList.add(WorkOrderStatusMapping.MATERIAL_DELIVERED_TORECEIVE);
		toToList.add(WorkOrderStatusMapping.MATERIAL_RECEIVED_TOSTART);
		toToList.add(WorkOrderStatusMapping.CONFIRMED_TOREVIEW);
		
		return toToList;
    }
    
}
