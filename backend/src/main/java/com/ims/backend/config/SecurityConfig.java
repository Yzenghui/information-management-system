package com.ims.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration // 声明这是一个Spring配置类
@EnableWebSecurity // 启用Spring Security的Web安全功能，并允许自定义配置
public class SecurityConfig {

    /**
     * 定义安全过滤链，这是配置Spring Security规则的核心方法。
     * 它决定了哪些请求需要认证、哪些可以直接访问。
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 配置URL的访问权限规则
                .authorizeHttpRequests(auth -> auth
                        // 规则1：登录接口无需认证，允许所有人访问
                        .requestMatchers("/api/auth/login").permitAll()
                        // 规则2：目前所有其他请求也无需认证（开发阶段方便测试）
                        .anyRequest().permitAll()
                        //正式使用时改为： .anyRequest().authenticated()
                )
                // 关闭CSRF防护，对于使用Token的无状态REST API是标准做法
                .csrf(AbstractHttpConfigurer::disable)
                // 禁用HTTP Basic认证，避免浏览器弹出默认登录框
                .httpBasic(AbstractHttpConfigurer::disable)
                // 禁用Spring Security默认的表单登录页面
                .formLogin(AbstractHttpConfigurer::disable);
        return http.build();
    }

    /**
     * 提供BCrypt密码加密器Bean。
     * 这是引入security依赖的主要目的之一，用于在Service层加密和验证密码。
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 提供一个空的UserDetailsService实现。
     * 这是一个巧妙的"占位符"，主要目的：
     * 1. 防止Spring Security自动配置生成随机密码用户。
     * 2. 因为已禁用默认登录方式(formLogin/httpBasic)，这个服务实际上不会被调用。
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            // 永远"找不到用户"，但因为不用默认认证流程，所以这行永远不会执行
            throw new UsernameNotFoundException("User not found (SecurityConfig placeholder)");
        };
    }
}