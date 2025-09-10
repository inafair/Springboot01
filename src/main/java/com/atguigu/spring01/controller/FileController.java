package com.atguigu.spring01.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.atguigu.spring01.entity.Admin;
import com.atguigu.spring01.service.AdminService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class FileController {
    @Autowired
    AdminService adminService;

    //数据导出
    /**
     * 导出管理员数据为Excel文件
     * @param resp HttpServletResponse对象，用于响应下载请求
     * @throws Exception 可能抛出的异常
     */
    @GetMapping("/export") // HTTP GET请求映射到/export路径
    public void exportData(Admin admin , HttpServletResponse resp) throws Exception {
        String ids = admin.getIds();
        if (StrUtil.isNotBlank(ids)){
            String [] idsArr = ids.split(",");
            admin.setIdsArr(idsArr);
        }
        //拿到所有数据
        List<Admin> list = adminService.selectAll(admin);
        //构建writter对象
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //设置中文表头
        writer.addHeaderAlias("username","账号");
        writer.addHeaderAlias("name","名称");
        writer.addHeaderAlias("phone","电话");
        writer.addHeaderAlias("email","邮箱");

        writer.setOnlyAlias(true);

        writer.write(list);
        resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("管理员信息", StandardCharsets.UTF_8);
        resp.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");

        //写出到输出流，并关闭writer
        ServletOutputStream os = resp.getOutputStream();
        writer.flush(os);
        writer.close();
        os.close();
    }

    //文件的导入
}
