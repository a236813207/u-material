package com.ken.material.common.constant;

/**
 * <p>
 * 正则表达式
 * </p>
 *
 * @author ken
 * @version 1.0
 * @date 2020-09-14
 **/
public interface RegexPatterns {
    /**
     * 手机号
     */
    String MOBILE_PHONE_NUMBER_PATTERN = "^0{0,1}1[3456789][0-9]{9}$";

    /**
     * 邮箱
     */
    String EMAIL_PATTERN = "^[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?$";

    /**
     * 身份证号
     */
    String IDCARD_PATTERN = "^[1-9]([0-9]{16}|[0-9]{13})[xX0-9]$";

    /**
     * 银行卡号
     */
    String BANKCARD_PATTERN = "^[0-9]*$";
}
