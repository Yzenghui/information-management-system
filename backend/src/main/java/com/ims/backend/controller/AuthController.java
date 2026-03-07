package com.ims.backend.controller;

import com.ims.backend.dto.request.LoginRequest;
import com.ims.backend.dto.request.RegisterRequest;
import com.ims.backend.dto.response.LoginResponse;
import com.ims.backend.dto.response.RegisterResponse;
import com.ims.backend.pojo.Result;
import com.ims.backend.pojo.User;
import com.ims.backend.service.AuthService;
import com.ims.backend.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 用户认证相关的API控制器。
 * 处理所有与认证（登录、注册等）相关的HTTP请求。
 */
@RestController // @Controller 与 @ResponseBody 的组合注解，标记该类所有方法的返回值直接作为 HTTP 响应体
@RequestMapping("/api/auth") // 定义此控制器下所有请求的基路径
public class AuthController {

    // 业务逻辑层服务，由Spring容器提供实现类的实例
    private final AuthService authService;

    // JWT工具类，用于生成和解析令牌
    private final JwtUtil jwtUtil;

    // 采用构造器注入
    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }


    /**
     * 处理用户登录请求。
     * 这是一个典型的POST请求处理流程：接收JSON -> 执行业务 -> 返回JSON。
     *
     * @param loginRequest 前端发送的登录数据（JSON自动转换而来）
     * @return 统一格式的响应结果，包含登录状态和数据
     */
    @PostMapping("/login") // 映射HTTP POST /api/auth/login 请求到本方法
    public Result<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        // 委托Service层进行核心业务逻辑验证（用户名、密码、状态）
        User user = authService.login(loginRequest);
        if (user == null) {
            // 登录失败：返回401状态码和提示信息
            // 注意：这里模糊提示是为了安全，不明确告知是用户名错误还是密码错误
            return Result.error(401, "用户名或密码错误，或账户已被禁用");
        }

        // 登录成功，使用工具类生成JWT令牌（Token）
        // Token中通常包含用户标识（如ID、用户名）和过期时间
        String token = jwtUtil.generateToken(user.getUsername(), user.getId());

        // 构建返回给前端的响应数据
        LoginResponse response = new LoginResponse();
        response.setId(user.getId()); // 用户ID
        response.setUsername(user.getUsername()); // 用户名
        response.setRole(user.getRole()); // 用户角色（权限控制的基础）
        response.setToken(token); // 身份令牌，前端需在后续请求中携带

        // 使用统一成功响应模板返回数据
        return Result.success(response);
    }

    /**
     * 处理用户注册请求。
     * 注册成功后自动生成 JWT 令牌，实现"注册即登录"。
     *
     * @param registerRequest 前端发送的注册数据（JSON 自动转换而来）
     * @return 统一格式的响应结果，包含注册状态和用户信息
     */
    @PostMapping("/register") // 映射 HTTP POST /api/auth/register 请求到本方法
    public Result<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            // 委托 Service 层执行注册逻辑
            User user = authService.register(registerRequest);

            // 业务验证失败（用户名已存在、密码不一致等）
            if (user == null) {
                return Result.error(400, "用户名已被注册或信息填写有误");
            }

            // 注册成功，生成 JWT 令牌（类似登录逻辑）
            String token = jwtUtil.generateToken(user.getUsername(), user.getId());

            // 构建返回给前端的响应数据
            RegisterResponse response = new RegisterResponse();
            response.setId(user.getId());
            response.setUsername(user.getUsername());
            response.setRole(user.getRole());
            response.setToken(token);

            // 使用统一成功响应模板返回数据
            return Result.success(response);

        } catch (RuntimeException e) {
            // 系统级异常（数据库错误等）
            return Result.error(500, e.getMessage());
        }
    }
}
