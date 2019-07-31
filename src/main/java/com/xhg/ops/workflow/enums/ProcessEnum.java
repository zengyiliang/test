package com.xhg.ops.workflow.enums;

/**
 * 流程实例名称，key的对应关系
 */
public enum ProcessEnum {

    WORK_ORDER_PROCESS("WORKORDERPROCESS","工单系统"),

    //布点系统有关的流程
    MARKETLOCATION_PICKPLACEPROCESS("pickplaceProcess","选点申请流程"),
    MARKETLOCATION_INSTALLPROCESS("installProcess","设备安装申请流程"),
    MARKETLOCATION_CONTRACTPROCESS("contractProcess","合同报批申请流程"),

    //这个枚举只是用来获取静态流程进度，在调用时要判断：常规流程
    MARKETLOCATION_CONTRACTPROCESS_CONVENTIONAL("contractProcess_conventional","合同报批申请流程  常规流程"),
    //这个枚举只是用来获取静态流程进度，在调用时要判断： 非常规流程
    MARKETLOCATION_CONTRACTPROCESS_UNCONVENTIONAL("contractProcess_unconventional","合同报批申请流程 非常规流程")
    ;



	/**
	 * 值
	 */
	private   String processKey;
    /**
     * 名称
     */
    private  String processName;


    ProcessEnum(String processKey,String processName) {
        this.processName = processName;
        this.processKey = processKey;
    }


    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getProcessKey() {
        return processKey;
    }

    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }


}
