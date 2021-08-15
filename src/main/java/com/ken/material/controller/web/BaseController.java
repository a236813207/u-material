package com.ken.material.controller.web;

import com.ken.material.entity.User;
import com.ken.material.service.IUserService;
import com.ken.material.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * @author ken
 * @version 1.0
 * @date 2021-08-01
 */
@Controller
public class BaseController {

    public IUserService userService;

    public UserVo getUser(HttpServletRequest request) {
        Object userId = request.getSession().getAttribute("userId");
        if (userId == null) {
            return null;
        }
        UserVo vo = new UserVo();
        User user = this.userService.getById((Serializable) userId);
        BeanUtils.copyProperties(user, vo);
        return vo;
    }


    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }
}
