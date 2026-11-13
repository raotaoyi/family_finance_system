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

/**
 * 预算计划服务实现类。
 *
 * <p>继承 MyBatis-Plus 的 {@link ServiceImpl}，获得针对 {@link BudgetPlanEntity} 的
 * 通用 CRUD 能力（this.baseMapper），同时实现 {@link BudgetPlanService} 中定义的自定义业务方法。</p>
 *
 * <p>主要业务：</p>
 * <ul>
 *   <li>年度预算总览（总预算 / 已用 / 剩余 / 使用率）</li>
 *   <li>按分类统计预算 vs 实际消费（图表数据）</li>
 *   <li>预算计划列表分页查询</li>
 *   <li>预算计划的增删改</li>
 * </ul>
 */
@Service
@Slf4j
public class BudgetPlanServiceImpl extends ServiceImpl<BudgetPlanMapper, BudgetPlanEntity> implements BudgetPlanService {

    /**
     * 账单 Mapper，用于查询实际消费金额（跨模块依赖，避免直接使用 Service 层造成循环调用）。
     */
    @Autowired
    private FinanceBillMapper financeBillMapper;

    /**
     * 查询某一年度的预算总览信息。
     *
     * <p>计算流程：</p>
     * <ol>
     *   <li>查询该年度所有预算计划的总预算（total）。</li>
     *   <li>查询该年度所有账单的实际消费，并按分类分组（used 的实际消费）。</li>
     *   <li>将属于「预算分类」范畴内的消费累加得到已用金额（used）。</li>
     *   <li>计算剩余金额（left）和剩余占比（rate）。</li>
     * </ol>
     *
     * @param query 查询条件，包含年度（year）
     * @return 年度预算总览 VO（total / used / left / rate）
     */
    @Override
    public YearBudgetInfoVO getYearBudgetInfo(BudgetPlanQuery query) {
        // 1. 获取该年度所有预算计划的预算总和
        Double total = this.baseMapper.getAllYearPlanBudget(query.getYear());

        // 2. 获取该年度实际消费：根据年份拼接出起止日期，再按分类统计消费金额
        String beginDate = DateUtils.getYearFirstDayStr(query.getYear()); // 例如 "2026-01-01"
        String endDate = DateUtils.getYearLastDayStr(query.getYear());    // 例如 "2026-12-31"
        FinanceQuery financeQuery = FinanceQuery.builder().startMonth(beginDate).endMonth(endDate).build();
        // 查询结果按分类编码（cd）分组：Map<分类编码, 该分类的消费记录列表>
        Map<Integer, List<FinanceBillSummaryStaticPO>> staticPOListMap = financeBillMapper.queryBillSummaryStaticByCategoryList(financeQuery)
                .stream().collect(Collectors.groupingBy(FinanceBillSummaryStaticPO::getCd));

        // 3. 计算实际已使用的预算：遍历所有预算分类，累加对应分类的实际消费金额
        AtomicReference<Double> useTotal = new AtomicReference<>((double) 0);
        Arrays.stream(BudgetPlanCategoryEnum.values()).forEach(categoryEnum -> {
            if (staticPOListMap.containsKey(categoryEnum.getCategoryCd())) {
                useTotal.updateAndGet(v -> (double) (v + staticPOListMap.get(categoryEnum.getCategoryCd()).get(0).getAmount()));
            }
        });

        // 4. 组装返回结果并计算剩余金额、剩余占比
        YearBudgetInfoVO yearBudgetInfoVO = YearBudgetInfoVO.builder()
                .total(Objects.isNull(total) ? 0 : total)   // 总预算（无计划时为 0）
                .used(useTotal.get())                        // 已使用金额
                .build();
        yearBudgetInfoVO.setLeft(yearBudgetInfoVO.getTotal() - yearBudgetInfoVO.getUsed()); // 剩余 = 总预算 - 已用
        yearBudgetInfoVO.setRate(Objects.isNull(total) || total == 0 ? 0 : yearBudgetInfoVO.getLeft() / yearBudgetInfoVO.getTotal() * 100); // 剩余占比（%）
        return yearBudgetInfoVO;
    }

