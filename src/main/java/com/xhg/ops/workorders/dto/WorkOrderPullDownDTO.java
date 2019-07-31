package com.xhg.ops.workorders.dto;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "工单下拉框基础类")
@Data
public class WorkOrderPullDownDTO<T> {

    @ApiModelProperty(value = "标识")
    private String code;
    
    @ApiModelProperty(value = "名称")
    private String name;
    
    public List<WorkOrderPullDownDTO<T>> getPullDownList(Class<T> clazz) throws IllegalAccessException, IllegalArgumentException, 
    InvocationTargetException, NoSuchMethodException, SecurityException {
    	
    	Method methodValues = clazz.getDeclaredMethod("values", null);
    	Object returnValue = methodValues.invoke(null, null);
    	T[] values = (T[])returnValue;
    	
    	List<WorkOrderPullDownDTO<T>> pullDownList = new ArrayList<>();
		for (T t : values) {
			Method methodGetCode = clazz.getDeclaredMethod("getCode", null);
			Integer code = (Integer)methodGetCode.invoke(t);
			Method methodGetName = clazz.getDeclaredMethod("getName", null);
			String name = (String)methodGetName.invoke(t);
			
			WorkOrderPullDownDTO<T> pullDown = new WorkOrderPullDownDTO<T>();
			pullDown.setCode(String.valueOf(code));
			pullDown.setName(String.valueOf(name));
			pullDownList.add(pullDown);
		}
		
		return pullDownList;
    }
    
/*    public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
    	WorkOrderPullDownDTO<WorkOrderLevel> dto = new WorkOrderPullDownDTO<>();
    	dto.getPullDownList(WorkOrderLevel.class);
	}*/
    
    
}
