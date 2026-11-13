package com.family.finance.system.controller.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HealthMetricCommand {

    private Long id;

    private String member;

    private String recordDate;

    private Double weight;

    private Integer systolic;

    private Integer diastolic;

    private Integer heartRate;

    private String remark;
}
