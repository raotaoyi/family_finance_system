package com.family.finance.system.common.commonenum;

import lombok.Getter;

@Getter
public enum BudgetPlanCategoryEnum {
    BUDGET_PLAN_CATEGORY_TRAVEL("旅游", 15),
    BUDGET_PLAN_CATEGORY_EDUCATION("教育", 16),
    BUDGET_PLAN_CATEGORY_INCOME("其他", 100);

    private final String categoryName;

    private final Integer categoryCd;

    BudgetPlanCategoryEnum(String categoryName, Integer categoryCd) {
        this.categoryName = categoryName;
        this.categoryCd = categoryCd;
    }

    // 根据类型编码查询名称
    public static BudgetPlanCategoryEnum getBudgetPlanCategoryEnum(Integer categoryCd) {
        // 遍历全部枚举实例匹配编码
        for (BudgetPlanCategoryEnum item : BudgetPlanCategoryEnum.values()) {
            if (item.categoryCd.equals(categoryCd)) {
                return item;
            }
        }
        // 无匹配编码返回空，可按需改为抛异常/返回默认值
        return null;
    }

    public static BudgetPlanCategoryEnum getBudgetPlanCategoryEnum(String categoryName) {
        // 遍历全部枚举实例匹配编码
        for (BudgetPlanCategoryEnum item : BudgetPlanCategoryEnum.values()) {
            if (item.categoryName.equals(categoryName)) {
                return item;
            }
        }
        // 无匹配编码返回空，可按需改为抛异常/返回默认值
        return null;
    }
}
