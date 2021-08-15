package com.ken.material.vo;

import com.ken.material.enums.Status;
import lombok.Data;

/**
 * @author ken
 * @version 1.0
 * @date 2021-07-19
 */
@Data
public class TagAddVo {

    private String name;

    private Long sortNo;

    private Status status;

}
