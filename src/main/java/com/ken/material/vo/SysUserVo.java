package com.ken.material.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 新增系统用户实体
 * </p>
 *
 * @author ken
 * @version 1.0
 * @date 2020-09-14
 */
@Data
@ApiModel("用户创建")
public class SysUserVo {

    private Long id;

    @ApiModelProperty("账号")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("联系方式")
    private String telephone;

}
