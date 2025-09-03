package com.atguigu.spring01.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域配置类
 * 用于配置跨域资源共享(CORS)的策略
 */
@Configuration
public class CorsConfig {
    /**
     * 创建并配置跨域过滤器(CorsFilter)的Bean
     * @return 配置好的CorsFilter实例
     */
    @Bean
    public CorsFilter corsFilter() {
        // 创建CorsConfiguration对象，用于配置跨域策略
        CorsConfiguration config = new CorsConfiguration();

        // 设置允许的源，这里设置为允许所有源
        // 使用addAllowedOriginPattern而不是addAllowedOrigin，因为addAllowedOriginPattern支持通配符
        config.addAllowedOriginPattern("*");

        // 设置允许的HTTP方法，这里设置为允许所有方法
        // 包括GET, POST, PUT, DELETE等HTTP方法
        config.addAllowedMethod("*");

        // 设置允许的头部信息，这里设置为允许所有头部
        // 允许前端在请求中携带自定义的头部信息
        config.addAllowedHeader("*");

        // 创建URL-based的跨域配置源
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // 设置要拦截的路径，这里设置为拦截所有路径
        // 表示拦截所有路径，即所有API请求都会应用此跨域配置
        source.registerCorsConfiguration("/**", config);

        // 返回CorsFilter对象
        return new CorsFilter(source);
    }
}
