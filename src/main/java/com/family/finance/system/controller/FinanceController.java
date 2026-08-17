package com.family.finance.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.family.finance.system.common.result.Result;
import com.family.finance.system.controller.command.FinanceBillCommand;
import com.family.finance.system.controller.query.FinanceQuery;
import com.family.finance.system.controller.vo.*;
import com.family.finance.system.service.impl.FinanceBillServiceImpl;
import com.family.finance.system.service.impl.FinanceSummaryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finance")
public class FinanceController {
    @Autowired
    private FinanceSummaryServiceImpl financeSummaryService;

    @Autowired
    private FinanceBillServiceImpl financeBillService;

    @GetMapping("/get/summary/static")
    public Result<?> getSummary() {
        Result<FinanceSummaryStaticVO> result = new Result<>();
        result.setData(financeSummaryService.getFinanceSummary());
        result.setCode(200);
        result.setMsg("查询成功");
        return result;
    }

    @GetMapping("/get/summary/category")
    public Result<?> getSummaryCategory(@RequestParam("type") String type) {
        Result<List<FinanceSummaryCategoryStaticVO>> result = new Result<>();
        result.setData(financeSummaryService.getFinanceSummaryCategory(type));
        result.setCode(200);
        result.setMsg("查询成功");
        return result;
    }


    @PostMapping("/get/bill/summary/static")
    public Result<?> getBillSummaryStatic(@RequestBody FinanceQuery query) {
        Result<List<FinanceBillSummaryStaticVO>> result = new Result<>();
        result.setData(financeBillService.getBillSummaryStatic(query));
        result.setCode(200);
        result.setMsg("查询成功");
        return result;
    }

    @PostMapping("/get/bill/summary/month/trend")
    public Result<?> getBillSummaryMonthTrend(@RequestBody FinanceQuery query) {
        Result<List<FinanceBillSummaryTrendVO>> result = new Result<>();
        result.setData(financeBillService.getBillSummaryMonthTrend(query));
        result.setCode(200);
        result.setMsg("查询成功");
        return result;
    }

    @PostMapping("/get/bill/list")
    public Result<?> getBillList(@RequestBody FinanceQuery query) {
        Result<Page<FinanceBillVO>> result = new Result<>();
        result.setData(financeBillService.getBillList(query));
        result.setCode(200);
        result.setMsg("查询成功");
        return result;
    }

    @DeleteMapping("/delete/bill/{id}")
    public Result<?> getBillList(@PathVariable("id") Long id) {
        Result<String> result = new Result<>();
        financeBillService.removeBillById(id);
        result.setCode(200);
        result.setMsg("查询成功");
        return result;
    }

    @PostMapping("/batch/delete/bill")
    public Result<?> getBillList(@RequestBody List<Long> ids) {
        Result<String> result = new Result<>();
        financeBillService.batchRemoveBill(ids);
        result.setCode(200);
        result.setMsg("查询成功");
        return result;
    }

    @PostMapping("/add/bill")
    public Result<?> addBillList(@RequestBody FinanceBillCommand command) {
        Result<String> result = new Result<>();
        financeBillService.addFinanceBill(command);
        result.setCode(200);
        result.setMsg("查询成功");
        return result;
    }

    @PostMapping("/update/bill")
    public Result<?> updateBill(@RequestBody FinanceBillCommand command) {
        Result<String> result = new Result<>();
        financeBillService.updateFinanceBill(command);
        result.setCode(200);
        result.setMsg("查询成功");
        return result;
    }
}
