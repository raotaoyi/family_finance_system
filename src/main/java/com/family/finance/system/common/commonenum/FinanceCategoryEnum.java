package com.family.finance.system.common.commonenum;

import lombok.Getter;

@Getter
public enum FinanceCategoryEnum {
    FINANCE_CATEGORY_DEPOSIT("定期存款", 1),
    FINANCE_CATEGORY_FUND("基金", 2),
    FINANCE_CATEGORY_GOLD("黄金", 3),
    FINANCE_CATEGORY_T_BOND("国债", 4),
    FINANCE_CATEGORY_HOUSING_FUND("公积金", 5),
    FINANCE_CATEGORY_HOUSING_MORTGAGE("房贷", 6),
    FINANCE_CATEGORY_STOCK("股票", 7);;

    private final String categoryName;

    private final Integer categoryCd;


    FinanceCategoryEnum(String categoryName, Integer categoryCd) {
        this.categoryName = categoryName;
        this.categoryCd = categoryCd;

    }

    // 根据类型编码查询名称
    public static FinanceCategoryEnum getFinanceCategoryEnum(Integer categoryCd) {
        // 遍历全部枚举实例匹配编码
        for (FinanceCategoryEnum item : FinanceCategoryEnum.values()) {
            if (item.categoryCd.equals(categoryCd)) {
                return item;
            }
        }
        // 无匹配编码返回空，可按需改为抛异常/返回默认值
        return null;
    }
}
