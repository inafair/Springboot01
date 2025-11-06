package com.atguigu.spring01.service;

import com.atguigu.spring01.entity.Record;
import com.atguigu.spring01.exception.CustomException;
import com.atguigu.spring01.mapper.RecordMapper;
import com.atguigu.spring01.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordService {
    @Autowired
    RecordMapper recordMapper;

    @Resource
    UserMapper userMapper;
    // 自动注入RecordMapper接口，用于数据库操作
    /**
     * 根据名称查询通知的方法
     * @param name 要查询的通知名称
     * @return String 返回通知名称，如果名称不匹配则抛出异常
     * @throws CustomException 当传入的名称不是"record"时抛出异常
     */
    public String record(String name) {
        if ("record".equals(name)) {  // 检查传入的名称是否为"record"
            return "record";  // 如果匹配则返回通知名称
        } else {
            throw new CustomException("账户不存在");  // 如果不匹配则抛出异常
        }
    }

    /**
     * 查询所有管理员信息的方法
     * 该方法用于从数据库中检索所有管理员记录
     *
     * @param record 查询条件对象，包含查询参数
     * @return List<Record> 返回包含所有管理员对象的列表，如果没有记录则返回空列表
     */
    public List<Record> selectAll(Record record) {

        return recordMapper.selectAll(record);  // 调用recordMapper的selectAll方法获取所有管理员数据
    }

    /**
     * 分页查询管理员信息
     *
     * @param pageNum  当前页码
     * @param pageSize 每页显示数量
     * @param record   查询条件对象，包含查询参数
     * @return PageInfo<Record> 分页结果对象，包含分页信息和数据列表
     */
    public PageInfo<Record> selectPage(Integer pageNum, Integer pageSize, Record record) {
        // 使用PageHelper开始分页，设置当前页码和每页显示数量
        PageHelper.startPage(pageNum, pageSize);
        // 查询所有管理员信息
        List<Record> list = recordMapper.selectAll(record);
        // 将查询结果列表转换为PageInfo对象并返回
        return PageInfo.of(list);
    }

    /**
     * 添加通知信息的方法
     * @param record 要添加的通知对象
     * @throws CustomException 当当前用户角色为"USER"时抛出异常，表示无权限操作
     */
    public void add(Record record) {
        recordMapper.insert(record);  // 调用recordMapper的insert方法将通知信息插入数据库

    }

    /**
     * 更新通知信息的方法
     * @param record 包含更新信息的通知对象
     */
    public void update(Record record) {
        recordMapper.updateById(record);  // 调用recordMapper的updateById方法更新通知信息
    }


    /**
     * 根据ID删除通知信息的方法
     * @param id 要删除的通知ID
     */
    public void deleteById(Integer id) {
        recordMapper.deleteById(id);  // 调用recordMapper的deleteById方法删除指定ID的通知
    }
}
