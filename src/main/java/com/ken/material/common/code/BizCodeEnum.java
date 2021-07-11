package com.ken.material.common.code;

/**
 * 异常形式枚举基类
 * @author ken
 * @version 1.0
 * @date 2020-09-14
 */
public interface BizCodeEnum<E extends Enum<E>> {
    /**
     * 获取code
     * @return code
     */
    int getCode();

    /**
     * 获取枚举信息
     * @return message
     */
    String getMessage();

    /**
     * 获取起始值
     * @return start
     */
    int getStart();
}
