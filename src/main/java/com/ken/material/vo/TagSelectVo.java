package com.ken.material.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ken
 * @version 1.0
 * @date 2021-07-19
 */
@Data
public class TagSelectVo {

    private Long id;

    private String name;

    private LocalDateTime createTime;

}
