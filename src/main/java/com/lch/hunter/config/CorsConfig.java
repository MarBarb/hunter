package com.lch.hunter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") //允许跨域访问的路径
                .allowedOrigins("*") //修改跨域访问的端口
                .allowedMethods("POST","GET","PUT","OPTIONS","DELETE")  //允许请求方法
                .maxAge(168000)  //预检间隔时间
                .allowedHeaders("*")  //允许头部发送
                .allowCredentials(false); //是否发送cookie
    }
}
