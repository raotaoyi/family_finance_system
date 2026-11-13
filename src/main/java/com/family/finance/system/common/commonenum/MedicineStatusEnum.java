package com.family.finance.system.common.commonenum;

import lombok.Getter;

/**
 * 药品/疫苗 状态枚举：0未开始 1进行中 2已完成 3已停用
 */
@Getter
public enum MedicineStatusEnum {

    NOT_START(0, "未开始"),
    PROGRESS(1, "进行中"),
    FINISHED(2, "已完成"),
    STOPPED(3, "已停用");

    private final Integer code;
    private final String desc;

    MedicineStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(Integer code) {
        for (MedicineStatusEnum item : MedicineStatusEnum.values()) {
            if (item.code.equals(code)) {
                return item.desc;
            }
        }
        return "";
    }

    public static Integer getCodeByDesc(String desc) {
        if (desc == null) {
            return null;
        }
        for (MedicineStatusEnum item : MedicineStatusEnum.values()) {
            if (item.desc.equals(desc)) {
                return item.code;
            }
        }
        return null;
    }
}
