package com.ken.material.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.ken.material.common.entity.BaseEntity;
import com.ken.material.enums.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 标签表
 * </p>
 *
 * @author ken
 * @since 2021-07-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tb_tag")
public class Tag extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 标签名称
     */
    @TableField("name")
    private String name;

    /**
     * 状态 1正常
     */
    @TableField("status")
    private Status status;

    /**
     * 排序号
     */
    @TableField("sort_no")
    private Long sortNo;

    /**
     * 是否删除
     */
    @TableField("deleted")
    private Boolean deleted;


}
