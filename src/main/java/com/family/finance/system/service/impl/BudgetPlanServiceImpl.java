package com.family.finance.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.family.finance.system.common.commonenum.BudgetPlanCategoryEnum;
import com.family.finance.system.common.commonenum.BudgetPlanFinishStatusEnum;
import com.family.finance.system.common.util.DateUtils;
import com.family.finance.system.controller.command.BudgetPlanCommand;
import com.family.finance.system.controller.query.BudgetPlanQuery;
import com.family.finance.system.controller.query.FinanceQuery;
import com.family.finance.system.controller.vo.BudgetPlanCategoryStaticVO;
import com.family.finance.system.controller.vo.BudgetPlanVO;
import com.family.finance.system.controller.vo.YearBudgetInfoVO;
import com.family.finance.system.mapper.BudgetPlanMapper;
import com.family.finance.system.mapper.FinanceBillMapper;
import com.family.finance.system.mapper.po.BudgetPlanStaticPO;
import com.family.finance.system.mapper.po.FinanceBillSummaryStaticPO;
import com.family.finance.system.service.BudgetPlanService;
import com.family.finance.system.service.entity.BudgetPlanEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BudgetPlanServiceImpl extends ServiceImpl<BudgetPlanMapper, BudgetPlanEntity> implements BudgetPlanService {
    @Autowired
    private FinanceBillMapper financeBillMapper;

    @Override
    public YearBudgetInfoVO getYearBudgetInfo(BudgetPlanQuery query) {
        //1，获取预算
        Double total = this.baseMapper.getAllYearPlanBudget(query.getYear());
        //2，获取实际消费
        String beginDate = DateUtils.getYearFirstDayStr(query.getYear()); // "2026-01-01"
        String endDate = DateUtils.getYearLastDayStr(query.getYear());
        FinanceQuery financeQuery = FinanceQuery.builder().startMonth(beginDate).endMonth(endDate).build();
        Map<Integer, List<FinanceBillSummaryStaticPO>> staticPOListMap = financeBillMapper.queryBillSummaryStaticByCategoryList(financeQuery)
                .stream().collect(Collectors.groupingBy(FinanceBillSummaryStaticPO::getCd));
        //3,计算实际预算使用
        AtomicReference<Double> useTotal = new AtomicReference<>((double) 0);
        Arrays.stream(BudgetPlanCategoryEnum.values()).forEach(categoryEnum -> {
            if (staticPOListMap.containsKey(categoryEnum.getCategoryCd())) {
                useTotal.updateAndGet(v -> (double) (v + staticPOListMap.get(categoryEnum.getCategoryCd()).get(0).getAmount()));
            }
        });

        YearBudgetInfoVO yearBudgetInfoVO = YearBudgetInfoVO.builder()
                .total(Objects.isNull(total) ? 0 : total)
                .used(useTotal.get())
                .build();
        yearBudgetInfoVO.setLeft(yearBudgetInfoVO.getTotal() - yearBudgetInfoVO.getUsed());
        yearBudgetInfoVO.setRate(Objects.isNull(total) || total == 0 ? 0 : yearBudgetInfoVO.getLeft() / yearBudgetInfoVO.getTotal() * 100);
        return yearBudgetInfoVO;
    }

    @Override
    public List<BudgetPlanCategoryStaticVO> getChartData(BudgetPlanQuery query) {
        try {
            //1，获取类别预算
            List<BudgetPlanStaticPO> staticPOList = this.baseMapper.queryBudgetPlanStaticListByCategory(query.getYear());
            //2，获取实际消费
            String beginDate = DateUtils.getYearFirstDayStr(query.getYear()); // "2026-01-01"
            String endDate = DateUtils.getYearLastDayStr(query.getYear());
            FinanceQuery financeQuery = FinanceQuery.builder().startMonth(beginDate).endMonth(endDate).build();
            Map<Integer, List<FinanceBillSummaryStaticPO>> staticPOListMap = financeBillMapper.queryBillSummaryStaticByCategoryList(financeQuery)
                    .stream().collect(Collectors.groupingBy(FinanceBillSummaryStaticPO::getCd));

            List<BudgetPlanCategoryStaticVO> staticVOList = Lists.newArrayList();
            staticPOList.forEach(po -> {
                if (!Objects.isNull(po.getAmount()) && po.getAmount() != 0) {
                    staticVOList.add(BudgetPlanCategoryStaticVO.builder()
                            .name(Objects.requireNonNull(BudgetPlanCategoryEnum.getBudgetPlanCategoryEnum(po.getCd())).getCategoryName())
                            .plan(po.getAmount())
                            .real(CollectionUtils.isEmpty(staticPOListMap.get(po.getCd())) ? 0.0 : staticPOListMap.get(po.getCd()).stream()
                                    .map(FinanceBillSummaryStaticPO::getAmount)
                                    .filter(Objects::nonNull)
                                    .reduce(0.0, Double::sum))
                            .build());
                }
            });
            return staticVOList;
        } catch (Exception e) {
            log.error("查询失败", e);
        }
        return Lists.newArrayList();
    }

    @Override
    public Page<BudgetPlanVO> getBudgetPlanList(BudgetPlanQuery query) {
        try {
            Page<BudgetPlanEntity> page = new Page<>(query.getPageNum(), query.getPageSize());
            //1,获取预算列表
            LambdaQueryWrapper<BudgetPlanEntity> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(BudgetPlanEntity::getYear, query.getYear());
            wrapper.orderByDesc(BudgetPlanEntity::getCreateTime);
            Page<BudgetPlanEntity> budgetPlanEntityPage = this.baseMapper.selectPage(page, wrapper);
            //2，获取分类实际消费的钱

            List<BudgetPlanVO> budgetPlanVOList = Lists.newArrayList();
            budgetPlanEntityPage.getRecords().forEach(entity -> {
                budgetPlanVOList.add(BudgetPlanVO.builder()
                        .id(entity.getId())
                        .title(entity.getTitle())
                        .category(Objects.requireNonNull(BudgetPlanCategoryEnum.getBudgetPlanCategoryEnum(entity.getCategory())).getCategoryName())
                        .planBudget(String.valueOf(entity.getPlanBudget()))
                        .realPay("")
                        .diff("")
                        .status("")
                        .expectFinishTime(entity.getExpectFinishTime())
                        .finishStatus(BudgetPlanFinishStatusEnum.getDescByCode(entity.getFinishStatus()))
                        .executor(entity.getExecutor())
                        .remark(entity.getRemark())
                        .build());
            });
            Page<BudgetPlanVO> result = new Page<>();
            result.setTotal(page.getTotal());
            result.setRecords(budgetPlanVOList);
            return result;
        } catch (Exception e) {
            log.error("查询失败", e);
        }
        return new Page<>();
    }

    @Override
    public void removeBudgetPlanById(Long id) {
        this.baseMapper.deleteById(id);

    }

    @Override
    public void addBudgetPlan(BudgetPlanCommand command) {
        this.baseMapper.insert(BudgetPlanEntity.builder()
                .title(command.getTitle())
                .category(Objects.requireNonNull(BudgetPlanCategoryEnum.getBudgetPlanCategoryEnum(command.getCategory())).getCategoryCd())
                .planBudget(command.getPlanBudget())
                .expectFinishTime(command.getExpectFinishTime())
                .finishStatus(BudgetPlanFinishStatusEnum.getEnumByDesc(command.getFinishStatus()).getCode())
                .executor(command.getExecutor())
                .remark(command.getRemark())
                .year(String.valueOf(LocalDate.now().getYear()))
                .build());
    }

    @Override
    public void updateBudgetPlan(BudgetPlanCommand command) {
        this.baseMapper.updateById(BudgetPlanEntity.builder()
                .id(command.getId())
                .title(command.getTitle())
                .category(Objects.requireNonNull(BudgetPlanCategoryEnum.getBudgetPlanCategoryEnum(command.getCategory())).getCategoryCd())
                .planBudget(command.getPlanBudget())
                .expectFinishTime(command.getExpectFinishTime())
                .finishStatus(BudgetPlanFinishStatusEnum.getEnumByDesc(command.getFinishStatus()).getCode())
                .executor(command.getExecutor())
                .remark(command.getRemark())
                .year(String.valueOf(LocalDate.now().getYear()))
                .build());
    }
}
