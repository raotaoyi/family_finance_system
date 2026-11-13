package com.family.finance.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.family.finance.system.common.commonenum.MedicineCategoryEnum;
import com.family.finance.system.common.commonenum.MedicineStatusEnum;
import com.family.finance.system.controller.command.MedicineCommand;
import com.family.finance.system.controller.query.MedicineQuery;
import com.family.finance.system.controller.vo.MedicineStatVO;
import com.family.finance.system.controller.vo.MedicineVO;
import com.family.finance.system.controller.vo.NameValueVO;
import com.family.finance.system.mapper.MedicineMapper;
import com.family.finance.system.service.MedicineService;
import com.family.finance.system.service.entity.MedicineEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MedicineServiceImpl extends ServiceImpl<MedicineMapper, MedicineEntity> implements MedicineService {

    @Override
    public Page<MedicineVO> getMedicineList(MedicineQuery query) {
        LambdaQueryWrapper<MedicineEntity> wrapper = buildWrapper(query);
        wrapper.orderByDesc(MedicineEntity::getStartDate);

        Page<MedicineEntity> page = new Page<>(query.getPageNum(), query.getPageSize());
        Page<MedicineEntity> entityPage = this.baseMapper.selectPage(page, wrapper);

        List<MedicineVO> voList = entityPage.getRecords().stream()
                .map(this::toVO)
                .collect(Collectors.toList());

        Page<MedicineVO> result = new Page<>();
        result.setTotal(entityPage.getTotal());
        result.setCurrent(entityPage.getCurrent());
        result.setSize(entityPage.getSize());
        result.setRecords(voList);
        return result;
    }

    @Override
    public MedicineStatVO getStat(MedicineQuery query) {
        List<MedicineEntity> list = this.list(buildWrapper(query));

        Map<Integer, Long> categoryCount = list.stream()
                .collect(Collectors.groupingBy(e -> e.getCategory() == null ? 0 : e.getCategory(), Collectors.counting()));
        List<NameValueVO> categoryList = categoryCount.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> {
                    MedicineCategoryEnum cat = MedicineCategoryEnum.getByCode(e.getKey());
                    String name = cat != null ? cat.getName() : "其他";
                    return NameValueVO.builder().name(name).value(BigDecimal.valueOf(e.getValue())).build();
                })
                .collect(Collectors.toList());

        Map<Integer, Long> statusCount = list.stream()
                .collect(Collectors.groupingBy(e -> e.getStatus() == null ? 0 : e.getStatus(), Collectors.counting()));
        List<NameValueVO> statusList = statusCount.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> NameValueVO.builder()
                        .name(MedicineStatusEnum.getDescByCode(e.getKey()))
                        .value(BigDecimal.valueOf(e.getValue()))
                        .build())
                .collect(Collectors.toList());

        return MedicineStatVO.builder()
                .totalCount((long) list.size())
                .categoryList(categoryList)
                .statusList(statusList)
                .build();
    }

    @Override
    public void addMedicine(MedicineCommand command) {
        MedicineEntity entity = MedicineEntity.builder()
                .member(command.getMember())
                .name(command.getName())
                .category(command.getCategory())
                .dose(command.getDose())
                .frequency(command.getFrequency())
                .startDate(command.getStartDate())
                .endDate(command.getEndDate())
                .remaining(command.getRemaining())
                .status(command.getStatus())
                .remind(command.getRemind())
                .remark(command.getRemark())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        this.save(entity);
    }

    @Override
    public void updateMedicine(MedicineCommand command) {
        MedicineEntity entity = MedicineEntity.builder()
                .id(command.getId())
                .member(command.getMember())
                .name(command.getName())
                .category(command.getCategory())
                .dose(command.getDose())
                .frequency(command.getFrequency())
                .startDate(command.getStartDate())
                .endDate(command.getEndDate())
                .remaining(command.getRemaining())
                .status(command.getStatus())
                .remind(command.getRemind())
                .remark(command.getRemark())
                .updateTime(LocalDateTime.now())
                .build();
        this.updateById(entity);
    }

    @Override
    public void deleteMedicine(Long id) {
        this.removeById(id);
    }

    private LambdaQueryWrapper<MedicineEntity> buildWrapper(MedicineQuery query) {
        LambdaQueryWrapper<MedicineEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(query.getMember())) {
            wrapper.eq(MedicineEntity::getMember, query.getMember());
        }
        if (query.getCategory() != null) {
            wrapper.eq(MedicineEntity::getCategory, query.getCategory());
        }
        if (query.getStatus() != null) {
            wrapper.eq(MedicineEntity::getStatus, query.getStatus());
        }
        return wrapper;
    }

    private MedicineVO toVO(MedicineEntity e) {
        MedicineCategoryEnum cat = MedicineCategoryEnum.getByCode(e.getCategory());
        return MedicineVO.builder()
                .id(e.getId())
                .member(e.getMember())
                .name(e.getName())
                .categoryCode(e.getCategory())
                .category(cat != null ? cat.getName() : "")
                .dose(e.getDose())
                .frequency(e.getFrequency())
                .startDate(e.getStartDate())
                .endDate(e.getEndDate())
                .remaining(e.getRemaining())
                .statusCode(e.getStatus())
                .status(MedicineStatusEnum.getDescByCode(e.getStatus()))
                .remind(e.getRemind())
                .remark(e.getRemark())
                .build();
    }
}
