package com.ken.material.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.ken.material.common.code.BizCodeFace;
import com.ken.material.common.code.ErrorCode;
import com.ken.material.common.exception.BizException;
import com.ken.material.common.page.PageVo;
import com.ken.material.common.util.PasswordUtil;
import com.ken.material.controller.admin.query.AdminSysUserQueryParam;
import com.ken.material.entity.SysUser;
import com.ken.material.enums.Status;
import com.ken.material.mapper.SysUserMapper;
import com.ken.material.service.ISysUserService;
import com.ken.material.vo.SysUserListVo;
import com.ken.material.vo.SysUserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ken
 * @version 1.0
 * @date 2020-09-14
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    private static final String DEFAULT_PASSWORD = "123456";

    @Override
    public PageVo<SysUserListVo> searchPage(AdminSysUserQueryParam queryParam) {
        Page<SysUser> page = new Page<>(queryParam.getPage(), queryParam.getRows());
        page.setOrders(Lists.newArrayList(
                OrderItem.desc("create_time")
        ));
        QueryWrapper<SysUser> params = new QueryWrapper<>();
        if (StringUtils.hasText(queryParam.getUsername())) {
            params.lambda().like(SysUser::getUsername, queryParam.getUsername());
        }
        Page<SysUser> result = this.page(page, params);
        if (CollectionUtils.isEmpty(result.getRecords())) {
            return PageVo.create(queryParam.getPage(), queryParam.getRows(), 0, Lists.newArrayList());
        }
        List<SysUserListVo> list = result.getRecords().stream().map(item -> {
            SysUserListVo vo = new SysUserListVo();
            BeanUtils.copyProperties(item, vo);
            return vo;
        }).collect(Collectors.toList());
        return PageVo.create(queryParam.getPage(), queryParam.getRows(), result.getTotal(), list);
    }

    @Override
    public boolean saveOrUpdate(SysUserVo vo) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(vo, sysUser);
        if (vo.getId() != null) {
            sysUser.setSalt(null);
            sysUser.setPassword(null);
        }else {
            String password;
            String salt = PasswordUtil.generateSalt();
            if (StringUtils.hasText(vo.getPassword())) {
                password = PasswordUtil.encodePassword(vo.getPassword(), salt);
            }else {
                password = PasswordUtil.encodePassword(DEFAULT_PASSWORD, salt);
            }
            sysUser.setSalt(salt);
            sysUser.setPassword(password);
        }

        return this.saveOrUpdate(sysUser);
    }


    @Override
    public boolean deleteUser(Long id) {
        return this.removeById(id);
    }

    @Override
    public boolean resetPassword(Long id) {
        return this.changePassword(id, DEFAULT_PASSWORD);
    }

    private boolean changePassword(Long userId, String password) {
        String salt = PasswordUtil.generateSalt();
        String secret = PasswordUtil.encodePassword(password, salt);
        return this.update(new UpdateWrapper<SysUser>()
                .lambda()
                .set(SysUser::getSalt, salt)
                .set(SysUser::getPassword, secret)
                .eq(SysUser::getId, userId));
    }

    @Override
    public boolean changePassword(Long id, String oldPassword, String newPassword) {
        SysUser sysUser = getById(id);
        if (sysUser == null) {
            return false;
        }
        boolean verifyResult = PasswordUtil.verifyPassword(oldPassword, sysUser.getPassword(), sysUser.getSalt());
        if (!verifyResult) {
            throw new BizException(BizCodeFace.createBizCode(ErrorCode.FAIL).message("原密码错误"));
        }
        return this.changePassword(id, newPassword);
    }

    @Override
    public SysUser getByUsername(String username) {
        return this.getOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getUsername, username));
    }

    @Override
    public boolean changeState(Long id, Status state) {
        return this.update(new UpdateWrapper<SysUser>().lambda()
                .eq(SysUser::getId,id)
                .set(SysUser::getStatus, state)
        );
    }

    private void modifyLastLoginTime(String username) {
        this.update(new UpdateWrapper<SysUser>().lambda()
                .eq(SysUser::getUsername, username)
                .set(SysUser::getLastLoginTime, LocalDateTime.now())
        );
    }

}
