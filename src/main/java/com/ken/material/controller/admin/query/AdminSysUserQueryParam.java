package com.ken.material.controller.admin.query;

import com.ken.material.common.page.PageRequest;
import lombok.Data;

/**
 * @author ken
 * @version 1.0
 * @date 2020-09-14
 */
@Data
public class AdminSysUserQueryParam extends PageRequest {
    private String username;
}
