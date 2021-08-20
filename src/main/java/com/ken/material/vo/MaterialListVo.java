package com.ken.material.vo;

import com.ken.material.enums.MaterialStatus;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ken
 * @version 1.0
 * @date 2021-07-11
 */
@Data
public class MaterialListVo {

    private Long id;

    private String username;

    private String brand;

    private String keywords;

    private String tags;

    private Long sortNo;

    private MaterialStatus status;

    private String url;

    private Long downloads;

    private LocalDateTime createTime;

}
