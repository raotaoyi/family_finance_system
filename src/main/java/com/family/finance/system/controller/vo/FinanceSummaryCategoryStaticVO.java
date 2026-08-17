package com.family.finance.system.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinanceSummaryCategoryStaticVO {
    private String  name;

    private Long value;
}

