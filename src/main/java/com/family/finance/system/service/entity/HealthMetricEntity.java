package com.family.finance.system.service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 健康指标（体重/血压/心率）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("health_metric")
public class HealthMetricEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 家庭成员：爸爸/妈妈/宝宝
     */
    private String member;

    /**
     * 记录日期 yyyy-MM-dd
     */
    private String recordDate;

    /**
     * 体重kg
     */
    private Double weight;

    /**
     * 收缩压 mmHg
     */
    private Integer systolic;

    /**
     * 舒张压 mmHg
     */
    private Integer diastolic;

    /**
     * 心率 次/分
     */
    private Integer heartRate;

    /**
     * 备注
     */
    private String remark;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
