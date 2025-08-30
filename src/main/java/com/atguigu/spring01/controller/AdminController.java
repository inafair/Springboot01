package com.atguigu.spring01.controller;

import com.atguigu.spring01.common.Result;
import com.atguigu.spring01.entity.Admin;
import com.atguigu.spring01.service.AdminService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController {
    @Resource
    private AdminService adminService;

    @GetMapping("/selectAll")
    public Result selectAll(){
        List<Admin> adminList= adminService.selectAll();
        return Result.success(adminList);
    }

}
