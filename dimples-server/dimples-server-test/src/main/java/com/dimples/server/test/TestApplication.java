package com.dimples.server.test;

import com.dimples.common.annotation.EnableAuthExceptionHandler;
import com.dimples.common.annotation.EnableAuthFeignClient;
import com.dimples.common.annotation.EnableServerProtect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/12/30
 */
@EnableAuthExceptionHandler
@EnableAuthFeignClient
@EnableServerProtect
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@Slf4j
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
        log.info("======================================= 程序启动成功 =======================================");
    }
}

















