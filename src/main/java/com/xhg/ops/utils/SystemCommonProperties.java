package com.xhg.ops.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class SystemCommonProperties {
	
	@Value(value = "${jiguang_account}")
	private String jiguangAccount;
	
	@Value(value= "${jiguang_pwd}")
	private String jiguangPwd;

	@Value(value="${appkey_Test}")
	private String appkeyTest;

	@Value(value="${master_secret_Test}")
	private String masterSecretTest;

	@Value(value="${appkey_Ops}")
	private String appkeyOps;

	@Value(value="${master_secret_Ops}")
	private String masterSecretOps;

	@Value(value="${ios_context_flag}")
	private String iosContextFlag;
	
	@Value(value="${maxRetryTime}")
	private String maxRetryTime;
	
	@Value(value="${connetion_time_out}")
	private String connetionTimeOut;

}