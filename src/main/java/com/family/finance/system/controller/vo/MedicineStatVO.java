package com.family.finance.system.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 药品/疫苗 统计：总数 + 类型分布 + 状态分布
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedicineStatVO {

    private Long totalCount;

    private List<NameValueVO> categoryList;

    private List<NameValueVO> statusList;
}
