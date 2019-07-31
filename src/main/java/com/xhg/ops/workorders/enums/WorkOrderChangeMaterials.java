package com.xhg.ops.workorders.enums;

/**
 * 更换配件
 * 
 * @author 区涛
 * @date 2018年7月17日
 */
public enum WorkOrderChangeMaterials {

	PART_1(1, "物料1"),
	PART_2(2, "物料2"),
	PART_3(3, "物料3 "),
	PART_4(4, "物料4"),
	PART_5(5, "物料5"),
	;

	WorkOrderChangeMaterials(int code, String name) {
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
	
	public static WorkOrderChangeMaterials getObject(int code) {
		WorkOrderChangeMaterials[] parts = WorkOrderChangeMaterials.values();
		for (WorkOrderChangeMaterials part : parts) {
			if (part.getCode() == code) {
				return part;
			}
		}
		return null;
	}
	
	public static String getNameByCode(int code) {
		WorkOrderChangeMaterials obj = WorkOrderChangeMaterials.getObject(code);
		if(obj!=null) {
			return obj.getName();
		}
		
		return null;
	}

}
