package com.family.finance.system.controller;

import com.family.finance.system.common.result.Result;
import com.family.finance.system.controller.command.HealthMetricCommand;
import com.family.finance.system.controller.query.HealthMetricQuery;
import com.family.finance.system.service.impl.HealthMetricServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 健康指标（体重/血压/心率）
 */
@RestController
@RequestMapping("/health/metric")
public class HealthMetricController {

    @Autowired
    private HealthMetricServiceImpl healthMetricService;

    @PostMapping("/get/list")
    public Result<?> getMetricList(@RequestBody HealthMetricQuery query) {
        return Result.success(healthMetricService.getMetricList(query));
    }

    @PostMapping("/get/trend")
    public Result<?> getTrend(@RequestBody HealthMetricQuery query) {
        return Result.success(healthMetricService.getTrend(query));
    }

    @PostMapping("/add")
    public Result<?> addMetric(@RequestBody HealthMetricCommand command) {
        healthMetricService.addMetric(command);
        return Result.success();
    }

    @PostMapping("/update")
    public Result<?> updateMetric(@RequestBody HealthMetricCommand command) {
        healthMetricService.updateMetric(command);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<?> deleteMetric(@PathVariable("id") Long id) {
        healthMetricService.deleteMetric(id);
        return Result.success();
    }
}
