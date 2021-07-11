package com.ken.material.common.exception;


import com.ken.material.common.code.BizCodeFace;

/**
 * 业务类异常
 * @author ken
 * @version 1.0
 * @date 2020-09-14
 */
public class BizException extends RuntimeException {

    private BizCodeFace.BizCode bizCode;

    public BizException(BizCodeFace.BizCode bizCode) {
        super(bizCode.getMessage());
        this.bizCode = bizCode;
    }

    public BizException() {
    }

    public BizCodeFace.BizCode getBizCode() {
        return bizCode;
    }

    public void setBizCode(BizCodeFace.BizCode bizCode) {
        this.bizCode = bizCode;
    }
}
