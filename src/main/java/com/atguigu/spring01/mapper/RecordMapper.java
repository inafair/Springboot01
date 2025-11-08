package com.atguigu.spring01.mapper;

import com.atguigu.spring01.entity.Record;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RecordMapper {
    List<Record> selectAll(Record record) ;

    void insert(Record record);

    void updateById(Record record);

    @Select("select * from `record` where username=#{username}")
    Record selectByUsername(String username);

    @Delete("delete from `record` where id=#{id}")
    void deleteById(Integer id);

    @Select("select * from `record` where id=#{id}")
    Record selectById(Integer Id);
}
