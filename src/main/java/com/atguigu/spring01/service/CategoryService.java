package com.atguigu.spring01.service;

import cn.hutool.core.date.DateUtil;
import com.atguigu.spring01.entity.Account;
import com.atguigu.spring01.entity.Category;
import com.atguigu.spring01.exception.CustomException;
import com.atguigu.spring01.mapper.CategoryMapper;
import com.atguigu.spring01.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    public String category(String name) {
        if ("category".equals(name)) {
            return "category";
        } else {
            throw new CustomException("账户不存在");
        }
    }

    /**
     * 查询所有管理员信息的方法
     * 该方法用于从数据库中检索所有管理员记录
     *
     * @return List<Category> 返回包含所有管理员对象的列表，如果没有记录则返回空列表
     */
    public List<Category> selectAll(Category category) {

        return categoryMapper.selectAll(category);  // 调用categoryMapper的selectAll方法获取所有管理员数据
    }

    /**
     * 分页查询管理员信息
     *
     * @param pageNum  当前页码
     * @param pageSize 每页显示数量
     * @return PageInfo<Category> 分页结果对象，包含分页信息和数据列表
     */
    public PageInfo<Category> selectPage(Integer pageNum, Integer pageSize, Category category) {
        // 使用PageHelper开始分页，设置当前页码和每页显示数量
        PageHelper.startPage(pageNum, pageSize);
        // 查询所有管理员信息
        List<Category> list = categoryMapper.selectAll(category);
        // 将查询结果列表转换为PageInfo对象并返回
        return PageInfo.of(list);
    }

    public void add(Category category) {
        Account account = TokenUtils.getCurrentUser();
        if ("USER".equals(account.getRole())) {
            throw new CustomException("500","您的角色无权限该操作");
        }
        categoryMapper.insert(category);

    }

    public void update(Category category) {
        categoryMapper.updateById(category);
    }


    public void deleteById(Integer id) {
        categoryMapper.deleteById(id);
    }
}
