package com.xhg.ops;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = {"com.xhg"})
@EnableCaching(proxyTargetClass = true) // 开启缓存功能
@EnableAsync
@MapperScan({"com.xhg.**.dao","com.xhg.**.mapper"})
public class OpsApplication extends SpringBootServletInitializer implements CommandLineRunner {
    // 入口
    public static void main(String[] args) {
        SpringApplication.run(OpsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("springboot启动完成！");
    }

}