package com.ken.material.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author ken
 * @version 1.0
 * @date 2021-07-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AdminMaterialAddVo extends MaterialAddVo{

    private Long id;

    @NotNull(message = "请输入上传的用户名称")
    private String username;

    @NotNull(message = "排序号不能为空")
    private Long sortNo;

}
