package com.family.finance.system.controller.query;

import com.family.finance.system.common.page.PageParam;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BudgetPlanQuery extends PageParam {
    private String year;
}
