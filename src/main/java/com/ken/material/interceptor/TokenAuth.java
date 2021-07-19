package com.ken.material.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ken
 * @version 1.0
 * @date 2020-09-14
 * 对接口进行权限控制
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TokenAuth {
    //不需要验证时使用
    boolean require() default true;
}
