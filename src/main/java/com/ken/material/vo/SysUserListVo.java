package com.ken.material.vo;

import com.ken.material.enums.Status;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ken
 * @version 1.0
 * @date 2020-09-14
 */
@ApiModel
@Data
public class SysUserListVo implements Serializable {
    private Long id;

    @ApiModelProperty("状态")
    private Status status;

    @ApiModelProperty("账号")
    private String username;

    @ApiModelProperty("联系方式")
    private String telephone;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("最近登录时间")
    private LocalDateTime lastLoginTime;
}
