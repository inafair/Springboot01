package com.atguigu.spring01.controller;

import com.atguigu.spring01.common.Result;
import com.atguigu.spring01.service.AdminService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Objects;

/**
 * WebController 控制器类
 * 用于处理Web请求并返回响应结果
 */
@RestController
public class WebController {
    //http://localhost:9090/
    @GetMapping("/")
    public Result index(){
        return Result.success("欢迎来到SpringBoot");
    }

}