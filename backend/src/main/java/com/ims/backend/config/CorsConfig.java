package com.ims.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域配置：允许前端与后端进行联调
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")              // 对所有接口路径启用 CORS
                .allowedOrigins("http://localhost:8081")    // 允许指定前端源
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许的 HTTP 方法
                .allowedHeaders("*")                        // 允许任意请求头
                .allowCredentials(true)                     // 允许携带 Cookie 等凭据
                .maxAge(3600);                              // 预检请求结果缓存 1 小时
    }
}