package com.family.finance.system.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinanceBillVO {

    private Long id;

    /**
     * 分类ID
     */
    private String category;

    /**
     * 金额
     */
    private String amount;

    /**
     * 1收入 2支出
     */
    private String type;

    /**
     * 备注
     */
    private String remark;

    /**
     * 账单日期 date类型
     */
    private String billDate;

    /**
     * 所属用户id
     */
    private String userName;
}
