package com.atguigu.spring01.mapper;

import com.atguigu.spring01.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {
    List<User> selectAll(User user) ;

    void insert(User user);

    void updateById(User user);

    @Select("select * from `user` where username=#{username}")
    User selectByUsername(String username);

    @Delete("delete from `user` where id=#{id}")
    void deleteById(Integer id);

    @Select("select * from `user` where id = #{userId}")
    User selectById(String userId);
}
