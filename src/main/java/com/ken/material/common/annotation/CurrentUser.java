package com.ken.material.common.annotation;

import com.ken.material.common.constant.CommonConstant;

import java.lang.annotation.*;

/**
 * <p>绑定当前登录的用户</p>
 * <p>不同于@ModelAttribute</p>
 * @author ken
 * @version 1.0
 * @date 2020-09-14
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {

    /**
     * 当前用户在request中的名字
     * @return request中的名字
     */
    String value() default CommonConstant.CURRENT_USER;

}