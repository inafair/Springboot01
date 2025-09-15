package com.atguigu.spring01.entity;

import lombok.Data;

@Data
public class User extends Account {
    private Integer id;
    private String username;
    private String name;
    private String password;
    private String phone;
    private String email;
    private String role;
    private String token;

    //非数据库字段
    private String ids;
    private String[] idsArr;
}
