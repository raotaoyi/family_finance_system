package com.family.finance.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.family.finance.system.controller.query.FinanceQuery;
import com.family.finance.system.mapper.po.FinanceBillSummaryStaticPO;
import com.family.finance.system.mapper.po.FinanceBillSummaryTrendPO;
import com.family.finance.system.service.entity.FinanceBillEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FinanceBillMapper extends BaseMapper<FinanceBillEntity> {

    List<FinanceBillSummaryStaticPO> queryBillSummaryStaticList(@Param("query") FinanceQuery query);


    List<FinanceBillSummaryTrendPO> queryBillSummaryMonthTrendList(@Param("query") FinanceQuery query);


    List<FinanceBillSummaryStaticPO> queryBillSummaryStaticByCategoryList(@Param("query") FinanceQuery query);
}
