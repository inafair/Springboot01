package com.atguigu.spring01.controller;

import com.atguigu.spring01.common.Result;
import com.atguigu.spring01.entity.Notice;
import com.atguigu.spring01.service.NoticeService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理员控制器
 * 提供管理员相关的API接口
 */
@RestController()  // 标记此类为RESTful控制器，所有方法默认返回JSON格式数据
@RequestMapping("/notice")  // 设置控制器的基础请求路径，所有接口URL前都会加上/notice
public class NoticeController {
    // 注入NoticeService服务
    //http://localhost:9090/
    @Resource  // 使用@Resource注解自动注入NoticeService实例
    private NoticeService noticeService;  // 声明NoticeService类型的私有变量，用于处理管理员相关业务逻辑

    /**
     * 查询所有管理员信息
     *
     * @return 返回包含所有管理员信息的Result对象
     */
    @PostMapping("/add")  // 将HTTP POST请求映射到"/add"路径
    public Result add(@RequestBody Notice notice) {  // 接收HTTP请求体中的JSON数据并转换为Notice对象
        //@requestBody注解用于将请求体中的JSON数据转换为Notice对象
        noticeService.add(notice);  // 调用服务层的add方法，添加新的管理员信息
        return Result.success();  // 返回成功操作的结果对象
    }

    @PutMapping("/update")  // 将HTTP POST请求映射到"/update"路径
    public Result update(@RequestBody Notice notice) {  // 接收HTTP请求体中的JSON数据并转换为Notice对象
        noticeService.update(notice);  // 调用服务层的update方法，更新管理员信息
        return Result.success();  // 返回成功操作的结果对象
    }

    @GetMapping("/selectAll")  // HTTP GET请求映射到"/selectAll"路径
    public Result selectAll(Notice notice) {  // 定义一个返回Result类型的方法，无参数
        // 调用服务层方法获取所有管理员列表
        List<Notice> noticeList = noticeService.selectAll(notice);  // 从noticeService中获取所有管理员数据
        // 返回成功结果，包含管理员列表数据
        return Result.success(noticeList);  // 使用Result.success()方法封装查询结果并返回
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        noticeService.deleteById(id);
        return Result.success();
    }

    // 接收HTTP请求体中的JSON数据并转换为List<Integer>对象
    // 调用服务层的delete方法，删除指定管理员信息

    /**
     * 分页查询管理员信息
     * （此方法尚未实现）
     */
    @GetMapping("/selectPage")  // 将HTTP GET请求映射到"/selectPage"路径
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,  // 设置页码默认值为1
                             @RequestParam(defaultValue = "10") Integer pageSize,  // 设置每页大小默认值为10
                             Notice notice) {  // 接收查询条件参数
        PageInfo<Notice> pageInfo = noticeService.selectPage(pageNum, pageSize, notice);  // 调用服务层方法获取分页数据
        return Result.success(pageInfo); //返回分页对象
    }


}

