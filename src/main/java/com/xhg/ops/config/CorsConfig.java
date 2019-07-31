package com.xhg.ops.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 跨域设置
 *
 * @author  xjh
 */
@Configuration
public class CorsConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("PUT", "DELETE","GET","POST")
                .allowedHeaders("Accept","Origin","X-Requested-With","Content-Type","Last-Modified")
                .exposedHeaders("Set-Cookie")
                .allowCredentials(true);
    }
}
