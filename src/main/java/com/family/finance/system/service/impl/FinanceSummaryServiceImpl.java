package com.family.finance.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.family.finance.system.common.commonenum.FinanceCategoryEnum;
import com.family.finance.system.common.commonenum.FinanceTypeEnum;
import com.family.finance.system.service.entity.FinanceSummaryEntity;
import com.family.finance.system.mapper.FinanceSummaryMapper;
import com.family.finance.system.mapper.po.FinanceSummaryStaticPO;
import com.family.finance.system.service.FinanceSummaryService;
import com.family.finance.system.controller.vo.FinanceSummaryCategoryStaticVO;
import com.family.finance.system.controller.vo.FinanceSummaryStaticVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class FinanceSummaryServiceImpl extends ServiceImpl<FinanceSummaryMapper, FinanceSummaryEntity> implements FinanceSummaryService {

    @Autowired
    private FinanceSummaryMapper financeSummaryMapper;


    @Override
    public FinanceSummaryStaticVO getFinanceSummary() {
        try {
            List<FinanceSummaryStaticPO> summaryStaticPOList =
                    financeSummaryMapper.queryFinanceSummaryStaticList();
            FinanceSummaryStaticVO summaryStaticVO = new FinanceSummaryStaticVO();
            summaryStaticPOList.forEach(summaryStaticPO -> {
                        switch (Objects.requireNonNull(FinanceTypeEnum.getFinanceTypeEnum(summaryStaticPO.getCd()))) {
                            case FINANCE_TYPE_DEPOSIT:
                                summaryStaticVO.setDeposit(summaryStaticPO.getAmount());
                                break;
                            case FINANCE_TYPE_CASH_FLOW:
                                summaryStaticVO.setCashFlow(summaryStaticPO.getAmount());
                                break;
                            case FINANCE_TYPE_DEBT:
                                summaryStaticVO.setDebt(summaryStaticPO.getAmount());
                                break;
                            case FINANCE_TYPE_INVEST:
                                summaryStaticVO.setInvest(summaryStaticPO.getAmount());
                                break;
                            default:
                                log.info("不存在");
                        }
                    }
            );
            summaryStaticVO.setAsset(summaryStaticVO.getDeposit() + summaryStaticVO.getInvest());
            summaryStaticVO.setBalance(summaryStaticVO.getDeposit() - summaryStaticVO.getDebt());
            return summaryStaticVO;
        } catch (Exception e) {
            log.error("查询失败", e);
        }
        return FinanceSummaryStaticVO.builder().build();
    }

    @Override
    public List<FinanceSummaryCategoryStaticVO> getFinanceSummaryCategory(String type) {
        try {
            List<FinanceSummaryStaticPO> summaryCategoryPOList =
                    financeSummaryMapper.queryFinanceSummaryCategoryStaticList(type);
            List<FinanceSummaryCategoryStaticVO> categoryVOList = Lists.newArrayList();
            summaryCategoryPOList.forEach(financeSummaryCategoryPO -> {
                categoryVOList.add(FinanceSummaryCategoryStaticVO.builder()
                        .name(Objects.requireNonNull(FinanceCategoryEnum.getFinanceCategoryEnum(financeSummaryCategoryPO.getCd())).getCategoryName())
                        .value(financeSummaryCategoryPO.getAmount())
                        .build());
            });
            return categoryVOList;
        } catch (Exception e) {
            log.error("查询失败", e);
        }
        return Collections.emptyList();
    }
}
