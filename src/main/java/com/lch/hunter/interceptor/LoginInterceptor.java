package com.lch.hunter.interceptor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 检查是否是HandlerMethod
        if (handler instanceof HandlerMethod handlerMethod) {

            // 检查方法上是否有 @PostMapping 注解
            if (handlerMethod.hasMethodAnnotation(PostMapping.class)) {
                Object user = request.getSession().getAttribute("user");
                if (user == null) {
                    // 用户未登录，拦截请求
                    System.out.println("拦截！！！");
                    //response.sendRedirect("/login"); // 重定向到登录页面
                    return false; // 请求被拦截
                }
            }
        }

        // 如果不是 @PostMapping 请求或用户已登录，放行请求
        return true;
    }

    // 其他方法保持不变
}
