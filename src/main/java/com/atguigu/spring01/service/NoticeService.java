package com.atguigu.spring01.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.atguigu.spring01.entity.Account;
import com.atguigu.spring01.entity.Notice;
import com.atguigu.spring01.entity.User;
import com.atguigu.spring01.exception.CustomException;
import com.atguigu.spring01.mapper.NoticeMapper;
import com.atguigu.spring01.mapper.UserMapper;
import com.atguigu.spring01.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {
    @Autowired
    NoticeMapper noticeMapper;

    @Resource
    UserMapper userMapper;
    // 自动注入NoticeMapper接口，用于数据库操作
    /**
     * 根据名称查询通知的方法
     * @param name 要查询的通知名称
     * @return String 返回通知名称，如果名称不匹配则抛出异常
     * @throws CustomException 当传入的名称不是"notice"时抛出异常
     */
    public String notice(String name) {
        if ("notice".equals(name)) {  // 检查传入的名称是否为"notice"
            return "notice";  // 如果匹配则返回通知名称
        } else {
            throw new CustomException("账户不存在");  // 如果不匹配则抛出异常
        }
    }

    /**
     * 查询所有管理员信息的方法
     * 该方法用于从数据库中检索所有管理员记录
     *
     * @param notice 查询条件对象，包含查询参数
     * @return List<Notice> 返回包含所有管理员对象的列表，如果没有记录则返回空列表
     */
    public List<Notice> selectAll(Notice notice) {

        return noticeMapper.selectAll(notice);  // 调用noticeMapper的selectAll方法获取所有管理员数据
    }

    /**
     * 分页查询管理员信息
     *
     * @param pageNum  当前页码
     * @param pageSize 每页显示数量
     * @param notice   查询条件对象，包含查询参数
     * @return PageInfo<Notice> 分页结果对象，包含分页信息和数据列表
     */
    public PageInfo<Notice> selectPage(Integer pageNum, Integer pageSize, Notice notice) {
        // 使用PageHelper开始分页，设置当前页码和每页显示数量
        PageHelper.startPage(pageNum, pageSize);
        // 查询所有管理员信息
        List<Notice> list = noticeMapper.selectAll(notice);
        for(Notice dbnotice :list){
            Integer userId = dbnotice.getUserId();
            User user = userMapper.selectById(userId.toString());
            if (ObjectUtil.isNotEmpty(user)){
                dbnotice.setUserName(user.getName());
            }
        }
        // 将查询结果列表转换为PageInfo对象并返回
        return PageInfo.of(list);
    }

    /**
     * 添加通知信息的方法
     * @param notice 要添加的通知对象
     * @throws CustomException 当当前用户角色为"USER"时抛出异常，表示无权限操作
     */
    public void add(Notice notice) {
        Account account = TokenUtils.getCurrentUser();  // 获取当前登录用户信息
        if ("USER".equals(account.getRole())) {  // 检查用户角色是否为普通用户
            throw new CustomException("500","您的角色无权限该操作");  // 如果是普通用户则抛出无权限异常
        }
        notice.setTime(DateUtil.now());  // 设置通知时间为当前时间
        noticeMapper.insert(notice);  // 调用noticeMapper的insert方法将通知信息插入数据库

    }

    /**
     * 更新通知信息的方法
     * @param notice 包含更新信息的通知对象
     */
    public void update(Notice notice) {
        noticeMapper.updateById(notice);  // 调用noticeMapper的updateById方法更新通知信息
    }


    /**
     * 根据ID删除通知信息的方法
     * @param id 要删除的通知ID
     */
    public void deleteById(Integer id) {
        noticeMapper.deleteById(id);  // 调用noticeMapper的deleteById方法删除指定ID的通知
    }
}
