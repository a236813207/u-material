package com.ken.material.service;

import com.ken.material.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

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
     * 根据用户名查找
     * @param username 用户名
     * @return User
     */
    User findByUsername(String username);

    /**
     * 注册
     * @param phone 电话
     * @param password 密码
     * @param smsCode 短信验证码
     */
    void register(String phone, String password, String smsCode);


}
