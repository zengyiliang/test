package com.xhg.ops.config;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Slf4j
public class Swagger2Config {


    private static Logger logger = LoggerFactory.getLogger(Swagger2Config.class);
    @Bean
    public Docket createRestApi() {
        logger.info("SwaggerConfig start");
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo()).select()
                //扫描指定包中的swagger注解
                //扫描所有有注解的api，用这种方式更灵活
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("小黄狗-OPS-api")
                .description("ops")
                .termsOfServiceUrl("")
                .version("1.0")
                .build();
    }
}