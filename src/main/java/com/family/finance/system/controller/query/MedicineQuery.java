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
public class MedicineQuery extends PageParam {

    /**
     * 家庭成员：爸爸/妈妈/宝宝（为空表示全部）
     */
    private String member;

    /**
     * 类型：1药品 2疫苗（为空表示全部）
     */
    private Integer category;

    /**
     * 状态：0未开始 1进行中 2已完成 3已停用（为空表示全部）
     */
    private Integer status;
}
