package com.family.finance.system.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HealthExamVO {

    private Long id;

    private String member;

    private String examDate;

    private String hospital;

    private Double height;

    private Double weight;

    private String vision;

    private String result;

    private String abnormal;

    private String remark;
}
