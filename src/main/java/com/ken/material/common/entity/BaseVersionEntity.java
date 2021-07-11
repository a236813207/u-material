package com.ken.material.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author ken
 * @version 1.0
 * @date 2020-09-14
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class BaseVersionEntity extends BaseEntity{

    @TableField(value = "version")
    @Version
    private Long version;

}
