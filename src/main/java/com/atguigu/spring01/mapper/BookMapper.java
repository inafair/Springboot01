package com.atguigu.spring01.mapper;

import com.atguigu.spring01.entity.Book;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BookMapper {
    List<Book> selectAll(Book book) ;

    void insert(Book book);

    void updateById(Book book);

    @Select("select * from `book` where username=#{username}")
    Book selectByUsername(String username);

    @Delete("delete from `book` where id=#{id}")
    void deleteById(Integer id);

    @Select("select * from `book` where id=#{id}")
    Book selectById(Integer id);
}
