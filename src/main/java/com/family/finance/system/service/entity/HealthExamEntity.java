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
 * 体检记录
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("health_exam")
public class HealthExamEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 家庭成员：爸爸/妈妈/宝宝
     */
    private String member;

    /**
     * 体检日期 yyyy-MM-dd
     */
    private String examDate;

    /**
     * 体检机构
     */
    private String hospital;

    /**
     * 身高cm
     */
    private Double height;

    /**
     * 体重kg
     */
    private Double weight;

    /**
     * 视力
     */
    private String vision;

    /**
     * 体检结论
     */
    private String result;

    /**
     * 异常项
     */
    private String abnormal;

    /**
     * 备注
     */
    private String remark;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
