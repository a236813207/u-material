package com.ken.material.common.code;

/**
 * @author ken
 * @version 1.0
 * @date 2020-09-14
 */
public enum ErrorCode implements BizCodeEnum<ErrorCode> {
    /**
     * 错误码
     */
    OK(200, "接口调用成功"),
    PERMISSION_EXPIRED(201, "权限授权过期"),
    PERMISSION_DENIED(202,"接口权限不足"),
    AUTH_FAIL(203, "账号密码错误"),
    ACCOUNT_NO_EXISTIS(204, "账号不存在"),
    ACCOUNT_LOCKED(205, "账号被冻结"),
    ACCOUNT_EXISTIS(206, "账号已存在"),
    LACK_BALANCE(210, "余额不足"),
    NOT_PASSWORD(211, "未设置交易密码"),
    WRONG_PASSWORD(212, "交易密码错误"),
    PARAM_ERROR(301, "接口参数错误"),
    DATE_NULL(302, "数据异常"),
    FAST_FREQUENCY(333, "请求过于频繁"),
    REPEAT_AUDIT(400, "重复审核"),
    FAIL(500, "服务器繁忙"),

    ;

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public int getStart() {
        return 0;
    }
}
