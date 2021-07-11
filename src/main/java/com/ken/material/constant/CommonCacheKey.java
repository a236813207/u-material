package com.ken.material.constant;

/**
 * @author ken
 * @version 1.0
 * @date 2021-07-10
 */
public interface CommonCacheKey {

    /**
     * 缓存名字
     */
    String CACHE_NAME_SMS_CODE = "smsCode";

    /**
     * 登录验证码
     */
    String LOGIN_CODE = "sms:login:%s";

    /**
     * 注册验证码
     */
    String REGISTER_CODE = "sms:register:%s";

    /**
     * 忘记密码验证码
     */
    String FORGET_CODE = "sms:forget:%s";

    /**
     * 忘记密码验证码
     */
    String RESET_CODE = "sms:reset:%s";

}
