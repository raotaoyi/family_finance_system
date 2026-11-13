package com.family.finance.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.family.finance.system.controller.command.HealthMetricCommand;
import com.family.finance.system.controller.query.HealthMetricQuery;
import com.family.finance.system.controller.vo.HealthMetricVO;

import java.util.List;

public interface HealthMetricService {

    Page<HealthMetricVO> getMetricList(HealthMetricQuery query);

    List<HealthMetricVO> getTrend(HealthMetricQuery query);

    void addMetric(HealthMetricCommand command);

    void updateMetric(HealthMetricCommand command);

    void deleteMetric(Long id);
}
