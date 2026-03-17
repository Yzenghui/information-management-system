package com.ims.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring Security 配置类。
 * 用于定义安全策略和认证规则。
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // 放行所有静态资源
                        .requestMatchers(
                                "/*.js", "/*.css", "/*.png", "/*.jpg", "/*.ico",
                                "/static/**", "/assets/**", "/css/**", "/js/**", "/fonts/**"
                        ).permitAll()
                        // 放行登录和注册API
                        .requestMatchers("/api/auth/login", "/api/auth/register").permitAll()
                        // 放行前端路由（关键：允许所有路径访问，但不认证）
                        .requestMatchers("/", "/login", "/register", "/**").permitAll()
                        // 其他API权限控制
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/user/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/teacher/**").hasAnyRole("TEACHER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/teacher/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/teacher/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/student/**").hasAnyRole("STUDENT", "TEACHER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/student/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/student/**").hasRole("ADMIN")
                        .requestMatchers("/api/search/**").authenticated()
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/", "/**", "/api/**")
                )
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(org.springframework.security.config.http.SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * 前端路由处理：匹配所有不包含点的路径（即排除静态资源）
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/{path:[^\\.]*}")
                .setViewName("forward:/index.html");
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