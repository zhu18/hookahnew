package com.jusfoun.hookah.core.annotation;

import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by chenhf on 2017/7/18.
 */
@Document
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Log {
    //操作平臺
    String platform() default "";
    //日志类型
    String logType() default "";
    //操作类型
    String optType() default "";
}
