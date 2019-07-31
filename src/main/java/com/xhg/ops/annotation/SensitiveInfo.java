package com.xhg.ops.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.xhg.ops.utils.SensitiveInfoUtils;


/**
 * @Title: SensitiveInfo.java
 * @Copyright: Copyright (c) 2015
 * @Description: <br>
 *               敏感信息注解标记 <br>
 */
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface SensitiveInfo {
 
    public SensitiveInfoUtils.SensitiveType type();

}



