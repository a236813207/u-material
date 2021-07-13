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
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author ken
 * @since 2021-07-04
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private ISmsService smsService;

    @Override
    public void login(String phone, String password, HttpServletRequest request, HttpServletResponse response) {
        User user = this.findByUsername(phone);
        if (user == null) {
            log.error("登陆失败,username:[{}]", phone);
            throw new AuthenticationException("用户名或密码错误");
        }
        if (UserStatus.LOCKED == user.getStatus()) {
            throw new AuthenticationException("账号已锁定");
        }
        if (UserStatus.INVALID == user.getStatus()) {
            throw new AuthenticationException("账号已禁用");
        }

        String encryptPassword = PasswordUtil.encodePassword(password, user.getSalt());
        if (!encryptPassword.equals(user.getPassword())) {
            throw new AuthenticationException("账号或密码错误");
        }
        request.getSession().setAttribute("userId", user.getId());
        Cookie cookie = new Cookie(phone, user.getPassword());
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
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

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId != null) {
            User user = this.getById(userId);
            if (user != null) {
                Cookie cookie = new Cookie(user.getUsername(), "");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        session.removeAttribute("userId");
    }

    @Override
    public User findByUsername(String username) {
        return this.getOne(Wrappers.lambdaQuery(User.class).eq(User::getUsername, username).last("limit 1"));
    }

    @Autowired
    public void setSmsService(ISmsService smsService) {
        this.smsService = smsService;
    }
}
