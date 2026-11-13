package com.family.finance.system.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 就医费用统计：总费用 + 科室分布 + 月度趋势
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedicalCostStatVO {

    private BigDecimal totalCost;

    private List<NameValueVO> departmentList;

    private List<NameValueVO> monthTrend;
}
