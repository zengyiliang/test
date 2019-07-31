package com.xhg.ops.workflow.exception;

import com.xhg.core.web.vo.Status;

public class WorkflowException extends RuntimeException {
    private static final long serialVersionUID = 5441923856899380112L;
    private String errorCode;
    private String errorValue;


    public WorkflowException(String errorCode, String errorValue) {
        super(errorValue);
        this.errorCode = errorCode;
        this.errorValue = errorValue;
    }


    public String getErrorCode() {
        return this.errorCode;
    }

    public String getErrorValue() {
        return this.errorValue;
    }
}
