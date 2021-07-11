package com.ken.material.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ken.material.common.entity.BaseEntity;
import com.ken.material.enums.UserStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author ken
 * @since 2021-07-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tb_user")
public class User extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 密码盐
     */
    @TableField("salt")
    private String salt;

    /**
     * 昵称
     */
    @TableField("nickname")
    private String nickname;

    /**
     * 头像
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 最后登录时间
     */
    @TableField("last_login_time")
    private Date lastLoginTime;

    /**
     * 最近登录ip
     */
    @TableField("last_login_ip")
    private String lastLoginIp;

    /**
     * 状态 1正常
     */
    @TableField("status")
    private UserStatus status;

    /**
     * 是否删除
     */
    @TableField("deleted")
    private Boolean deleted;


}
