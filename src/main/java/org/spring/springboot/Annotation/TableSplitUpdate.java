package org.spring.springboot.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yinxiaoen on 2018/2/7.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface TableSplitUpdate {
    //是否分表
    public boolean split() default true;

    public String value() default "";

    public String field() default "";

    //获取分表策略
    public String strategy();
}
