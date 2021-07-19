package com.ken.material.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author ken
 * @version 1.0
 * @date 2021-07-11
 */
@Data
public class MaterialAddVo {

    @NotNull(message = "请选择文件")
    private String url;

    @NotNull(message = "品牌不能为空")
    private String brand;

    @NotNull(message = "关键词不能为空")
    private String keywords;

    @NotNull(message = "标签不能为空")
    private String tags;

}
