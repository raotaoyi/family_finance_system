package com.family.finance.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.family.finance.system.common.result.Result;
import com.family.finance.system.common.util.JwtUtil;
import com.family.finance.system.service.entity.SysUserEntity;
import com.family.finance.system.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserServiceImpl userService;
    @Resource
    private JwtUtil jwtUtil;

    // 注册
    @PostMapping("/register")
    public Result<?> register(@RequestBody SysUserEntity user){
        LambdaQueryWrapper<SysUserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserEntity::getUsername,user.getUsername());
        SysUserEntity one = userService.getOne(wrapper);
        if(one != null){
            return Result.fail(500,"账号已存在");
        }
        userService.save(user);
        return Result.success();
    }

    // 登录
    @PostMapping("/login")
    public Result<?> login(@RequestBody SysUserEntity user){
        LambdaQueryWrapper<SysUserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserEntity::getUsername,user.getUsername())
                .eq(SysUserEntity::getPassword,user.getPassword());
        SysUserEntity loginUser = userService.getOne(wrapper);
        if(loginUser == null){
            return Result.fail(500,"账号密码错误");
        }
        String token = jwtUtil.generateToken(loginUser.getId());
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        map.put("nickName",loginUser.getNickName());
        map.put("userId",loginUser.getId());
        return Result.success(map);
    }


    @GetMapping("/test")
    public Result<?> test(){
        return new Result<>();

    }

}
