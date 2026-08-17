package com.family.finance.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.family.finance.system.service.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<SysUserEntity> {
}