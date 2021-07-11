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
public enum UserStatus implements IEnum<Integer> {
    /**
     * 用户状态
     */
    LOCKED(0, "锁定"),
    VALID(1, "正常"),
    INVALID(-1, "无效"),
    ;

    private int value;
    private String desc;

    UserStatus(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    public static UserStatus getByValue(int value) {
        for (UserStatus auditStatus : UserStatus.values()) {
            if (auditStatus.getValue() == value) {
                return auditStatus;
            }
        }
        return null;
    }
}
