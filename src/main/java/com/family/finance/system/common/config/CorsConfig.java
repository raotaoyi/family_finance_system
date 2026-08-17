package com.family.finance.system.common.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                //前端地址
                .allowedOrigins("http://localhost:3000")
                //允许全部请求方法，必须包含DELETE、PUT
                .allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
                //允许自定义Token请求头
                .allowedHeaders("*")
                //允许携带header
                .allowCredentials(true)
                .maxAge(3600);
    }
}