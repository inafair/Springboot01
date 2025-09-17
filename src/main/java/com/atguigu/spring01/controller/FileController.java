package com.atguigu.spring01.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.atguigu.spring01.common.Result;
import com.atguigu.spring01.entity.Admin;
import com.atguigu.spring01.exception.CustomException;
import com.atguigu.spring01.service.AdminService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 处理文件上传下载的相关接口
 */
@RestController
@RequestMapping("/files")
public class FileController {
/**
 * 文件下载接口
 * 根据文件名下载指定文件
 * @param fileName 文件名
 * @param response HTTP响应对象
 * @throws Exception 可能抛出的异常
 */
    @GetMapping("/download/{fileName}")  // HTTP GET请求映射，用于处理文件下载请求
    public void download(@PathVariable String fileName, HttpServletResponse response) throws Exception {  // 方法定义，接收文件名和HTTP响应对象作为参数
            String filePath = System.getProperty("user.dir") + "/files/";//获得项目的根路径
            String realPath = filePath + fileName;
            boolean exist = FileUtil.exist(realPath);
            if (!exist) {
                throw new CustomException("文件不存在");
            }
            //读取文件的字节流
            byte[] bytes = FileUtil.readBytes(realPath);
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
    }

    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file) throws Exception {
        String filePath = System.getProperty("user.dir") + "/files/";//获得项目的根路径
        if (!FileUtil.isDirectory(filePath)) {
            FileUtil.mkdir(filePath);
        }
        byte[] bytes = file.getBytes();
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();//文件原始名称

        //写入文件
        FileUtil.writeBytes(bytes,filePath + fileName);

        String url = "http://localhost:9090/files/download/" + fileName;

        return Result.success(url);
    }

}
