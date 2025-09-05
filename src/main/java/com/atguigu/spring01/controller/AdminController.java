package com.atguigu.spring01.controller;

import com.atguigu.spring01.common.Result;
import com.atguigu.spring01.entity.Admin;
import com.atguigu.spring01.service.AdminService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 管理员控制器
 * 提供管理员相关的API接口
 */
@RestController()
@RequestMapping("/admin")
public class AdminController {
    // 注入AdminService服务
    //http://localhost:9090/
    @Resource
    private AdminService adminService;

    /**
     * 查询所有管理员信息
     * @return 返回包含所有管理员信息的Result对象
     */
    @GetMapping("/selectAll")  // HTTP GET请求映射到"/selectAll"路径
    public Result selectAll(){  // 定义一个返回Result类型的方法，无参数
        // 调用服务层方法获取所有管理员列表
        List<Admin> adminList= adminService.selectAll();  // 从adminService中获取所有管理员数据
        // 返回成功结果，包含管理员列表数据
        return Result.success(adminList);  // 使用Result.success()方法封装查询结果并返回
    }

    /**
     * 分页查询管理员信息
     * （此方法尚未实现）
     */
    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             Admin admin){
        PageInfo<Admin> pageInfo = adminService.selectPage(pageNum, pageSize, admin);
        return Result.success(pageInfo); //返回分页对象
    }

}
