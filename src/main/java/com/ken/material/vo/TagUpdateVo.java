package com.ken.material.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ken
 * @version 1.0
 * @date 2021-07-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TagUpdateVo extends TagAddVo{

    private Long id;

}
