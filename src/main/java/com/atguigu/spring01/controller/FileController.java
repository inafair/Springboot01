package com.atguigu.spring01.controller;

import cn.hutool.core.io.FileUtil;
import com.atguigu.spring01.common.Result;
import com.atguigu.spring01.exception.CustomException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/files")
public class FileController {
    public static final String filePath = System.getProperty("user.dir") + "/files/";
    @GetMapping("/download/{fileName}")
    public void  download(@PathVariable String fileName, HttpServletResponse  response)throws  Exception{
        //获取文件位置
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
        response.setContentType("application/octet-stream");
        String realPath =  filePath + fileName;
        boolean exist = FileUtil.exist(realPath);
        if(!exist){
            FileUtil.mkdir(filePath);
        }
        byte[] bytes = FileUtil.readBytes(realPath);
        OutputStream os = response.getOutputStream();
        os.write(bytes);
        os.flush();
        os.close();
    }


    @PostMapping("/upload")
    public Result upload(MultipartFile  file) throws Exception {
        String originalFilename = file.getOriginalFilename();
        if (!FileUtil.isDirectory(filePath)) {
            FileUtil.mkdir(filePath);
        }
        String fileName = System.currentTimeMillis()+"_"+ originalFilename ;
        String realPath = filePath + fileName;
        try {
            FileUtil.writeBytes(file.getBytes(),realPath);
        }catch (Exception e){
            e.printStackTrace();
            throw new CustomException("文件上传失败");
        }

        String url = "http://localhost:9090/files/download/" + fileName;
        return Result.success(url);
    }
}
