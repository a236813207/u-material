package com.ken.material.controller.admin;

import com.ken.material.common.page.PageVo;
import com.ken.material.common.response.ResBody;
import com.ken.material.controller.admin.query.AdminUserQueryParam;
import com.ken.material.service.IUserService;
import com.ken.material.vo.UserListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author ken
 * @since 2021-01-23
 */
@Controller
@RequestMapping("/admin/user")
public class AdminUserController {

    private IUserService userService;

    @GetMapping("")
    public String index() {
        return "/admin/user/user";
    }

    @GetMapping("/list")
    @ResponseBody
    public Object list(AdminUserQueryParam queryParam) {
        PageVo<UserListVo> pageVo = this.userService.searchPage(queryParam);
        Map<String, Object> map = new HashMap<>(8);
        map.put("recordsTotal", pageVo.getTotal());
        map.put("recordsFiltered", pageVo.getTotal());
        map.put("data", pageVo.getContent());
        map.put("code", 200);
        map.put("draw", queryParam.getDraw());
        return map;
    }


    @PostMapping("/update/{id}")
    @ResponseBody
    public ResBody<?> deleteUser(@PathVariable Long id, int status) {
        this.userService.updateStatus(id, status);
        return ResBody.success();
    }


    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }
}

