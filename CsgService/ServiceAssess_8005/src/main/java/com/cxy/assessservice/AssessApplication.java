package com.cxy.assessservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Lishuguang
 * @create 2022-03-23-16:41
 */

@MapperScan("com.cxy.assessservice.mapper")
@ComponentScan(basePackages = {"com.cxy"}) //添加扫描路径，扫描上一层路径（也包括跨模块组件)
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class AssessApplication {
    public static void main(String[] args) {
        SpringApplication.run(AssessApplication.class,args);
    }
}
