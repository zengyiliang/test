package com.xhg.ops.workorders.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 订单状态枚举
 * 
 * @author 刘涛
 * @date 2018年7月12日
 */
public enum WorkOrderStatus {

	CREATED(1, "已创建", "待初审") {
		@Override
		public List<String> operations() {
			return Arrays.asList(WorkOrderOperation.AUDIT.getKey(), WorkOrderOperation.AUDIT_REJECT.getKey());
		}
	},
	AUDITED(2, "已初审", "待分配") {
		@Override
		public List<String> operations() {
			return Arrays.asList(WorkOrderOperation.ASSIGN.getKey(), WorkOrderOperation.ASSIGN_REJECT.getKey());
		}
	},
	CLOSED(2001, "已关闭", "结束") {
		@Override
		public List<String> operations() {
			return new ArrayList<String>();
		}
	},
	ASSIGNED(3, "已分配", "待签单") {
		@Override
		public List<String> operations() {
			return Arrays.asList(WorkOrderOperation.AGREEN.getKey(), WorkOrderOperation.AGREEN_REVOKE.getKey());
		}
	},
	REJECTED(3001, "已拒绝", "待初审") {
		@Override
		public List<String> operations() {
			return WorkOrderStatus.CREATED.operations();
		}
	},
	AGREED(4, "已签单", "待出发") {
		@Override
		public List<String> operations() {
			return Arrays.asList(WorkOrderOperation.START.getKey(), WorkOrderOperation.START_TRANSFER.getKey());
		}
	},
	REVOKED(4001, "已撤回", "待分配") {
		@Override
		public List<String> operations() {
			return WorkOrderStatus.AUDITED.operations();
		}
	},
	STARTED(5, "已出发", "待签到") {
		@Override
		public List<String> operations() {
			return Arrays.asList(WorkOrderOperation.SIGN.getKey());
		}
	},
	TRANSFERED(5001, "已转单", "待分配") {
		@Override
		public List<String> operations() {
			return WorkOrderStatus.AUDITED.operations();
		}
	},
	SIGNED(6, "已签到", "待处理") {
		@Override
		public List<String> operations() {
			return Arrays.asList(WorkOrderOperation.PROCESS.getKey(), WorkOrderOperation.PROCESS_MATERIAL_APPLY.getKey());
		}
	},
	PROCESSED(7, "已处理", "待审核") {
		@Override
		public List<String> operations() {
			return Arrays.asList(WorkOrderOperation.CONFIRM.getKey());
		}
	},
	MATERIAL_APPLY(7001, "已申请更换物料", "待审批物料申请") {
		@Override
		public List<String> operations() {
			return Arrays.asList(WorkOrderOperation.MATERIAL_APPROVE.getKey());
		}
	},
	MATERIAL_APPROVED(7002, "已审批物料申请", "待审核物料申请") {
		@Override
		public List<String> operations() {
			return Arrays.asList(WorkOrderOperation.MATERIAL_AUDITE.getKey());
		}
	},
	MATERIAL_AUDITED(7003, "已审核物料申请", "待发货") {
		@Override
		public List<String> operations() {
			return Arrays.asList(WorkOrderOperation.MATERIAL_DELIVERY.getKey());
		}
	},
	MATERIAL_DELIVERED(7004, "已发货物料", "待查收") {
		@Override
		public List<String> operations() {
			return Arrays.asList(WorkOrderOperation.MATERIAL_RECEIVE.getKey());
		}
	},
	MATERIAL_RECEIVED(7005, "已查收物料", "待出发") {
		@Override
		public List<String> operations() {
			return WorkOrderStatus.AGREED.operations();
		}
	},
	CONFIRMED(8, "已审核", "待复核") {
		@Override
		public List<String> operations() {
      return Arrays.asList(WorkOrderOperation.RECEIVE.getKey());
		}
	},
	REVIEWED(9, "已复核", "结束") {
		@Override
		public List<String> operations() {
			return new ArrayList<>();
		}
	},;

	/***
	 * 获取当前状态工单可操作列表
	 * @return
	 */
	public abstract List<String> operations();

	WorkOrderStatus(int code, String desc, String next_desc) {
		this.code = code;
		this.desc = desc;
		this.next_desc = next_desc;
	}

	/**
	 * 状态码
	 */
	private int code;
	/***
	 * 当前状态描述
	 */
	private String desc;

	/**
	 * 下一步状态描述
	 */
	private String next_desc;

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public String getNext_desc() {
		return next_desc;
	}

	/**
	 * 根据状态码获取状态
	 * 
	 * @param code
	 * @return
	 */
	public static WorkOrderStatus getOrderStatus(int code) {
		WorkOrderStatus[] statusArr = WorkOrderStatus.values();
		for (WorkOrderStatus status : statusArr) {
			if (status.getCode() == code) {
				return status;
			}
		}
		return null;
	}
	
	/**
	 * 判断工单是否结束
	 * @param code
	 * @return
	 */
	public static boolean isFinished(int code) {
		if(code == CLOSED.getCode() || code == REVIEWED.getCode()) {
			return true;
		}
		return false;
	}

}
