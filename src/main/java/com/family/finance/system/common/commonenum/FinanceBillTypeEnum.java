package com.family.finance.system.common.commonenum;

import lombok.Getter;

@Getter
public enum FinanceBillTypeEnum {
    /**
     * 收入
     */
    FINANCE_TYPE_INCOME("收入", 1),
    /**
     * 支出
     */
    FINANCE_TYPE_EXPENSE("支出", 2);

    // 补充Getter供前端/业务取值
    private final String typeName;

    private final Integer typeCd;


    FinanceBillTypeEnum(String typeName, Integer typeCd) {
        this.typeName = typeName;
        this.typeCd = typeCd;

    }

    // 根据类型编码查询名称
    public static FinanceBillTypeEnum getFinanceTypeEnum(Integer typeCd) {
        // 遍历全部枚举实例匹配编码
        for (FinanceBillTypeEnum item : FinanceBillTypeEnum.values()) {
            if (item.typeCd.equals(typeCd)) {
                return item;
            }
        }
        // 无匹配编码返回空，可按需改为抛异常/返回默认值
        return null;
    }


    // 根据类型编码查询名称
    public static FinanceBillTypeEnum getFinanceTypeEnum(String typeName) {
        // 遍历全部枚举实例匹配编码
        for (FinanceBillTypeEnum item : FinanceBillTypeEnum.values()) {
            if (item.typeName.equals(typeName)) {
                return item;
            }
        }
        // 无匹配编码返回空，可按需改为抛异常/返回默认值
        return null;
    }

}
