package com.cxy.checkservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.cxy.checkservice.mapper")
@ComponentScan(basePackages = {"com.cxy"}) //添加扫描路径，扫描上一层路径（也包括跨模块组件)
@SpringBootApplication
public class CheckApplication {
    public static void main(String[] args) {
        SpringApplication.run(CheckApplication.class,args);
    }
}
