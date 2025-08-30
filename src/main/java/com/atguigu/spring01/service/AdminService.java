package com.atguigu.spring01.service;

import com.atguigu.spring01.entity.Admin;
import com.atguigu.spring01.exception.CustomException;
import com.atguigu.spring01.mapper.AdminMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 管理员服务类
 * 该类提供与管理员相关的业务逻辑处理功能
 */
@Service
public class AdminService{
    @Resource
    AdminMapper adminMapper;
    public String admin(String name){
        if ("admin".equals(name)){
            return "admin";
        }
        else {
            throw new CustomException("账户不存在");
        }
    }

    public List<Admin> selectAll(){
        return adminMapper.selectAll();
    }
}
