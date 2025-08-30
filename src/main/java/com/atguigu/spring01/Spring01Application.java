package com.atguigu.spring01;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.atguigu.spring01.mapper")
public class Spring01Application {

    public static void main(String[] args) {
        SpringApplication.run(Spring01Application.class, args);
    }

}
