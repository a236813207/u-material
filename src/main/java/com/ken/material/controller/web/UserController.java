package com.ken.material.controller.web;


import com.ken.material.common.response.ResBody;
import com.ken.material.service.IUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author ken
 * @since 2021-07-04
 */
@Controller
@RequestMapping("/user")
@Api(value = "用户", tags = "用户")
public class UserController {

    private IUserService userService;

    @GetMapping("/login")
    public String login() {
        return "/web/login";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResBody<?> login(String phone, String password) {

        return ResBody.success();
    }

    @GetMapping("/register")
    public String register() {
        return "/web/register";
    }

    @PostMapping("/register")
    @ResponseBody
    public ResBody<?> register(String phone, String password, String smsCode) {
        this.userService.register(phone, password, smsCode);
        return ResBody.success();
    }

    @PostMapping("/send/sms")
    public ResBody<?> register(String phone, String validCode) {

        return ResBody.success();
    }

    @GetMapping("/logout")
    @ResponseBody
    public ResBody<?> logout() {
        return ResBody.success();
    }

    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }
}

