package com.family.finance.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.family.finance.system.controller.command.HealthExamCommand;
import com.family.finance.system.controller.query.HealthExamQuery;
import com.family.finance.system.controller.vo.HealthExamVO;

public interface HealthExamService {

    Page<HealthExamVO> getExamList(HealthExamQuery query);

    void addExam(HealthExamCommand command);

    void updateExam(HealthExamCommand command);

    void deleteExam(Long id);
}
