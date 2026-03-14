package com.ims.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 配置类。
 * 用于定义安全策略和认证规则。
 */
@Configuration // 声明这是一个 Spring 配置类
@EnableWebSecurity // 启用 Spring Security 的 Web 安全功能，并允许自定义配置
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * 定义安全过滤链，这是配置 Spring Security 规则的核心方法。
     * 它决定了哪些请求需要认证、哪些可以直接访问。
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 配置 URL 的访问权限规则
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login", "/api/auth/register").permitAll()

                        // 管理员专属接口
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // 教师和管理员可访问
                        .requestMatchers("/api/teacher/**").hasAnyRole("TEACHER", "ADMIN")

                        // 学生、教师、管理员都可访问
                        .requestMatchers("/api/student/**").hasAnyRole("STUDENT","TEACHER", "ADMIN")
                        .requestMatchers("/api/user/**").authenticated()

                        // 其他所有请求需要认证
                        .anyRequest().authenticated()
                )
                // 关闭 CSRF 防护，对于使用 Token 的无状态 REST API 是标准做法
                .csrf(AbstractHttpConfigurer::disable)
                // 禁用 HTTP Basic 认证，避免浏览器弹出默认登录框
                .httpBasic(AbstractHttpConfigurer::disable)
                // 禁用 Spring Security 默认的表单登录页面
                .formLogin(AbstractHttpConfigurer::disable)
                // 禁用会话管理，使用无状态的 JWT 认证
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 添加 JWT 过滤器到 UsernamePasswordAuthenticationFilter 之前
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * 提供 BCrypt 密码加密器 Bean。
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 提供一个空的 UserDetailsService 实现。
     */
    @Bean
    public org.springframework.security.core.userdetails.UserDetailsService userDetailsService() {
        return username -> {
            throw new org.springframework.security.core.userdetails.UsernameNotFoundException(
                    "User not found (SecurityConfig placeholder)"
            );
        };
    }
}
