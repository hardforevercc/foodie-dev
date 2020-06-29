package com.imooc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2 {
    //http:localhost:8088/swagger-ui.html 原路径
    //http:localhost:8088/doc.html 换肤
    //配置swagger2核心配置
    @Bean
    public Docket createRestApi(){
        return  new Docket(DocumentationType.SWAGGER_2) //指定API类型为swagger2
                    .apiInfo(apiInfo()) //定义API文档汇总信息
                .select().apis(RequestHandlerSelectors.basePackage("com.imooc.controller")) //扫描controller
                .paths(PathSelectors.any()) //所有controller
                .build();

    }



    public ApiInfo apiInfo() {
        return new ApiInfoBuilder().
                title("天天吃货API文档") //文档页标题
                .contact(new Contact("hello","www.11.com","123@163.com")) //联系人信息
                .description("天天吃货开发接口API") //详细信息
                .version("1.0.1") //版本号
                .termsOfServiceUrl("https://www.111.com") //网站地址
                .build();
    }
}
