package com.family.finance.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.family.finance.system.service.entity.FinanceSummaryEntity;
import com.family.finance.system.mapper.po.FinanceSummaryStaticPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FinanceSummaryMapper extends BaseMapper<FinanceSummaryEntity> {

    List<FinanceSummaryStaticPO> queryFinanceSummaryStaticList();

    List<FinanceSummaryStaticPO> queryFinanceSummaryCategoryStaticList(String type);
}
