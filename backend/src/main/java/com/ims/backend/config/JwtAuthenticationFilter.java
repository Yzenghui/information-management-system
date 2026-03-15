package com.ims.backend.config;

import com.ims.backend.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * JWT 认证过滤器。
 * 拦截所有请求，验证 Token 的有效性，并将用户信息存入 Spring Security 上下文。
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,      // HTTP 请求对象（包含请求头、参数等）
            HttpServletResponse response,    // HTTP 响应对象（用于返回错误信息）
            FilterChain filterChain          // 过滤器链（继续执行后续处理）
    ) throws ServletException, IOException {

        // 1. 从请求头中获取 Token
        // 前端发送的格式：Authorization: Bearer {token}
        String token = request.getHeader("Authorization");

        // 2. 处理 Token 格式（去掉 "Bearer " 前缀）
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        // 3. 验证 Token 的有效性
        if (StringUtils.hasText(token) && jwtUtil.validateToken(token)) {
            try {
                // 4. 从 Token 中解析用户信息
                String username = jwtUtil.getUsernameFromToken(token);
                Integer userId = jwtUtil.getUserIdFromToken(token);
                String role = jwtUtil.getRoleFromToken(token);

                // 5. 将用户信息存入 Spring Security 上下文

                // 创建权限列表并添加角色
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                if (role != null && !role.isEmpty()) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
                }

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                username,         //  principal（主体，通常是用户名）
                                null,             //  credentials（凭证，验证通过后不需要了）
                                authorities       //  authorities（权限列表）
                        );

                // 存入上下文，这样在整个请求处理过程中都能获取到当前用户
                // SecurityContextHolder - 全局安全上下文持有者（单例模式，整个应用共享）
                // .getContext()         - 获取当前线程的上下文对象（每个请求独立）
                // .setAuthentication()  - 设置认证信息到上下文中（供后续使用）
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                // Token 解析失败
                response.setStatus(401);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"code\":401,\"message\":\"Token 解析失败\"}");
                return;
            }
        }

        // 6. 继续执行后续过滤器
        filterChain.doFilter(request, response);
    }
}
