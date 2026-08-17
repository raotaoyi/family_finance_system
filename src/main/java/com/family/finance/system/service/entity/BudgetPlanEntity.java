package com.family.finance.system.service.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("budget_plan")
public class BudgetPlanEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 预算标题
     */
    private String title;

    /**
     * 计划分类
     */
    private Integer category;

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
    private Integer finishStatus;

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

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}