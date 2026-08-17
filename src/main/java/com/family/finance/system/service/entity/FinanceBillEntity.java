package com.family.finance.system.service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("finance_bill")
public class FinanceBillEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 分类ID
     */
    private Integer categoryId;

    /**
     * 金额
     */
    private String money;

    /**
     * 类型：1收入 2支出
     */
    private Integer type;

    /**
     * 备注
     */
    private String remark;

    /**
     * 账单日期
     */
    private String billDate;

    /**
     * 所属用户ID
     */
    private Integer userId;

}
