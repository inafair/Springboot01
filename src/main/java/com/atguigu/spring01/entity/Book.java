package com.atguigu.spring01.entity;

import lombok.Data;

/**
 * 图书信息表
 */
@Data
public class Book {
    private Integer id;
    private String name;
    private String img;
    private String author;
    private String price;
    private Integer num;
}
