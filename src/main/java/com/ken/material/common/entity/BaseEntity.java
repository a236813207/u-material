package com.ken.material.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class BaseEntity extends BaseFillEntity {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

}
