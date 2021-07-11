package com.ken.material.controller.web.query;

import com.ken.material.common.page.PageParams;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 查询实体
 * </p>
 *
 * @author ken
 * @date 2020-08-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
@Accessors(chain = true)
public class MaterialIndexQueryParam extends PageParams {

    @ApiModelProperty("查询参数")
    private String key;

}