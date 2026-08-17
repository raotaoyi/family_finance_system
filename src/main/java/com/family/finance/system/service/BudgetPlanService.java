package com.family.finance.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.family.finance.system.controller.command.BudgetPlanCommand;
import com.family.finance.system.controller.query.BudgetPlanQuery;
import com.family.finance.system.controller.vo.BudgetPlanCategoryStaticVO;
import com.family.finance.system.controller.vo.BudgetPlanVO;
import com.family.finance.system.controller.vo.YearBudgetInfoVO;

import java.util.List;

public interface BudgetPlanService {

    YearBudgetInfoVO getYearBudgetInfo(BudgetPlanQuery query);

    List<BudgetPlanCategoryStaticVO> getChartData(BudgetPlanQuery query);

    Page<BudgetPlanVO> getBudgetPlanList(BudgetPlanQuery query);

    void removeBudgetPlanById(Long id);

    void addBudgetPlan(BudgetPlanCommand command);

    void updateBudgetPlan(BudgetPlanCommand command);
}
