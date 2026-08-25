package com.family.finance.system.common.commonenum;

import lombok.Getter;

@Getter
public enum FinanceBillCategoryEnum {
    // ========== 收入类 ==========
    FINANCE_BILL_CATEGORY_SALARY("工资", 1),
    FINANCE_BILL_CATEGORY_PART_TIME("兼职", 2),

    // ========== 支出消费类 ==========
    FINANCE_BILL_CATEGORY_FOOD("餐饮", 3),
    FINANCE_BILL_CATEGORY_SHOPPING("购物", 4),
    FINANCE_BILL_CATEGORY_TRAFFIC("交通", 5),
    FINANCE_BILL_CATEGORY_HOUSE_LOAN("房贷", 6),

    FINANCE_BILL_CATEGORY_HONGBAO("人情", 7),
    FINANCE_BILL_CATEGORY_DAILY_SUPPLIES("日用品", 8),
    FINANCE_BILL_CATEGORY_UTILITIES("水电", 9),
    FINANCE_BILL_CATEGORY_ENTERTAINMENT("娱乐", 10),
    FINANCE_BILL_CATEGORY_PROPERTY_FEE("物业费", 11),
    FINANCE_BILL_CATEGORY_PHONE_BILL("话费", 12),
    FINANCE_BILL_CATEGORY_INSURANCE("保险", 14),
    FINANCE_BILL_CATEGORY_TRAVEL("旅游", 15),
    FINANCE_BILL_CATEGORY_EDUCATION("教育", 16),
    FINANCE_BILL_CATEGORY_CLOTHING("服饰", 17),
    FINANCE_BILL_CATEGORY_OTHER("其他", 100);

    private final String categoryName;

    private final Integer categoryCd;


    FinanceBillCategoryEnum(String categoryName, Integer categoryCd) {
        this.categoryName = categoryName;
        this.categoryCd = categoryCd;

    }

    // 根据类型编码查询名称
    public static FinanceBillCategoryEnum getFinanceBillCategoryEnum(Integer categoryCd) {
        // 遍历全部枚举实例匹配编码
        for (FinanceBillCategoryEnum item : FinanceBillCategoryEnum.values()) {
            if (item.categoryCd.equals(categoryCd)) {
                return item;
            }
        }
        // 无匹配编码返回空，可按需改为抛异常/返回默认值
        return null;
    }

    public static FinanceBillCategoryEnum getFinanceBillCategoryEnum(String categoryName) {
        // 遍历全部枚举实例匹配编码
        for (FinanceBillCategoryEnum item : FinanceBillCategoryEnum.values()) {
            if (item.categoryName.equals(categoryName)) {
                return item;
            }
        }
        // 无匹配编码返回空，可按需改为抛异常/返回默认值
        return null;
    }
}
