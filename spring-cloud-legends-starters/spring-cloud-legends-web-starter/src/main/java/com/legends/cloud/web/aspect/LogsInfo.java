package com.legends.cloud.web.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;


@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface LogsInfo {
	/**
	 * 是否打印日志，默认是
	 * @return
     */
	boolean isOutPut() default true;

	/**
	 * 最大输出，超过则不输出
	 * @return
     */
	int maxSize() default 1024;

	/**
	 * 删除标识
	 * @return
     */
	String[] removeRge()default {};
}
