package com.ken.material.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

/**
 * @author Ken
 * @date 2020/7/15
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum MaterialStatus implements IEnum<Integer> {
    /**
     * 素材状态
     */
    WAIT_AUDIT(0, "待审核"),
    VALID(1, "正常"),
    INVALID(-1, "无效"),
    ;

    private int value;
    private String desc;

    MaterialStatus(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    public static MaterialStatus getByValue(int value) {
        for (MaterialStatus auditStatus : MaterialStatus.values()) {
            if (auditStatus.getValue() == value) {
                return auditStatus;
            }
        }
        return null;
    }
}
