package com.family.finance.system.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 通用名称-数值 统计项（饼图/柱状图数据）
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NameValueVO {

    private String name;

    private BigDecimal value;
}
