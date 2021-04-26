/**
 * Created by llj on 2019/10/25 14:18
 */
package com.plus.bysj.account.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        //header中的ticket参数非必填，传空也可以
        ticketPar.name("Authorization").description("Bearer token").defaultValue("Bearer ")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();
        pars.add(ticketPar.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.plus.bysj.account.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars)
                ;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //标题
                .title("接口文档")
                //描述
                .description("接口RestfulDoc")
                //这里配置的是服务网站
                .termsOfServiceUrl("")
                // 三个参数依次是姓名，个人网站，邮箱
                .contact(new Contact("", "", "66666666@qq.com"))
                //版本
                .version("1.0.0")
                .build();
    }
}
