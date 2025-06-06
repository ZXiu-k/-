package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.interceptor.AdminInterceptor;
import com.example.interceptor.LoginInterceptor;

@Configuration // 配置类
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Autowired
    private AdminInterceptor adminInterceptor;

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**") // 拦截所有请求
                .excludePathPatterns("/login"); // 排除请求
        registry.addInterceptor(adminInterceptor).addPathPatterns("/admin/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
