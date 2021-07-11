package com.ken.material.controller.admin;

import com.ken.material.common.code.BizCodeFace;
import com.ken.material.common.code.ErrorCode;
import com.ken.material.common.constant.CommonConstant;
import com.ken.material.common.exception.BizException;
import com.ken.material.common.response.ResBody;
import com.ken.material.entity.SysUser;
import com.ken.material.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author Ken
 * @date 2019/4/24
 * @description
 */
@Controller
@RequestMapping("/admin")
@Api(tags = "登录")
public class AdminLoginController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private ISysUserService sysUserService;

    @GetMapping("/login")
    @ApiOperation(value = "登录页面")
    public String login() {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null && subject.isAuthenticated()) {
            return "redirect:/admin/main";
        }
        return "/admin/login";
    }

    @PostMapping("/login")
    @ResponseBody
    @ApiOperation(value = "登录", response = ResBody.class)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "用户名", name = "username", paramType = "form", defaultValue = "admin"),
            @ApiImplicitParam(value = "密码", name = "password", paramType = "form", defaultValue = "123456")
    })
    public ResBody<?> login(@ApiIgnore HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null && subject.isAuthenticated()) {
            SysUser sysUser = (SysUser) subject.getSession().getAttribute(CommonConstant.SYS_SESSION_ATTR_KEY);
            sysUser.setLastLoginTime(LocalDateTime.now());
            this.sysUserService.updateById(sysUser);
            return ResBody.success();
        }
        String shiroLoginFailureEx = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);

        if (IncorrectCredentialsException.class.getName().equals(shiroLoginFailureEx)) {
            throw new BizException(BizCodeFace.createBizCode(ErrorCode.AUTH_FAIL));
        } else if (LockedAccountException.class.getName().equals(shiroLoginFailureEx)) {
            throw new BizException(BizCodeFace.createBizCode(ErrorCode.ACCOUNT_LOCKED));
        } else if (UnknownAccountException.class.getName().equals(shiroLoginFailureEx)) {
            throw new BizException(BizCodeFace.createBizCode(ErrorCode.ACCOUNT_NO_EXISTIS));
        }
        throw new BizException(BizCodeFace.createBizCode(ErrorCode.AUTH_FAIL));
    }

    @GetMapping("/main")
    @ApiOperation(value = "主页")
    public ModelAndView main() {
        ModelAndView model = new ModelAndView();
        model.setViewName("/admin/main");
        return model;
    }

    @GetMapping("/index")
    @ApiOperation(value = "首页")
    public String index() {
        return "/admin/index";
    }

    @GetMapping("/logout")
    @ApiOperation(value = "退出登录")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.logout();
        } catch (SessionException ise) {
            logger.debug("Encountered session exception during logout.  This can generally safely be ignored.", ise);
        }
        return "/admin/login";
    }

    @GetMapping("/password")
    @ApiOperation(value = "修改密码页面")
    public String password() {
        return "/admin/sysuser/password";
    }

    @PostMapping("/password")
    @ApiOperation(value = "修改密码")
    @ResponseBody
    public ResBody<?> password(String oldPassword, String newPassword) {
        SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        boolean changePassword = this.sysUserService.changePassword(sysUser.getId(), oldPassword, newPassword);
        if (!changePassword) {
            return ResBody.failure();
        }
        return ResBody.success();
    }

    @Autowired
    public void setSysUserService(ISysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }
}
