package com.family.finance.system.service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("finance_summary")
public class FinanceSummaryEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long amount;

    private Long category;

    private Long type;
}
