package com.ken.material.vo;

import com.ken.material.enums.UserStatus;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ken
 * @version 1.0
 * @date 2021-01-23
 */
@Data
public class UserListVo {

    private Long id;

    private String username;

    private UserStatus status;

    private LocalDateTime createTime;

}
