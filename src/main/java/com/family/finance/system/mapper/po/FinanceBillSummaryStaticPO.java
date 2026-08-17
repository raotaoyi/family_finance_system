package com.family.finance.system.mapper.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinanceBillSummaryStaticPO {
    private Integer cd;

    private Double amount;
}
