package com.family.finance.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.family.finance.system.controller.command.MedicineCommand;
import com.family.finance.system.controller.query.MedicineQuery;
import com.family.finance.system.controller.vo.MedicineStatVO;
import com.family.finance.system.controller.vo.MedicineVO;

public interface MedicineService {

    Page<MedicineVO> getMedicineList(MedicineQuery query);

    MedicineStatVO getStat(MedicineQuery query);

    void addMedicine(MedicineCommand command);

    void updateMedicine(MedicineCommand command);

    void deleteMedicine(Long id);
}
