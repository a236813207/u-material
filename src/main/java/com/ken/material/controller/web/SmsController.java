package com.ken.material.controller.web;

import com.ken.material.common.code.BizCodeFace;
import com.ken.material.common.code.ErrorCode;
import com.ken.material.common.constant.RegexPatterns;
import com.ken.material.common.exception.BizException;
import com.ken.material.common.response.ResBody;
import com.ken.material.constant.CommonCacheKey;
import com.ken.material.entity.User;
import com.ken.material.enums.UserStatus;
import com.ken.material.service.ISmsService;
import com.ken.material.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author ken
 * @version 1.0
 * @date 2020-09-23
 */
@RestController
@RequestMapping("/sms")
@Api(value = "发送短信管理", tags = "发送短信管理")
public class SmsController {

    /**
     * 用户登录短信模板名
     */
    private final static String LOGIN_SMS_TEMPLATE = "user_login_sms_template";

    /**
     * 用户注册短信模板名
     */
    private final static String REGISTER_SMS_TEMPLATE = "user_register_sms_template";

    /**
     * 用户忘记密码短信模板名
     */
    private final static String FORGET_PASSWORD_SMS_TEMPLATE = "user_forget_password_sms_template";

    /**
     * 重置交易密码短信模板名
     */
    private final static String RESET_WALLET_PASSWORD_SMS_TEMPLATE = "user_reset_wallet_password_sms_template";

    /**
     * 交易短信模板名
     */
    private final static String DEAL_SMS_TEMPLATE = "deal_sms_template";

    private IUserService userService;
    private ISmsService smsService;

    @PostMapping("/login")
    @ApiOperation("发送登录短信验证码")
    public ResBody<?> login(@Valid @RequestParam String phone) {
        this.validPhone(phone);
        this.smsService.sendCode(LOGIN_SMS_TEMPLATE, phone, String.format(CommonCacheKey.LOGIN_CODE, phone));
        return ResBody.success("发送成功");
    }

    @PostMapping("/register")
    @ApiOperation("发送注册短信验证码")
    public ResBody<?> register(@Valid @RequestParam String phone) {
        Assert.isTrue(phone.matches(RegexPatterns.MOBILE_PHONE_NUMBER_PATTERN), "请输入正确的手机号格式");
        User user = this.userService.findByUsername(phone);
        if (user != null) {
            throw new BizException(BizCodeFace.createBizCode(ErrorCode.ACCOUNT_EXISTIS).message("手机号已注册"));
        }
        this.smsService.sendCode(REGISTER_SMS_TEMPLATE, phone, String.format(CommonCacheKey.REGISTER_CODE, phone));
        return ResBody.success("发送成功");
    }

    @PostMapping("/forget-pwd")
    @ApiOperation(value = "忘记密码发送短信验证码", response = String.class)
    public ResBody<?> forgetPassword(@Valid @RequestParam String phone) {
        this.validPhone(phone);
        this.smsService.sendCode(FORGET_PASSWORD_SMS_TEMPLATE, phone, String.format(CommonCacheKey.FORGET_CODE, phone));
        return ResBody.success();
    }

    /**
     * 校验手机号是否有效
     * @param phone 手机号
     */
    private void validPhone(String phone) {
        Assert.isTrue(phone.matches(RegexPatterns.MOBILE_PHONE_NUMBER_PATTERN), "请输入正确的手机号格式");
        User user = this.userService.findByUsername(phone);
        if (user == null) {
            throw new BizException(BizCodeFace.createBizCode(ErrorCode.ACCOUNT_NO_EXISTIS));
        }
        if (UserStatus.LOCKED.equals(user.getStatus()) || UserStatus.INVALID.equals(user.getStatus())) {
            throw new BizException(BizCodeFace.createBizCode(ErrorCode.ACCOUNT_LOCKED));
        }
    }



    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setSmsService(ISmsService smsService) {
        this.smsService = smsService;
    }
}
