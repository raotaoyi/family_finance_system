package com.family.finance.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.family.finance.system.service.entity.SysUserEntity;
import com.family.finance.system.mapper.UserMapper;
import com.family.finance.system.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, SysUserEntity>  implements UserService {
}