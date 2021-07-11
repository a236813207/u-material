package com.ken.material.common.util;

import cn.hutool.core.util.ObjectUtil;
import com.ken.material.common.code.BizCodeFace;
import com.ken.material.common.code.ErrorCode;
import com.ken.material.common.exception.BizException;

/**
 * 校验工具
 * @author ken
 * @version 1.0
 * @date 2020-09-14
 */
public class ValidationUtil {

    public static void isNull(Object object, String entity, String params, Object value) {
        if (ObjectUtil.isNull(object)) {
            String msg = entity + "不存在：" + params + "为" + value;
            throw new BizException(BizCodeFace.createBizCode(ErrorCode.DATE_NULL).message(msg));
        }
    }

    public static void notNull(Object object, String entity, String params, Object value) {
        if (ObjectUtil.isNotNull(object)) {
            String msg = entity + "已存在：" + params + "为" + value;
            throw new BizException(BizCodeFace.createBizCode(ErrorCode.DATE_NULL).message(msg));
        }
    }

}
