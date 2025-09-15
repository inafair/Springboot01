package com.atguigu.spring01.entity;

import lombok.Data;

@Data
public class Account {
    private Integer id;
    private String username;
    private String name;
    private String password;
    private String phone;
    private String email;
    private String role;

    //非数据库字段
    private String ids;
    private String[] idsArr;
}
