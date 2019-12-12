package com.dimples.sys.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/12/12
 */
@Slf4j
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        log.info("==================== 开启Swagger2配置 ====================");
        log.info("=============== 访问网址：IP:Port/doc.html ================");
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("Dimples微服务项目", "http://139.9.220.139:8088", "1126834403@qq.com");
        return new ApiInfoBuilder()
                .title("springboot利用swagger2构建api文档")
                .description("Dimples微服务项目")
                .termsOfServiceUrl("127.0.0.1")
                .contact(contact)
                .version("1.0")
                .build();
    }

}