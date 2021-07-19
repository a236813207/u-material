package com.ken.material.interceptor;

import com.ken.material.entity.User;
import com.ken.material.service.IUserService;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

/**
 * @author ken
 * @version 1.0
 * @date 2021-07-13
 */
@Component
public class CookieInterceptor extends HandlerInterceptorAdapter {

    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        TokenAuth auth = method.getAnnotation(TokenAuth.class);
        if (auth == null) {
            auth = method.getDeclaringClass().getAnnotation(TokenAuth.class);
        }
        if (auth == null) {
            return true;
        }
        HttpSession session = request.getSession();
        if (session.getAttribute("userId") != null) {
            return true;
        }
        Cookie[] cookies = request.getCookies();
        String phone = null;
        String password = null;
        if (cookies == null) {
            WebUtils.issueRedirect(request, response, "/user/login");
            return true;
        }
        for (Cookie cookie : cookies) {
            if ("phone".equals(cookie.getName())) {
                phone = cookie.getValue();
            }
            if ("security".equals(cookie.getName())) {
                password = cookie.getValue();
            }
        }
        if (!StringUtils.isEmpty(phone) && !StringUtils.isEmpty(password)) {
            User user = this.userService.findByUsernameAndPassword(phone, password);
            if (user != null) {
                session.setAttribute("userId", user.getId());
                return true;
            }
        }
        WebUtils.issueRedirect(request, response, "/user/login");
        return true;
    }

    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }
}
