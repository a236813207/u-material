package com.ken.material.controller.admin;


import com.ken.material.common.page.PageVo;
import com.ken.material.common.response.ResBody;
import com.ken.material.controller.admin.query.AdminSysUserQueryParam;
import com.ken.material.entity.SysUser;
import com.ken.material.enums.Status;
import com.ken.material.service.ISysUserService;
import com.ken.material.vo.SysUserListVo;
import com.ken.material.vo.SysUserVo;
import org.apache.shiro.SecurityUtils;
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
@RequestMapping("/admin")
public class AdminSysUserController {

    private ISysUserService sysUserService;

    @GetMapping("/sysuser")
    public String index() {
        return "/admin/sysuser/sysuser";
    }

    @GetMapping("/sysuser/list")
    @ResponseBody
    public Object list(AdminSysUserQueryParam queryParam) {
        PageVo<SysUserListVo> pageVo = this.sysUserService.searchPage(queryParam);
        Map<String, Object> map = new HashMap<>(8);
        map.put("recordsTotal", pageVo.getTotal());
        map.put("recordsFiltered", pageVo.getTotal());
        map.put("data", pageVo.getContent());
        map.put("code", 200);
        map.put("draw", queryParam.getDraw());
        return map;
    }

    @PostMapping("/sysuser")
    @ResponseBody
    public ResBody<?> createOrUpdate(@RequestBody SysUserVo vo) {
        boolean saveOrUpdate = this.sysUserService.saveOrUpdate(vo);
        if (!saveOrUpdate) {
            return ResBody.failure();
        }
        return ResBody.success();
    }

    @PostMapping("/sysuser/reset-password/{id}")
    @ResponseBody
    public ResBody<?> changePassword(@PathVariable Long id) {
        boolean update = this.sysUserService.resetPassword(id);
        if (!update) {
            return ResBody.failure();
        }
        return ResBody.success();
    }

    @PostMapping("/sysuser/change-state")
    @ResponseBody
    public ResBody<?> changeState(@RequestParam Long id, @RequestParam Status status) {
        boolean update = this.sysUserService.changeState(id, status);
        if (!update) {
            return ResBody.failure();
        }
        return ResBody.success();
    }

    @PostMapping("/sysuser/change-password")
    @ResponseBody
    public ResBody<?> changePassword(String oldPassword, String newPassword) {
        SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        boolean saveOrUpdate = this.sysUserService.changePassword(sysUser.getId(), oldPassword, newPassword);
        if (!saveOrUpdate) {
            return ResBody.failure();
        }
        return ResBody.success();
    }

    @PostMapping("/sysuser/delete/{id}")
    @ResponseBody
    public ResBody<?> deleteUser(@PathVariable Long id) {
        boolean deleteUser = this.sysUserService.deleteUser(id);
        if (!deleteUser) {
            return ResBody.failure();
        }
        return ResBody.success();
    }


    @Autowired
    public void setSysUserService(ISysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }
}

