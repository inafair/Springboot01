package com.atguigu.spring01.mapper;

import com.atguigu.spring01.entity.Notice;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

public interface NoticeMapper {
    List<Notice> selectAll(Notice notice);

    void insert(Notice notice);

    void updateById(Notice notice);

    @Delete("delete from `notice` where id=#{id}")
    void deleteById(Integer id);

}
