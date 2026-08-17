package com.family.finance.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.family.finance.system.controller.command.FinanceBillCommand;
import com.family.finance.system.controller.query.FinanceQuery;
import com.family.finance.system.controller.vo.FinanceBillSummaryStaticVO;
import com.family.finance.system.controller.vo.FinanceBillSummaryTrendVO;
import com.family.finance.system.controller.vo.FinanceBillVO;

import java.util.List;

public interface FinanceBillService {

    List<FinanceBillSummaryStaticVO> getBillSummaryStatic(FinanceQuery query);


    List<FinanceBillSummaryTrendVO> getBillSummaryMonthTrend(FinanceQuery query);

    Page<FinanceBillVO> getBillList(FinanceQuery query);


    void removeBillById(Long id);

    void batchRemoveBill(List<Long> idList);

    void addFinanceBill(FinanceBillCommand command);

    void updateFinanceBill(FinanceBillCommand command);
}
