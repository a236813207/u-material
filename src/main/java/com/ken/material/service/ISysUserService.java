package com.ken.material.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ken.material.common.page.PageVo;
import com.ken.material.controller.admin.query.AdminSysUserQueryParam;
import com.ken.material.entity.SysUser;
import com.ken.material.enums.Status;
import com.ken.material.vo.SysUserVo;
import com.ken.material.vo.SysUserListVo;


/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ken
 * @version 1.0
 * @date 2020-09-14
 */
public interface ISysUserService extends IService<SysUser> {

    PageVo<SysUserListVo> searchPage(AdminSysUserQueryParam queryParam);

    boolean saveOrUpdate(SysUserVo vo);

    boolean deleteUser(Long id);

    boolean resetPassword(Long id);

    boolean changePassword(Long id, String oldPassword, String newPassword);

    SysUser getByUsername(String username);

    boolean changeState(Long id, Status state);
}
