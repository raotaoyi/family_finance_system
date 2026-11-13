package com.family.finance.system.controller.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecordCommand {

    private Long id;

    private String member;

    private String visitDate;

    private String hospital;

    private String department;

    private String doctor;

    private String diagnosis;

    private String prescription;

    private BigDecimal cost;

    private String remark;
}
