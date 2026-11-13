package com.family.finance.system.controller;

import com.family.finance.system.common.result.Result;
import com.family.finance.system.controller.command.MedicineCommand;
import com.family.finance.system.controller.query.MedicineQuery;
import com.family.finance.system.service.impl.MedicineServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 药品/疫苗管理
 */
@RestController
@RequestMapping("/health/medicine")
public class MedicineController {

    @Autowired
    private MedicineServiceImpl medicineService;

    @PostMapping("/get/list")
    public Result<?> getMedicineList(@RequestBody MedicineQuery query) {
        return Result.success(medicineService.getMedicineList(query));
    }

    @PostMapping("/get/stat")
    public Result<?> getStat(@RequestBody MedicineQuery query) {
        return Result.success(medicineService.getStat(query));
    }

    @PostMapping("/add")
    public Result<?> addMedicine(@RequestBody MedicineCommand command) {
        medicineService.addMedicine(command);
        return Result.success();
    }

    @PostMapping("/update")
    public Result<?> updateMedicine(@RequestBody MedicineCommand command) {
        medicineService.updateMedicine(command);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<?> deleteMedicine(@PathVariable("id") Long id) {
        medicineService.deleteMedicine(id);
        return Result.success();
    }
}
