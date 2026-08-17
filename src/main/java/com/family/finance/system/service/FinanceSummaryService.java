package com.family.finance.system.service;

import com.family.finance.system.controller.vo.FinanceSummaryCategoryStaticVO;
import com.family.finance.system.controller.vo.FinanceSummaryStaticVO;

import java.util.List;

public interface FinanceSummaryService {
    FinanceSummaryStaticVO getFinanceSummary();

    List<FinanceSummaryCategoryStaticVO> getFinanceSummaryCategory(String type);
}
