package com.lch.hunter.config;

import com.lch.hunter.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器，并指定拦截的路径
//        registry.addInterceptor(new LoginInterceptor())
//                .addPathPatterns("/require/**")
//                .addPathPatterns("/requireByLater/**")
//                .addPathPatterns("/user/save")
//                .addPathPatterns("/user/forgetPassword")
//                .addPathPatterns("/user/modify")
//                .addPathPatterns("/user/changePassword")
//                .addPathPatterns("/user/delete/**");
    }
}
