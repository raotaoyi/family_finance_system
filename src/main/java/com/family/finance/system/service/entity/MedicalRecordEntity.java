package com.family.finance.system.service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 就医记录
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("medical_record")
public class MedicalRecordEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 家庭成员：爸爸/妈妈/宝宝
     */
    private String member;

    /**
     * 就诊日期 yyyy-MM-dd
     */
    private String visitDate;

    /**
     * 医院
     */
    private String hospital;

    /**
     * 科室
     */
    private String department;

    /**
     * 医生
     */
    private String doctor;

    /**
     * 诊断结果
     */
    private String diagnosis;

    /**
     * 处方/用药
     */
    private String prescription;

    /**
     * 费用
     */
    private BigDecimal cost;

    /**
     * 备注
     */
    private String remark;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
