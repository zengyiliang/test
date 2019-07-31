package com.xhg.ops.workorders.enums;

/**
 * 更换配件
 * 
 * @author 区涛
 * @date 2018年7月17日
 */
public enum WorkOrderChangeParts {

	PART_1(1, "配件1"),
	PART_2(2, "配件2"),
	PART_3(3, "配件3 "),
	PART_4(4, "配件4"),
	PART_5(5, "配件5"),
	;

	WorkOrderChangeParts(int code, String name) {
		this.code = code;
		this.name = name;
	}

	/**
	 * 值
	 */
	private int code;

	/**
	 * 名称
	 */
	private String name;

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
	public static WorkOrderChangeParts getObject(int code) {
		WorkOrderChangeParts[] parts = WorkOrderChangeParts.values();
		for (WorkOrderChangeParts part : parts) {
			if (part.getCode() == code) {
				return part;
			}
		}
		return null;
	}
	
	public static String getNameByCode(int code) {
		WorkOrderChangeParts obj = WorkOrderChangeParts.getObject(code);
		if(obj!=null) {
			return obj.getName();
		}
		
		return null;
	}

}
