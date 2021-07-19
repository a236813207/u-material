package com.ken.material.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ken.material.common.page.PageVo;
import com.ken.material.controller.admin.query.UserQueryParam;
import com.ken.material.entity.User;
import com.ken.material.vo.UserListVo;

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
     * 用户分页查询
     * @param queryParam 查询参数
     * @return PageVo<UserListVo>
     */
    PageVo<UserListVo> searchPage(UserQueryParam queryParam);

    /**
     * 更新状态
     * @param id id
     * @param status 状态
     */
    void updateStatus(Long id, int status);

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

    /**
     * 根据用户名和密码查找
     * @param phone 手机号
     * @param password 密码
     * @return User
     */
    User findByUsernameAndPassword(String phone, String password);
}
