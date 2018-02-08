package org.spring.springboot.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yinxiaoen on 2018/2/5.
 * 支持left join 主子表。类似于tbl_chain_bill 和 tbl_chain_bill_detail
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface  TableParentsSplit {
    //是否分表
    public boolean split() default true;

    public String value() default "";

    public String field() default "";

    //获取分表策略
    public String strategy();
}