    /**
     * 查询预算图表数据：按预算分类对比「计划预算」与「实际消费」。
     *
     * <p>先查询各分类的计划预算，再查询各分类的实际消费，最后按分类合并成图表数据。</p>
     *
     * @param query 查询条件，包含年度（year）
     * @return 各分类的预算 vs 实际消费 VO 列表
     */
    @Override
    public List<BudgetPlanCategoryStaticVO> getChartData(BudgetPlanQuery query) {
        try {
            // 1. 获取该年度各分类的计划预算
            List<BudgetPlanStaticPO> staticPOList = this.baseMapper.queryBudgetPlanStaticListByCategory(query.getYear());

            // 2. 获取该年度各分类的实际消费
            String beginDate = DateUtils.getYearFirstDayStr(query.getYear()); // 例如 "2026-01-01"
            String endDate = DateUtils.getYearLastDayStr(query.getYear());    // 例如 "2026-12-31"
            FinanceQuery financeQuery = FinanceQuery.builder().startMonth(beginDate).endMonth(endDate).build();
            Map<Integer, List<FinanceBillSummaryStaticPO>> staticPOListMap = financeBillMapper.queryBillSummaryStaticByCategoryList(financeQuery)
                    .stream().collect(Collectors.groupingBy(FinanceBillSummaryStaticPO::getCd));

            // 3. 遍历各分类预算，组装「计划预算 + 实际消费」的图表数据
            List<BudgetPlanCategoryStaticVO> staticVOList = Lists.newArrayList();
            staticPOList.forEach(po -> {
                // 只处理有预算金额（且不为 0）的分类
                if (!Objects.isNull(po.getAmount()) && po.getAmount() != 0) {
                    staticVOList.add(BudgetPlanCategoryStaticVO.builder()
                            .name(Objects.requireNonNull(BudgetPlanCategoryEnum.getBudgetPlanCategoryEnum(po.getCd())).getCategoryName())
                            .plan(po.getAmount())   // 计划预算
                            .real(CollectionUtils.isEmpty(staticPOListMap.get(po.getCd())) ? 0.0 : staticPOListMap.get(po.getCd()).stream()
                                    .map(FinanceBillSummaryStaticPO::getAmount)
                                    .filter(Objects::nonNull)
                                    .reduce(0.0, Double::sum))  // 实际消费（无记录时为 0）
                            .build());
                }
            });
            return staticVOList;
        } catch (Exception e) {
            log.error("查询失败", e);
        }
        return Lists.newArrayList();
    }

    /**
     * 分页查询某一年度的预算计划列表。
     *
     * <p>按年度过滤，并按创建时间倒序排列。</p>
     *
     * @param query 查询条件，包含年度（year）及分页参数（pageNum / pageSize）
     * @return 分页的预算计划 VO 列表
     */
    @Override
    public Page<BudgetPlanVO> getBudgetPlanList(BudgetPlanQuery query) {
        try {
            // 1. 构建分页对象并查询该年度的预算计划列表
            Page<BudgetPlanEntity> page = new Page<>(query.getPageNum(), query.getPageSize());
            LambdaQueryWrapper<BudgetPlanEntity> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(BudgetPlanEntity::getYear, query.getYear());   // 按年度过滤
            wrapper.orderByDesc(BudgetPlanEntity::getCreateTime);      // 按创建时间倒序
            Page<BudgetPlanEntity> budgetPlanEntityPage = this.baseMapper.selectPage(page, wrapper);

            // 2. 将实体列表转换为 VO 列表（当前 realPay / diff / status 尚未实现，先置空）
            List<BudgetPlanVO> budgetPlanVOList = Lists.newArrayList();
            budgetPlanEntityPage.getRecords().forEach(entity -> {
                budgetPlanVOList.add(BudgetPlanVO.builder()
                        .id(entity.getId())
                        .title(entity.getTitle())
                        .category(Objects.requireNonNull(BudgetPlanCategoryEnum.getBudgetPlanCategoryEnum(entity.getCategory())).getCategoryName())
                        .planBudget(String.valueOf(entity.getPlanBudget()))
                        .realPay("")   // TODO: 实际支付金额待补充
                        .diff("")      // TODO: 差额待补充
                        .status("")    // TODO: 状态待补充
                        .expectFinishTime(entity.getExpectFinishTime())
                        .finishStatus(BudgetPlanFinishStatusEnum.getDescByCode(entity.getFinishStatus()))
                        .executor(entity.getExecutor())
                        .remark(entity.getRemark())
                        .build());
            });

            // 3. 组装分页返回结果
            Page<BudgetPlanVO> result = new Page<>();
            result.setTotal(page.getTotal());         // 总记录数
            result.setRecords(budgetPlanVOList);      // 当前页数据
            return result;
        } catch (Exception e) {
            log.error("查询失败", e);
        }
        return new Page<>();
    }

    /**
     * 根据主键删除预算计划。
     *
     * @param id 预算计划主键 ID
     */
    @Override
    public void removeBudgetPlanById(Long id) {
        this.baseMapper.deleteById(id);
    }

    /**
     * 新增预算计划。
     *
     * <p>年度（year）取当前系统年份，而非前端传入。</p>
     *
     * @param command 新增预算计划命令对象
     */
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
                .year(String.valueOf(LocalDate.now().getYear()))   // 年度取当前年份
                .build());
    }

    /**
     * 更新预算计划。
     *
     * @param command 更新预算计划命令对象（包含主键 ID）
     */
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
                .year(String.valueOf(LocalDate.now().getYear()))   // 更新时同样将年度置为当前年份
                .build());
    }
}
