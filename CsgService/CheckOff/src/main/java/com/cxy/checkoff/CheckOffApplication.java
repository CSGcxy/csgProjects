package com.cxy.checkoff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.cxy"}) //添加扫描路径，扫描上一层路径（也包括跨模块组件)
public class CheckOffApplication {
    public static void main(String[] args) {
        SpringApplication.run(CheckOffApplication.class, args);
    }
}
