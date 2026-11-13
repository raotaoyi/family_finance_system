package com.family.finance.system.common.commonenum;

import lombok.Getter;

/**
 * 药品/疫苗 类型枚举
 */
@Getter
public enum MedicineCategoryEnum {

    MEDICINE("药品", 1),
    VACCINE("疫苗", 2);

    private final String name;
    private final Integer code;

    MedicineCategoryEnum(String name, Integer code) {
        this.name = name;
        this.code = code;
    }

    public static MedicineCategoryEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (MedicineCategoryEnum item : MedicineCategoryEnum.values()) {
            if (item.code.equals(code)) {
                return item;
            }
        }
        return null;
    }

    public static MedicineCategoryEnum getByName(String name) {
        if (name == null) {
            return null;
        }
        for (MedicineCategoryEnum item : MedicineCategoryEnum.values()) {
            if (item.name.equals(name)) {
                return item;
            }
        }
        return null;
    }
}
