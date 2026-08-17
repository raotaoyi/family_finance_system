package com.family.finance.system.mapper.po;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinanceBillSummaryTrendPO {
    private String date;

    private String name;

    private Long amount;
}
