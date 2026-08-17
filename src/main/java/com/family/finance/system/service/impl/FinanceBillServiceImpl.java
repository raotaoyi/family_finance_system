package com.family.finance.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.family.finance.system.common.commonenum.FinanceBillCategoryEnum;
import com.family.finance.system.common.commonenum.FinanceBillTypeEnum;
import com.family.finance.system.common.commonenum.SystemUserEnum;
import com.family.finance.system.controller.command.FinanceBillCommand;
import com.family.finance.system.controller.query.FinanceQuery;
import com.family.finance.system.controller.vo.FinanceBillSummaryStaticVO;
import com.family.finance.system.controller.vo.FinanceBillSummaryTrendVO;
import com.family.finance.system.controller.vo.FinanceBillVO;
import com.family.finance.system.mapper.FinanceBillMapper;
import com.family.finance.system.mapper.po.FinanceBillSummaryStaticPO;
import com.family.finance.system.mapper.po.FinanceBillSummaryTrendPO;
import com.family.finance.system.service.FinanceBillService;
import com.family.finance.system.service.entity.FinanceBillEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FinanceBillServiceImpl extends ServiceImpl<FinanceBillMapper, FinanceBillEntity> implements FinanceBillService {

    @Autowired
    private FinanceBillMapper financeBillMapper;

    @Override
    public List<FinanceBillSummaryStaticVO> getBillSummaryStatic(FinanceQuery query) {
        try {
            List<FinanceBillSummaryStaticPO> staticPOList = financeBillMapper.queryBillSummaryStaticList(query);
            List<FinanceBillSummaryStaticVO> summaryStaticVOList = Lists.newArrayList();
            staticPOList.forEach(staticPO -> {
                summaryStaticVOList.add(FinanceBillSummaryStaticVO.builder()
                        .name(Objects.requireNonNull(SystemUserEnum.getSystemUserEnum(staticPO.getCd())).getUserName())
                        .value(staticPO.getAmount())
                        .build());
            });
            return summaryStaticVOList;
        } catch (Exception e) {
            log.error("查询失败", e);
        }
        return Collections.emptyList();
    }

    @Override
    public List<FinanceBillSummaryTrendVO> getBillSummaryMonthTrend(FinanceQuery query) {
        try {
            List<FinanceBillSummaryTrendPO> trendPOList = financeBillMapper.queryBillSummaryMonthTrendList(query);
            Map<String, List<FinanceBillSummaryTrendPO>> listMap = trendPOList.stream().collect(Collectors.groupingBy(FinanceBillSummaryTrendPO::getDate));
            List<FinanceBillSummaryTrendVO> trendVOList = Lists.newArrayList();
            listMap.forEach((dateKey, poList) -> {
                // v(poList)转stream，再toMap
                Map<String, Long> userAmountMap = poList.stream()
                        .collect(Collectors.toMap(
                                FinanceBillSummaryTrendPO::getName,
                                FinanceBillSummaryTrendPO::getAmount,
                                (oldVal, newVal) -> newVal
                        ));
                FinanceBillSummaryTrendVO vo = FinanceBillSummaryTrendVO.builder()
                        .date(dateKey)
                        .data(userAmountMap)
                        .build();
                trendVOList.add(vo);
            });
            trendVOList.sort(Comparator.comparing(FinanceBillSummaryTrendVO::getDate));
            return trendVOList;
        } catch (Exception e) {
            log.error("查询失败", e);
        }
        return Lists.newArrayList();
    }

    @Override
    public Page<FinanceBillVO> getBillList(FinanceQuery query) {
        try {
            Page<FinanceBillEntity> page = new Page<>(query.getPageNum(), query.getPageSize());
            LambdaQueryWrapper<FinanceBillEntity> wrapper = new LambdaQueryWrapper<>();
            if (StringUtils.isNoneEmpty(query.getStartMonth())) {
                YearMonth startYm = YearMonth.parse(query.getStartMonth());
                YearMonth endYm = YearMonth.parse(query.getEndMonth());
                LocalDate firstDay = startYm.atDay(1);
                LocalDate lastDay = endYm.atEndOfMonth();
                wrapper.ge(FinanceBillEntity::getBillDate, firstDay)
                        .le(FinanceBillEntity::getBillDate, lastDay);
            }
            wrapper.orderByDesc(FinanceBillEntity::getBillDate);
            Page<FinanceBillEntity> billPOPage = this.baseMapper.selectPage(page, wrapper);
            List<FinanceBillVO> financeBillVOList = Lists.newArrayList();
            billPOPage.getRecords().forEach(financeBillEntity -> {
                financeBillVOList.add(FinanceBillVO.builder()
                        .id(financeBillEntity.getId())
                        .billDate(financeBillEntity.getBillDate())
                        .amount(financeBillEntity.getMoney())
                        .category(Objects.requireNonNull(FinanceBillCategoryEnum.getFinanceBillCategoryEnum(financeBillEntity.getCategoryId())).getCategoryName())
                        .type(Objects.requireNonNull(FinanceBillTypeEnum.getFinanceTypeEnum(financeBillEntity.getType())).getTypeName())
                        .remark(financeBillEntity.getRemark())
                        .userName(Objects.requireNonNull(SystemUserEnum.getSystemUserEnum(financeBillEntity.getUserId())).getUserName())
                        .build());
            });
            Page<FinanceBillVO> result = new Page<>();
            result.setTotal(page.getTotal());
            result.setRecords(financeBillVOList);
            return result;
        } catch (Exception e) {
            log.error("查询失败", e);
        }
        return new Page<>();
    }

    @Override
    public void removeBillById(Long id) {
        this.baseMapper.deleteById(id);
    }

    @Override
    public void batchRemoveBill(List<Long> idList) {
        this.baseMapper.deleteBatchIds(idList);
    }

    @Override
    public void addFinanceBill(FinanceBillCommand command) {
        try {
            this.baseMapper.insert(FinanceBillEntity.builder()
                    .billDate(command.getBillDate())
                    .money(command.getAmount())
                    .remark(command.getRemark())
                    .categoryId(Objects.requireNonNull(FinanceBillCategoryEnum.getFinanceBillCategoryEnum(command.getCategory())).getCategoryCd())
                    .type(Objects.requireNonNull(FinanceBillTypeEnum.getFinanceTypeEnum(command.getType())).getTypeCd())
                    .userId(Objects.requireNonNull(SystemUserEnum.getSystemUserEnum(command.getUserName())).getUserCd())
                    .build());
        } catch (Exception e) {
            log.error("插入失败", e);
        }
    }

    @Override
    public void updateFinanceBill(FinanceBillCommand command) {
        this.baseMapper.updateById(FinanceBillEntity.builder()
                .id(command.getId())
                .billDate(command.getBillDate())
                .money(command.getAmount())
                .remark(command.getRemark())
                .categoryId(Objects.requireNonNull(FinanceBillCategoryEnum.getFinanceBillCategoryEnum(command.getCategory())).getCategoryCd())
                .type(Objects.requireNonNull(FinanceBillTypeEnum.getFinanceTypeEnum(command.getType())).getTypeCd())
                .userId(Objects.requireNonNull(SystemUserEnum.getSystemUserEnum(command.getUserName())).getUserCd())
                .build());
    }
}
