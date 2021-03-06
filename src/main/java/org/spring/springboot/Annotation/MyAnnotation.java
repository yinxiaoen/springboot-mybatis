package org.spring.springboot.Annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by 殷效恩 on 2017/12/27.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation
{
    String hello() default "gege";
    String world();
    int[] array() default { 2, 4, 5, 6 };
    Class style() default String.class;
}