package com.cxy.baseService.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableOpenApi
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
//                .paths(PathSelectors.regex("/swagger/.*"))
                .build();
    }

    private ApiInfo apiInfo(){

        return new ApiInfoBuilder()
                .title("CSG监控系统API文档")
                .description("本文档描述了CSG监控系统微服务接口定义")
                .version("2.0")
                .contact(new Contact("Cxy", "http://www.cxy.com", "dajiage@sb.com"))
                .build();
    }
}
