package com.atguigu.spring01.entity;

import lombok.Data;

/**
 * 系统公告信息
 */
@Data
public class Introduction {
    private Integer id;
    private String img;
    private String title;
    private String content;
    private String time;
    private Integer categoryId;
    private Integer userId;


    //不是数据库的字段
    //用来存储数据库中用category_id关联category表中的title字段
    private String categoryTitle;
    private String userName;
}
