package com.atguigu.spring01.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.atguigu.spring01.common.Result;
import com.atguigu.spring01.entity.User;
import com.atguigu.spring01.service.UserService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 管理员控制器
 * 提供管理员相关的API接口
 */
@RestController()  // 标记此类为RESTful控制器，所有方法默认返回JSON格式数据
@RequestMapping("/user")  // 设置控制器的基础请求路径，所有接口URL前都会加上/user
public class UserController {
    // 注入UserService服务
    //http://localhost:9090/
    @Resource  // 使用@Resource注解自动注入UserService实例
    private UserService userService;  // 声明UserService类型的私有变量，用于处理管理员相关业务逻辑

    /**
     * 查询所有管理员信息
     * @return 返回包含所有管理员信息的Result对象
      */
    @PostMapping("/add")  // 将HTTP POST请求映射到"/add"路径
    public Result add(@RequestBody User user){  // 接收HTTP请求体中的JSON数据并转换为User对象
        //@requestBody注解用于将请求体中的JSON数据转换为User对象
        userService.add(user);  // 调用服务层的add方法，添加新的管理员信息
        return Result.success();  // 返回成功操作的结果对象
    }

    @PutMapping("/update")  // 将HTTP POST请求映射到"/update"路径
    public Result update(@RequestBody User user){  // 接收HTTP请求体中的JSON数据并转换为User对象
        userService.update(user);  // 调用服务层的update方法，更新管理员信息
        return Result.success();  // 返回成功操作的结果对象
    }
    @GetMapping("/selectAll")  // HTTP GET请求映射到"/selectAll"路径
    public Result selectAll(User user){  // 定义一个返回Result类型的方法，无参数
        // 调用服务层方法获取所有管理员列表
        List<User> userList= userService.selectAll(user);  // 从userService中获取所有管理员数据
        // 返回成功结果，包含管理员列表数据
        return Result.success(userList);  // 使用Result.success()方法封装查询结果并返回
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id){
        userService.deleteById(id);
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
                             User user){  // 接收查询条件参数
        PageInfo<User> pageInfo = userService.selectPage(pageNum, pageSize, user);  // 调用服务层方法获取分页数据
        return Result.success(pageInfo); //返回分页对象
    }


    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<User> list){
        userService.deleteBatch(list);
        return Result.success();
    }

    @GetMapping("/export") // HTTP GET请求映射到/export路径
    public void exportData(User user, HttpServletResponse resp) throws Exception {
        String ids = user.getIds();
        if (StrUtil.isNotBlank(ids)) {
            String[] idsArr = ids.split(",");
            user.setIdsArr(idsArr);
        }
        //拿到所有数据
        List<User> list = userService.selectAll(user);
        //构建writter对象
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //设置中文表头
        writer.addHeaderAlias("username", "账号");
        writer.addHeaderAlias("name", "名称");
        writer.addHeaderAlias("phone", "电话");
        writer.addHeaderAlias("email", "邮箱");

        writer.setOnlyAlias(true);

        writer.write(list);
        resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("管理员信息", StandardCharsets.UTF_8);
        resp.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        //写出到输出流，并关闭writer
        ServletOutputStream os = resp.getOutputStream();
        writer.flush(os);
        writer.close();
        os.close();
    }

    //文件的导入
    @PostMapping("/import")
    public Result importData(MultipartFile file) throws Exception {
        //拿到输入流
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        //设置中文表头
        reader.addHeaderAlias("账号", "username");
        reader.addHeaderAlias("名称", "name");
        reader.addHeaderAlias("电话", "phone");
        reader.addHeaderAlias("邮箱", "email");
        List<User> users = reader.readAll(User.class);
        //将数据写到数据库
        for(User user:users)
        {
            userService.add(user);
        }
        return Result.success();

    }
}

