package com.ken.material.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ken.material.common.code.BizCodeFace;
import com.ken.material.common.code.ErrorCode;
import com.ken.material.common.constant.RegexPatterns;
import com.ken.material.common.exception.BizException;
import com.ken.material.common.util.PasswordUtil;
import com.ken.material.constant.CommonCacheKey;
import com.ken.material.entity.User;
import com.ken.material.enums.UserStatus;
import com.ken.material.mapper.UserMapper;
import com.ken.material.service.ISmsService;
import com.ken.material.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author ken
 * @since 2021-07-04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private ISmsService smsService;

    @Override
    public User findByUsername(String username) {
        return this.getOne(Wrappers.lambdaQuery(User.class).eq(User::getUsername, username).last("limit 1"));
    }

    @Override
    public void register(String phone, String password, String smsCode) {
        boolean checkCode = this.smsService.checkCode(phone, smsCode, String.format(CommonCacheKey.REGISTER_CODE, phone));
        if (!checkCode) {
            throw new BizException(BizCodeFace.createBizCode(ErrorCode.DATE_NULL).message("验证码错误"));
        }
        Assert.isTrue(StringUtils.hasText(phone), "手机号不能为空");
        Assert.isTrue(StringUtils.hasText(password), "密码不能为空");
        Assert.isTrue(phone.matches(RegexPatterns.MOBILE_PHONE_NUMBER_PATTERN), "请输入正确的手机号格式");

        User existUser = this.getOne(Wrappers.lambdaQuery(User.class).eq(User::getUsername, phone));
        if (existUser != null) {
            throw new BizException(BizCodeFace.createBizCode(ErrorCode.ACCOUNT_EXISTIS));
        }

        String salt = PasswordUtil.generateSalt();
        password = PasswordUtil.encodePassword(password, salt);
        User user = new User();
        user.setUsername(phone);
        user.setPassword(password);
        user.setSalt(salt);
        user.setStatus(UserStatus.VALID);
        boolean result = this.save(user);
        if (!result) {
            throw new BizException(BizCodeFace.createBizCode(ErrorCode.FAIL));
        }
    }

    @Autowired
    public void setSmsService(ISmsService smsService) {
        this.smsService = smsService;
    }
}
