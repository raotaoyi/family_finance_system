package com.family.finance.system.controller.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinanceBillCommand {
    /**
     * 主键id，编辑时有值，新增为空
     */
    private Long id;

    /**
     * 账单日期
     */
    private String billDate;

    /**
     * 家庭成员 userName（前端字段）
     */
    private String userName;

    /**
     * 收支："收入" / "支出"  前端传字符串
     */
    private String type;

    /**
     * 分类
     */
    private String category;

    /**
     * 金额
     */
    private String amount;

    /**
     * 备注
     */
    private String remark;
}
