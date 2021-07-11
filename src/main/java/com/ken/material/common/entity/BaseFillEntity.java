package com.ken.material.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ken
 * @version 1.0
 * @date 2020-09-14
 */
@Data
@Accessors(chain = true)
public class BaseFillEntity implements Serializable {

    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "modify_by", fill = FieldFill.INSERT_UPDATE)
    private String modifyBy;

    @TableField(value = "modify_Time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime modifyTime;

}
