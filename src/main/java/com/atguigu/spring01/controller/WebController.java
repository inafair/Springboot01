package com.atguigu.spring01.controller;

import com.atguigu.spring01.common.Result;
import com.atguigu.spring01.entity.Account;
import com.atguigu.spring01.entity.Admin;
import com.atguigu.spring01.entity.User;
import com.atguigu.spring01.exception.CustomException;
import com.atguigu.spring01.service.AdminService;
import com.atguigu.spring01.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Objects;

/**
 * WebController 控制器类
 * 用于处理Web请求并返回响应结果
 */
@RestController
public class WebController {
    @Resource
    AdminService adminService;
    @Resource
    UserService userService;
    //http://localhost:9090/
    @GetMapping("/")
    public Result index(){
        return Result.success("欢迎来到SpringBoot");
    }

    @PostMapping("/login")
    public Result login(@RequestBody Account account){
        Account dbAccount = null;
        //判断逻辑
        if("ADMIN".equals(account.getRole()))
        {
            dbAccount = adminService.login(account);
        }
        else if("USER".equals(account.getRole()))
        {
            dbAccount = userService.login(account);
        }
        else{
            throw new CustomException("非法请求");
        }
        return Result.success(dbAccount);
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user)
    {
        userService.register(user);
        return Result.success("注册成功");
    }

}