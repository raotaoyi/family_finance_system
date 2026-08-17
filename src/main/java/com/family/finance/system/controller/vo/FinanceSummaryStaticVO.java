package com.family.finance.system.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinanceSummaryStaticVO {

    private Long deposit = 0L;

    private Long cashFlow = 0L;

    private Long debt = 0L;

    private Long balance = 0L;
}
