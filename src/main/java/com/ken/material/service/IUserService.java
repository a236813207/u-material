package com.ken.material.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ken.material.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author ken
 * @since 2021-07-04
 */
public interface IUserService extends IService<User> {

    /**
     * 登录
     * @param phone 电话
     * @param password 密码
     * @param request 请求
     * @param response 返回
     */
    void login(String phone, String password, HttpServletRequest request, HttpServletResponse response);

    /**
     * 注册
     * @param phone 电话
     * @param password 密码
     * @param smsCode 短信验证码
     */
    void register(String phone, String password, String smsCode);

    /**
     * 登出
     * @param request 请求
     * @param response 返回
     */
    void logout(HttpServletRequest request, HttpServletResponse response);

    /**
     * 根据用户名查找
     * @param username 用户名
     * @return User
     */
    User findByUsername(String username);


}
