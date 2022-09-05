package org.ohx.studymybatisplus.dal.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * 性别枚举
 *
 * @author mudkip
 * @date 2022/9/5
 */
@Getter
public enum SexEnum {
    /**
     * 男
     */
    MALE(1, "男"),
    /**
     * 女
     */
    FEMALE(2, "女");

    @EnumValue
    private Integer sex;
    private String sexName;

    SexEnum(Integer sex, String sexName) {
        this.sex = sex;
        this.sexName = sexName;
    }
}
