package com.atguigu.spring01.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Dict;
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
import java.util.HashMap;
import java.util.Map;

@RestController()
@RequestMapping("/files")
public class FileController {
    public static final String filePath = System.getProperty("user.dir") + "/files/";
    @GetMapping("/download/{fileName}")
    public void  download(@PathVariable String fileName, HttpServletResponse  response)throws  Exception{
        //获取文件位置
        String realPath = filePath + fileName;
        boolean exist = FileUtil.exist(realPath);
        if(!exist){
            throw new CustomException("文件不存在");
        }
        byte[] bytes = FileUtil.readBytes(realPath);
        ServletOutputStream os = response.getOutputStream();
        os.write(bytes);
        os.flush();
        os.close();
    }


    @PostMapping("/upload")
    public Result upload(@RequestParam("file")  MultipartFile  file) throws Exception {
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

    @PostMapping("/wang/upload")
    public Map<String,Object> wangEditorUpload(MultipartFile  file) {
       String flag = System.currentTimeMillis() +"";
       String fileName = file.getOriginalFilename();
       try {
           String filePath = System.getProperty("user.dir") + "/files/";
           //文件存储形式：时间戳_文件名
           FileUtil.writeBytes(file.getBytes(),filePath+flag+"_"+fileName);
       }catch (Exception e){
           System.err.println(fileName + "上传失败");
       }
       String http = "http://localhost:9090/files/download/";
       Map<String,Object> resMap = new HashMap<>();
       //wangEditor上传图片成功后，需要返回参数
        resMap.put("errno",0);
        resMap.put("data", CollUtil.newArrayList(Dict.create().set("url",http + flag +"-" + fileName)));
        return resMap;

    }
}
