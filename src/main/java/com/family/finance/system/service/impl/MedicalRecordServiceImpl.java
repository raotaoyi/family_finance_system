package com.family.finance.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.family.finance.system.controller.command.MedicalRecordCommand;
import com.family.finance.system.controller.query.MedicalRecordQuery;
import com.family.finance.system.controller.vo.MedicalCostStatVO;
import com.family.finance.system.controller.vo.MedicalRecordVO;
import com.family.finance.system.controller.vo.NameValueVO;
import com.family.finance.system.mapper.MedicalRecordMapper;
import com.family.finance.system.service.MedicalRecordService;
import com.family.finance.system.service.entity.MedicalRecordEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MedicalRecordServiceImpl extends ServiceImpl<MedicalRecordMapper, MedicalRecordEntity> implements MedicalRecordService {

    @Override
    public Page<MedicalRecordVO> getMedicalRecordList(MedicalRecordQuery query) {
        LambdaQueryWrapper<MedicalRecordEntity> wrapper = buildWrapper(query);
        wrapper.orderByDesc(MedicalRecordEntity::getVisitDate);

        Page<MedicalRecordEntity> page = new Page<>(query.getPageNum(), query.getPageSize());
        Page<MedicalRecordEntity> entityPage = this.baseMapper.selectPage(page, wrapper);

        List<MedicalRecordVO> voList = entityPage.getRecords().stream()
                .map(this::toVO)
                .collect(Collectors.toList());

        Page<MedicalRecordVO> result = new Page<>();
        result.setTotal(entityPage.getTotal());
        result.setCurrent(entityPage.getCurrent());
        result.setSize(entityPage.getSize());
        result.setRecords(voList);
        return result;
    }

    @Override
    public MedicalCostStatVO getCostStat(MedicalRecordQuery query) {
        List<MedicalRecordEntity> list = this.list(buildWrapper(query));

        BigDecimal totalCost = list.stream()
                .map(MedicalRecordEntity::getCost)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 科室费用分布（金额降序）
        Map<String, BigDecimal> deptMap = list.stream()
                .filter(e -> e.getCost() != null)
                .collect(Collectors.groupingBy(
                        e -> StringUtils.defaultIfBlank(e.getDepartment(), "其他"),
                        Collectors.reducing(BigDecimal.ZERO, MedicalRecordEntity::getCost, BigDecimal::add)));
        List<NameValueVO> departmentList = deptMap.entrySet().stream()
                .sorted(Map.Entry.<String, BigDecimal>comparingByValue().reversed())
                .map(e -> NameValueVO.builder().name(e.getKey()).value(e.getValue()).build())
                .collect(Collectors.toList());

        // 月度费用趋势（月份升序）
        Map<String, BigDecimal> monthMap = list.stream()
                .filter(e -> e.getCost() != null && e.getVisitDate() != null && e.getVisitDate().length() >= 7)
                .collect(Collectors.groupingBy(
                        e -> e.getVisitDate().substring(0, 7),
                        Collectors.reducing(BigDecimal.ZERO, MedicalRecordEntity::getCost, BigDecimal::add)));
        List<NameValueVO> monthTrend = monthMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> NameValueVO.builder().name(e.getKey()).value(e.getValue()).build())
                .collect(Collectors.toList());

        return MedicalCostStatVO.builder()
                .totalCost(totalCost)
                .departmentList(departmentList)
                .monthTrend(monthTrend)
                .build();
    }

    @Override
    public void addMedicalRecord(MedicalRecordCommand command) {
        MedicalRecordEntity entity = MedicalRecordEntity.builder()
                .member(command.getMember())
                .visitDate(command.getVisitDate())
                .hospital(command.getHospital())
                .department(command.getDepartment())
                .doctor(command.getDoctor())
                .diagnosis(command.getDiagnosis())
                .prescription(command.getPrescription())
                .cost(command.getCost())
                .remark(command.getRemark())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        this.save(entity);
    }

    @Override
    public void updateMedicalRecord(MedicalRecordCommand command) {
        MedicalRecordEntity entity = MedicalRecordEntity.builder()
                .id(command.getId())
                .member(command.getMember())
                .visitDate(command.getVisitDate())
                .hospital(command.getHospital())
                .department(command.getDepartment())
                .doctor(command.getDoctor())
                .diagnosis(command.getDiagnosis())
                .prescription(command.getPrescription())
                .cost(command.getCost())
                .remark(command.getRemark())
                .updateTime(LocalDateTime.now())
                .build();
        this.updateById(entity);
    }

    @Override
    public void deleteMedicalRecord(Long id) {
        this.removeById(id);
    }

    private LambdaQueryWrapper<MedicalRecordEntity> buildWrapper(MedicalRecordQuery query) {
        LambdaQueryWrapper<MedicalRecordEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(query.getMember())) {
            wrapper.eq(MedicalRecordEntity::getMember, query.getMember());
        }
        if (StringUtils.isNotBlank(query.getDepartment())) {
            wrapper.like(MedicalRecordEntity::getDepartment, query.getDepartment());
        }
        if (StringUtils.isNotBlank(query.getStartDate())) {
            wrapper.ge(MedicalRecordEntity::getVisitDate, query.getStartDate());
        }
        if (StringUtils.isNotBlank(query.getEndDate())) {
            wrapper.le(MedicalRecordEntity::getVisitDate, query.getEndDate());
        }
        return wrapper;
    }

    private MedicalRecordVO toVO(MedicalRecordEntity e) {
        return MedicalRecordVO.builder()
                .id(e.getId())
                .member(e.getMember())
                .visitDate(e.getVisitDate())
                .hospital(e.getHospital())
                .department(e.getDepartment())
                .doctor(e.getDoctor())
                .diagnosis(e.getDiagnosis())
                .prescription(e.getPrescription())
                .cost(e.getCost())
                .remark(e.getRemark())
                .build();
    }
}
