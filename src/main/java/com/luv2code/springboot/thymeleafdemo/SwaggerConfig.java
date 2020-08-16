//package com.luv2code.springboot.thymeleafdemo;
//
//import java.util.Collections;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig {
//
//    @Bean
//    public Docket apiDocket(){
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.way2learnonline"))
//                .paths(PathSelectors.any())
//                .build()
//                .apiInfo(getApiInfo());
//    }
//
//    private ApiInfo getApiInfo(){
//        return new ApiInfo(
//                "Cargo App Api",
//                "Api for Cargo App",
//                "1.0.0",
//                "Terms of Service",
//                new Contact("Deepak", "google.com", "deepakp14@hotmail.com"),
//                "",
//                "",
//                Collections.emptyList());
//    }
//
//}
//
