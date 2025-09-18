package com.atguigu.spring01.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置类
 * 实现WebMvcConfigurer接口，用于配置Spring MVC的拦截器
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * 添加拦截器配置
     * @param registry 拦截器注册器，用于注册拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        // 注册JWT拦截器
        // 拦截所有路径（/**）
        // 排除登录和注册路径，允许这些路径无需JWT验证即可访问
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login","/register","/files/download", "/files/upload");
    }

    /**
     * 创建JWT拦截器Bean
     * @return 返回JWT拦截器实例
     */
    @Bean
    public JWTInterceptor jwtInterceptor(){
        return new JWTInterceptor();
    }
}
