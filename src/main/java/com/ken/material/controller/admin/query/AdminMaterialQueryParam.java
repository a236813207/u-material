package com.ken.material.controller.admin.query;

import com.ken.material.common.page.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * @author ken
 * @version 1.0
 * @date 2020-09-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AdminMaterialQueryParam extends PageRequest {

    private String username;
    private String brand;
    private String keywords;
    private Integer status;
    private LocalDate startTime;
    private LocalDate endTime;

}
