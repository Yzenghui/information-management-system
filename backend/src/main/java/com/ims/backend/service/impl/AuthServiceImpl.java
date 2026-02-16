package com.ims.backend.service.impl;

import com.ims.backend.dto.LoginDTO;
import com.ims.backend.mapper.UserMapper;
import com.ims.backend.pojo.User;
import com.ims.backend.service.AuthService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
    public User login(LoginDTO loginDTO) {
        // 1. 根据用户名查询用户
        User user = userMapper.findByUsername(loginDTO.getUsername());
        if (user == null) {
            return null;
        }
        // 2. 验证用户状态
        if (user.getStatus() != null && user.getStatus() != 1) {
            return null;
        }
        // 3. 验证密码（使用注入的passwordEncoder）
        if (passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            user.setPassword(null); // 安全：移除敏感信息
            return user;
        } else {
            return null;
        }
    }
}