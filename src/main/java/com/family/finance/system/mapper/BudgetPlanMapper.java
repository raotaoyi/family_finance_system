package com.family.finance.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.family.finance.system.mapper.po.BudgetPlanStaticPO;
import com.family.finance.system.service.entity.BudgetPlanEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BudgetPlanMapper extends BaseMapper<BudgetPlanEntity> {

    Double getAllYearPlanBudget(String year);

    List<BudgetPlanStaticPO> queryBudgetPlanStaticListByCategory(String year);
}