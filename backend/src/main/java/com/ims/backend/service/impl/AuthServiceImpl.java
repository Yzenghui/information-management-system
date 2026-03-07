package com.ims.backend.service.impl;

import com.ims.backend.dto.request.LoginRequest;
import com.ims.backend.dto.request.RegisterRequest;
import com.ims.backend.mapper.UserMapper;
import com.ims.backend.pojo.User;
import com.ims.backend.service.AuthService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 认证服务的具体实现类。
 * 使用构造器注入，是当前Spring官方推荐的最佳实践。
 */
@Service // 标识为Spring的业务层组件
public class AuthServiceImpl implements AuthService {

    // 依赖声明为final，确保它们在被注入后不可变，更安全
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * 构造器注入：Spring会自动将容器中对应的Bean传入。
     * 这是推荐的依赖注入方式，它使依赖关系明确，且便于测试。
     *
     * @param userMapper 用户数据访问接口
     * @param passwordEncoder 密码加密验证器（来自SecurityConfig的Bean）
     */
    public AuthServiceImpl(UserMapper userMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User login(LoginRequest loginRequest) {
        try {
            // 1. 根据用户名查询用户
            User user = userMapper.findByUsername(loginRequest.getUsername());
            if (user == null) {
                return null;
            }
            // 2. 验证用户状态
            if (user.getStatus() != null && user.getStatus() != 1) {
                return null;
            }
            // 3. 验证密码（使用注入的passwordEncoder）
            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                user.setPassword(null); // 安全：移除敏感信息
                return user;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException("登录验证过程中发生系统错误：" + e.getMessage());
        }
    }

    @Override
    public User register(RegisterRequest request) {
        try {
            // 1. 检查用户名是否存在
            User existingUser = userMapper.findByUsername(request.getUsername());
            if (existingUser != null) {
                return null;
            }

            // 2. 创建用户对象
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole("USER");
            user.setStatus(1);
            user.setCreateTime(LocalDateTime.now());
            user.setUpdateTime(LocalDateTime.now());

            // 3. 保存并验证
            int result = userMapper.insertUser(user);  // ← 返回1表示成功
            if (result == 1) {
                user.setPassword(null);
                return user;  // ← 直接返回我们创建的user对象
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("注册过程中发生系统错误：" + e.getMessage());
        }
    }

}