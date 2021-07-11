package com.ken.material.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Optional;

/**
 * @author ken
 * @version 1.0
 * @date 2020-09-14
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class TreeEntity extends BaseEntity {
    @TableField("parent_ids")
    private String parentIds;

    @TableField("parent_id")
    private Long parentId;

    public String makeSelfAsParentsIds() {
        return Optional.ofNullable(getParentIds()).orElse("") + getId() + "/";
    }
}
