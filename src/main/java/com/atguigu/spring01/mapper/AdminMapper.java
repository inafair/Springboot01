package com.atguigu.spring01.mapper;

import com.atguigu.spring01.entity.Admin;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AdminMapper {
    List<Admin> selectAll(Admin admin) ;

    void insert(Admin admin);

    void updateById(Admin admin);

    @Select("select * from `admin` where username=#{username}")
    Admin selectByUsername(String username);

    @Delete("delete from `admin` where id=#{id}")
    void deleteById(Integer id);
}
