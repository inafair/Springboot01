package com.atguigu.spring01.mapper;

import com.atguigu.spring01.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CategoryMapper {
    @Select("select * from `category` where id=#{id}")
    Category selectById(int categoryId) ;

    List<Category> selectAll(Category category);

    void insert(Category category);

    void updateById(Category category);

    @Delete("delete from `category` where id=#{id}")
    void deleteById(Integer id);

}
