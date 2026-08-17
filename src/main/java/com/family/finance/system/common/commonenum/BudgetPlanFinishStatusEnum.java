package com.family.finance.system.common.commonenum;

import lombok.Getter;

/**
 * 预算计划完成情况枚举
 * 0未开始 1进行中 2已完成 3已终止
 */
@Getter
public enum BudgetPlanFinishStatusEnum {

    NOT_START(0, "未开始"),
    PROGRESS(1, "进行中"),
    FINISHED(2, "已完成"),
    TERMINATED(3, "已终止");

    private final Integer code;
    private final String desc;

    BudgetPlanFinishStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据code获取描述文本
     * @param code 编码
     * @return 中文描述
     */
    public static String getDescByCode(Integer code) {
        if (code == null) {
            return "";
        }
        for (BudgetPlanFinishStatusEnum statusEnum : BudgetPlanFinishStatusEnum.values()) {
            if (statusEnum.getCode().equals(code)) {
                return statusEnum.getDesc();
            }
        }
        return "";
    }

    /**
     * 根据code获取枚举对象
     */
    public static BudgetPlanFinishStatusEnum getEnumByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (BudgetPlanFinishStatusEnum statusEnum : BudgetPlanFinishStatusEnum.values()) {
            if (statusEnum.getCode().equals(code)) {
                return statusEnum;
            }
        }
        return null;
    }

    public static BudgetPlanFinishStatusEnum getEnumByDesc(String desc) {
        if (desc == null) {
            return null;
        }
        for (BudgetPlanFinishStatusEnum statusEnum : BudgetPlanFinishStatusEnum.values()) {
            if (statusEnum.getDesc().equals(desc)) {
                return statusEnum;
            }
        }
        return null;
    }
}