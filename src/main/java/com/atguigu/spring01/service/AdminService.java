package com.atguigu.spring01.service;

import cn.hutool.core.util.StrUtil;
import com.atguigu.spring01.entity.Admin;
import com.atguigu.spring01.exception.CustomException;
import com.atguigu.spring01.mapper.AdminMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 管理员服务类
 * 该类提供与管理员相关的业务逻辑处理功能
 */
@Service
public class AdminService{
    @Autowired
    AdminMapper adminMapper;
    public String admin(String name){
        if ("admin".equals(name)){
            return "admin";
        }
        else {
            throw new CustomException("账户不存在");
        }
    }

/**
 * 查询所有管理员信息的方法
 * 该方法用于从数据库中检索所有管理员记录
 *
 * @return List<Admin> 返回包含所有管理员对象的列表，如果没有记录则返回空列表
 */
    public List<Admin> selectAll(){

        return adminMapper.selectAll(null);  // 调用adminMapper的selectAll方法获取所有管理员数据
    }

/**
 * 分页查询管理员信息
 * @param pageNum 当前页码
 * @param pageSize 每页显示数量
 * @return PageInfo<Admin> 分页结果对象，包含分页信息和数据列表
 */
    public PageInfo<Admin> selectPage(Integer pageNum, Integer pageSize,Admin admin) {
    // 使用PageHelper开始分页，设置当前页码和每页显示数量
        PageHelper.startPage(pageNum, pageSize);
    // 查询所有管理员信息
        List<Admin> list = adminMapper.selectAll(admin);
    // 将查询结果列表转换为PageInfo对象并返回
        return PageInfo.of(list);
    }

    public void add(Admin admin) {
        //根据新的账号查询数据库，是否存在同样的账号
        Admin dbAdmin1 = adminMapper.selectByUsername(admin.getUsername());
        if(dbAdmin1 != null){
            throw new CustomException("账号已存在");
        }
        //默认密码
        if (StrUtil.isBlank(admin.getPassword()))
        {
            admin.setPassword("admin");
        }
        adminMapper.insert(admin);

    }

    public void update(Admin admin) {
        adminMapper.updateById(admin);
    }


    public void deleteById(Integer id) {
        adminMapper.deleteById(id);
    }

    public void deleteBatch(List<Admin> list) {
        for (Admin admin : list)
         deleteById(admin.getId());
    }
}
