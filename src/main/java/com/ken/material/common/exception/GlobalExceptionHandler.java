package com.ken.material.common.exception;

import com.ken.material.common.code.BizCodeFace;
import com.ken.material.common.code.ErrorCode;
import com.ken.material.common.response.ResBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * 统一异常处理
 * @author ken
 * @version 1.0
 * @date 2020-09-14
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BizException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResBody<?> processBizException(BizException e) {
        BizCodeFace.BizCode bizCode = e.getBizCode();
        log.warn(bizCode.getMessage(), e);
        return ResBody.failure(bizCode.getMessage()).code(bizCode.getCode());
    }

    /**
     * 校验错误
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResBody<?> processBindException(BindException e) {
        FieldError fieldError = e.getFieldError();
        assert fieldError != null;
        log.warn("数据校验失败:" + fieldError.getField() + "[" + fieldError.getDefaultMessage() + "]", e);
        return ResBody.failure(fieldError.getDefaultMessage()).code(ErrorCode.PARAM_ERROR.getCode());
    }

    /**
     * 缺少参数
     */
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.OK)
    public ResBody<?> processMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.warn("参数缺失", e);
        return ResBody.failure(ErrorCode.PARAM_ERROR).message("缺少参数[" + e.getParameterName() + "]");
    }

    /**
     * Assert 校验错误
     */
    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.OK)
    public ResBody<?> processIllegalArgumentException(IllegalArgumentException e) {
        log.warn("数据校验失败错误", e);
        return ResBody.failure(ErrorCode.PARAM_ERROR).message(e.getMessage());
    }

    @ExceptionHandler({AuthenticationException.class})
    @ResponseStatus(HttpStatus.OK)
    public ResBody<?> authenticationException(AuthenticationException e) {
        log.warn("认证失败", e);
        return ResBody.failure(ErrorCode.PERMISSION_EXPIRED).message(e.getMessage());
    }

    /**
     * 默认的异常处理
     *
     * @param exception exception
     * @return ResBody
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public ResBody<?> exceptionHandler(NativeWebRequest request, Exception exception) {
        log.error("exception:", exception);
        ResBody<?> failure = ResBody.failure();
        String debug = request.getHeader("debug");
        if ("true".equals(debug)) {
            failure.trace(exception.getMessage());
        }
        return failure;
    }

}
