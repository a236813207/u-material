package com.ken.material.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ken.material.common.entity.BaseEntity;
import com.ken.material.enums.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author ken
 * @version 1.0
 * @date 2020-09-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_user")
public class SysUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("username")
    private String username;

    @TableField("password")
    private String password;

    @TableField("salt")
    private String salt;

    @TableField("telephone")
    private String telephone;

    /**
     * 最后登录时间
     */
    @TableField("last_login_time")
    private LocalDateTime lastLoginTime;

    @TableField("status")
    private Status status = Status.enable;
}
