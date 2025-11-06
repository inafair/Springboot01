package com.atguigu.spring01.entity;

import lombok.Data;

import java.util.function.Predicate;

/**
 * 请假信息表
 */
@Data
public class Apply {
    private Integer id;
    private Integer userId;
    private String title;
    private String content;
    private String time;
    private String status;
    private String reason;

    private String userName;

}
