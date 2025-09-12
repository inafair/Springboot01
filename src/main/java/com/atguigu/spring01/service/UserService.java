package com.atguigu.spring01.service;

import cn.hutool.core.util.StrUtil;
import com.atguigu.spring01.entity.User;
import com.atguigu.spring01.exception.CustomException;
import com.atguigu.spring01.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 管理员服务类
 * 该类提供与管理员相关的业务逻辑处理功能
 */
@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    public String user(String name){
        if ("user".equals(name)){
            return "user";
        }
        else {
            throw new CustomException("账户不存在");
        }
    }

/**
 * 查询所有管理员信息的方法
 * 该方法用于从数据库中检索所有管理员记录
 *
 * @return List<User> 返回包含所有管理员对象的列表，如果没有记录则返回空列表
 */
    public List<User> selectAll(User user ){

        return userMapper.selectAll(user);  // 调用userMapper的selectAll方法获取所有管理员数据
    }

/**
 * 分页查询管理员信息
 * @param pageNum 当前页码
 * @param pageSize 每页显示数量
 * @return PageInfo<User> 分页结果对象，包含分页信息和数据列表
 */
    public PageInfo<User> selectPage(Integer pageNum, Integer pageSize,User user) {
    // 使用PageHelper开始分页，设置当前页码和每页显示数量
        PageHelper.startPage(pageNum, pageSize);
    // 查询所有管理员信息
        List<User> list = userMapper.selectAll(user);
    // 将查询结果列表转换为PageInfo对象并返回
        return PageInfo.of(list);
    }

    public void add(User user) {
        //根据新的账号查询数据库，是否存在同样的账号
        User dbUser1 = userMapper.selectByUsername(user.getUsername());
        if(dbUser1 != null){
            throw new CustomException("账号已存在");
        }
        //默认密码
        if (StrUtil.isBlank(user.getPassword()))
        {
            user.setPassword("user");
        }
        userMapper.insert(user);

    }

    public void update(User user) {
        userMapper.updateById(user);
    }


    public void deleteById(Integer id) {
        userMapper.deleteById(id);
    }

    public void deleteBatch(List<User> list) {
        for (User user : list)
         deleteById(user.getId());
    }

    public User login (User user) {
        //验证账号是否存在
        User dbuser = userMapper.selectByUsername(user.getUsername());
        if (dbuser == null){
            throw new CustomException("账号不存在");
        }
        //验证密码是否正确
        if (!dbuser.getPassword().equals(user.getPassword())){
            throw new CustomException("账号或者密码错误");
        }
        return dbuser;
    }
}
