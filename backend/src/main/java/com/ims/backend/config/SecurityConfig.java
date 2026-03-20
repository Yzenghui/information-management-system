package com.ims.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
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
                // 1. 关闭 CSRF（API 项目必需）
                .csrf(AbstractHttpConfigurer::disable)

                // 2. 启用 CORS（使用 CorsConfig 的 Bean 配置）
                .cors(Customizer.withDefaults())

                // 3. 配置请求授权
                .authorizeHttpRequests(auth -> auth
                        // 登录和注册 API - 必须放行
                        .requestMatchers("/api/auth/login", "/api/auth/register").permitAll()

                        // 静态资源 - 完整放行
                        .requestMatchers(
                                "/*.js", "/*.css", "/*.png", "/*.jpg", "/*.ico",
                                "/static/**", "/assets/**", "/css/**", "/js/**", "/fonts/**",
                                "/index.html", "/favicon.ico"
                        ).permitAll()

                        // 前端路由 - 放行所有非 API 路径
                        // 这样前端 SPA 路由才能访问 index.html
                        .requestMatchers(
                                "/",
                                "/login",
                                "/register",
                                "/browse",
                                "/query",
                                "/add",
                                "/delete",
                                "/profile"
                        ).permitAll()

                        // 管理接口 - 需要 ADMIN 角色
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // 教师接口 - 按方法区分权限
                        .requestMatchers(HttpMethod.GET, "/api/teacher/**").hasAnyRole("TEACHER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/teacher/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/teacher/**").hasRole("ADMIN")

                        // 学生接口 - 按方法区分权限
                        .requestMatchers(HttpMethod.GET, "/api/student/**").hasAnyRole("STUDENT", "TEACHER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/student/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/student/**").hasRole("ADMIN")

                        // 搜索接口 - 需要认证
                        .requestMatchers("/api/search/**").authenticated()

                        // 其他所有 API 请求需要认证
                        .anyRequest().authenticated()
                )

                // 4. 禁用 HTTP Basic 和表单登录（使用 JWT）
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)

                // 5. 无状态会话（JWT 不需要 session）
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                org.springframework.security.config.http.SessionCreationPolicy.STATELESS))

                // 6. 添加 JWT 过滤器
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * 前端路由处理
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 匹配所有不包含点的路径（排除静态资源）
        registry.addViewController("/{path:[^\\.]*}")
                // 转发到 index.html，由 Vue Router 渲染对应组件
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