package com.family.finance.system.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedicineVO {

    private Long id;

    private String member;

    private String name;

    /**
     * 类型编码：1药品 2疫苗
     */
    private Integer categoryCode;

    /**
     * 类型名称
     */
    private String category;

    private String dose;

    private String frequency;

    private String startDate;

    private String endDate;

    private String remaining;

    /**
     * 状态编码
     */
    private Integer statusCode;

    /**
     * 状态名称
     */
    private String status;

    /**
     * 是否提醒：0否 1是
     */
    private Integer remind;

    private String remark;
}
