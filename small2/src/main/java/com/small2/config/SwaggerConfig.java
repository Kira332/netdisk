package com.small2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo(){

        Contact contact=new Contact("Kira","http://localhost8081/","937241332@qq.com");

        return new ApiInfo(
                "小小网盘的Swagger日志",
                "试试看",
                "1.0",
                "http://localhost8081/",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",new ArrayList<>()
        );
    }
}
