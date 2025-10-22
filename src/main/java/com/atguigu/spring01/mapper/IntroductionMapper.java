package com.atguigu.spring01.mapper;

import com.atguigu.spring01.entity.Introduction;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

public interface IntroductionMapper {
    List<Introduction> selectAll(Introduction introduction);

    void insert(Introduction introduction);

    void updateById(Introduction introduction);

    @Delete("delete from `introduction` where id=#{id}")
    void deleteById(Integer id);

}
