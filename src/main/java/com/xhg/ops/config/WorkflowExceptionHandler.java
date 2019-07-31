package com.xhg.ops.config;

import org.activiti.engine.ActivitiException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhg.core.config.validator.GlobalExceptionHandler;
import com.xhg.core.web.exception.BusinessException;
import com.xhg.core.web.vo.ResponseBean;
import com.xhg.core.web.vo.RsBody;
import com.xhg.ops.enums.Status;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @Description: 工作流全局异常捕获提示 
 * @Copyright: Copyright (c) 2018   
 * @Author hubowei  
 * @Create 2018年7月19日 下午4:00:21 
 * @Version 1.0.0
 */
@Slf4j
@Component
public class WorkflowExceptionHandler extends GlobalExceptionHandler {

	/**
	 * 工作流引擎异常处理提示
	 * @param exception
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(value = { ActivitiException.class })
	public ResponseBean<?> handle(ActivitiException exception) {
		ResponseBean<Object> responseBean = new ResponseBean<>();
		RsBody<Object> rsBody = new RsBody<>();
		rsBody.setCode(Status.ACTIVITI_ERROR_CODE.getName());
		rsBody.setMessage(Status.ACTIVITI_ERROR_CODE.getValue());
		responseBean.setResponseBody(rsBody);
		return responseBean;
	}
	/**
	 * 重写BusinessException setMessage值
	 */
    @ResponseBody
    @ExceptionHandler(value = { BusinessException.class })
    public ResponseBean<?> handle(BusinessException exception) {
    	ResponseBean<Object> responseBean = new ResponseBean<>();
        responseBean.setCode(exception.getErrorCode());
        String message=StringUtils.isEmpty(exception.getMessage())?exception.getErrorValue():exception.getMessage();
        responseBean.setMessage(message);
        log.warn(responseBean.toString());
        return responseBean;
    }

}
