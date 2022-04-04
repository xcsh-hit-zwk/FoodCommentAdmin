package com.example.foodcommentadmin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 扫描mapper文件夹
@MapperScan("com.example.foodcomment.mapper")
@SpringBootApplication
public class FoodCommentAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodCommentAdminApplication.class, args);
    }

}
