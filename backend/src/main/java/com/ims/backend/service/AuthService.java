package com.ims.backend.service;

import com.ims.backend.dto.request.LoginRequest;
import com.ims.backend.dto.request.RegisterRequest;
import com.ims.backend.pojo.User;

/**
 * 认证服务接口。
 * 定义了认证模块对外提供的业务能力，是服务层与控制器层的契约。
 */
public interface AuthService {
    /**
     * 执行用户登录验证。
     *
     * @param loginRequest 包含用户名和密码的登录数据传输对象
     * @return 登录成功返回用户信息（密码字段已清空），失败返回 null
     * @throws RuntimeException 当发生系统错误时
     */
    User login(LoginRequest loginRequest);

    /**
     * 用户注册功能。
     *
     * @param registerRequest 包含用户名、密码等注册信息的数据传输对象
     * @return 注册成功返回用户信息（密码字段已清空），失败返回null
     * @throws RuntimeException 当发生系统错误时
     */
    User register(RegisterRequest registerRequest);

}

