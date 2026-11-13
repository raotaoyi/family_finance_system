package com.family.finance.system.controller;

import com.family.finance.system.common.result.Result;
import com.family.finance.system.controller.command.MedicalRecordCommand;
import com.family.finance.system.controller.query.MedicalRecordQuery;
import com.family.finance.system.service.impl.MedicalRecordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 就医记录
 */
@RestController
@RequestMapping("/health/medical")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordServiceImpl medicalRecordService;

    @PostMapping("/get/list")
    public Result<?> getMedicalRecordList(@RequestBody MedicalRecordQuery query) {
        return Result.success(medicalRecordService.getMedicalRecordList(query));
    }

    @PostMapping("/get/cost/stat")
    public Result<?> getCostStat(@RequestBody MedicalRecordQuery query) {
        return Result.success(medicalRecordService.getCostStat(query));
    }

    @PostMapping("/add")
    public Result<?> addMedicalRecord(@RequestBody MedicalRecordCommand command) {
        medicalRecordService.addMedicalRecord(command);
        return Result.success();
    }

    @PostMapping("/update")
    public Result<?> updateMedicalRecord(@RequestBody MedicalRecordCommand command) {
        medicalRecordService.updateMedicalRecord(command);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<?> deleteMedicalRecord(@PathVariable("id") Long id) {
        medicalRecordService.deleteMedicalRecord(id);
        return Result.success();
    }
}
