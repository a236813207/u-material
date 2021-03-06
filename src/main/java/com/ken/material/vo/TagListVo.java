package com.ken.material.vo;

import com.ken.material.enums.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author ken
 * @version 1.0
 * @date 2021-07-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TagListVo extends TagUpdateVo{

    private Status status;

    private LocalDateTime createTime;

}
