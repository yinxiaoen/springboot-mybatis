package org.spring.springboot.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Created by 殷效恩 on 2017/12/27.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface TableSplit {
	//是否分表
	 public boolean split() default true;
	 
	 public String value() default "";

	 public String field() default "";
	 
	 //获取分表策略
	 public String strategy();
	 
}
