package com.atguigu.spring01.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.atguigu.spring01.entity.Account;
import com.atguigu.spring01.entity.Apply;
import com.atguigu.spring01.entity.User;
import com.atguigu.spring01.exception.CustomException;
import com.atguigu.spring01.mapper.ApplyMapper;
import com.atguigu.spring01.mapper.UserMapper;
import com.atguigu.spring01.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplyService {
    @Autowired
    ApplyMapper applyMapper;

    @Resource
    UserMapper userMapper;
    // 自动注入ApplyMapper接口，用于数据库操作
    /**
     * 根据名称查询通知的方法
     * @param name 要查询的通知名称
     * @return String 返回通知名称，如果名称不匹配则抛出异常
     * @throws CustomException 当传入的名称不是"apply"时抛出异常
     */
    public String apply(String name) {
        if ("apply".equals(name)) {  // 检查传入的名称是否为"apply"
            return "apply";  // 如果匹配则返回通知名称
        } else {
            throw new CustomException("账户不存在");  // 如果不匹配则抛出异常
        }
    }

    /**
     * 查询所有管理员信息的方法
     * 该方法用于从数据库中检索所有管理员记录
     *
     * @param apply 查询条件对象，包含查询参数
     * @return List<Apply> 返回包含所有管理员对象的列表，如果没有记录则返回空列表
     */
    public List<Apply> selectAll(Apply apply) {

        return applyMapper.selectAll(apply);  // 调用applyMapper的selectAll方法获取所有管理员数据
    }

    /**
     * 分页查询管理员信息
     *
     * @param pageNum  当前页码
     * @param pageSize 每页显示数量
     * @param apply   查询条件对象，包含查询参数
     * @return PageInfo<Apply> 分页结果对象，包含分页信息和数据列表
     */
    public PageInfo<Apply> selectPage(Integer pageNum, Integer pageSize, Apply apply) {
        // 使用PageHelper开始分页，设置当前页码和每页显示数量
        Account currentUser = TokenUtils.getCurrentUser();
        if ("USER".equals(currentUser.getRole())) {
            apply.setUserId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        // 查询所有管理员信息
        List<Apply> list = applyMapper.selectAll(apply);

        // 将查询结果列表转换为PageInfo对象并返回
        return PageInfo.of(list);
    }

    /**
     * 添加通知信息的方法
     * @param apply 要添加的通知对象
     * @throws CustomException 当当前用户角色为"USER"时抛出异常，表示无权限操作
     */
    public void add(Apply apply) {
        Account currentUser = TokenUtils.getCurrentUser();
        apply.setTime(DateUtil.now());// 设置通知时间为当前时间
        apply.setStatus("待审核");
        apply.setUserId(currentUser.getId());
        applyMapper.insert(apply);  // 调用applyMapper的insert方法将通知信息插入数据库

    }

    /**
     * 更新通知信息的方法
     * @param apply 包含更新信息的通知对象
     */
    public void update(Apply apply) {
        applyMapper.updateById(apply);  // 调用applyMapper的updateById方法更新通知信息
    }


    /**
     * 根据ID删除通知信息的方法
     * @param id 要删除的通知ID
     */
    public void deleteById(Integer id) {
        applyMapper.deleteById(id);  // 调用applyMapper的deleteById方法删除指定ID的通知
    }
}
