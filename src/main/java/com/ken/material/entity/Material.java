package com.ken.material.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ken.material.common.entity.BaseEntity;
import com.ken.material.enums.MaterialStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 素材表
 * </p>
 *
 * @author ken
 * @since 2021-07-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tb_material")
public class Material extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 用户名
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * url
     */
    @TableField("url")
    private String url;

    /**
     * 品牌
     */
    @TableField("brand")
    private String brand;

    /**
     * 关键词
     */
    @TableField("keywords")
    private String keywords;

    /**
     * 标签
     */
    @TableField("tags")
    private String tags;

    /**
     * 文件格式
     */
    @TableField("file_format")
    private String fileFormat;

    /**
     * 下载次数
     */
    @TableField("downloads")
    private Long downloads;

    /**
     * 版权所有
     */
    @TableField("copyright")
    private String copyright;

    /**
     * 排序号
     */
    @TableField("sort_no")
    private Long sortNo;

    /**
     * 状态 0待审核 1正常 -1无效
     */
    @TableField("status")
    private MaterialStatus status;

    /**
     * 是否删除
     */
    @TableField("deleted")
    private Boolean deleted;


}
