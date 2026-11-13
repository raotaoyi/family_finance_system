package com.family.finance.system.controller.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedicineCommand {

    private Long id;

    private String member;

    private String name;

    /**
     * 类型：1药品 2疫苗
     */
    private Integer category;

    private String dose;

    private String frequency;

    private String startDate;

    private String endDate;

    private String remaining;

    /**
     * 状态：0未开始 1进行中 2已完成 3已停用
     */
    private Integer status;

    /**
     * 是否提醒：0否 1是
     */
    private Integer remind;

    private String remark;
}
