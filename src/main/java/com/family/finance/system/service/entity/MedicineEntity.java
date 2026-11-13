package com.family.finance.system.service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 药品/疫苗管理
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("medicine")
public class MedicineEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 家庭成员：爸爸/妈妈/宝宝
     */
    private String member;

    /**
     * 药品/疫苗名称
     */
    private String name;

    /**
     * 类型：1药品 2疫苗
     */
    private Integer category;

    /**
     * 剂量/规格/剂次
     */
    private String dose;

    /**
     * 用法用量/频次
     */
    private String frequency;

    /**
     * 开始日期 yyyy-MM-dd
     */
    private String startDate;

    /**
     * 结束日期 yyyy-MM-dd
     */
    private String endDate;

    /**
     * 剩余量/库存
     */
    private String remaining;

    /**
     * 状态：0未开始 1进行中 2已完成 3已停用
     */
    private Integer status;

    /**
     * 是否提醒：0否 1是
     */
    private Integer remind;

    /**
     * 备注
     */
    private String remark;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
