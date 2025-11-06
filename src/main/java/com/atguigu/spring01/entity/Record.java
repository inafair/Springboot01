package com.atguigu.spring01.entity;

import lombok.Data;

@Data
public class Record {
    private Integer id;
    private Integer userId;
    private Integer bookId;
    private String time;
    private String status;
    private String reason;
}
