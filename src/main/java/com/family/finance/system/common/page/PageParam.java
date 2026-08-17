package com.family.finance.system.common.page;

import lombok.Data;

@Data
public class PageParam {
    private Long pageNum = 1L;
    private Long pageSize = 10L;
}