package com.family.finance.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.family.finance.system.common.result.Result;
import com.family.finance.system.service.entity.MedicineEntity;
import com.family.finance.system.service.impl.HealthExamServiceImpl;
import com.family.finance.system.service.impl.HealthMetricServiceImpl;
import com.family.finance.system.service.impl.MedicalRecordServiceImpl;
import com.family.finance.system.service.impl.MedicineServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 健康模块总览统计
 */
@RestController
@RequestMapping("/health")
public class HealthController {

    @Autowired
    private HealthExamServiceImpl healthExamService;

    @Autowired
    private MedicalRecordServiceImpl medicalRecordService;

    @Autowired
    private MedicineServiceImpl medicineService;

    @Autowired
    private HealthMetricServiceImpl healthMetricService;

    @GetMapping("/summary")
    public Result<?> summary() {
        Map<String, Object> map = new HashMap<>();
        map.put("examCount", healthExamService.count());
        map.put("medicalCount", medicalRecordService.count());
        map.put("medicineCount", medicineService.count(
                new LambdaQueryWrapper<MedicineEntity>().eq(MedicineEntity::getCategory, 1)));
        map.put("vaccineCount", medicineService.count(
                new LambdaQueryWrapper<MedicineEntity>().eq(MedicineEntity::getCategory, 2)));
        map.put("metricCount", healthMetricService.count());
        return Result.success(map);
    }
}
