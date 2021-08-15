package com.ken.material.controller.admin.query;

import com.ken.material.common.page.PageRequest;
import com.ken.material.enums.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ken
 * @version 1.0
 * @date 2020-09-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AdminTagQueryParam extends PageRequest {

    private String name;
    private Status status;

}
