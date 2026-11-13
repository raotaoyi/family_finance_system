package com.family.finance.system.controller;

import com.family.finance.system.common.result.Result;
import com.family.finance.system.controller.command.HealthExamCommand;
import com.family.finance.system.controller.query.HealthExamQuery;
import com.family.finance.system.service.impl.HealthExamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 体检记录
 */
@RestController
@RequestMapping("/health/exam")
public class HealthExamController {

    @Autowired
    private HealthExamServiceImpl healthExamService;

    @PostMapping("/get/list")
    public Result<?> getExamList(@RequestBody HealthExamQuery query) {
        return Result.success(healthExamService.getExamList(query));
    }

    @PostMapping("/add")
    public Result<?> addExam(@RequestBody HealthExamCommand command) {
        healthExamService.addExam(command);
        return Result.success();
    }

    @PostMapping("/update")
    public Result<?> updateExam(@RequestBody HealthExamCommand command) {
        healthExamService.updateExam(command);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<?> deleteExam(@PathVariable("id") Long id) {
        healthExamService.deleteExam(id);
        return Result.success();
    }
}
