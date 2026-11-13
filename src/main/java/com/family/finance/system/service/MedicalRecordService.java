package com.family.finance.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.family.finance.system.controller.command.MedicalRecordCommand;
import com.family.finance.system.controller.query.MedicalRecordQuery;
import com.family.finance.system.controller.vo.MedicalCostStatVO;
import com.family.finance.system.controller.vo.MedicalRecordVO;

public interface MedicalRecordService {

    Page<MedicalRecordVO> getMedicalRecordList(MedicalRecordQuery query);

    MedicalCostStatVO getCostStat(MedicalRecordQuery query);

    void addMedicalRecord(MedicalRecordCommand command);

    void updateMedicalRecord(MedicalRecordCommand command);

    void deleteMedicalRecord(Long id);
}
