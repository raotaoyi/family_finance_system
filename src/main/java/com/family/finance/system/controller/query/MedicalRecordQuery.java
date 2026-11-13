package com.family.finance.system.controller.query;

import com.family.finance.system.common.page.PageParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecordQuery extends PageParam {

    /**
     * 家庭成员：爸爸/妈妈/宝宝（为空表示全部）
     */
    private String member;

    /**
     * 就诊开始日期 yyyy-MM-dd
     */
    private String startDate;

    /**
     * 就诊结束日期 yyyy-MM-dd
     */
    private String endDate;

    /**
     * 科室（模糊）
     */
    private String department;
}
