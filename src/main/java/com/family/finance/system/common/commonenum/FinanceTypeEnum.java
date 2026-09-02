package com.family.finance.system.common.commonenum;

import lombok.Getter;

@Getter
public enum FinanceTypeEnum {
    FINANCE_TYPE_CASH_FLOW("现金流", 1),
    FINANCE_TYPE_DEPOSIT("存款", 2),
    FINANCE_TYPE_DEBT("负债", 3),
    FINANCE_TYPE_INVEST("投资", 4);

    // 补充Getter供前端/业务取值
    private final String typeName;

    private final Integer typeCd;


    FinanceTypeEnum(String typeName, Integer typeCd) {
        this.typeName = typeName;
        this.typeCd = typeCd;

    }

    // 根据类型编码查询名称
    public static FinanceTypeEnum getFinanceTypeEnum(Integer typeCd) {
        // 遍历全部枚举实例匹配编码
        for (FinanceTypeEnum item : FinanceTypeEnum.values()) {
            if (item.typeCd.equals(typeCd)) {
                return item;
            }
        }
        // 无匹配编码返回空，可按需改为抛异常/返回默认值
        return null;
    }

}
