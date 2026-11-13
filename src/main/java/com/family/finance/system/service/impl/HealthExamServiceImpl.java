package com.family.finance.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.family.finance.system.controller.command.HealthExamCommand;
import com.family.finance.system.controller.query.HealthExamQuery;
import com.family.finance.system.controller.vo.HealthExamVO;
import com.family.finance.system.mapper.HealthExamMapper;
import com.family.finance.system.service.HealthExamService;
import com.family.finance.system.service.entity.HealthExamEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HealthExamServiceImpl extends ServiceImpl<HealthExamMapper, HealthExamEntity> implements HealthExamService {

    @Override
    public Page<HealthExamVO> getExamList(HealthExamQuery query) {
        LambdaQueryWrapper<HealthExamEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(query.getMember())) {
            wrapper.eq(HealthExamEntity::getMember, query.getMember());
        }
        if (StringUtils.isNotBlank(query.getHospital())) {
            wrapper.like(HealthExamEntity::getHospital, query.getHospital());
        }
        if (StringUtils.isNotBlank(query.getStartDate())) {
            wrapper.ge(HealthExamEntity::getExamDate, query.getStartDate());
        }
        if (StringUtils.isNotBlank(query.getEndDate())) {
            wrapper.le(HealthExamEntity::getExamDate, query.getEndDate());
        }
        wrapper.orderByDesc(HealthExamEntity::getExamDate);

        Page<HealthExamEntity> page = new Page<>(query.getPageNum(), query.getPageSize());
        Page<HealthExamEntity> entityPage = this.baseMapper.selectPage(page, wrapper);

        List<HealthExamVO> voList = entityPage.getRecords().stream()
                .map(this::toVO)
                .collect(Collectors.toList());

        Page<HealthExamVO> result = new Page<>();
        result.setTotal(entityPage.getTotal());
        result.setCurrent(entityPage.getCurrent());
        result.setSize(entityPage.getSize());
        result.setRecords(voList);
        return result;
    }

    @Override
    public void addExam(HealthExamCommand command) {
        HealthExamEntity entity = HealthExamEntity.builder()
                .member(command.getMember())
                .examDate(command.getExamDate())
                .hospital(command.getHospital())
                .height(command.getHeight())
                .weight(command.getWeight())
                .vision(command.getVision())
                .result(command.getResult())
                .abnormal(command.getAbnormal())
                .remark(command.getRemark())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        this.save(entity);
    }

    @Override
    public void updateExam(HealthExamCommand command) {
        HealthExamEntity entity = HealthExamEntity.builder()
                .id(command.getId())
                .member(command.getMember())
                .examDate(command.getExamDate())
                .hospital(command.getHospital())
                .height(command.getHeight())
                .weight(command.getWeight())
                .vision(command.getVision())
                .result(command.getResult())
                .abnormal(command.getAbnormal())
                .remark(command.getRemark())
                .updateTime(LocalDateTime.now())
                .build();
        this.updateById(entity);
    }

    @Override
    public void deleteExam(Long id) {
        this.removeById(id);
    }

    private HealthExamVO toVO(HealthExamEntity e) {
        return HealthExamVO.builder()
                .id(e.getId())
                .member(e.getMember())
                .examDate(e.getExamDate())
                .hospital(e.getHospital())
                .height(e.getHeight())
                .weight(e.getWeight())
                .vision(e.getVision())
                .result(e.getResult())
                .abnormal(e.getAbnormal())
                .remark(e.getRemark())
                .build();
    }
}
