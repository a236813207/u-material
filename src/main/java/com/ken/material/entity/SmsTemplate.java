package com.ken.material.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ken.material.common.entity.BaseEntity;
import com.ken.material.enums.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 短信模板
 * </p>
 *
 * @author ken
 * @since 2020-09-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("tb_sms_template")
public class SmsTemplate extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 签名
     */
    @TableField("sign")
    private String sign;

    /**
     * 启用状态
     */
    @TableField("status")
    private Status status;

    /**
     * 模板编码
     */
    @TableField("template_code")
    private String templateCode;

    /**
     * 模板名称
     */
    @TableField("template_name")
    private String templateName;

}
