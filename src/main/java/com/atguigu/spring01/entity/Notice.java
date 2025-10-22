package com.atguigu.spring01.entity;

import lombok.Data;

/**
 * 系统公告信息
 */
@Data
public class Notice {
    private Integer id;
    private String title;
    private String content;
    private String time;
    private Integer userId;
    //非数据库字段
    private String userName;

}
