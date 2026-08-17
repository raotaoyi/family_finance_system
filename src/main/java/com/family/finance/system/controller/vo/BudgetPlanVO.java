package com.family.finance.system.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BudgetPlanVO {

    private Long id;
    private String title;
    private String category;
    private String planBudget;
    private String realPay;
    private String diff;
    private String status;

    private String expectFinishTime;
    private String finishStatus;
    private String finishStatusText;

    private String executor;
    private String remark;
    private String year;
}