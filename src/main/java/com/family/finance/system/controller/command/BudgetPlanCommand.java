package com.family.finance.system.controller.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BudgetPlanCommand {
    private Long id;

    /**
     * 预算标题
     */
    private String title;

    /**
     * 计划分类
     */
    private String category;

    /**
     * 年度计划预算
     */
    private Long planBudget;

    /**
     * 预计完成时间
     */
    private String expectFinishTime;

    /**
     * 完成情况：0未开始 1进行中 2已完成 3已终止
     */
    private String finishStatus;

    /**
     * 执行人：爸爸/妈妈/宝宝
     */
    private String executor;

    /**
     * 备注
     */
    private String remark;

    /**
     * 所属年度 如 2026
     */
    private String year;

}
