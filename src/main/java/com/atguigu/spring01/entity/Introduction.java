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
}
