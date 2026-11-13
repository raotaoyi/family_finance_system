package com.family.finance.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.family.finance.system.controller.command.HealthMetricCommand;
import com.family.finance.system.controller.query.HealthMetricQuery;
import com.family.finance.system.controller.vo.HealthMetricVO;
import com.family.finance.system.mapper.HealthMetricMapper;
import com.family.finance.system.service.HealthMetricService;
import com.family.finance.system.service.entity.HealthMetricEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HealthMetricServiceImpl extends ServiceImpl<HealthMetricMapper, HealthMetricEntity> implements HealthMetricService {

    @Override
    public Page<HealthMetricVO> getMetricList(HealthMetricQuery query) {
        LambdaQueryWrapper<HealthMetricEntity> wrapper = buildWrapper(query);
        wrapper.orderByDesc(HealthMetricEntity::getRecordDate);

        Page<HealthMetricEntity> page = new Page<>(query.getPageNum(), query.getPageSize());
        Page<HealthMetricEntity> entityPage = this.baseMapper.selectPage(page, wrapper);

        List<HealthMetricVO> voList = entityPage.getRecords().stream()
                .map(this::toVO)
                .collect(Collectors.toList());

        Page<HealthMetricVO> result = new Page<>();
        result.setTotal(entityPage.getTotal());
        result.setCurrent(entityPage.getCurrent());
        result.setSize(entityPage.getSize());
        result.setRecords(voList);
        return result;
    }

    @Override
    public List<HealthMetricVO> getTrend(HealthMetricQuery query) {
        LambdaQueryWrapper<HealthMetricEntity> wrapper = buildWrapper(query);
        wrapper.orderByAsc(HealthMetricEntity::getRecordDate);
        return this.list(wrapper).stream().map(this::toVO).collect(Collectors.toList());
    }

    @Override
    public void addMetric(HealthMetricCommand command) {
        HealthMetricEntity entity = HealthMetricEntity.builder()
                .member(command.getMember())
                .recordDate(command.getRecordDate())
                .weight(command.getWeight())
                .systolic(command.getSystolic())
                .diastolic(command.getDiastolic())
                .heartRate(command.getHeartRate())
                .remark(command.getRemark())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        this.save(entity);
    }

    @Override
    public void updateMetric(HealthMetricCommand command) {
        HealthMetricEntity entity = HealthMetricEntity.builder()
                .id(command.getId())
                .member(command.getMember())
                .recordDate(command.getRecordDate())
                .weight(command.getWeight())
                .systolic(command.getSystolic())
                .diastolic(command.getDiastolic())
                .heartRate(command.getHeartRate())
                .remark(command.getRemark())
                .updateTime(LocalDateTime.now())
                .build();
        this.updateById(entity);
    }

    @Override
    public void deleteMetric(Long id) {
        this.removeById(id);
    }

    private LambdaQueryWrapper<HealthMetricEntity> buildWrapper(HealthMetricQuery query) {
        LambdaQueryWrapper<HealthMetricEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(query.getMember())) {
            wrapper.eq(HealthMetricEntity::getMember, query.getMember());
        }
        if (StringUtils.isNotBlank(query.getStartDate())) {
            wrapper.ge(HealthMetricEntity::getRecordDate, query.getStartDate());
        }
        if (StringUtils.isNotBlank(query.getEndDate())) {
            wrapper.le(HealthMetricEntity::getRecordDate, query.getEndDate());
        }
        return wrapper;
    }

    private HealthMetricVO toVO(HealthMetricEntity e) {
        return HealthMetricVO.builder()
                .id(e.getId())
                .member(e.getMember())
                .recordDate(e.getRecordDate())
                .weight(e.getWeight())
                .systolic(e.getSystolic())
                .diastolic(e.getDiastolic())
                .heartRate(e.getHeartRate())
                .remark(e.getRemark())
                .build();
    }
}
