package com.ken.material.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 映射注解
 *
 * @author ken
 * @version 1.0
 * @date 2020-09-14
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Mapping {
    String value() default "";

    boolean aes() default false;
}
