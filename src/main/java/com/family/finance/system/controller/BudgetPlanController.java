package com.family.finance.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.family.finance.system.common.result.Result;
import com.family.finance.system.controller.command.BudgetPlanCommand;
import com.family.finance.system.controller.query.BudgetPlanQuery;
import com.family.finance.system.controller.vo.BudgetPlanCategoryStaticVO;
import com.family.finance.system.controller.vo.BudgetPlanVO;
import com.family.finance.system.controller.vo.YearBudgetInfoVO;
import com.family.finance.system.service.BudgetPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.List;

@RestController
@RequestMapping("/budget/plan")
public class BudgetPlanController {
    @Autowired
    private BudgetPlanService budgetPlanService;

    @PostMapping("/get/yearBudgetInfo")
    public Result<?> getYearBudgetInfo(@RequestBody BudgetPlanQuery query) {
        Result<YearBudgetInfoVO> result = new Result<>();
        result.setData(budgetPlanService.getYearBudgetInfo(query));
        result.setCode(200);
        result.setMsg("查询成功");
        return result;
    }

    @PostMapping("/get/chartData")
    public Result<?> getChartData(@RequestBody BudgetPlanQuery query) {
        Result<List<BudgetPlanCategoryStaticVO>> result = new Result<>();
        result.setData(budgetPlanService.getChartData(query));
        result.setCode(200);
        result.setMsg("查询成功");
        return result;
    }

    @PostMapping("/get/list")
    public Result<?> getBudgetPlanList(@RequestBody BudgetPlanQuery query) {
        Result<Page<BudgetPlanVO>> result = new Result<>();
        result.setData(budgetPlanService.getBudgetPlanList(query));
        result.setCode(200);
        result.setMsg("查询成功");
        return result;
    }

    @DeleteMapping("/delete/{id}")
    public Result<?> removeBudgetPlan(@PathVariable("id") Long id) {
        Result<String> result = new Result<>();
        budgetPlanService.removeBudgetPlanById(id);
        result.setCode(200);
        result.setMsg("查询成功");
        return result;
    }


    @PostMapping("/add")
    public Result<?> addBudgetPlan(@RequestBody BudgetPlanCommand command) {
        Result<String> result = new Result<>();
        budgetPlanService.addBudgetPlan(command);
        result.setCode(200);
        result.setMsg("查询成功");
        return result;
    }

    @PostMapping("/update")
    public Result<?> updateBudgetPlan(@RequestBody BudgetPlanCommand command) {
        Result<String> result = new Result<>();
        budgetPlanService.updateBudgetPlan(command);
        result.setCode(200);
        result.setMsg("查询成功");
        return result;
    }
}
