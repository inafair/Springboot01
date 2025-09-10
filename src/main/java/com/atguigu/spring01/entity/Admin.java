package com.atguigu.spring01.entity;

import lombok.Data;
import org.springframework.context.annotation.Bean;

/**
 * 管理员信息实体类
 */
@Data
public class Admin {
   private Integer id;
   private String username;
   private String name;
   private String password;
   private String phone;
   private String email;

   //非数据库字段
   private String ids;
   private String[] idsArr;

}
